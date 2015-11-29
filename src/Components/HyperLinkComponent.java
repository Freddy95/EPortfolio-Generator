
package Components;

import java.util.ArrayList;

/**
 *
 * @author Freddy Estevez
 */
public class HyperLinkComponent  extends Component{
    ArrayList<String> URL;
    String text;
    String header;

    public HyperLinkComponent(String header, String text) {
        URL = new ArrayList<>();
        this.text = text;
        this.header = header;
        super.setType("HyperLink");
    }

    public ArrayList<String> getURL() {
        return URL;
    }

    public void addURL(String URL) {
       this.URL.add(URL);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
    
    

}
