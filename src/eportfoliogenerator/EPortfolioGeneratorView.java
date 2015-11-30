
package eportfoliogenerator;

import Components.Component;
import EPortfolioGeneratorUI.AddBannerImageDialog;
import EPortfolioGeneratorUI.AddHyperLinkDialog;
import EPortfolioGeneratorUI.AddImageDialog;
import EPortfolioGeneratorUI.AddListDialog;
import EPortfolioGeneratorUI.AddParagraphDialog;
import EPortfolioGeneratorUI.AddSlideShowDialog;
import EPortfolioGeneratorUI.AddVideoDialog;
import EPortfolioGeneratorUI.ComponentEditView;
import EPortfolioGeneratorUI.Page;
import EPortfolioGeneratorUI.PageEditView;
import EPortfolioGeneratorUI.RemoveComponentDialog;
import EPortfolioGeneratorUI.SelectDialog;
import EPortfolioGeneratorUI.SetDialog;
import EPortfolioGeneratorUI.SiteView;
import File.FileController;
import File.FileManager;
import java.util.ArrayList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Freddy Estevez
 */
public class EPortfolioGeneratorView {
     FileController controller;
    
     EPortfolio currentEPortfolio;
     BorderPane pane;
     FlowPane fileToolbar;
     VBox siteToolbar;
     VBox workSpace;
     PageEditView pageEditor;
     ScrollPane pageEditorScrollPane;
     Stage primaryStage;
    
     Page currentPage;
     ArrayList<Label> pages;
     ArrayList<Component> comps;
    
     WebView webView;
     WebEngine engine;
    
     double width;
    
     Button addPage;
     Button removePage;
    
     Button newEPortfolio;
     Button save;
     Button saveAs;
     Button export;
     Button exit;
     Button load;
     Button changeTitle;
     Label ePortfolioTitle;
    
     Button changePageTitle;
     Button setName;
     Button addComponent;
     Button removeComponent;
     Button selectFont;
     Button selectLayout;
     Button selectBannerImage;
     Button selectColorTemplate;
     Button setFooter;
     Button pageEditView;
     Button siteEditView;
     Button toggleView;
    public EPortfolioGeneratorView(){
        
    }

    public void start(Stage primaryStage) {
        
        comps = new ArrayList<>();
        initFileToolbar();
        initSiteToolbar();
        //controller = new FileController(this, new FileManager());
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
        initStuff();
        //pane.setCenter(pageEditorScrollPane);

    }
    public void makeUI(){
        pane.setLeft(siteToolbar);
        pane.setRight(workSpace);
        pane.setCenter(pageEditorScrollPane);
    }

    public void initStuff(){
        currentEPortfolio = new EPortfolio();
        currentEPortfolio.setTitle("NEW TITLE");
        Page p = new Page();
        p.setTitle("My title");
        p.setBannerTitle("My title");
        p.setPath("COULD BE A PATH");
       // p.addComponent(new ParagraphComponent("", "My stuff"));
        currentEPortfolio.addPage(p);
    }
   
    /**
     * 
     * @param toolbar - toolbar in which to add button to.
     * @param iconPath - icon path of button image
     * @param toolTip - small description of button use
     * @param disabled - whether or not button is disabled at launch.
     * @return button
     */
    public Button initChildButton(
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
    
    public Button initButton(Pane toolbar, String title, boolean disabled){  
        Button button = new Button(title);
        button.setDisable(disabled);
        toolbar.getChildren().add(button);
        return button;
    }
    
    /**
     * Initialize the file toolbar with buttons such as load, save, etc.
     */
    public void initFileToolbar() {
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
    
    public void initSiteToolbar(){
        siteToolbar = new VBox(20);
        siteToolbar.getStyleClass().add("siteToolbar");
        addPage = initChildButton(siteToolbar, "icons/add.png", "Add Page", false);
        removePage = initChildButton(siteToolbar, "icons/Remove.png", "Remove Current Page", false);
        
        pages = new ArrayList<>();

        
    }
    /**
    * Adds some pages to siteToolbar
    */
    public void addPages(){
        for (int i = 0; i < pages.size(); i++) {
            siteToolbar.getChildren().add(pages.get(i));
        }
    }
    /**
    * Initializes the window size
    */
    public void initWindow(Stage primaryStage){
        // GET THE SIZE OF THE SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        this.primaryStage = primaryStage;

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
    public void initPageEditView(){
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
    public void initWorkSpace(){
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
    public double getWidth(){
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        width = bounds.getWidth() - siteToolbar.getWidth();
        width -= workSpace.getWidth();
        return width;
    }
    
    /**
     * when a change is made to the content reload the workspace.
     */
    public void reloadPane(){
       // components = new VBox();
        pageEditor.getChildren().clear();
        for (Component b : currentPage.getComponents()) {
            ComponentEditView view = new ComponentEditView(b, currentPage, this);
            view.setMaxWidth(pageEditor.getWidth());
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
    public void initHandlers(){
        ArrayList<String> components = new ArrayList<>();
        save.setOnAction(e->{
            controller.handleSaveEPortfolioRequest();
        });
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
            dia.getButton().setOnAction(c -> {
                addComponent(dia.getValue());
                dia.close();
            });
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
        
        addPage.setOnAction(e -> {
            currentPage = new Page();
            currentEPortfolio.addPage(currentPage);
            currentPage.setTitle("My page");
            pages.add(new Label(currentPage.getTitle()));
            addPages();
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
    
    
    public void promptToSave(){
        
    }
    
    public boolean isSaveEnabled(){
        return save.isDisabled();
    }
    
    public EPortfolio getEPortfolio(){
        return currentEPortfolio;
    }
    
    public Stage getWindow(){
        return primaryStage;
    }
    
    public void addComponent(String type){
         if(type.equals("Paragraph")){
                AddParagraphDialog d = new AddParagraphDialog(currentPage, this);
                d.display("Add Paragraph");
                
            }else if(type.equals("Image")){
                AddImageDialog d = new AddImageDialog(currentPage);
                d.display("Add Image");
            }else if(type.equals("Video")){
                AddVideoDialog d = new AddVideoDialog();
                d.display("Add Video");
            }else if(type.equals("Slide Show")){
                AddSlideShowDialog d = new AddSlideShowDialog();
                d.display("Add Slide Show");
            }else if(type.equals("HyperLink Text")){
                AddHyperLinkDialog d = new AddHyperLinkDialog();
                d.display("Add HyperLink Text");
            }else if(type.equals("List")){
                AddListDialog d = new AddListDialog();
                d.display("Add List");
                
            }//add eportfolio generator into paragraph component
    }
}
