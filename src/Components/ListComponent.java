
package Components;

import java.util.ArrayList;
import javax.json.JsonValue;

/**
 *
 * @author Freddy Estevez
 */
public class ListComponent extends Component{

    String title;
    ArrayList<String> elements;

    public ListComponent(String title) {
        this.title = title;
        elements = new ArrayList<>();
        super.setType("List");
    }
    
    
    
    public String getTitle() {
        return title;
    }
    
    public ArrayList<String> getElements(){
        return elements;
    }
    
    public void addElements(String s){
        elements.add(s);
    }

}
