
package View;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author Freddy Estevez
 */
public class SiteView {
    
    WebView webView;
     WebEngine engine;
     Stage window;
     Scene scene;
    public void display(){
        webView = new WebView();
        engine = webView.getEngine();
        File file = new File("index.html");
        if (file.exists()); else {
            try {
                file.createNewFile();
            } catch (IOException ex) {
            }
        }
        
        try {
            engine.load(file.toURI().toURL().toExternalForm());
        } catch (MalformedURLException ex) {
        }
        
        scene = new Scene(webView);
        window = new Stage();
        window.setScene(scene);
        window.show();
    }
}
