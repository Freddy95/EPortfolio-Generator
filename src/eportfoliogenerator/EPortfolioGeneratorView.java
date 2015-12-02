
package eportfoliogenerator;

import Components.Component;
import EPortfolioGeneratorUI.AddBannerImageDialog;
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
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
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
     VBox workSpaceToolbar;
     HBox workSpace;
     PageEditView pageEditor;
     ScrollPane pageEditorScrollPane;
     Stage primaryStage;
    
     Page currentPage;
     ArrayList<Label> pages;
     Label currentLabelPage;
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
     TabPane pageEditorPane;
    public EPortfolioGeneratorView(){
        
    }

    public void start(Stage primaryStage) {
        
        comps = new ArrayList<>();
        initFileToolbar();
        initSiteToolbar();
        controller = new FileController(this, new FileManager());
        initWorkSpace();
        pane = new BorderPane();
        pane.setTop(fileToolbar);
        //pane.setLeft(siteToolbar);
        //pane.setRight(workSpaceToolbar);
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("Style/EPortfolioGeneratorStyle.css");
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Oxygen");
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Ubuntu");
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Dosis");
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Average");
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Courgette");
        primaryStage.setScene(scene);
        primaryStage.setTitle("EPortfolio Generator");
        primaryStage.getIcons().add(new Image("file:icons/icon.png"));
        initWindow(primaryStage);
        initFileToolbarHandlers();
        initSiteToolbarHandlers();
        initPageEditorHandlers();
        initPageEditView();
        initStuff();
        //pane.setCenter(pageEditorScrollPane);

    }
    public void makeUI(){
                System.out.println("left Width: " + siteToolbar.getWidth());

        pane.setLeft(siteToolbar);
                System.out.println("left Width: " + siteToolbar.getWidth());


        
    }
    
    public void setPageWorkSpace(){
        
        pane.setCenter(pageEditorPane);
        pageEditor.setPrefWidth(getWidth() * .79);
        

        
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
        siteToolbar.setPrefWidth(getWidth() * .08);
        addPage = initChildButton(siteToolbar, "icons/add.png", "Add Page", false);
        addPage.getStyleClass().add("siteToolbarButton");
        removePage = initChildButton(siteToolbar, "icons/Remove.png", "Remove Current Page", true);
        removePage.getStyleClass().add("siteToolbarButton");
        
        pages = new ArrayList<>();

        
    }
    /**
    * Adds some pages to siteToolbar
    */
    public void addPages(Label p){
        siteToolbar.getChildren().add(p);
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
          workSpace = new HBox();
          pageEditor = new PageEditView();
          pageEditor.getStyleClass().add("pageEditView");
          
          //pageEditor.setMinWidth(getWidth());
          //pageEditor.setPrefWidth(getWidth());
//        
//       
          pageEditorPane = new TabPane();
          Tab pageEditorTab = new Tab();
          pageEditorTab.setText("Edit Page");
          Tab pageViewerTab = new Tab();
          pageViewerTab.setText("View Page");
          pageEditorPane.getTabs().add(pageEditorTab);
          pageEditorPane.getTabs().add(pageViewerTab);
          pageEditorScrollPane = new ScrollPane(pageEditor);
          workSpaceToolbar.setPrefWidth(getWidth() * .13);
          workSpace.getChildren().add(pageEditorScrollPane);
          workSpace.getChildren().add(workSpaceToolbar);
          pageEditorTab.setContent(workSpace);
         

    }
    
    /**
     * initializes the workspace/buttons to use to edit the current page of the
     * EPortfolio.
     */
    public void initWorkSpace(){
        workSpaceToolbar = new VBox(10);
        workSpaceToolbar.getStyleClass().add("workSpaceToolbar");
        changePageTitle = initButton(workSpaceToolbar, "Set Page Title", false);
        setName = initButton(workSpaceToolbar, "Set Name",  false);
        selectBannerImage = initButton(workSpaceToolbar, "Select Banner Image",  false);
        selectLayout = initButton(workSpaceToolbar, "Select  Layout", false);
        selectColorTemplate = initButton(workSpaceToolbar, "Select Color Theme",  false);
        selectFont = initButton(workSpaceToolbar, "Select Font",  false);
        addComponent = initButton(workSpaceToolbar, "Add Component", false);
        removeComponent = initButton(workSpaceToolbar, "Remove Component", false);
        setFooter = initButton(workSpaceToolbar, "Set Footer", false);
        
    }
    /**
    * Return width of the workspace.
    */
    public double getWidth(){
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
//        System.out.println("Width: " + bounds.getWidth());
//        System.out.println("left Width: " + siteToolbar.getMaxWidth());
//        workSpaceToolbar.maxWidthProperty();
// 
//        System.out.println("right Width: " + workSpaceToolbar.getMaxWidth());
//        
//        width = bounds.getWidth() - (siteToolbar.getWidth()*4.3);
//        width -= workSpaceToolbar.getWidth();
//        System.out.println("Width: " + width);
//        width = bounds.getWidth() * .8;
        width = bounds.getWidth();
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
    
    public void initSiteToolbarHandlers(){
         addPage.setOnAction(e -> {
            removePage.setDisable(false);
            currentPage = new Page();
            currentEPortfolio.addPage(currentPage);
            currentPage.setTitle("Page " + (pages.size()+1));
            Label l = new Label(currentPage.getTitle());
            l.setOnMouseClicked(d -> {
                for(int i = 0; i < pages.size(); i++){
                    pages.get(i).getStyleClass().clear();
                }
                l.getStyleClass().add("currentPage");
                currentPage = currentEPortfolio.getPages().get(pages.indexOf(l));
                reloadPane();
            });
            pages.add(l);
            for(int i = 0; i < pages.size(); i++){
                    pages.get(i).getStyleClass().clear();
                }
            l.getStyleClass().add("currentPage");
            currentLabelPage = l;
            addPages(l);
            if(pane.getCenter() == null)
                setPageWorkSpace();
            reloadPane();
        });
         
       removePage.setOnAction(e -> {
           int ind = currentEPortfolio.getPages().indexOf(currentPage);
           siteToolbar.getChildren().remove(pages.get(ind));
           pages.remove(ind);
           if(ind == pages.size())
               ind--;
           
           
           currentEPortfolio.getPages().remove(currentPage);
           if(pages.isEmpty()){
               pane.setCenter(null);
               removePage.setDisable(true);
               return;
           }
           pages.get(ind).getStyleClass().add("currentPage");
           currentLabelPage = pages.get(ind);
           currentPage = currentEPortfolio.getPages().get(ind);
           reloadPane();
           
       });
       
        
    }
    
    public void initPageEditorHandlers(){
        ArrayList<String> components = new ArrayList<>();
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
            dia.getButton().setOnAction(x -> {
                currentPage.setLayout(dia.getValue());
               
            });
        }); 
        changePageTitle.setOnAction(e -> {
            SetDialog d = new SetDialog();
            d.display("Enter Title", "Enter Title of Page");
            d.getButton().setOnAction(b -> {
                currentLabelPage.setText(d.getValue());
                currentPage.setTitle(d.getValue());
                d.getWindow().close();
            });
        });  
        setName.setOnAction(e -> {
            SetDialog d = new SetDialog();
            d.display("Enter Banner Text", "Enter Banner Text");
            d.getButton().setOnAction(a -> {
                currentPage.setBannerTitle(d.getValue());
                d.getWindow().close();
            });
            
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
    }
    /**
     * initialize handlers of buttons on file toolbar.
     */
    public void initFileToolbarHandlers(){
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
            }else if(type.equals("List")){
                AddListDialog d = new AddListDialog();
                d.display("Add List");
                
            }//add eportfolio generator into paragraph component
    }
    
    
}


