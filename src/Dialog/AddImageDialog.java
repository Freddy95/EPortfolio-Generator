package Dialog;

import Components.ImageComponent;
import Controller.ImageSelectionController;
import Page.Page;
import View.EPortfolioGeneratorView;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class is used to present the user with a dialog to add an image to their
 * page he/she is currently editing.
 *
 * @author Freddy Estevez
 */
public class AddImageDialog {

    Stage window;
    Scene scene;
    Label labelCaption;
    Label labelParagraph;
    TextField caption;
    Label labelWidth;
    Label labelHeight;
    ChoiceBox<String> position;
    TextField width;
    TextField height;
    Button okBtn;
    Button addImage;
    Page page;
    Image image;
    ImageView view;
    File file;
    EPortfolioGeneratorView ui;

    VBox sizing;
    HBox mainContent;
    HBox tools;

    public AddImageDialog(Page p, EPortfolioGeneratorView initUi) {
        page = p;
        ui = initUi;

        position = new ChoiceBox<>();
        position.getItems().add("Left");
        position.getItems().add("Center");
        position.getItems().add("Right");

        labelWidth = new Label("Set Width");
        labelHeight = new Label("Set Height");
        width = new TextField();
        height = new TextField();
        labelCaption = new Label("Enter Caption:");
        caption = new TextField();
        caption.setPrefWidth(200);

        image = new Image("file:image.jpg");
        file = new File("image.jpg");
        okBtn = new Button("OK");
        addImage = new Button("Add Image");
        view = new ImageView();
        sizing = new VBox(10);
        mainContent = new HBox(15);
        sizing.getChildren().addAll(labelWidth, width, labelHeight, height);
        mainContent.getChildren().addAll(view, sizing);

        tools = new HBox(15);
        position.setValue("Center");
        tools.getChildren().addAll(addImage, position, okBtn);
    }

    public void display(String title) {

        view.setImage(image);
        view.setFitWidth(300);
        view.setPreserveRatio(true);
        view.setSmooth(true);
        view.setCache(true);

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(labelCaption, caption, mainContent, tools);
        scene = new Scene(layout, 500, 500);
        window = new Stage();
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialogImage");

        addImage.setOnAction(e -> {
            ImageSelectionController c = new ImageSelectionController();
            file = c.processSelectImage();
            if (file != null) {
                try {
                    // GET AND SET THE IMAGE
                    URL fileURL = file.toURI().toURL();
                    image = new Image(fileURL.toExternalForm());
                    view.setImage(image);

                } catch (Exception a) {
                    // @todo - use Error handler to respond to missing image
                }

            }
        });

        okBtn.setOnAction(a -> {
            addImage();
            ui.reloadPane();
            close();
        });
        window.showAndWait();
    }

    public void editDisplay(ImageComponent imageComponent) {
        initEditDisplay(imageComponent);
        view.setFitWidth(300);
        view.setPreserveRatio(true);
        view.setSmooth(true);
        view.setCache(true);
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(labelCaption, caption, mainContent, tools);
        scene = new Scene(layout, 500, 500);
        window = new Stage();
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialogImage");

        addImage.setOnAction(e -> {
            ImageSelectionController c = new ImageSelectionController();
            file = c.processSelectImage();
            if (file != null) {
                try {
                    // GET AND SET THE IMAGE
                    URL fileURL = file.toURI().toURL();
                    image = new Image(fileURL.toExternalForm());
                    view.setImage(image);

                } catch (Exception a) {
                    // @todo - use Error handler to respond to missing image
                }

            }
        });

        okBtn.setOnAction(a -> {
            URL url;
            try {
                url = file.toURI().toURL();

                imageComponent.setCaption(caption.getText());
                imageComponent.setFileName(file.getName());
                imageComponent.setPosition(position.getValue());
                imageComponent.setPath(file.getPath());
                imageComponent.setWidth(width.getText());
                imageComponent.setHeight(height.getText());
                ui.reloadPane();
            } catch (MalformedURLException ex) {
                Logger.getLogger(AddImageDialog.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ERROR 1");
            }
            close();
        });
        window.showAndWait();

    }

    public void initEditDisplay(ImageComponent imageComponent) {
        caption.setText(imageComponent.getCaption());
        position.setValue(imageComponent.getPosition());
        file = new File(imageComponent.getPath());
        URL url;
        try {
            url = file.toURI().toURL();

            image = new Image(url.toExternalForm());
            view.setImage(image);
        } catch (MalformedURLException ex) {
            Logger.getLogger(AddImageDialog.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR");
        }

    }

    public Button getButton() {
        return okBtn;
    }

    public Image getImage() {
        return image;
    }

    public void addImage() {
        URL url;
        try {
            url = file.toURI().toURL();

            ImageComponent comp = new ImageComponent(caption.getText(), file.getPath(), file.getName(),
                    position.getValue(), width.getText(), height.getText());

            System.out.println("URL: " + url.toExternalForm());
            page.addComponent(comp);

        } catch (Exception ex) {
            Logger.getLogger(AddImageDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        window.close();
    }
}
