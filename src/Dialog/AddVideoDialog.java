package Dialog;

import Components.Component;
import Components.VideoComponent;
import Controller.SelectionController;
import Page.Page;
import View.EPortfolioGeneratorView;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Freddy Estevez
 */
public class AddVideoDialog {

    Stage window;
    Scene scene;
    Label labelCaption;
    Label labelWidth;
    Label labelHeight;
    VBox size;
    HBox content;
    TextField width;
    TextField height;
    TextField caption;
    Button okBtn;
    Button addVideo;
    File file;
    Page page;
    Media media;
    MediaPlayer player;
    MediaView view;
    EPortfolioGeneratorView ui;

    public AddVideoDialog(Page page, EPortfolioGeneratorView ui) {
        this.page = page;
        this.ui = ui;
    }

    public void initDisplay() {
        labelCaption = new Label("Enter a Caption");
        addVideo = new Button("Select Video");
        okBtn = new Button("OK");
        caption = new TextField();

        labelHeight = new Label("Enter the Height");
        height = new TextField();
        labelWidth = new Label("Enter the Width");
        width = new TextField();
        size = new VBox(10);
        size.getChildren().addAll(labelWidth, width, labelHeight, height);
        content = new HBox(15);
        content.getChildren().add(size);
    }

    public void display(String title) {

        initDisplay();
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.getChildren().addAll(labelCaption, caption, content, addVideo, okBtn);
        scene = new Scene(layout, 300, 300);
        window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialog");

        addVideo.setOnAction(e -> {
            SelectionController c = new SelectionController();
            file = c.processSelectVideo();
            URL url;
            try {
                url = file.toURI().toURL();
                media = new Media(url.toExternalForm());
                player = new MediaPlayer(media);
                view = new MediaView(player);
                view.setFitWidth(400);
                view.setFitHeight(400);
                content.getChildren().clear();
                content.getChildren().addAll(view, size);
                scene.setRoot(new VBox());
                scene = new Scene(layout, 600, 550);
                scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
                window.setScene(scene);
            } catch (MalformedURLException ex) {
                Logger.getLogger(AddVideoDialog.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        okBtn.setOnAction(e -> {
            if (file == null) {
                return;
            }
            VideoComponent video = new VideoComponent(file.getPath(), caption.getText());
            video.setWidth(width.getText());
            video.setHeight(height.getText());
            page.addComponent(video);
            select(video);
            ui.reloadPane();
            window.close();
        });
        window.showAndWait();

    }

    public void editDisplay(VideoComponent videoComponent) {
        initDisplay();
        URL url;

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(15, 15, 15, 15));
        file = new File(videoComponent.getPath());
        try {
            url = file.toURI().toURL();
            media = new Media(url.toExternalForm());
            player = new MediaPlayer(media);
            view = new MediaView(player);
            view.setFitWidth(400);
            view.setFitHeight(400);
           content.getChildren().clear();
           content.getChildren().addAll(view, size);
            scene = new Scene(layout, 600, 550);
            scene.setRoot(new VBox());
            window = new Stage();
            scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
            window.setScene(scene);
        } catch (MalformedURLException ex) {
            Logger.getLogger(AddVideoDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        caption.setText(videoComponent.getCaption());
        
        width.setText(Double.toString(videoComponent.getWidth()));
        height.setText(Double.toString(videoComponent.getHeight()));
        layout.getChildren().addAll(labelCaption, caption, content, addVideo, okBtn);
        scene = new Scene(layout, 600, 550);

        window = new Stage();
        window.setTitle("Edit Video");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        layout.getStyleClass().add("dialog");
        addVideo.setOnAction(e -> {
            SelectionController c = new SelectionController();
            file = c.processSelectVideo();
            URL url2;
            try {
                url2 = file.toURI().toURL();
                media = new Media(url2.toExternalForm());
                player = new MediaPlayer(media);
                view = new MediaView(player);
                view.setFitWidth(400);
                view.setFitHeight(400);
                content.getChildren().clear();
                content.getChildren().addAll(view, size);
                scene.setRoot(new VBox());
                scene = new Scene(layout, 600, 550);
                scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
                window.setScene(scene);
            } catch (MalformedURLException ex) {
                Logger.getLogger(AddVideoDialog.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        okBtn.setOnAction(e -> {
            if (file == null) {
                return;
            }
            videoComponent.setCaption(caption.getText());
            videoComponent.setPath(file.getPath());
            videoComponent.setFileName(file.getName());
            videoComponent.setWidth(width.getText());
            videoComponent.setHeight(height.getText());
            select(videoComponent);
            ui.reloadPane();
            window.close();
        });
        
        window.show();
    }

    
     public void select(Component c){
        List<Component> views = page.getComponents();
            for(int i = 0; i < views.size(); i++){
                views.get(i).setSelected(false);   
            }
          c.setSelected(true);
    }
}
