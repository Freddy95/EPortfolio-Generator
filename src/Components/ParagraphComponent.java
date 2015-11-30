
package Components;

/**
 *
 * @author Freddy Estevez
 */
public class ParagraphComponent extends Component{
    String header;
    String text;
    String font;

    public ParagraphComponent(String header, String text, String font) {
        this.header = header;
        this.text = text;
        this.font = font;
        super.setType("Paragraph");
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
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
