
package Components;

/**
 *
 * @author Freddy Estevez
 */
public class VideoComponent extends Component{
    String path;
    String caption;

    public VideoComponent(String path, String caption, String type) {
        this.path = path;
        this.caption = caption;
        super.setType(type);
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
    

}
