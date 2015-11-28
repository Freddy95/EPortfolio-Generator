
package Components;

/**
 *
 * @author Freddy Estevez
 */
public class VideoComponent extends Component{
    String path;
    String caption;
    String fileName;

    public VideoComponent(String path, String caption) {
        this.path = path;
        this.caption = caption;
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
    

}
