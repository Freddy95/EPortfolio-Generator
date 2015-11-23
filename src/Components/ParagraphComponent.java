
package Components;

/**
 *
 * @author Freddy Estevez
 */
public class ParagraphComponent extends Component{
    String header;
    String text;

    public ParagraphComponent(String header, String text, String type) {
        this.header = header;
        this.text = text;
        super.setType(type);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
