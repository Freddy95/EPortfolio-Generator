package File;

import Components.Component;
import Components.ImageComponent;
import Components.ListComponent;
import Components.ParagraphComponent;
import Components.Slide;
import Components.SlideShowComponent;
import Components.VideoComponent;
import Page.Page;
import eportfoliogenerator.EPortfolio;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public static String JSON_EPORTFOLIO_TITLE = "ePortfolio_title";
    public static String JSON_TITLE = "title";
    public static String JSON_PAGES = "pages";
    public static String JSON_CAPTION = "caption";
    public static String JSON_NAME = "name";
    public static String JSON_PAGE_TITLE = "page_title";
    public static String JSON_LINKS = "links";
    public static String JSON_URL = "url";
    public static String JSON_TYPE = "type";
    public static String JSON_HEADING = "heading";
    public static String JSON_STUDENT_NAME = "student_name";
    public static String JSON_BANNER_IMAGE = "banner_image";
    public static String JSON_CONTENT = "content";
    public static String JSON_SLIDES = "slides";
    public static String JSON_TEXT = "text";
    public static String JSON_FONT = "font";
    public static String JSON_COLOR = "color_theme";
    public static String JSON_LAYOUT = "layout";
    public static String JSON_POSITION = "position";
    public static String JSON_WIDTH = "width";
    public static String JSON_HEIGHT = "height";
    public static String JSON_PAGE_INFO = "page_info";
    public static String JSON_IMAGE_FILE_NAME = "image_file_name";
    public static String JSON_IMAGE_PATH = "image_path";
    public static String JSON_VIDEO_PATH = "file_path";
    public static String JSON_VIDEO_FILE_NAME = "file_name";
    public static String JSON_EXT = ".json";
    public static String JSON_ELEMENTS = "elements";
    public static String SLASH = "/";

    public EPortfolio ePortfolio;

    /**
     * This method saves all the data associated with a slide show to a JSON
     * file.
     *
     * @param ePortfolio The course whose data we are saving.
     *
     * @throws IOException Thrown when there are issues writing to the JSON
     * file.
     */
    public void saveEPortfolio(EPortfolio ePortfolio) throws IOException {
        // BUILD THE FILE PATH
        this.ePortfolio = ePortfolio;
        File folder = new File("EPortfolios" + SLASH + ePortfolio.getTitle());
            if(folder.exists()){
                folder.delete();
            }
            
                folder.mkdir();
           File file = new File("EPortfolios" + SLASH + ePortfolio.getTitle() + SLASH + ePortfolio.getTitle() + ".txt");
           BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            
        for (int i = 0; i < ePortfolio.getPages().size(); i++) {
            Page p = ePortfolio.getPages().get(i);
            String title = "" + p.getTitle();
            writer.write(title);
            writer.newLine();
            String jsonFilePath = "EPortfolios" + SLASH + ePortfolio.getTitle()+ SLASH + title + JSON_EXT;

        // INIT THE WRITER
            
            OutputStream os = new FileOutputStream(jsonFilePath);
            JsonWriter jsonWriter = Json.createWriter(os);

            ArrayList<Page> pages = ePortfolio.getPages();
            JsonArray pageArray = makePageArray(pages);
            // NOW BUILD THE COURSE USING EVERYTHING WE'VE ALREADY MADE
            JsonObject courseJsonObject = Json.createObjectBuilder()
                    .add(JSON_EPORTFOLIO_TITLE, ePortfolio.getTitle())
                    .add(JSON_STUDENT_NAME, ePortfolio.getStudentName())
                    .add(JSON_PAGE_TITLE, p.getTitle())
                    .add(JSON_PAGES, pageArray)
                    .add(JSON_PAGE_INFO, makePageInfoObject(p))
                    .build();

            // AND SAVE EVERYTHING AT ONCE
            jsonWriter.writeObject(courseJsonObject);
        }
        writer.close();
    }

    private JsonObject makePageInfoObject(Page p) {
        JsonObject js = Json.createObjectBuilder()
                .add(JSON_BANNER_IMAGE, p.getPath())
                .add(JSON_LAYOUT, p.getLayout())
                .add(JSON_FONT, p.getFont())
                .add(JSON_COLOR, p.getColorTheme())
                .add(JSON_CONTENT, makeContentArray(p))
                .build();
        return js;
    }

    private JsonObject makePageObject(Page p, int i) {

        JsonObject js = Json.createObjectBuilder()
                .add(JSON_PAGE_TITLE, p.getTitle())
                .add(JSON_URL, "page" + i + ".html")
                .build();
        return js;
    }

    private JsonArray makePageArray(List<Page> pages) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (int i = 0; i < pages.size(); i++) {
            Page p = pages.get(i);
            jsb.add(makePageObject(p, i));
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    /**
     * This method loads the contents of a JSON file representing a slide show
     * into a SlideSShowModel object.
     *
     * @param EPortfoloToLoad The slide show to load
     * @param jsonFilePath The TXT file which contains all json files to load.
     * @throws IOException
     */
    public void loadEPortfolio(EPortfolio ePortfolioToLoad, File ePortfolioFile) throws IOException {
        // LOAD THE JSON FILE WITH ALL THE DATA
        ePortfolio = ePortfolioToLoad;
        String ePortfolioPath = ePortfolioFile.getPath();
        ePortfolio.setTitle(ePortfolioFile.getName().substring(0, ePortfolioFile.getName().indexOf(".txt")));
         try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(ePortfolioPath);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String path = ePortfolioFile.getPath().substring(0, ePortfolioFile.getPath().indexOf(ePortfolioFile.getName()));
                JsonObject js = loadJSONFile(path + line + JSON_EXT);
                Page p = new Page();
                p.setTitle(line);
                loadPage(js, p);
            }   
            
            // Always close files.
            bufferedReader.close();         
        } catch (Exception e){
            //throw IOException e  = new IOException();
        }
        // NOW LOAD THE COURSE
       
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
    
    private void loadPage(JsonObject js, Page page){
        ePortfolio.setStudentName(js.getString(JSON_STUDENT_NAME));
        JsonObject pageInfo = js.getJsonObject(JSON_PAGE_INFO);
        page.setTitle(js.getString(JSON_PAGE_TITLE));
        page.setBannerImagePath(pageInfo.getString(JSON_BANNER_IMAGE));
        page.setFont(pageInfo.getString(JSON_FONT));
        page.setColorTheme(pageInfo.getString(JSON_COLOR));
        JsonArray content = pageInfo.getJsonArray(JSON_CONTENT);
        for(int i = 0; i < content.size(); i++){
            JsonObject jo = content.getJsonObject(i);
            addComponent(page, jo);
            
            
        }
        ePortfolio.addPage(page);
    }
    
    private ArrayList<String> loadJSONArray(JsonObject jo, String arrayName) throws IOException {
       
        ArrayList<String> items = new ArrayList();
        JsonArray jsonArray = jo.getJsonArray(arrayName);
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
                .add(JSON_CAPTION, slide.getCaption())
                .build();
        return jso;
    }

    private JsonArray makeContentArray(Page p) {
        ArrayList<Component> comps = p.getComponents();
        JsonArrayBuilder jsb = Json.createArrayBuilder();

        for (Component c : comps) {
            if (c.getType().equals("Paragraph")) {
                jsb.add(makeParagraphJSONObject(c));
            } else if (c.getType().equals("Image")) {
                jsb.add(makeImageJSONObject(c));
            } else if (c.getType().equals("Video")) {
                jsb.add(makeVideoJSONObject(c));
            } else if (c.getType().equals("Slide Show")) {
                jsb.add(makeSlideShowJSONObject(c));
            } else if (c.getType().equals("List")) {
                jsb.add(makeListJSONObject(c));
            }
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    private JsonObject makeParagraphJSONObject(Component c) {
        ParagraphComponent comp = (ParagraphComponent) c;
        JsonObject js = Json.createObjectBuilder()
                .add(JSON_TYPE, comp.getType())
                .add(JSON_FONT, comp.getFont())
                .add(JSON_HEADING, comp.getHeader())
                .add(JSON_TEXT, comp.getText())
                .build();
        return js;
    }

    private JsonObject makeImageJSONObject(Component c) {
        ImageComponent comp = (ImageComponent) c;
        JsonObject js = Json.createObjectBuilder()
                .add(JSON_TYPE, comp.getType())
                .add(JSON_WIDTH, Double.toString(comp.getWidth()))
                .add(JSON_HEIGHT, Double.toString(comp.getHeight()))
                .add(JSON_POSITION, comp.getPosition())
                .add(JSON_IMAGE_PATH, comp.getPath())
                .add(JSON_IMAGE_FILE_NAME, comp.getFileName())
                .add(JSON_CAPTION, comp.getCaption())
                .build();
        return js;
    }

    private JsonObject makeVideoJSONObject(Component c) {
        VideoComponent comp = (VideoComponent) c;
        JsonObject js = Json.createObjectBuilder()
                .add(JSON_TYPE, comp.getType())
                .add(JSON_WIDTH, Double.toString(comp.getWidth()))
                .add(JSON_HEIGHT, Double.toString(comp.getHeight()))
                .add(JSON_VIDEO_PATH, comp.getPath())
                .add(JSON_VIDEO_FILE_NAME, comp.getFileName())
                .add(JSON_CAPTION, comp.getCaption())
                .build();
        return js;
    }

    private JsonObject makeListJSONObject(Component c) {
        ListComponent comp = (ListComponent) c;
        JsonObject js = Json.createObjectBuilder()
                .add(JSON_TYPE, comp.getType())
                .add(JSON_TITLE, comp.getTitle())
                .add(JSON_ELEMENTS, createListArray(comp.getElements()))
                .build();
        return js;
    } //create json array list from elements

    private JsonArray createListArray(ArrayList<String> elements) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (String s : elements) {
            jsb.add(s);
        }
        JsonArray jA = jsb.build();
        return jA;
    }

    private JsonObject makeSlideShowJSONObject(Component c) {
        SlideShowComponent comp = (SlideShowComponent) c;
        JsonObject js = Json.createObjectBuilder()
                .add(JSON_TYPE, comp.getType())
                .add(JSON_TITLE, comp.getTitle())
                .add(JSON_SLIDES, makeSlidesJsonArray(comp.getSlides()))
                .build();
        return js;
    }
    public void addComponent(Page page, JsonObject jo){
        String type = jo.getString(JSON_TYPE);
        if(type.equals("Paragraph"))
            page.addComponent(makeParagraphObject(jo));
        if(type.equals("Image"))
            page.addComponent(makeImageObject(jo));
        if(type.equals("Video"))
            page.addComponent(makeVideoObject(jo));
        if(type.equals("List"))
            page.addComponent(makeListObject(jo));
        if(type.equals("Slide Show"))
            page.addComponent(makeSlideShowObject(jo));
    }
    
    private ParagraphComponent makeParagraphObject(JsonObject jo){
        ParagraphComponent comp = new ParagraphComponent();
        comp.setFont(jo.getString(JSON_FONT));
        comp.setHeader(jo.getString(JSON_HEADING));
        comp.setText(jo.getString(JSON_TEXT));
        return comp;
    }
    
    private ImageComponent makeImageObject(JsonObject jo){
        ImageComponent comp = new ImageComponent();
        comp.setCaption(jo.getString(JSON_CAPTION));
        comp.setFileName(jo.getString(JSON_IMAGE_FILE_NAME));
        comp.setPath(jo.getString(JSON_IMAGE_PATH));
        comp.setPosition(jo.getString(JSON_POSITION));
        comp.setWidth(jo.getString(JSON_WIDTH));
        comp.setHeight(jo.getString(JSON_HEIGHT));
        return comp;
    }
    
    private VideoComponent makeVideoObject(JsonObject jo){
        VideoComponent video = new VideoComponent();
        video.setCaption(jo.getString(JSON_CAPTION));
        video.setFileName(JSON_VIDEO_FILE_NAME);
        video.setPath(JSON_VIDEO_PATH);
        video.setWidth(JSON_WIDTH);
        video.setHeight(JSON_HEIGHT);
        return video;
    }
    
    private ListComponent makeListObject(JsonObject jo){
        ListComponent comp = new ListComponent();
        comp.setTitle(jo.getString(JSON_TITLE));
        try {
            comp.setElements(loadJSONArray(jo, JSON_ELEMENTS));
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comp;
    }
    
    private SlideShowComponent makeSlideShowObject(JsonObject jo){
        SlideShowComponent slideShow = new SlideShowComponent();
        JsonArray slides = jo.getJsonArray(JSON_SLIDES);
        for(int i = 0; i < slides.size(); i++){
           JsonObject JSONSlide = slides.getJsonObject(i);
          slideShow.addSlide(makeSlideObject(JSONSlide));
        }
        return slideShow;
    }
    
    private Slide makeSlideObject(JsonObject JSONSlide){
        Slide s = new Slide();
        s.setCaption(JSONSlide.getString(JSON_CAPTION));
        s.setFileName(JSONSlide.getString(JSON_IMAGE_FILE_NAME));
        s.setPath(JSONSlide.getString(JSON_IMAGE_PATH));
        return s;
    }

}
