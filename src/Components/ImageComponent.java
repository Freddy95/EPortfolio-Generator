
package Components;

/**
 * Represents an image component with an image and caption.
 * @author Freddy Estevez
 */
public class ImageComponent extends Component{
    String path;
    String caption;

    
    String fileName;

    public ImageComponent(String caption, String path) {
        this.path = path;
        this.caption = caption;
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
}
