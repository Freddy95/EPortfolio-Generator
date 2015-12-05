package Dialog;


import Components.Component;
import Components.ParagraphComponent;
import Page.Page;
import eportfoliogenerator.EPortfolio;
import View.EPortfolioGeneratorView;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    Button okBtn;
    ChoiceBox<String> box;
    ArrayList<String> urls;
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
        urls = new ArrayList<>();
     
        
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
        box.getItems().add("Courgette");
        box.getItems().add("Ubuntu");
        box.getItems().add("Dosis");
        box.getItems().add("Average");
        box.getItems().add("Oxygen");
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

        okBtn.setOnAction(e->{
           comp = new ParagraphComponent();
           comp.setHeader(heading.getText());
           comp.setFont( box.getValue());
           comp.setText(paragraph.getText());
           page.addComponent(comp);
           select(comp);
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
        
        paragraph = new TextArea();
        paragraph.setTooltip(new Tooltip("Enter Text Here"));
        okBtn = new Button("OK");
        box.getItems().add("Courgette");
        box.getItems().add("Ubuntu");
        box.getItems().add("Dosis");
        box.getItems().add("Average");
        box.getItems().add("Oxygen");

        box.setValue("Select Font");
        heading.setText(p.getHeader());
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
            p.setSelected(true);
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
     public void select(Component c){
        List<Component> views = page.getComponents();
            for(int i = 0; i < views.size(); i++){
                views.get(i).setSelected(false);   
            }
          c.setSelected(true);
    }
  
}
