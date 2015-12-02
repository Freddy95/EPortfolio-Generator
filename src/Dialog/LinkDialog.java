
package Dialog;

import Components.ParagraphComponent;
import View.EPortfolioGeneratorView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This Dialog is used for adding, editing, and removing links from a paragraph.
 *
 * @author Freddy Estevez
 */
public class LinkDialog {
    ParagraphComponent comp;
    IndexRange range;
    EPortfolioGeneratorView ui;
    
    public LinkDialog(ParagraphComponent c, IndexRange range, EPortfolioGeneratorView initUi) {
        comp = c;
        this.range = range;
        ui = initUi;
    }
    
    public void addDisplay(){
        Stage window;
         Scene scene;
        Label labelHeading;
        TextField link;
         Button addLink;
         window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Link");
        labelHeading = new Label();
        labelHeading.setText("Enter URL here");
        link = new TextField();
        addLink = new Button("Add Link");
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(labelHeading, link, addLink);
        scene = new Scene(layout, 400, 250);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialog");
        window.setScene(scene);
        
        addLink.setOnAction( e -> {
            comp.addLink(link.getText());
            int x = range.getStart();
            int y = range.getEnd()-1;
            String original = comp.getText();
            StringBuilder s = new StringBuilder();
            s.append(original.substring(0, x));
            s.append("***");
            s.append(original.substring(x, y+1));
            s.append("***");
            s.append(original.substring(y+1));
            comp.setText(s.toString());
            window.close();
        });
        window.showAndWait();
    }
    
    
    public void editDisplay(){
        Stage window;
         Scene scene;
        Label labelHeading;
        ListView links;
         Button removeLink;
         window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Remove Link");
        labelHeading = new Label();
        labelHeading.setText("Change URL");
        links = new ListView();
        removeLink = new Button("Remove Link");
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(labelHeading, links, removeLink);
        scene = new Scene(layout, 400, 250);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialog");
        window.setScene(scene);
        
        removeLink.setOnAction( e -> {
            comp.getLinks().remove((links.getSelectionModel().getSelectedIndex()));
            links.getItems().clear();
            links.getItems().addAll(comp.getLinks());
            
        });
        window.setOnCloseRequest(e -> {
            ui.reloadPane();
        });
        
        window.showAndWait();
    }

    public IndexRange findIndexRange(String s, int val){
        int x = 0;
        int y = 0;
        IndexRange ret;
        for(int i = 0; i < val; i++){
             y = s.indexOf("***", x);
            x += y;
        }
        x = s.indexOf("***", y);
        ret = new IndexRange(y,x);
        return ret;
       
    }
}
