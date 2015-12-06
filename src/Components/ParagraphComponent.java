
package Components;

import java.util.ArrayList;

/**
 *
 * @author Freddy Estevez
 */
public class ParagraphComponent extends Component{
    String header;
    String text;
    String font;
    ArrayList<String> links;

    public ParagraphComponent() {
        header = "";
        text = "";
        font = "";
        super.setType("Paragraph");
        links = new ArrayList<>();
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        if(font != null)
            this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public void addLink(String l){
        links.add(l);
    }
    
    public void removeLink(String s){
        links.remove(s);
    }
    
    public ArrayList<String> getLinks(){
        return links;
    }
    
}
