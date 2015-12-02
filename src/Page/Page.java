
package Page;

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
    String layout;
    String colorTheme;
    String font;

    public Page() {
        components = new ArrayList<>();
        URL = "";
        bannerImagePath = "";
        bannerTitle = "";
        title = "";
        layout = "";
        font = "";
        colorTheme = "";
    }
    
    
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

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(String colorTheme) {
        this.colorTheme = colorTheme;
    }

    public String getBannerImagePath() {
        return bannerImagePath;
    }

    public void setBannerImagePath(String bannerImagePath) {
        this.bannerImagePath = bannerImagePath;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
    
    
}
