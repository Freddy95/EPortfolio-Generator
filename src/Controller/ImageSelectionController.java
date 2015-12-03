
package Controller;

import Components.Component;
import Components.ImageComponent;
import java.io.File;
import javafx.stage.FileChooser;

/**
 *
 * @author Freddy Estevez
 */
public class ImageSelectionController {
    String defaultDirectory = "images/";
      
    /**
     * This function provides the response to the user's request to
     * select an image.
     * 
     */
    public File processSelectImage() {
	FileChooser imageFileChooser = new FileChooser();
	
	// SET THE STARTING DIRECTORY
	imageFileChooser.setInitialDirectory(new File(defaultDirectory));
	
	// LET'S ONLY SEE IMAGE FILES
	FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
	imageFileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter, gifFilter);
	
	// LET'S OPEN THE FILE CHOOSER
	File file = imageFileChooser.showOpenDialog(null);
	if (file != null) {
            
            
	   return file;
	}	    
	else {
	    // @todo provide error message for no files selected
            return null;
	}
    }
    
}
