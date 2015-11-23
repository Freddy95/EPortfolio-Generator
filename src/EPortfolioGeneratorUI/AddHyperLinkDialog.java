
package EPortfolioGeneratorUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class is used for the user to enter a paragraph component into the page
 * they are editing.
 *
 * @author Freddy Estevez
 */
public class AddHyperLinkDialog {

    Stage window;
    Scene scene;
    Label labelHeading;
    Label labelParagraph;
    TextField heading;
    TextArea paragraph;
    Button okBtn;

    public void display(String title) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        labelHeading = new Label();
        labelParagraph = new Label();
        labelHeading.setText("Enter a Heading");
        labelParagraph.setText("Enter Text. To add an inline-hyperlink insert '***'"
                + " before and after the link.");
        heading = new TextField();
        heading.setTooltip(new Tooltip("Enter Heading Here"));
        paragraph = new TextArea();
        paragraph.setTooltip(new Tooltip("Enter Text Here"));
        okBtn = new Button("OK");
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(labelHeading, heading, labelParagraph, paragraph, okBtn);
        scene = new Scene(layout, 650, 400);
        paragraph.setMinSize(100, 100);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialog");
        okBtn.getStyleClass().add("dialogButton");
        window.setScene(scene);
        window.show();
       
    }
}
