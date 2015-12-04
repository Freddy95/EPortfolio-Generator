
package Components;

import java.util.ArrayList;

/**
 *
 * @author Freddy Estevez
 */
public class SlideShowComponent extends Component{
    String title;
    ArrayList<Slide> slides;
    
    public SlideShowComponent(String title){
        slides = new ArrayList<>();
        this.title = title;
        super.setType("Slide Show");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Slide> getSlides() {
        return slides;
    }

    public void addSlide(Slide slide) {
       slides.add(slide);
    }
    
    public void removeSlide(Slide slide){
        for(Slide s : slides){
            if(s == slide){
                slides.remove(s);
                break;
            }
        }
    }

}
