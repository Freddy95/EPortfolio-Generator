package View;

import Dialog.LinkDialog;
import Dialog.AddParagraphDialog;
import Components.Component;
import Components.ImageComponent;
import Components.ParagraphComponent;
import eportfoliogenerator.EPortfolioGenerator;
import Page.Page;
import View.EPortfolioGeneratorView;
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
        getStyleClass().add(p.getFont());
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
        if(c.getFont() != null || c.getFont() != ""){
            heading.getStyleClass().add(c.getFont());
            text.getStyleClass().add(c.getFont());       
        }else{
            heading.getStyleClass().add(page.getFont());
            text.getStyleClass().add(page.getFont()); 
        }
        
        para.getChildren().addAll(heading, text);
        getChildren().add(para);
        btns.getChildren().add(editComponent);
        btns.getChildren().add(addLink);
        btns.getChildren().add(removeLink);
        getChildren().add(btns);
        editComponent.setOnAction(e -> {
            AddParagraphDialog dia = new AddParagraphDialog(page, ui);
            dia.editDisplay(c);
        });
        
        addLink.setOnAction(e -> {
            if(text.getSelectedText()== "" || text.getSelectedText() == null)
                return;
            LinkDialog dia = new LinkDialog(c, text.getSelection(), ui);
            dia.addDisplay();
            ui.reloadPane();
            removeLink.setDisable(!(c.getLinks().size() > 0));
        });
        
        removeLink.setOnAction(e -> {
            if(text.getSelectedText().equals("") || text.getSelectedText() == null)
                return;
            String t = text.getSelectedText();
            if(t.indexOf("***") != 0 || t.lastIndexOf("***") != t.length()-3){
               
                return;
                
            }
            StringBuilder s = new StringBuilder();
            IndexRange r = text.getSelection();
            
            s.append(text.getText().substring(0, r.getStart()));
            System.out.println(r.getStart() + " " + r.getEnd());
            s.append(text.getText().substring(r.getStart()+3, r.getEnd()-3));
            
            s.append(text.getText().substring(r.getEnd()));
            c.setText(s.toString());
            c.getLinks().remove(getIndexOfLink(r.getStart(), text.getText()));
            removeLink.setDisable(!(c.getLinks().size() > 0));
            ui.reloadPane();
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
     
    
    public int getIndexOfLink(int start, String text){
        int i = 0;
        while(text.lastIndexOf("***", start-1) != -1){
            start = text.lastIndexOf("***", start-1);
            start = text.lastIndexOf("***", start-1);
            i++;
        }
        
        return i;
    }
    
   
  
    
}
