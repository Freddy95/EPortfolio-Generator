package EPortfolioGeneratorUI;

import Components.Component;
import Components.ImageComponent;
import Components.ParagraphComponent;
import eportfoliogenerator.EPortfolioGenerator;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Freddy Estevez
 */
public class ComponentEditView extends HBox {

    Component component;
    Button editComponent;

    public ComponentEditView(Component comp) {
        getStyleClass().add("componentEditView");
        setSpacing(30);
        System.out.println(EPortfolioGenerator.getWidth());
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

}
