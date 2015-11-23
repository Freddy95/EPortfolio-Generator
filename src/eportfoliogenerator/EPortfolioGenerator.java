/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eportfoliogenerator;

import EPortfolioGeneratorUI.AddImageDialog;
import EPortfolioGeneratorUI.AddListDialog;
import EPortfolioGeneratorUI.AddParagraphDialog;
import EPortfolioGeneratorUI.AddVideoDialog;
import EPortfolioGeneratorUI.SelectDialog;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Freddy Estevez
 */
public class EPortfolioGenerator extends Application {

    static FlowPane fileToolbar;
    static VBox siteToolbar;
    static VBox workSpace;
    static VBox top;
    static Label currentPage;
    static ArrayList<Label> pages;
    static HBox addRemove;
    static WebView webView;
    static WebEngine engine;
    
    
    static Button addPage;
    static Button removePage;
    static Button newEportfolio;
    static Button save;
    static Button saveAs;
    static Button export;
    static Button exit;
    static Button load;
    static Button setTitle;
    static Button setName;
    static Button addComponent;
    static Button removeComponent;
    static Button editComponent;
    static Button selectFont;
    static Button selectLayout;
    static Button selectBannerImage;
    static Button selectColorTemplate;

    @Override
    public void start(Stage primaryStage) {
        initFileToolbar();
        initSiteToolbar();
        initPageEditView();
        initWorkSpace();
        BorderPane pane = new BorderPane();
        pane.setTop(fileToolbar);
        pane.setLeft(siteToolbar);
        pane.setCenter(webView);
        pane.setRight(workSpace);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Eportfolio Generator");
        primaryStage.getIcons().add(new Image("file:icons/icon.png"));
        initWindow(primaryStage);
        AddVideoDialog dia = new AddVideoDialog();
        dia.display("Add Image");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * 
     * @param toolbar - toolbar in which to add button to.
     * @param iconPath - icon path of button image
     * @param toolTip - small description of button use
     * @param disabled - whether or not button is disabled at launch.
     * @return button
     */
    public static Button initChildButton(
            Pane toolbar,
            String iconPath,
            String toolTip,
            boolean disabled) {
        String imagePath = "file:" + iconPath;
        Image buttonImage = new Image(imagePath);
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(toolTip);
        button.setTooltip(buttonTooltip);
        toolbar.getChildren().add(button);
        return button;
    }
    
    public static Button initButton(Pane toolbar, String title,String toolTip, boolean disabled){  
        Button button = new Button(title);
        button.setDisable(disabled);
        Tooltip buttonTooltip = new Tooltip(toolTip);
        button.setTooltip(buttonTooltip);
        toolbar.getChildren().add(button);
        return button;
    }
    
    /**
     * Initialize the file toolbar with buttons such as load, save, etc.
     */
    public static void initFileToolbar() {
        fileToolbar = new FlowPane();
        fileToolbar.setHgap(20);
        newEportfolio = initChildButton(fileToolbar, "icons/new.png", "New Eportfolio", false);
        load = initChildButton(fileToolbar, "icons/load.png", "Load Eportfolio", false);
        save = initChildButton(fileToolbar, "icons/save.png", "Save", false);
        saveAs = initChildButton(fileToolbar, "icons/saveAs.png", "Save As", false);
        export = initChildButton(fileToolbar, "icons/export.png", "Export Eportfolio", false);
        exit = initChildButton(fileToolbar, "icons/exit.png", "Exit", false);
    }
    
    public static void initSiteToolbar(){
        siteToolbar = new VBox(10);
        addRemove = new HBox(10);
        currentPage = new Label();
        addPage = initChildButton(addRemove, "icons/add.png", "Export Eportfolio", false);
        removePage = initChildButton(addRemove, "icons/Remove.png", "Export Eportfolio", false);
        siteToolbar.getChildren().add(addRemove);
        currentPage.setText("Current page");
        pages = new ArrayList<>();
        pages.add(new Label("Page 1"));
        pages.add(new Label("Page 2"));
        //pages.add(currentPage);
        siteToolbar.getChildren().add(currentPage);
        addPages();
        
    }
    
    public static void addPages(){
        for (int i = 0; i < pages.size(); i++) {
            siteToolbar.getChildren().add(pages.get(i));
        }
    }
    
    public static void initWindow(Stage primaryStage){
        // GET THE SIZE OF THE SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // AND USE IT TO SIZE THE WINDOW
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.show();
    }
    
    
    public static void initPageEditView(){
        webView = new WebView();
        engine = webView.getEngine();
        engine.load("file:index.html");
    }
    
    
    public static void initWorkSpace(){
        workSpace = new VBox(10);
        setTitle = initButton(workSpace, "Set Title", "", false);
        setName = initButton(workSpace, "Set Name", "", false);
        selectLayout = initButton(workSpace, "Select  Layout", "", false);
        selectColorTemplate = initButton(workSpace, "Select Color Theme", "", false);
        selectFont = initButton(workSpace, "Select Font", "", false);
        editComponent = initButton(workSpace, "Edit Component", "", false);
        addComponent = initButton(workSpace, "Add Component", "", false);
        removeComponent = initButton(workSpace, "Remove Component", "", false);
    }
}
