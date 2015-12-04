package Dialog;

import Components.Slide;
import Components.SlideShowComponent;
import Controller.SelectionController;
import Page.Page;
import View.EPortfolioGeneratorView;
import View.SlideEditView;
import java.io.File;
import java.net.URL;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
    VBox slideEditorPane;
    ScrollPane scrollPane;
    EPortfolioGeneratorView ui;
    Page page;
    File file;
    
   public AddSlideShowDialog(Page p, EPortfolioGeneratorView initUi){
       page = p;
       ui = initUi;
   } 
   
    public void display() {
        btns = new HBox(15);
        slideEditorPane = new VBox(15);
        scrollPane = new ScrollPane(slideEditorPane);
        slideEditorPane.setMinHeight(200);
        VBox layout = new VBox(20);
        okBtn = new Button("OK");
        okBtn.setDisable(true);
        addSlide = new Button("Add Slide");
        removeSlide = new Button("Remove Slide");
        labelTitle = new Label("Enter Slide Show Title");
        slideShowTitle = new TextField();
        layout.setPadding(new Insets(15, 15, 15, 15));
        btns.getChildren().addAll(addSlide, removeSlide);
        btns.getStyleClass().add("dialogButton");
        layout.getChildren().addAll(labelTitle, slideShowTitle, scrollPane, btns, okBtn);
        scene = new Scene(layout, 550, 600);
        window = new Stage();
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialog");
        window.setTitle("Add Slide Show");

        removeSlide.setDisable(true);
        

        addSlide.setOnAction(e -> {
            SelectionController c = new SelectionController();
            file = c.processSelectImage();
            if (file != null) {
                try {
                    // GET AND SET THE IMAGE
                    SlideEditView v = new SlideEditView(file, slideEditorPane);
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
                if(x.isSelected()){
                    slideEditorPane.getChildren().remove(i);
                    if(slideEditorPane.getChildren().size() == 0){
                        removeSlide.setDisable(true);
                        okBtn.setDisable(true);
                    }
                    else if(i == slideEditorPane.getChildren().size()){
                        SlideEditView y = (SlideEditView) slideEditorPane.getChildren().get(i-1);
                        y.select();
                    }else{
                        SlideEditView y = (SlideEditView) slideEditorPane.getChildren().get(i);
                        y.select();
                    }
                               
                    break;
                }
            }
        });
        
        okBtn.setOnAction(e -> {
            SlideShowComponent slideShow = new SlideShowComponent(slideShowTitle.getText());
            for(int i = 0; i < slideEditorPane.getChildren().size(); i++){
                SlideEditView v = (SlideEditView) slideEditorPane.getChildren().get(i);
                Slide slide = new Slide();
                slide.setCaption(v.getCaption());
                slide.setPath(v.getImageFilePath());
                slide.setFileName(v.getImageFileName());
                slideShow.addSlide(slide);
            }
            
            page.addComponent(slideShow);
            ui.reloadPane();
            window.close();
        });
        
        window.showAndWait();

    }

}
