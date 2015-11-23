
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
public class AddSlideShowDialog {
    Stage window;
    Scene scene;
    Label labelTitle;
    TextField slideShowTitle;
    Button okBtn;
    Button addSlide;
    Button removeSlide;
    VBox slideEditorPane;
    
    public void display(String title){
        slideEditorPane = new VBox(5);
        SlideEditView view = new SlideEditView("Nice caption.");
        slideEditorPane.getChildren().add(view);
        VBox layout = new VBox(10);
        okBtn = new Button("OK");
        addSlide= new Button("Add Slide");
        removeSlide = new Button("Remove Slide");
        labelTitle = new Label("Enter Slide Show Title");
        slideShowTitle = new TextField();
        layout.getChildren().addAll(labelTitle, slideShowTitle, slideEditorPane, addSlide, removeSlide, okBtn);
        scene = new Scene(layout, 300, 400);
        window = new Stage();
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.show();

    }
    
}
