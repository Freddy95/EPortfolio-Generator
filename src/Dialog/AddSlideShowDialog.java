package Dialog;

import Components.Component;
import Components.Slide;
import Components.SlideShowComponent;
import Controller.SelectionController;
import Page.Page;
import View.EPortfolioGeneratorView;
import View.SlideEditView;
import java.io.File;
import java.net.URL;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Freddy Estevez
 */
public class AddSlideShowDialog {

    Stage window;
    Scene scene;
    Label labelTitle;
    TextField slideShowTitle;
    Button okBtn;
    Button addSlide;
    HBox btns;
    Button removeSlide;
    Button moveUpButton;
    Button moveDownButton;
    VBox moveButtons;
    VBox slideEditorPane;
    ScrollPane scrollPane;
    EPortfolioGeneratorView ui;
    Page page;
    File file;

    public AddSlideShowDialog(Page p, EPortfolioGeneratorView initUi) {
        page = p;
        ui = initUi;
    }

    public void initDisplay() {
        btns = new HBox(15);
        slideEditorPane = new VBox(15);
        HBox pane = new HBox(30);
        scrollPane = new ScrollPane(slideEditorPane);
        slideEditorPane.setMinHeight(200);
        VBox layout = new VBox(20);
        okBtn = new Button("OK");

        addSlide = new Button("Add Slide");
        removeSlide = new Button("Remove Slide");
        moveUpButton = new Button();
        moveDownButton = new Button();
        Image up = new Image("file:icons/upArrow.png");
        Image down = new Image("file:icons/downArrow.png");
        moveUpButton.setGraphic(new ImageView(up));
        moveDownButton.setGraphic(new ImageView(down));
        moveButtons = new VBox(20);
        moveButtons.getChildren().addAll(moveUpButton, moveDownButton);
        scrollPane.setMinWidth(500);
        pane.getChildren().addAll(scrollPane, moveButtons);
        pane.getStyleClass().add("center");
        labelTitle = new Label("Enter Slide Show Title");
        slideShowTitle = new TextField();
        layout.setPadding(new Insets(15, 15, 15, 15));
        btns.getChildren().addAll(addSlide, removeSlide);
        btns.getStyleClass().add("dialogButton");
        layout.getChildren().addAll(labelTitle, slideShowTitle, pane, btns, okBtn);
        scene = new Scene(layout, 650, 600);
        window = new Stage();
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialog");
    }

    public void initHandlers() {
        addSlide.setOnAction(e -> {
            SelectionController c = new SelectionController();
            file = c.processSelectImage();
            if (file != null) {
                try {
                    // GET AND SET THE IMAGE
                    SlideEditView v = new SlideEditView(file, slideEditorPane, this);
                    for (int i = 0; i < slideEditorPane.getChildren().size(); i++) {
                        SlideEditView x = (SlideEditView) slideEditorPane.getChildren().get(i);
                        x.deselect();
                    }
                    v.select();
                    removeSlide.setDisable(false);
                    if (slideEditorPane.getChildren().size() > 1) {
                        moveUpButton.setDisable(false);
                    }
                    okBtn.setDisable(false);
                } catch (Exception a) {
                    // @todo - use Error handler to respond to missing image
                }

            }
        });

        removeSlide.setOnAction(e -> {
            for (int i = 0; i < slideEditorPane.getChildren().size(); i++) {
                SlideEditView x = (SlideEditView) slideEditorPane.getChildren().get(i);
                if (x.isSelected()) {
                    slideEditorPane.getChildren().remove(i);
                    if (slideEditorPane.getChildren().size() == 0) {
                        removeSlide.setDisable(true);
                        okBtn.setDisable(true);
                    } else if (i == slideEditorPane.getChildren().size()) {
                        SlideEditView y = (SlideEditView) slideEditorPane.getChildren().get(i - 1);
                        y.select();
                    } else {
                        SlideEditView y = (SlideEditView) slideEditorPane.getChildren().get(i);
                        y.select();
                    }

                    break;
                }
            }
        });

        okBtn.setOnAction(e -> {
            SlideShowComponent slideShow = new SlideShowComponent(slideShowTitle.getText());
            for (int i = 0; i < slideEditorPane.getChildren().size(); i++) {
                SlideEditView v = (SlideEditView) slideEditorPane.getChildren().get(i);
                Slide slide = new Slide();
                slide.setCaption(v.getCaption());
                slide.setPath(v.getImageFilePath());
                slide.setFileName(v.getImageFileName());
                slideShow.addSlide(slide);
            }

            page.addComponent(slideShow);
            select(slideShow);
            ui.reloadPane();
            window.close();
        });

        moveUpButton.setOnAction(e -> {
            for (int i = 1; i < slideEditorPane.getChildren().size(); i++) {
                SlideEditView v = (SlideEditView) slideEditorPane.getChildren().get(i);
                if (v.isSelected()) {
                    SlideEditView u = (SlideEditView) slideEditorPane.getChildren().get(i - 1);
                    slideEditorPane.getChildren().remove(i-1);
                    slideEditorPane.getChildren().add(i, u);
                   
                    if ((i - 1) == 0) {
                        moveUpButton.setDisable(true);
                    }
                    moveDownButton.setDisable(false);
                }
            }
        });
        moveDownButton.setOnAction(e -> {
            for (int i = 0; i < slideEditorPane.getChildren().size()-1; i++) {
                SlideEditView v = (SlideEditView) slideEditorPane.getChildren().get(i);
                if (v.isSelected()) {
                    SlideEditView u = (SlideEditView) slideEditorPane.getChildren().get(i + 1);
                    slideEditorPane.getChildren().remove(i+1);
                    slideEditorPane.getChildren().add(i, u);
                    if ((i + 1) == slideEditorPane.getChildren().size() - 1) {
                        moveDownButton.setDisable(true);
                    }
                    moveUpButton.setDisable(false);
                }
            }
        });

    }

    public void display() {
        initDisplay();
        window.setTitle("Add Slide Show");
        okBtn.setDisable(true);
        removeSlide.setDisable(true);
        moveUpButton.setDisable(true);
        moveDownButton.setDisable(true);
        initHandlers();

        window.showAndWait();

    }

    public void editDisplay(SlideShowComponent slideShow) {
        initDisplay();
        window.setTitle("Edit Slide Show");

        if (slideShow.getSlides().size() > 1) {
            moveDownButton.setDisable(false);
        } else {
            moveDownButton.setDisable(true);
        }

        moveUpButton.setDisable(true);
        slideShowTitle.setText(slideShow.getTitle());
        for (int i = 0; i < slideShow.getSlides().size(); i++) {
            Slide s = slideShow.getSlides().get(i);

            file = new File(s.getPath());

            SlideEditView v = new SlideEditView(file, slideEditorPane, this);
            v.setCaption(s.getCaption());
            if (i == 0) {
                v.select();
            }
        }
        okBtn.setDisable(false);
        removeSlide.setDisable(false);
        initEditHandlers(slideShow);
        window.showAndWait();
    }

    public void initEditHandlers(SlideShowComponent slideShow) {
        addSlide.setOnAction(e -> {
            SelectionController c = new SelectionController();
            file = c.processSelectImage();
            if (file != null) {
                try {
                    // GET AND SET THE IMAGE
                    SlideEditView v = new SlideEditView(file, slideEditorPane, this);
                    for (int i = 0; i < slideEditorPane.getChildren().size(); i++) {
                        SlideEditView x = (SlideEditView) slideEditorPane.getChildren().get(i);
                        x.deselect();
                    }
                    v.select();
                    removeSlide.setDisable(false);
                    okBtn.setDisable(false);
                } catch (Exception a) {
                    // @todo - use Error handler to respond to missing image
                }

            }
        });

        removeSlide.setOnAction(e -> {
            for (int i = 0; i < slideEditorPane.getChildren().size(); i++) {
                SlideEditView x = (SlideEditView) slideEditorPane.getChildren().get(i);
                if (x.isSelected()) {
                    slideEditorPane.getChildren().remove(i);
                    if (slideEditorPane.getChildren().size() == 0) {
                        removeSlide.setDisable(true);
                        okBtn.setDisable(true);
                    } else if (i == slideEditorPane.getChildren().size()) {
                        SlideEditView y = (SlideEditView) slideEditorPane.getChildren().get(i - 1);
                        y.select();
                    } else {
                        SlideEditView y = (SlideEditView) slideEditorPane.getChildren().get(i);
                        y.select();
                    }

                    break;
                }
            }
        });

        okBtn.setOnAction(e -> {
            //remove all current slides
            slideShow.getSlides().clear();
            slideShow.setTitle(slideShowTitle.getText());
            //add slides
            for (int i = 0; i < slideEditorPane.getChildren().size(); i++) {
                SlideEditView v = (SlideEditView) slideEditorPane.getChildren().get(i);
                Slide slide = new Slide();
                slide.setCaption(v.getCaption());
                slide.setPath(v.getImageFilePath());
                slide.setFileName(v.getImageFileName());
                slideShow.addSlide(slide);
            }
            select(slideShow);
            ui.reloadPane();
            window.close();
        });

      moveUpButton.setOnAction(e -> {
            for (int i = 1; i < slideEditorPane.getChildren().size(); i++) {
                SlideEditView v = (SlideEditView) slideEditorPane.getChildren().get(i);
                if (v.isSelected()) {
                    SlideEditView u = (SlideEditView) slideEditorPane.getChildren().get(i - 1);
                    slideEditorPane.getChildren().remove(i-1);
                    slideEditorPane.getChildren().add(i, u);
                   
                    if ((i - 1) == 0) {
                        moveUpButton.setDisable(true);
                    }
                    moveDownButton.setDisable(false);
                }
            }
        });
        moveDownButton.setOnAction(e -> {
            for (int i = 0; i < slideEditorPane.getChildren().size()-1; i++) {
                SlideEditView v = (SlideEditView) slideEditorPane.getChildren().get(i);
                if (v.isSelected()) {
                    SlideEditView u = (SlideEditView) slideEditorPane.getChildren().get(i + 1);
                    slideEditorPane.getChildren().remove(i+1);
                    slideEditorPane.getChildren().add(i, u);
                    if ((i + 1) == slideEditorPane.getChildren().size() - 1) {
                        moveDownButton.setDisable(true);
                    }
                    moveUpButton.setDisable(false);
                }
            }
        });
    }

    public void updateButtons() {
        int size = slideEditorPane.getChildren().size();
        if (size == 1) {
            moveUpButton.setDisable(true);
            moveDownButton.setDisable(true);
            return;
        }

        for (int i = 0; i < size; i++) {
            SlideEditView v = (SlideEditView) slideEditorPane.getChildren().get(i);
            if (v.isSelected()) {
                if (i == (slideEditorPane.getChildren().size() - 1)) {
                    moveDownButton.setDisable(true);
                    moveUpButton.setDisable(false);
                } else if (i == 0) {
                    moveDownButton.setDisable(false);
                    moveUpButton.setDisable(true);
                } else {
                    moveDownButton.setDisable(false);
                    moveUpButton.setDisable(false);
                }
            }
        }
    }
    
     public void select(Component c){
        List<Component> views = page.getComponents();
            for(int i = 0; i < views.size(); i++){
                views.get(i).setSelected(false);   
            }
          c.setSelected(true);
    }
}
