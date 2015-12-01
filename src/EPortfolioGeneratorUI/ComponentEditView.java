package EPortfolioGeneratorUI;

import Components.Component;
import Components.ImageComponent;
import Components.ParagraphComponent;
import eportfoliogenerator.EPortfolioGenerator;
import eportfoliogenerator.EPortfolioGeneratorView;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Freddy Estevez
 */
public class ComponentEditView extends HBox {

    Component component;
    Button editComponent;
    boolean selected;
    Page page;
    EPortfolioGeneratorView ui;

    public ComponentEditView(Component comp, Page p, EPortfolioGeneratorView initUi) {
        getStyleClass().add("componentEditView");
        page = p;
        setSpacing(30);
        editComponent = new Button("Edit");
        editComponent.getStyleClass().add("editButton");
        component = comp;
        if (component.getType().equals("Paragraph")) {
            initParagraph();
        }
        if (component.getType().equals("Image")) {
            initImage();
        }
        setOnMouseClicked( e -> {
            select();
        });
        
        ui = initUi;
    }

    public void initParagraph() {
        VBox btns = new VBox(10);
        Button addLink = new Button("Add Link");
        Button removeLink = new Button("Remove Link");
        ParagraphComponent c = (ParagraphComponent) component;
        VBox para = new VBox(15);
        Label heading = new Label(c.getHeader());
        heading.getStyleClass().clear();
        heading.getStyleClass().add("heading");
        TextArea text = new TextArea(c.getText());
        text.setEditable(false);
        text.getStyleClass().clear();
        text.setMaxWidth(700);
        text.setMinHeight(100);
        text.setMaxHeight(500);
        text.setWrapText(true);
        switch(c.getFont()){
            case "Font 1":
                heading.getStyleClass().add("font1");
                text.getStyleClass().add("font1");
                break;
            case "Font 2":
                heading.getStyleClass().add("font2");
                text.getStyleClass().add("font2");
                break;
            case "Font 3":
                heading.getStyleClass().add("font3");
                text.getStyleClass().add("font3");
                break;
            case "Font 4":
                heading.getStyleClass().add("font4");
                text.getStyleClass().add("font4");
                break;
             case "Font 5":
                heading.getStyleClass().add("font5");
                text.getStyleClass().add("font5");
                break;
            default:
                break;
        }
        para.getChildren().addAll(heading, text);
        getChildren().add(para);
        btns.getChildren().add(editComponent);
        btns.getChildren().add(addLink);
        getChildren().add(btns);
        editComponent.setOnAction(e -> {
            AddParagraphDialog dia = new AddParagraphDialog(page, ui);
            dia.editDisplay(c);
        });
        
        addLink.setOnAction(e -> {
            if(text.getSelectedText()== "" || text.getSelectedText() == null)
                return;
            LinkDialog dia = new LinkDialog(c, text.getSelection());
            dia.addDisplay();
            ui.reloadPane();
            removeLink.setDisable(!(c.getLinks().size() > 0));
        });
        
        removeLink.setOnAction(e -> {
            if(text.getSelectedText().equals("") || text.getSelectedText() == null)
                return;
            
           
            removeLink.setDisable(!(c.getLinks().size() > 0));
        });
        
           
    }

    public void initImage() {
        ImageComponent image = (ImageComponent) component;
        VBox img = new VBox(15);
        img.getStyleClass().add("component2");
        Label cap = new Label(image.getCaption());
        cap.getStyleClass().add("heading");
        Image im = new Image(image.getPath());
        ImageView view = new ImageView(im);
        double scaledWidth = 500;
        double perc = scaledWidth / im.getWidth();
        double scaledHeight = im.getHeight() * perc;
        view.setFitWidth(scaledWidth);
        view.setFitHeight(scaledHeight);
        view.setSmooth(true);
        view.setCache(true);
        img.getChildren().addAll(cap, view);
        getChildren().addAll(img, editComponent);
    }
    
    public void select(){
       
        DropShadow ds = new DropShadow();
        ds.setColor(Color.RED);
        ds.setOffsetX(2.0);
        ds.setOffsetY(2.0);
        setEffect(ds);
        setSelected(true);
    }    

  

    public boolean isSelected() {
        return component.isSelected();
    }

    public void setSelected(boolean selected) {
        component.setSelected(selected);
    }
    
    public void deselect(){
        DropShadow ds = new DropShadow();
        ds.setColor(Color.WHITE);
        ds.setOffsetX(0.0);
        ds.setOffsetY(0.0);
        setEffect(ds);
    }
     
    
    public IndexRange findIndexRange(String s, int val){
        IndexRange ret = new IndexRange(0,0);
        return ret;
    }
  
    
}
