package Components;

/**
 * Represents an image component with an image and caption.
 *
 * @author Freddy Estevez
 */
public class ImageComponent extends Component {

    String path;
    String caption;
    String position;
    double width = 500;
    double height = 200;

    String fileName;

    public ImageComponent(String caption, String path, String fileName,
            String position, String w, String h) {
        this.path = path;
        this.caption = caption;
        this.fileName = fileName;
        super.setType("Image");
        this.position = position;
        try{
            this.width = getNumeric(w);
        }catch(NumberFormatException e){
            this.width = 500;
        }
        try{
            this.height = getNumeric(h);
        }catch(NumberFormatException e){
            this.height = 200;
        }
    }
    
    public ImageComponent(){
        path = "";
        caption = "";
        fileName = "";
        width = 500;
        height = 200;
        super.setType("Image");
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public double getNumeric(String str) throws NumberFormatException {
        
        double d = 0;
        d = Double.parseDouble(str);
       
        return d;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(String width) {
        try{
            this.width = getNumeric(width);
        }catch(NumberFormatException e){
            this.width = 500;
        }
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(String height) {
        try{
            this.height = getNumeric(height);
        }catch(NumberFormatException e){
            this.height = 200;
        }
    }
    
    
}
