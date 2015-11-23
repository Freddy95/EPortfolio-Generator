
package EPortfolioGeneratorUI;

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
public class AddListDialog {
    Stage window;
    Scene scene;
    Label labelHeading;
    Label createList;
    TextField heading;
    ListView list;
    Button addBtn;
    Button removeBtn;
    Button okBtn;
    
    
    public void display(String title){
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        labelHeading = new Label();
        labelHeading.setText("Enter title of list");
        heading = new TextField();
        createList = new Label();
        createList.setText("List: ");
        list = new ListView();
        list.setMinSize(300, 300);
        list.getItems().add("I like pie");
        list.getItems().add("I Like cookies");
        addBtn = new Button("Add Element");
        removeBtn = new Button("Remove Element");
        HBox btns = new HBox(15);
        btns.getChildren().addAll(addBtn, removeBtn);
        okBtn = new Button("OK");
        btns.setAlignment(Pos.CENTER);
        VBox layout = new VBox(25);
        layout.setPadding(new Insets(10,10,10,10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(labelHeading, heading, createList, list, btns, okBtn);
        scene = new Scene(layout, 400, 600);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialogList");
        window.setScene(scene);
        window.show();
    }
}
