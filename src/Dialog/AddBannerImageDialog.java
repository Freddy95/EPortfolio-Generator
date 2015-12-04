package Dialog;

import Components.Component;
import Controller.SelectionController;
import Page.Page;
import View.EPortfolioGeneratorView;
import java.io.File;
import java.net.URL;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Freddy Estevez
 */
public class AddBannerImageDialog {

    Stage window;
    Scene scene;
    Label labelParagraph;
    Button okBtn;
    Button addImage;
    Page page;
    EPortfolioGeneratorView ui;
    Image image;

    public AddBannerImageDialog(Page p, EPortfolioGeneratorView view) {
        page = p;
        ui = view;
    }

    public void display(String title) {

        ImageView view = new ImageView();
        image = new Image("file:image.jpg");
        view.setImage(image);
        view.setFitWidth(300);
        view.setPreserveRatio(true);
        view.setSmooth(true);
        view.setCache(true);
        okBtn = new Button("OK");
        addImage = new Button("Add Image");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(view, addImage, okBtn);
        scene = new Scene(layout, 400, 400);
        window = new Stage();
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialogImage");
        window.show();
        addImage.setOnAction(e -> {
            SelectionController c = new SelectionController();
            File file = c.processSelectImage();
            if (file != null) {
                try {
                    // GET AND SET THE IMAGE
                    URL fileURL = file.toURI().toURL();
                    image = new Image(fileURL.toExternalForm());
                    view.setImage(image);
                    page.setBannerImagePath(file.getPath());

                } catch (Exception a) {
                    // @todo - use Error handler to respond to missing image
                }

            }
        });
    }
    
   

    public Button getButton() {
        return okBtn;
    }

    public Image getImage() {
        return image;
    }

    public void close() {
        window.close();
    }
    
    
}
