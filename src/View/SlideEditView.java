package View;

import Dialog.AddSlideShowDialog;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
public class SlideEditView extends HBox {
    // SLIDE THIS COMPONENT EDITS
    //Slide slide;

    Boolean isSelected;
    // DISPLAYS THE IMAGE FOR THIS SLIDE
    ImageView imageSelectionView;
    //static SlideShowModel model;

    // CONTROLS FOR EDITING THE CAPTION
    VBox captionVBox;
    Label captionLabel;
    TextField captionTextField;
    File file;

    // PROVIDES RESPONSES FOR IMAGE SELECTION
    //ImageSelectionController imageController;
    /**
     * THis constructor initializes the full UI for this component, using the
     * initSlide data for initializing values./
     *
     * @param caption The slide to be edited by this component.
     */
    public SlideEditView(File f, VBox slideEditorPane, AddSlideShowDialog d) {
	// FIRST SELECT THE CSS STYLE CLASS FOR THIS CONTAINER
        //this.getStyleClass().add(CSS_CLASS_SLIDE_EDIT_VIEW);

        // KEEP THE SLIDE FOR LATER
        //slide = initSlide;
        // MAKE SURE WE ARE DISPLAYING THE PROPER IMAGE
        this.file = f;
        imageSelectionView = new ImageView();
        URL fileURL;
        try {
            fileURL = file.toURI().toURL();

            Image image = new Image(fileURL.toExternalForm());
            imageSelectionView.setImage(image);
            imageSelectionView.setFitWidth(300);
            imageSelectionView.setPreserveRatio(true);
            imageSelectionView.setSmooth(true);
            imageSelectionView.setCache(true);
            //updateSlideImage();
            setSpacing(20);
            // SETUP THE CAPTION CONTROLS
            captionVBox = new VBox(15);
            //PropertiesManager props = PropertiesManager.getPropertiesManager();
            captionLabel = new Label("Enter Caption");
            captionTextField = new TextField();
            captionVBox.getChildren().add(captionLabel);
            captionVBox.getChildren().add(captionTextField);
            // LAY EVERYTHING OUT INSIDE THIS COMPONENT
            getChildren().add(imageSelectionView);
            getChildren().add(captionVBox);

            // SETUP THE EVENT HANDLERS
            setOnMouseClicked(e -> {
                for (int i = 0; i < slideEditorPane.getChildren().size(); i++) {
                    SlideEditView v = (SlideEditView) slideEditorPane.getChildren().get(i);
                    v.deselect();
                }
                select();
                d.updateButtons();
            });

            getStyleClass().add("slideEditView");
            slideEditorPane.getChildren().add(this);

        } catch (MalformedURLException ex) {
            Logger.getLogger(SlideEditView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setCaption(String s) {
        captionTextField.setText(s);
    }
    public String getCaption(){
        return captionTextField.getText();
    }
    
    public String getImageFilePath(){
        return file.getPath();
    }
    public String getImageFileName(){
        return file.getName();
    }

    public void deselect() {
        isSelected = false;
        DropShadow ds = new DropShadow();
        ds.setColor(Color.WHITE);
        ds.setOffsetX(0);
        ds.setOffsetY(0);

        setEffect(ds);

    }

    public void select() {
        isSelected = true;
        DropShadow ds = new DropShadow();
        ds.setColor(Color.RED);
        ds.setOffsetX(2);
        ds.setOffsetY(2);
        setEffect(ds);
    }

    public boolean isSelected() {
        return isSelected;
    }
}
