
package Dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Freddy Estevez
 */
public class RemoveComponentDialog {
    Stage window;
    Scene scene;
    Label createList;
    ListView list;
    Button addBtn;
    Button removeBtn;
    Button okBtn;
    
    
    public void display(String title){
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        
        createList = new Label();
        createList.setText("Components: ");
        list = new ListView();
        list.setMinSize(300, 300);
        list.getItems().add("My Paragraph");
        list.getItems().add("My Image");
        
        removeBtn = new Button("Remove Element");
        HBox btns = new HBox(15);
        okBtn = new Button("OK");
        btns.getChildren().addAll(removeBtn, okBtn);
        btns.setAlignment(Pos.CENTER);
        VBox layout = new VBox(25);
        layout.setPadding(new Insets(10,10,10,10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll( createList, list, btns);
        scene = new Scene(layout, 400, 500);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialogList");
        window.setScene(scene);
        window.show();
    }
}
