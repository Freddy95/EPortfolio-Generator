
package Components;

/**
 *
 * @author Freddy Estevez
 */
public class VideoComponent extends Component{
    String path;
    String caption;
    String fileName;
    double width = 400;
    double height = 400;

    public VideoComponent(String path, String caption) {
        this.path = path;
        this.caption = caption;
        super.setType("Video");
    }

    public VideoComponent() {
        path = "";
        caption = "";
        super.setType("Video");
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

    public double getWidth() {
        return width;
    }

    public void setWidth(String width) {
        try{
            this.width = getNumeric(width);
        }catch(NumberFormatException e){
            this.width = 400;
        }
    }

    public double getHeight() {
        return height;
    }

   public void setHeight(String height) {
        try{
            this.height = getNumeric(height);
        }catch(NumberFormatException e){
            this.height = 400;
        }
    }
    
    public double getNumeric(String str) throws NumberFormatException {
        
        double d = 0;
        d = Double.parseDouble(str);
       
        return d;
    }

}
