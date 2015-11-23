package EPortfolioGeneratorUI;

import Components.Component;
import Components.ImageComponent;
import Components.ParagraphComponent;
import eportfoliogenerator.EPortfolioGenerator;
import java.util.ArrayList;
import javafx.scene.control.Button;
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
    static ArrayList<Component> comps = new ArrayList<>();

    public ComponentEditView(Component comp) {
        getStyleClass().add("componentEditView");
        
        setSpacing(30);
        setMaxWidth(EPortfolioGenerator.getWidth()* 1);
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
            EPortfolioGenerator.reloadPane();
        });
    }

    public void initParagraph() {
        ParagraphComponent c = (ParagraphComponent) component;
        VBox para = new VBox(15);
        Label heading = new Label(c.getHeader());
        heading.getStyleClass().add("heading");
        TextArea text = new TextArea(c.getText());
        text.setEditable(false);
        text.setMaxWidth(700);
        text.setMinHeight(100);
        text.setMaxHeight(500);
        text.setWrapText(true);
        para.getChildren().addAll(heading, text);
        getChildren().add(para);
        getChildren().add(editComponent);
        editComponent.setOnAction(e -> {
            
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
        for(Component c : comps){
            c.setSelected(false);
        }
        DropShadow ds = new DropShadow();
        ds.setColor(Color.RED);
        ds.setOffsetX(2.0);
        ds.setOffsetY(2.0);
        setEffect(ds);
        setSelected(true);
    }    

    public static ArrayList<Component> getComps() {
        return comps;
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
    
    public void addComponent(Component c){
        comps.add(c);
    }
    
    
}
