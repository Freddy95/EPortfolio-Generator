
package EPortfolioGeneratorUI;

import java.io.File;
import java.net.URL;
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
    
    // PROVIDES RESPONSES FOR IMAGE SELECTION
    //ImageSelectionController imageController;

    /**
     * THis constructor initializes the full UI for this component, using
     * the initSlide data for initializing values./
     * 
     * @param caption The slide to be edited by this component.
     */
    public SlideEditView(String caption) {
	// FIRST SELECT THE CSS STYLE CLASS FOR THIS CONTAINER
	//this.getStyleClass().add(CSS_CLASS_SLIDE_EDIT_VIEW);
	
	// KEEP THE SLIDE FOR LATER
	//slide = initSlide;
	
	// MAKE SURE WE ARE DISPLAYING THE PROPER IMAGE
	imageSelectionView = new ImageView();
        Image image = new Image("file:image.jpg");
        imageSelectionView.setImage(image);
        imageSelectionView.setFitWidth(200);
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
        captionTextField.setText(caption);
	captionVBox.getChildren().add(captionLabel);
	captionVBox.getChildren().add(captionTextField);
//        captionTextField.setOnAction(e -> {
//            slide.setCaption(captionTextField.getText());
//        });
//        captionTextField.setOnKeyTyped(e -> {
//            model.changed();
//        });
	// LAY EVERYTHING OUT INSIDE THIS COMPONENT
	getChildren().add(imageSelectionView);
	getChildren().add(captionVBox);

	// SETUP THE EVENT HANDLERS
//	imageController = new ImageSelectionController();
//	imageSelectionView.setOnMousePressed(e -> {
//            if(!slide.getImageFileName().equals(DEFAULT_SLIDE_IMAGE))
//                model.setSelectedSlide(slide);
//            else{
//             imageController.processSelectImage(slide, this);
//             model.setSelectedSlide(slide);
//             model.changed();
//            }
//	});
//        isSelected = true;
        getStyleClass().add("slideEditView");
    }
    
//     public static void setModel(SlideShowModel model) {
//        SlideEditView.model = model;
//    }
    /**
     * This function gets the image for the slide and uses it to
     * update the image displayed.
     */
//    public void updateSlideImage() {
//	String imagePath = slide.getImagePath() + SLASH + slide.getImageFileName();
//	File file = new File(imagePath);
//	try {
//	    // GET AND SET THE IMAGE
//	    URL fileURL = file.toURI().toURL();
//	    Image slideImage = new Image(fileURL.toExternalForm());
//	    imageSelectionView.setImage(slideImage);
//            
//	    // AND RESIZE IT
//	    double scaledWidth = DEFAULT_THUMBNAIL_WIDTH;
//	    double perc = scaledWidth / slideImage.getWidth();
//	    double scaledHeight = slideImage.getHeight() * perc;
//	    imageSelectionView.setFitWidth(scaledWidth);
//	    imageSelectionView.setFitHeight(scaledHeight);
//	} catch (Exception e) {
//	    // @todo - use Error handler to respond to missing image
//	}
//    }    
//    
//    public void saveCaption(){
//        slide.setCaption(captionTextField.getText());
//    }
//    
//    public void selectAnImage(Slide slide){
//        DropShadow ds = new DropShadow();
//        ds.setColor(Color.RED);
//        ds.setOffsetX(2.0);
//        ds.setOffsetY(2.0);
//        imageSelectionView.setEffect(ds);
//        
//    }
//    public void deselect(Slide slide){
//        DropShadow ds = new DropShadow();
//        ds.setColor(Color.AQUA);
//        ds.setOffsetX(0);
//        ds.setOffsetY(0);
//        imageSelectionView.setEffect(ds);
//    }
//    
//    public Slide getSlide() {
//        return slide;
//    }
//    
//    public ImageView getImage(){
//        return imageSelectionView;
//    }
}