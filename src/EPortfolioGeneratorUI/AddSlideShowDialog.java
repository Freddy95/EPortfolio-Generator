
package EPortfolioGeneratorUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Freddy Estevez
 */
public class AddSlideShowDialog {
    Stage window;
    Scene scene;
    Label labelTitle;
    TextField slideShowTitle;
    Button okBtn;
    Button addSlide;
    HBox btns;
    Button removeSlide;
    VBox slideEditorPane;
    
    public void display(String title){
        btns = new HBox(15);
        slideEditorPane = new VBox(5);
        SlideEditView view = new SlideEditView("Nice caption.");
        slideEditorPane.getChildren().add(view);
        VBox layout = new VBox(10);
        okBtn = new Button("OK");
        addSlide= new Button("Add Slide");
        removeSlide = new Button("Remove Slide");
        labelTitle = new Label("Enter Slide Show Title");
        slideShowTitle = new TextField();
        layout.setPadding(new Insets(15,15,15,15));
        btns.getChildren().addAll(addSlide, removeSlide);
        btns.getStyleClass().add("dialogButton");
        layout.getChildren().addAll(labelTitle, slideShowTitle, slideEditorPane, btns, okBtn);
        scene = new Scene(layout, 400, 400);
        window = new Stage();
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialog");
        window.setTitle(title);
        window.show();

    }
    
}
