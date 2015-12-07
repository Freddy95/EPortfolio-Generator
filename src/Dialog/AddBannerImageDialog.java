package Dialog;

import Components.Component;
import Controller.SelectionController;
import Page.Page;
import View.EPortfolioGeneratorView;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    String path;
    File f;
    URL fileURL;
    public AddBannerImageDialog(Page p, EPortfolioGeneratorView view) {
        page = p;
        ui = view;
    }

    public void display(String title) {

        ImageView view = new ImageView();
        f = new File("image.jpg");
        try {
            fileURL = f.toURI().toURL();
       
        image = new Image(fileURL.toExternalForm());
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
         } catch (MalformedURLException ex) {
            Logger.getLogger(AddBannerImageDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        addImage.setOnAction(e -> {
            SelectionController c = new SelectionController();
             f = c.processSelectImage();
            if (f != null) {
                try {
                    // GET AND SET THE IMAGE
                     fileURL = f.toURI().toURL();
                    image = new Image(fileURL.toExternalForm());
                    
                    view.setImage(image);
                    page.setBannerImagePath(f.getPath());
                    page.setBannerImageFile(f.getName());

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
    
    public String getPath(){
        return f.getPath();
    }
    
   public File getFile(){
       return f;
   }
    
}
