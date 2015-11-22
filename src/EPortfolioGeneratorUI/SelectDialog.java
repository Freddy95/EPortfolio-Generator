
package EPortfolioGeneratorUI;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Freddy Estevez
 */
public class SelectDialog{
    Stage window;
    Scene scene;
    Label label;
    ChoiceBox<String> box;
    Button okBtn;
    
    public SelectDialog(List<String> strings){
        box = new ChoiceBox<>();
        box.getItems().addAll(strings);
    }
    public  void display(String title, String message){
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        label = new Label();
        label.setText(message);
        okBtn = new Button("OK");
        VBox layout = new VBox(25);
        layout.setPadding(new Insets(10,10,10,10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, box, okBtn);
        scene = new Scene(layout, 300, 200);
        
        window.setScene(scene);
        window.show();
    }
    
    
}
