
package Components;

/**
 *
 * @author Freddy Estevez
 */
public class Slide {
     String path;
    String caption;
    String fileName;
    
    public String getPath() {
        return path;
    }
    
    public Slide(String path, String fileName, String caption) {
        this.path = path;
        this.caption = caption;
        this.fileName = fileName;
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
