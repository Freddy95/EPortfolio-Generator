
package Dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
public class SetDialog {
 Stage window;
    Scene scene;
    Label labelHeading;
    TextField heading;
    Button okBtn;

    public void display(String title, String message) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        labelHeading = new Label();
        labelHeading.setText(message);
        heading = new TextField();
        okBtn = new Button("OK");
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(labelHeading, heading, okBtn);
        scene = new Scene(layout, 400, 250);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialog");
        window.setScene(scene);
        window.show();
       
    }
    
    public Button getButton(){
        return okBtn;
    }
    
    public String getValue(){
        return heading.getText();
    }
    
    public Stage getWindow(){
        return window;
    }
}
