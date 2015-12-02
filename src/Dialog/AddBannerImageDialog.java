
package Dialog;

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
    
    
    public void display(String title){
       
        ImageView view = new ImageView();
        Image image = new Image("file:image.jpg");
        view.setImage(image);
        view.setFitWidth(300);
        view.setPreserveRatio(true);
        view.setSmooth(true);
        view.setCache(true);
        okBtn = new Button("OK");
        addImage = new Button("Add Image");
        
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(view, addImage, okBtn);
        scene = new Scene(layout, 400, 400);
        window = new Stage();
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialogImage");
        window.show();
    }
}
