
package EPortfolioGeneratorUI;

import Components.Component;
import java.util.ArrayList;

/**
 * This represents a page in the eportfolio. Holds information such as the 
 * components on the page,the banner image and title.
 * @author Freddy Estevez
 */
public class Page {
    String title;
    ArrayList<Component> components;
    String URL;
    String bannerImagePath;
    String bannerTitle;
    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }
    
    public void addComponent(Component c){
        components.add(c);
    }
   
    public String getPath(){
        return bannerImagePath;
    }
    
    public void setPath(String s){
        bannerImagePath = s;
    }
}
