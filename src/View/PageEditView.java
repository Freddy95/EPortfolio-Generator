
package View;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

/**
 *
 * @author Freddy Estevez
 */
public class PageEditView extends VBox{
    
    public PageEditView(){
        setSpacing(30);
        getStyleClass().add("pageEditView");
    }
    
    public void addComponent(ComponentEditView v){
        getChildren().add(v);
    }

}
