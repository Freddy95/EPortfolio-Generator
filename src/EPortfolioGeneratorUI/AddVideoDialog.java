
package EPortfolioGeneratorUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Freddy Estevez
 */
public class AddVideoDialog {
    Stage window;
    Scene scene;
    Label labelHeading;
    TextField heading;
    Button okBtn;
    Button selVideo;
    
    public void display(String title){
        
        labelHeading = new Label("Enter a Caption");
        selVideo = new Button("Select Video");
        okBtn = new Button("OK");
        VBox layout = new VBox(15);
        layout.getChildren().addAll(labelHeading, selVideo, okBtn);
        scene = new Scene(layout, 200, 100);
        window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.show();
    }
    
}
