
package EPortfolioGeneratorUI;

import Components.ImageComponent;
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
 * This class is used to present the user with a dialog to add an image to their
 * page he/she is currently editing.
 * @author Freddy Estevez
 */
public class AddImageDialog {
    Stage window;
    Scene scene;
    Label labelHeading;
    Label labelParagraph;
    TextField heading;
    Button okBtn;
    Button addImage;
    Page page;
    
    public AddImageDialog(Page p){
        page = p;
    }
    
    
    public void display(String title){
        labelHeading = new Label("Enter Caption:");
        heading = new TextField();
        heading.setPrefWidth(200);
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
        layout.getChildren().addAll(labelHeading, heading, view, addImage, okBtn);
        scene = new Scene(layout, 400, 500);
        window = new Stage();
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialogImage");
        window.show();
        
        okBtn.setOnAction(e -> {
           // String fileName = view.getImage();
            //ImageComponent img = new ImageComponent();
        });
        
    }
}
