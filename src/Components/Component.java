
package Components;

/**
 * Generic component class
 * @author Freddy Estevez
 */
public class Component {
    //Type of Component.
    String type;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    boolean selected;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
