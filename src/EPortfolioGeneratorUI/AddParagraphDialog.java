package EPortfolioGeneratorUI;


import Components.ParagraphComponent;
import eportfoliogenerator.EPortfolio;
import eportfoliogenerator.EPortfolioGeneratorView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class is used for the user to enter a paragraph component into the page
 * they are editing.
 *
 * @author Freddy Estevez
 */
public class AddParagraphDialog {

    Stage window;
    Scene scene;
    Label labelHeading;
    Label labelParagraph;
    TextField heading;
    TextArea paragraph;
    Button addLinkButton;
    Button okBtn;
    ChoiceBox<String> box;
    Page page;
    
    ParagraphComponent comp;
    
    EPortfolioGeneratorView ui;
    
    public AddParagraphDialog(Page p, EPortfolioGeneratorView initUi){
        page = p;
        ui = initUi;
    }
    /**
     * This is called when a used wants to first add a paragraph component into 
     * his/her ePortfolio. It creates a dialog box and request for a heading, text,
     * and font type. If no font type is selected then it will use the default font
     * of the page.
     * @param title - title of window
     */
    public void display(String title) {
        HBox elems = new HBox(15);
        window = new Stage();
        box = new ChoiceBox<>();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        labelHeading = new Label();
        labelParagraph = new Label();
        labelHeading.setText("Enter a Heading");
        labelParagraph.setText("Enter Text");
        heading = new TextField();
        heading.setTooltip(new Tooltip("Enter Heading Here"));
        paragraph = new TextArea();
        paragraph.setTooltip(new Tooltip("Enter Text Here"));
        okBtn = new Button("OK");
        box.getItems().add("Font 1");
        box.getItems().add("Font 2");
        box.getItems().add("Font 3");
        box.getItems().add("Font 4");
        box.getItems().add("Font 5");
        box.setValue("Select Font");
        
        elems.getChildren().addAll(box, okBtn);
        elems.getStyleClass().add("dialogButton");
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(labelHeading, heading, labelParagraph, paragraph, elems);
        scene = new Scene(layout, 400, 400);
        paragraph.setMinSize(100, 100);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialog");
        window.setScene(scene);
        addLinkButton.setOnAction(e -> {
        
        });
        okBtn.setOnAction(e->{
           comp = new ParagraphComponent(heading.getText(), paragraph.getText(), box.getValue());
           page.addComponent(comp);
           ui.reloadPane();
           window.close();
       });
        window.showAndWait();
       
    }
    /**
     * This called when the user wants to edit a specific paragraph component already
     * in his/her ePortfolio.
     * @param p - paragraph component they want to edit
     */
    public void editDisplay(ParagraphComponent p){
        comp = p;
        HBox elems = new HBox(15);
        window = new Stage();
        box = new ChoiceBox<>();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Paragraph");
        labelHeading = new Label();
        labelParagraph = new Label();
        labelHeading.setText("Enter a Heading");
        labelParagraph.setText("Enter Text");
        heading = new TextField();
        heading.setTooltip(new Tooltip("Enter Heading Here"));
        paragraph = new TextArea();
        paragraph.setTooltip(new Tooltip("Enter Text Here"));
        okBtn = new Button("OK");
        box.getItems().add("Font 1");
        box.getItems().add("Font 2");
        box.getItems().add("Font 3");
        box.getItems().add("Font 4");
        box.getItems().add("Font 5");

        box.setValue("Select Font");
        paragraph.setText(p.getText());
        heading.setText(p.getHeader());
        elems.getChildren().addAll(box, okBtn);
        elems.getStyleClass().add("dialogButton");
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(labelHeading, heading, labelParagraph, paragraph, elems);
        scene = new Scene(layout, 400, 400);
        paragraph.setMinSize(100, 100);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialog");
        window.setScene(scene);
        okBtn.setOnAction(e->{
            p.setText(paragraph.getText());
            p.setHeader(heading.getText());
            p.setFont(box.getValue());
            ui.reloadPane();
           window.close();
       });
        window.showAndWait();
    }
    
    public Page getPage(){
        return page;
    }
    
    public ParagraphComponent getComponent(){
        return comp;
    }
}
