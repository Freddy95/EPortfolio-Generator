/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eportfoliogenerator;

import Components.Component;
import Components.ImageComponent;
import Components.ParagraphComponent;
import EPortfolioGeneratorUI.AddBannerImageDialog;
import EPortfolioGeneratorUI.AddHyperLinkDialog;
import EPortfolioGeneratorUI.AddImageDialog;
import EPortfolioGeneratorUI.AddListDialog;
import EPortfolioGeneratorUI.AddParagraphDialog;
import EPortfolioGeneratorUI.AddSlideShowDialog;
import EPortfolioGeneratorUI.AddVideoDialog;
import EPortfolioGeneratorUI.ComponentEditView;
import EPortfolioGeneratorUI.PageEditView;
import EPortfolioGeneratorUI.RemoveComponentDialog;
import EPortfolioGeneratorUI.SelectDialog;
import EPortfolioGeneratorUI.SetDialog;
import EPortfolioGeneratorUI.SiteView;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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
    static EPortfolio currentEPortfolio;
    static BorderPane pane;
    static FlowPane fileToolbar;
    static VBox siteToolbar;
    static VBox workSpace;
    static PageEditView pageEditor;
    static ScrollPane pageEditorScrollPane;
    
    static Label currentPage;
    static ArrayList<Label> pages;
    static ArrayList<Component> comps;
    
    static WebView webView;
    static WebEngine engine;
    
    static double width;
    
    static Button addPage;
    static Button removePage;
    
    static Button newEPortfolio;
    static Button save;
    static Button saveAs;
    static Button export;
    static Button exit;
    static Button load;
    static Button changeTitle;
    static Label ePortfolioTitle;
    
    static Button changePageTitle;
    static Button setName;
    static Button addComponent;
    static Button removeComponent;
    static Button selectFont;
    static Button selectLayout;
    static Button selectBannerImage;
    static Button selectColorTemplate;
    static Button setFooter;
    static Button pageEditView;
    static Button siteEditView;
    static Button toggleView;

    @Override
    public void start(Stage primaryStage) {
        comps = new ArrayList<>();
        initFileToolbar();
        initSiteToolbar();
        
        initWorkSpace();
        pane = new BorderPane();
        pane.setTop(fileToolbar);
        //pane.setLeft(siteToolbar);
        //pane.setRight(workSpace);
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("EPortfolio Generator");
        primaryStage.getIcons().add(new Image("file:icons/icon.png"));
        initWindow(primaryStage);
        initHandlers();
        initPageEditView();
        //pane.setCenter(pageEditorScrollPane);

    }
    
    public static void makeUI(){
        pane.setLeft(siteToolbar);
        pane.setRight(workSpace);
        pane.setCenter(pageEditorScrollPane);
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
    
    public static Button initButton(Pane toolbar, String title, boolean disabled){  
        Button button = new Button(title);
        button.setDisable(disabled);
        toolbar.getChildren().add(button);
        return button;
    }
    
    /**
     * Initialize the file toolbar with buttons such as load, save, etc.
     */
    public static void initFileToolbar() {
        fileToolbar = new FlowPane();

        fileToolbar.getStyleClass().add("fileToolbar");
        fileToolbar.setHgap(20);
        newEPortfolio = initChildButton(fileToolbar, "icons/new.png", "New EPortfolio", false);
        load = initChildButton(fileToolbar, "icons/load.png", "Load EPortfolio", false);
        save = initChildButton(fileToolbar, "icons/save.png", "Save", false);
        saveAs = initChildButton(fileToolbar, "icons/saveAs.png", "Save As", false);
        changeTitle = initChildButton(fileToolbar, "icons/editTitle.png", "Change EPortfolio Title", false);
        export = initChildButton(fileToolbar, "icons/export.png", "Export EPortfolio", false);
        toggleView = initChildButton(fileToolbar, "icons/site.png", "Site View", false);
        exit = initChildButton(fileToolbar, "icons/exit.png", "Exit", false);
        ePortfolioTitle = new Label("");
        fileToolbar.getChildren().add(ePortfolioTitle);
    }
    
    public static void initSiteToolbar(){
        siteToolbar = new VBox(20);
        siteToolbar.getStyleClass().add("siteToolbar");
        currentPage = new Label();
        addPage = initChildButton(siteToolbar, "icons/add.png", "Add Page", false);
        removePage = initChildButton(siteToolbar, "icons/Remove.png", "Remove Current Page", false);
        currentPage.setText("Current page");
        currentPage.getStyleClass().add("currentPage");
        
        pages = new ArrayList<>();

        
    }
    /**
    * Adds some pages to siteToolbar
    */
    public static void addPages(){
        for (int i = 0; i < pages.size(); i++) {
            siteToolbar.getChildren().add(pages.get(i));
        }
    }
    /**
    * Initializes the window size
    */
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
    
    /**
     * initializes the page edit view which different components the user can add
     * onto.
     */
    public static void initPageEditView(){
          pageEditor = new PageEditView();
          pageEditor.getStyleClass().add("pageEditView");
          
          pageEditor.setMinWidth(getWidth());
//        
//       
          pageEditorScrollPane = new ScrollPane(pageEditor);

    }
    
    /**
     * initializes the workspace/buttons to use to edit the current page of the
     * EPortfolio.
     */
    public static void initWorkSpace(){
        workSpace = new VBox(10);
        workSpace.getStyleClass().add("workSpace");
        changePageTitle = initButton(workSpace, "Set Page Title", false);
        setName = initButton(workSpace, "Set Name",  false);
        selectBannerImage = initButton(workSpace, "Select Banner Image",  false);
        selectLayout = initButton(workSpace, "Select  Layout", false);
        selectColorTemplate = initButton(workSpace, "Select Color Theme",  false);
        selectFont = initButton(workSpace, "Select Font",  false);
        addComponent = initButton(workSpace, "Add Component", false);
        removeComponent = initButton(workSpace, "Remove Component", false);
        setFooter = initButton(workSpace, "Set Footer", false);
        
    }
    /**
    * Return width of the workspace.
    */
    public static double getWidth(){
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        width = bounds.getWidth() - siteToolbar.getWidth();
        width -= workSpace.getWidth();
        return width;
    }
    
    /**
     * when a change is made to the content reload the workspace.
     */
    public static void reloadPane(){
       // components = new VBox();
        pageEditor.getChildren().clear();
        for (Component b : comps) {
            ComponentEditView view = new ComponentEditView(b);
            pageEditor.getChildren().add(view);
            if (b.isSelected()) {
                view.select();
            } else {
                view.deselect();
            }
        }
    }
    /**
     * initialize handlers of buttons
     */
    public static void initHandlers(){
        ArrayList<String> components = new ArrayList<>();
        
        newEPortfolio.setOnAction(e ->{
            if(currentEPortfolio != null){
                promptToSave();
            }
            SetDialog d = new SetDialog();
            d.display("Create EPortfolio", "Set title of EPortfolio");
            d.getButton().setOnAction(c -> {
                currentEPortfolio = new EPortfolio();
                currentEPortfolio.setTitle(d.getValue());
                makeUI();
                ePortfolioTitle.setText(d.getValue());
                d.getWindow().close();            
            });
            
        });
        addComponent.setOnAction(e -> {
            components.clear();
            components.add("Paragraph");
            components.add("Image");
            components.add("Video");
            components.add("List");
            components.add("Slide Show");
            components.add("HyperLink Text");
            SelectDialog dia = new SelectDialog(components);
            dia.display("Add Component", "Select Type of"
                    + " Component");
        });
        
        selectFont.setOnAction(e ->{
            components.clear();
            components.add("Font 1");
            components.add("Font 2");
            components.add("Font 3");
            components.add("Font 4");
            components.add("Font 5");
            
            SelectDialog dia = new SelectDialog(components);
            dia.display("Select Font", "Select Font for the Page to use");
        });
        
        
        selectColorTemplate.setOnAction(e -> {
            components.clear();
            components.add("Blue");
            components.add("Red");
            components.add("Orange");
            components.add("Gray");
            components.add("Yellow");
            SelectDialog dia = new SelectDialog(components);
            dia.display("Select Color", "Select Color Template for the Page");
        });
        selectLayout.setOnAction(e -> {
            components.clear();
            components.add("Layout 1");
            components.add("Layout 2");
            components.add("Layout 3");
            components.add("Layout 4");
            components.add("Layout 5");
            SelectDialog dia = new SelectDialog(components);
            dia.display("Select Layout", "Select Layout for the Page to use");
        });
        changePageTitle.setOnAction(e -> {
            SetDialog d = new SetDialog();
            d.display("Enter Title", "Enter Title of Page");
        });  
        setName.setOnAction(e -> {
            SetDialog d = new SetDialog();
            d.display("Enter Banner Text", "Enter Banner Text");
        });  
        removeComponent.setOnAction(e -> {
            RemoveComponentDialog d = new RemoveComponentDialog();
            d.display("Remove Component");
        });
        setFooter.setOnAction(e -> {
            SetDialog d = new SetDialog();
            d.display("Enter Footer Text", "Enter Footer Text");
        });
        selectBannerImage.setOnAction(e -> {
            AddBannerImageDialog d = new AddBannerImageDialog();
            d.display("Add Banner Image");
        });
        
        toggleView.setOnAction(e -> {
            SiteView v = new SiteView();
            v.display();
        });
        
    }
    
    
    public static void promptToSave(){
        
    }
    
    public static boolean isSaveEnabled(){
        return save.isDisabled();
    }
    
    public static EPortfolio getEPortfolio(){
        return currentEPortfolio;
    }
}
