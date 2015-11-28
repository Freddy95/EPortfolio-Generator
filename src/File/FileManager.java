
package File;

import Components.Component;
import Components.ImageComponent;
import Components.ListComponent;
import Components.ParagraphComponent;
import Components.Slide;
import Components.VideoComponent;
import EPortfolioGeneratorUI.Page;
import eportfoliogenerator.EPortfolio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;

/**
 *
 * @author Freddy Estevez
 */
public class FileManager {
    // JSON FILE READING AND WRITING CONSTANTS
    public static String JSON_TITLE = "title";
    public static String JSON_PAGES = "pages";
    public static String JSON_CAPTION = "caption";
    public static String JSON_NAME= "name";
    public static String JSON_URL = "URL";
    public static String JSON_TYPE = "type";
    public static String JSON_HEADING = "heading";
    public static String JSON_BANNER = "banner";
    public static String JSON_CONTENT= "content";
    public static String JSON_SLIDES = "slides";
    public static String JSON_TEXT = "text";
    public static String JSON_IMAGE_FILE_NAME = "image_file_name";
    public static String JSON_IMAGE_PATH = "image_path";
    public static String JSON_VIDEO_PATH = "file_path";
    public static String JSON_VIDEO_FILE_NAME = "file_name";
    public static String JSON_IMAGE_CAPTION="caption";
    public static String JSON_EXT = ".json";
    public static String SLASH = "/";

    /**
     * This method saves all the data associated with a slide show to
     * a JSON file.
     * 
     * @param EPortfoloToSave The course whose data we are saving.
     * 
     * @throws IOException Thrown when there are issues writing
     * to the JSON file.
     */
    public void saveEPortfolio(EPortfolio ePortfolio) throws IOException {
        // BUILD THE FILE PATH
        String title = "" + eportfolio.getTitle();
        String jsonFilePath = "EPortfolios" + SLASH + title + JSON_EXT;
        
        // INIT THE WRITER
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonWriter = Json.createWriter(os);  
           
        // BUILD THE SLIDES ARRAY
        JsonArray contentJsonArray =  makeContentArray(ePortfolio);
        ArrayList<Page> pages = ePortfolio.getPages();
        for(Page p: pages){
            makeContentArray(p);
        }
        // NOW BUILD THE COURSE USING EVERYTHING WE'VE ALREADY MADE
        JsonObject courseJsonObject = Json.createObjectBuilder()
                                    .add(JSON_TITLE, eportfolio.getTitle())
                                    .add(JSON_SLIDES, contentJsonArray)
                                    
                .build();
        
        // AND SAVE EVERYTHING AT ONCE
        jsonWriter.writeObject(courseJsonObject);
    }
    
    /**
     * This method loads the contents of a JSON file representing a slide show
     * into a SlideSShowModel object.
     * 
     * @param EPortfoloToLoad The slide show to load
     * @param jsonFilePath The JSON file to load.
     * @throws IOException 
     */
    public void loadEPortfolio(EPortfolio EPortfoloToLoad, String jsonFilePath) throws IOException {
        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(jsonFilePath);
        
        // NOW LOAD THE COURSE
	//slideShowToLoad.reset();
        ePortfoloToLoad.setTitle(json.getString(JSON_TITLE));
        JsonArray jsonSlidesArray = json.getJsonArray(JSON_SLIDES);
        for (int i = 0; i < jsonSlidesArray.size(); i++) {
	    JsonObject slideJso = jsonSlidesArray.getJsonObject(i);
	  //  slideShowToLoad.addSlide(	slideJso.getString(JSON_IMAGE_FILE_NAME),
	//				slideJso.getString(JSON_IMAGE_PATH),
          //                              slideJso.getString(JSON_IMAGE_CAPTION));
	}
    }

    // AND HERE ARE THE PRIVATE HELPER METHODS TO HELP THE PUBLIC ONES
    
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }    
    
    private ArrayList<String> loadArrayFromJSONFile(String jsonFilePath, String arrayName) throws IOException {
        JsonObject json = loadJSONFile(jsonFilePath);
        ArrayList<String> items = new ArrayList();
        JsonArray jsonArray = json.getJsonArray(arrayName);
        for (JsonValue jsV : jsonArray) {
            items.add(jsV.toString());
        }
        return items;
    }
    
    private JsonArray makeSlidesJsonArray(List<Slide> slides) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Slide slide : slides) {
	    JsonObject jso = makeSlideJsonObject(slide);
	    jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;        
    }
    
    private JsonObject makeSlideJsonObject(Slide slide) {
        JsonObject jso = Json.createObjectBuilder()
		.add(JSON_IMAGE_FILE_NAME, slide.getFileName())
		.add(JSON_IMAGE_PATH, slide.getPath())
                .add(JSON_IMAGE_CAPTION, slide.getCaption())
		.build();
	return null;
    }
    
     /**
     * This helper method asks the user for a file to open. The user-selected
     * file is then loaded and the GUI updated. Note that if the user cancels
     * the open process, nothing is done. If an error occurs loading the file, a
     * message is displayed, but nothing changes.
     */
    private void promptToOpen() {
        // AND NOW ASK THE USER FOR THE COURSE TO OPEN
        FileChooser ePortfoloFileChooser = new FileChooser();
        ePortfoloFileChooser.setInitialDirectory(new File(PATH_SLIDE_SHOWS));
        File selectedFile = ePortfoloFileChooser.showOpenDialog(ui.getWindow());

        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                SlideShowModel ePortfoloToLoad = ui.getSlideShow();
                ePortfoloIO.loadSlideShow(ePortfoloToLoad, selectedFile.getAbsolutePath());
                ui.reloadSlideShowPane(ePortfoloToLoad);
                saved = true;
                ui.updateToolbarControls(saved);
            } catch (Exception e) {
                PropertiesManager props = PropertiesManager.getPropertiesManager();
                ErrorHandler eH = ui.getErrorHandler();
                Stage stage = new Stage();
                VBox layout = new VBox(10);
                layout.setPadding(new Insets(10, 10, 10, 10));
                layout.getStyleClass().add(CSS_CLASS_PROMPT_LANG);
                Label errorLabel = new Label(props.getProperty(ERROR_MESSAGE));
                Button btn = new Button("OK");
                btn.setAlignment(Pos.CENTER);
                layout.getChildren().addAll(errorLabel, btn); 
                layout.setAlignment(Pos.CENTER);
                btn.setOnAction(d -> stage.close());
                Scene scene = new Scene(layout);
                scene.getStylesheets().add(STYLE_SHEET_UI);
                stage.setScene(scene);
                stage.showAndWait();
                promptToOpen();
                // @todo
            }

        }
    }
    
    private JsonArray makeContentArray(Page p){
        ArrayList<Component> comps = p.getComponents();
        JsonArrayBuilder jsb = Json.createArrayBuilder();

        for(Component c: comps){
            if(c.getType().equals("Paragraph"))
               jsb.add(makeParagraphObject(c));
            else if(c.getType().equals("Image"))
                jsb.add(makeImageObject(c));
            else if(c.getType().equals("HyperLink Text"))
                jsb.add(makeHyperLinkObject(c));
            else if(c.getType().equals("Video"))
                jsb.add(makeVideoObject(c));
            else if(c.getType().equals("SlideShow"))
                jsb.add(makeVideoObject(c));
            else if(c.getType().equals("List"))
                jsb.add(makeListObject(c));
        }
       JsonArray jA = jsb.build();
       return jA;
    }
    
    
    private JsonObject makeParagraphObject(Component c){
        ParagraphComponent comp = (ParagraphComponent) c;
        JsonObject js = Json.createObjectBuilder()
                .add(JSON_TYPE, comp.getType())
                .add(JSON_HEADING, comp.getHeader())
                .add(JSON_TEXT, comp.getText())
                .build();
        return js;
    }    
    private JsonObject makeImageObject(Component c){
        ImageComponent comp = (ImageComponent) c;
        JsonObject js = Json.createObjectBuilder()
                .add(JSON_TYPE, comp.getType())
                .add(JSON_IMAGE_PATH, comp.getPath())
                .add(JSON_IMAGE_FILE_NAME, comp.getFileName())
                .add(JSON_CAPTION, comp.getCaption())
                .build();
        return js;
    } 
    private JsonObject makeVideoObject(Component c){
        VideoComponent comp = (VideoComponent) c;
        JsonObject js = Json.createObjectBuilder()
                .add(JSON_TYPE, comp.getType())
                .add(JSON_VIDEO_PATH, comp.getPath())
                .add(JSON_VIDEO_FILE_NAME, comp.getFileName())
                .add(JSON_CAPTION, comp.getCaption())
                .build();
        return js;
    } 
    private JsonObject makeListObject(Component c){
        ListComponent comp = (ListComponent) c;
        JsonObject js = Json.createObjectBuilder()
                .add(JSON_TYPE, comp.getType())
                .add(JSON_TITLE, comp.getTitle())
                .add(JSON_VIDEO_FILE_NAME, createListArray(comp.getElements()))
                .build();
        return js;
    } 
    
}
