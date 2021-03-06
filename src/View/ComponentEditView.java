package View;

import Dialog.LinkDialog;
import Dialog.AddParagraphDialog;
import Components.Component;
import Components.ImageComponent;
import Components.ListComponent;
import Components.ParagraphComponent;
import Components.Slide;
import Components.SlideShowComponent;
import Components.VideoComponent;
import Dialog.AddImageDialog;
import Dialog.AddListDialog;
import Dialog.AddSlideShowDialog;
import Dialog.AddVideoDialog;
import eportfoliogenerator.EPortfolioGenerator;
import Page.Page;
import View.EPortfolioGeneratorView;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

/**
 *
 * @author Freddy Estevez
 */
public class ComponentEditView extends HBox {

    Component component;
    Button editComponent;
    Page page;
    EPortfolioGeneratorView ui;
    int index;

    public ComponentEditView(Component comp, Page p, EPortfolioGeneratorView initUi) {
        getStyleClass().add("componentEditView");
        
        page = p;
        setSpacing(30);
        editComponent = new Button("Edit");
        editComponent.getStyleClass().add("editButton");
        component = comp;
         ui = initUi;
         
        if (component.getType().equals("Paragraph")) {
            initParagraph();
        }

        if (component.getType().equals("Image")) {
            initImage();
        }

        if (component.getType().equals("Video")) {
            initVideo();
        }

        if (component.getType().equals("List")) {
            initList();
        }
        if (component.getType().equals("Slide Show")) {
           initSlideShow();
        }
        initHandler();
       
    }

    /**
     * Initialize a paragraph component onto the page edit space.
     */
    public void initParagraph() {
       
        ParagraphComponent c = (ParagraphComponent) component;
        VBox para = new VBox(15);
        Label heading = new Label(c.getHeader());
        
        heading.getStyleClass().add("heading");
        TextArea text = new TextArea(c.getText());
        text.setEditable(false);
        text.setMaxWidth(700);
        text.setMinHeight(100);
        text.setMaxHeight(500);
        text.setWrapText(true);
        if (c.getFont() != null && !(c.getFont().equals(""))) {
            heading.getStyleClass().add(c.getFont());
            text.getStyleClass().add(c.getFont());
        } else {
            heading.getStyleClass().add(page.getFont());
            text.getStyleClass().add(page.getFont());
        }

        para.getChildren().addAll(heading, text);
        
        getChildren().add(para);
       
        getChildren().add(editComponent);
        editComponent.setOnAction(e -> {
            AddParagraphDialog dia = new AddParagraphDialog(page, ui);
            dia.editDisplay(c);
        });

       

    }

    /**
     * Initializes an image component onto the page edit space.
     */
    public void initImage() {
        ImageComponent image = (ImageComponent) component;
        VBox img = new VBox(15);
        img.getStyleClass().add("component2");
        Label cap = new Label(image.getCaption());
        cap.getStyleClass().add("heading");
        cap.getStyleClass().add(page.getFont());
        File file = new File(image.getPath());
        URL url;
        try {
            url = file.toURI().toURL();

            Image im = new Image(url.toExternalForm());
            ImageView view = new ImageView(im);
//        double scaledWidth = 500;
//        double perc = scaledWidth / im.getWidth();
//        double scaledHeight = im.getHeight() * perc;
            view.setFitWidth(image.getWidth());
            view.setFitHeight(image.getHeight());
            view.setSmooth(true);
            view.setCache(true);
            getStyleClass().add(image.getPosition() + "ComponentEditView");
            view.getStyleClass().add(image.getPosition());
            img.getChildren().addAll(cap, view);
            getChildren().addAll(img, editComponent);
            editComponent.setOnAction(e -> {
                AddImageDialog d = new AddImageDialog(page, ui);
                d.editDisplay(image);
            });
        } catch (MalformedURLException ex) {
            Logger.getLogger(ComponentEditView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes a video component onto the page edit space.
     */
    public void initVideo() {
        VideoComponent video = (VideoComponent) component;
        File file = new File(video.getPath());

        try {
            URL url = file.toURI().toURL();

            Media media = new Media(url.toExternalForm());
            MediaPlayer player = new MediaPlayer(media);
            MediaView view = new MediaView(player);
            Label cap = new Label(video.getCaption());
            cap.getStyleClass().add(page.getFont());
            cap.getStyleClass().add("leftCaption");
            view.setFitWidth(video.getWidth());
            view.setFitHeight(video.getHeight());
            VBox play = new VBox(20);
            Button playPause = new Button();
            Image playImage = new Image("file:icons/play.png");
            Image pauseImage = new Image("file:icons/pause.png");
            playPause.setGraphic(new ImageView(playImage));
            playPause.setOnAction(e -> {
                ImageView v = (ImageView) playPause.getGraphic();
                if (v.getImage().equals(playImage)) {
                    player.play();
                    playPause.setGraphic(new ImageView(pauseImage));
                } else {
                    player.pause();
                    playPause.setGraphic(new ImageView(playImage));
                }
            });
            play.setAlignment(Pos.CENTER);
            play.getChildren().addAll(cap, view, playPause);
            editComponent.setOnAction(e -> {
                AddVideoDialog d = new AddVideoDialog(page, ui);
                d.editDisplay(video);
            });

            getChildren().addAll(play, editComponent);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ComponentEditView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initList() {
        ListComponent list = (ListComponent) component;
        ListView listView = new ListView();
        listView.getItems().addAll(list.getElements());
        listView.setMaxSize(200, 300);
        Label title = new Label(list.getTitle());
        listView.getStyleClass().add(page.getFont());
        title.getStyleClass().add(page.getFont());
        title.getStyleClass().add("leftCaption");
        VBox content = new VBox(15);
        content.getChildren().addAll(title, listView);
        getChildren().addAll(content, editComponent);
        editComponent.setOnAction(e -> {
            AddListDialog d = new AddListDialog(page, ui);
            d.editDisplay(list);
        });
    }

    public void initSlideShow() {
        SlideShowComponent slideShow = (SlideShowComponent) component;
        final int size = slideShow.getSlides().size();
        index = 0;
        ImageView view = new ImageView();

        Slide initSlide = slideShow.getSlides().get(0);
        File file = new File(initSlide.getPath());
        Label title = new Label(slideShow.getTitle());
        title.getStyleClass().add("heading");
        title.getStyleClass().add(page.getFont());
        
        HBox btns = new HBox(30);
        URL url;
        VBox content = new VBox(20);
        try {
            url = file.toURI().toURL();

            Image image = new Image(url.toExternalForm());
            view.setImage(image);
            view.setFitWidth(500);
            view.setPreserveRatio(true);
            Label caption = new Label();
            title.setAlignment(Pos.CENTER);
            caption.setAlignment(Pos.CENTER);
            caption.getStyleClass().add("caption");
            caption.getStyleClass().add(page.getFont());
            caption.setText(initSlide.getCaption());
            caption.setAlignment(Pos.CENTER);
            Button nextSlide = new Button();
            Button previousSlide = new Button();
            Image next = new Image("file:icons/rightArrow.png");
            Image previous = new Image("file:icons/leftArrow.png");
            nextSlide.setGraphic(new ImageView(next));
            previousSlide.setGraphic(new ImageView(previous));
            btns.getChildren().addAll(previousSlide, nextSlide);
            btns.setAlignment(Pos.CENTER);
            nextSlide.setOnAction(e -> {
                index++;

                if (index == size) {
                    index = 0;
                }
                view.setImage(null);
                Slide slide = slideShow.getSlides().get(index);
                File file2 = new File(slide.getPath());
                URL url2;
                try {
                    url2 = file2.toURI().toURL();

                    Image image2 = new Image(url2.toExternalForm());
                    view.setImage(image2);
                    view.setFitWidth(500);
                    view.setPreserveRatio(true);
                    caption.setText(slide.getCaption());
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ComponentEditView.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            previousSlide.setOnAction(e -> {
                index--;

                if (index < 0) {
                    index = size - 1;
                }

                Slide slide = slideShow.getSlides().get(index);
                File file2 = new File(slide.getPath());
                URL url2;
                try {
                    url2 = file2.toURI().toURL();

                    Image image2 = new Image(url2.toExternalForm());
                    view.setImage(image2);
                    view.setFitWidth(500);
                    view.setPreserveRatio(true);
                    caption.setText(slide.getCaption());
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ComponentEditView.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            editComponent.setOnAction(e->{
                AddSlideShowDialog d = new AddSlideShowDialog(page, ui);
                d.editDisplay(slideShow); 
            });
            content.getChildren().addAll(title, view, caption, btns);
            getChildren().addAll(content, editComponent);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ComponentEditView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void select() {

        DropShadow ds = new DropShadow();
        ds.setColor(Color.RED);
        ds.setOffsetX(2.0);
        ds.setOffsetY(2.0);
        setEffect(ds);
    }

    public boolean isSelected() {
        return component.isSelected();
    }

    public void setSelected(boolean selected) {
        component.setSelected(selected);
    }

    public void deselect() {
        DropShadow ds = new DropShadow();
        ds.setColor(Color.WHITE);
        ds.setOffsetX(0.0);
        ds.setOffsetY(0.0);
        setEffect(ds);
    }

    public int getIndexOfLink(int start, String text) {
        int i = 0;
        while (text.lastIndexOf("***", start - 1) != -1) {
            start = text.lastIndexOf("***", start - 1);
            start = text.lastIndexOf("***", start - 1);
            i++;
        }

        return i;
    }
    
    public void initHandler(){
        setOnMouseClicked(e->{
            
            List<Component> views = page.getComponents();
            for(int i = 0; i < views.size(); i++){
                views.get(i).setSelected(false);   
            }
            component.setSelected(true);
            ui.reloadPane();
        });
    }
    
    public void currentComponent(){
        List<Component> views = page.getComponents();
            for(int i = 0; i < views.size(); i++){
                views.get(i).setSelected(false);   
            }
            component.setSelected(true);
            //ui.reloadPane();
    }

}
