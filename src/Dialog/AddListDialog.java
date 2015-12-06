
package Dialog;

import Components.Component;
import Components.ListComponent;
import Page.Page;
import View.EPortfolioGeneratorView;
import java.util.List;
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
    Label labelTitle;
    Label createList;
    TextField title;
    TextField element;
    ListView list;
    Button addBtn;
    Button removeBtn;
    Button okBtn;
    Page page;
    EPortfolioGeneratorView ui;
    
    public AddListDialog(Page p, EPortfolioGeneratorView initUi){
        page = p;
        ui = initUi;
    }
    
    public void display(){
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add List Component");
        labelTitle = new Label();
        labelTitle.setText("Enter title of list");
        element = new TextField();
        title = new TextField();
        createList = new Label();
        createList.setText("List: ");
        list = new ListView();
        list.setMinSize(300, 200);    
        addBtn = new Button("Add Element");
        removeBtn = new Button("Remove Element");
        removeBtn.setDisable(true);
        HBox btns = new HBox(15);
        btns.getChildren().addAll(addBtn, removeBtn);
        okBtn = new Button("OK");
        okBtn.setDisable(true);
        btns.setAlignment(Pos.CENTER);
        VBox layout = new VBox(25);
        layout.setPadding(new Insets(10,10,10,10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(labelTitle, title, createList, list, element, btns, okBtn);
        scene = new Scene(layout, 400, 600);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialogList");
        window.setScene(scene);
        addBtn.setOnAction(e -> {
            if(!(element.getText().equals("")) && element.getText() != null ){
                list.getItems().add(element.getText());
                okBtn.setDisable(false);
                removeBtn.setDisable(false);
                element.clear();
            }
        });
        removeBtn.setOnAction(e -> {
            list.getItems().remove(list.getSelectionModel().getSelectedIndex());
             if(list.getItems().isEmpty()){
                okBtn.setDisable(true);
                removeBtn.setDisable(true);
            }
        });
        
        okBtn.setOnAction(e -> {
            ListComponent c = new ListComponent(title.getText());
            for(int i = 0; i < list.getItems().size(); i++)
                c.addElements((String) list.getItems().get(i));
            select(c);
            page.addComponent(c);
            ui.reloadPane();
            window.close();
        });
        
        window.showAndWait();
       
    }
    
    public void editDisplay(ListComponent c){
         window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit List Component");
        labelTitle = new Label();
        labelTitle.setText("Enter title of list");
        element = new TextField();
        title = new TextField();
        title.setText(c.getTitle());
        createList = new Label();
        createList.setText("List: ");
        list = new ListView();
        list.getItems().addAll(c.getElements());
        list.setMinSize(300, 200);    
        addBtn = new Button("Add Element");
        removeBtn = new Button("Remove Element");
        HBox btns = new HBox(15);
        btns.getChildren().addAll(addBtn, removeBtn);
        okBtn = new Button("OK");
        btns.setAlignment(Pos.CENTER);
        VBox layout = new VBox(25);
        layout.setPadding(new Insets(10,10,10,10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(labelTitle, title, createList, list, element, btns, okBtn);
        scene = new Scene(layout, 400, 600);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialogList");
        window.setScene(scene);
        addBtn.setOnAction(e -> {
            if(!(element.getText().equals("")) && element.getText() != null ){
                list.getItems().add(element.getText());
                okBtn.setDisable(false);
                removeBtn.setDisable(false);
                element.clear();
            }
        });
        removeBtn.setOnAction(e -> {
            list.getItems().remove(list.getSelectionModel().getSelectedIndex());
            if(list.getItems().isEmpty()){
                okBtn.setDisable(true);
                removeBtn.setDisable(true);
            }
        });
        
        okBtn.setOnAction(e -> {
            c.setTitle(title.getText());
            c.getElements().clear();
            for(int i = 0; i < list.getItems().size(); i++)
                c.addElements((String) list.getItems().get(i));
            select(c);
            ui.reloadPane();
            window.close();
        });
        
        window.showAndWait();
    }
    
    public void select(Component c){
        List<Component> views = page.getComponents();
            for(int i = 0; i < views.size(); i++){
                views.get(i).setSelected(false);   
            }
          c.setSelected(true);
    }
}
