package View;

import Components.Component;
import Dialog.AddBannerImageDialog;
import Dialog.AddImageDialog;
import Dialog.AddListDialog;
import Dialog.AddParagraphDialog;
import Dialog.AddSlideShowDialog;
import Dialog.AddVideoDialog;
import Page.Page;
import Dialog.SelectDialog;
import Dialog.SetDialog;
import File.FileController;
import File.FileManager;
import eportfoliogenerator.EPortfolio;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
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
    VBox pageEditor;
    PageEditView pageEditorView;
    ScrollPane pageEditorScrollPane;
    Stage primaryStage;

    Page currentPage;
    ArrayList<Label> pages;
    List<Node> siteToolbarChildren;
    Label currentLabelPage;
    Label studentName;
    Label footer;
    ImageView bannerImage;
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

    public EPortfolioGeneratorView() {

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
        saveDisabled(true);
        //pane.setCenter(pageEditorScrollPane);
    }

    public void makeUI() {
        System.out.println("left Width: " + siteToolbar.getWidth());

        pane.setLeft(siteToolbar);
        saveDisabled(false);
        System.out.println("left Width: " + siteToolbar.getWidth());

    }

    public void setPageWorkSpace() {

        pane.setCenter(pageEditorPane);
        pageEditorView.setPrefWidth(getWidth() * .79);

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

    public Button initButton(Pane toolbar, String title, boolean disabled) {
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
        exit = initChildButton(fileToolbar, "icons/exit.png", "Exit", false);
        ePortfolioTitle = new Label("");
        ePortfolioTitle.getStyleClass().add("ePortfolioTitle");
        fileToolbar.getChildren().add(ePortfolioTitle);
    }

    public void initSiteToolbar() {
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
     * Adds a pages to siteToolbar
     */
    public void addPage(Label p) {
        siteToolbar.getChildren().add(p);
    }

    public void addMultiplePages(ArrayList<Page> ePortfolioPages) {
        this.pages.clear();
        Iterator<Page> iter = ePortfolioPages.iterator();
        while (iter.hasNext()) {
            currentPage = iter.next();
            footer.setText(currentPage.getFooter());
            removePage.setDisable(false);

            Label l = new Label(currentPage.getTitle());
            l.setOnMouseClicked(d -> {
                for (int i = 0; i < pages.size(); i++) {
                    this.pages.get(i).getStyleClass().clear();
                }

                l.getStyleClass().add("currentPage");
                currentLabelPage = l;
                currentPage = currentEPortfolio.getPages().get(pages.indexOf(l));
                footer.setText(currentPage.getFooter());
                setBannerImage();

                reloadPane();
            });
            this.pages.add(l);
            for (int i = 0; i < this.pages.size(); i++) {
                this.pages.get(i).getStyleClass().clear();
            }

            l.getStyleClass().add("currentPage");
            currentLabelPage = l;
            addPage(l);
            if (pane.getCenter() == null) {
                setPageWorkSpace();
            }
            if (!currentPage.getBannerImagePath().equals("")) {

                try {
                    File file = new File(currentPage.getBannerImagePath());

                    URL url = file.toURI().toURL();
                    bannerImage = new ImageView(new Image(url.toExternalForm()));

                } catch (Exception x) {
                }
            } else {
                bannerImage = null;
            }
            reloadPane();
        }
    }

    /**
     * Initializes the window size
     *
     * @param primaryStage
     */
    public void initWindow(Stage primaryStage) {
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
     * initializes the page edit view which different components the user can
     * add onto.
     */
    public void initPageEditView() {
        workSpace = new HBox();
        pageEditorView = new PageEditView();
        pageEditorView.getStyleClass().add("pageEditView");
        pageEditor = new VBox(10);

        pageEditor.getStyleClass().add("bannerImage");
        //bannerImage.setPreserveRatio(true);
        studentName = new Label("Add Student Name Here");
        footer = new Label();
        footer.getStyleClass().add("footer");
        studentName.getStyleClass().add("studentName");
        studentName.setOnMouseClicked(e -> {
            SetDialog d = new SetDialog();
            d.display("Enter Student Name", "Enter Student Name");
            d.getButton().setOnAction(a -> {
                studentName.setText(d.getValue());
                currentEPortfolio.setStudentName(d.getValue());
                reloadPane();
                d.getWindow().close();
            });
        });
        pageEditor.getChildren().add(pageEditorView);
        pageEditor.getChildren().add(studentName);

        //pageEditor.setMinWidth(getWidth());
        //pageEditor.setPrefWidth(getWidth());
//        
//       
        pageEditorPane = new TabPane();
        Tab pageEditorTab = new Tab();
        pageEditorTab.setText("Edit Page");
        Tab pageViewerTab = new Tab();
        pageViewerTab.setText("View Page");
        webView = new WebView();
        engine = webView.getEngine();
        pageViewerTab.setContent(webView);
        pageEditorTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {
                System.out.println("nnnfalsemmm");
                if (siteToolbar.getChildren().isEmpty()) {
                    for (int i = 0; i < siteToolbarChildren.size(); i++) {
                        siteToolbar.getChildren().add(siteToolbarChildren.get(i));
                    }

                }

            }
        });
        pageViewerTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event t) {
                System.out.println("nnnTruennn");
                if (!siteToolbar.getChildren().isEmpty()) {
                    siteToolbarChildren = new ArrayList<>();
                    for (int i = 0; i < siteToolbar.getChildren().size(); i++) {
                        siteToolbarChildren.add(siteToolbar.getChildren().get(i));
                    }
                    siteToolbar.getChildren().clear();
                    if (isSaveEnabled()) {
                        controller.handleSaveEPortfolioRequest();
                        saveDisabled(true);
                    }
                    File file = new File("EPortfolios/" + currentEPortfolio.getTitle() + "/" + currentPage.getTitle() + ".html");
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

                }

            }
        });
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
    public void initWorkSpace() {
        workSpaceToolbar = new VBox(10);
        workSpaceToolbar.getStyleClass().add("workSpaceToolbar");
        changePageTitle = initButton(workSpaceToolbar, "Set Page Title", false);
        selectBannerImage = initButton(workSpaceToolbar, "Select Banner Image", false);
        selectLayout = initButton(workSpaceToolbar, "Select  Layout", false);
        selectColorTemplate = initButton(workSpaceToolbar, "Select Color Theme", false);
        selectFont = initButton(workSpaceToolbar, "Select Font", false);
        addComponent = initButton(workSpaceToolbar, "Add Component", false);
        removeComponent = initButton(workSpaceToolbar, "Remove Component", false);
        setFooter = initButton(workSpaceToolbar, "Set Footer", false);

    }

    /**
     * Return width of the workspace.
     */
    public double getWidth() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        width = bounds.getWidth();
        return width;
    }

    /**
     * when a change is made to the content reload the workspace.
     */
    public void reloadPane() {
        // components = new VBox();

        removeDisabled();
        pageEditorView.getChildren().clear();
        pageEditor.getChildren().clear();
        pageEditor.getChildren().add(studentName);

        if (bannerImage != null) {
            pageEditor.getChildren().add(bannerImage);
        }
        pageEditor.getChildren().add(pageEditorView);
        ComponentEditView view = null;

        for (int i = 0; i < currentPage.getComponents().size(); i++) {
            Component b = currentPage.getComponents().get(i);
            view = new ComponentEditView(b, currentPage, this);
            view.setMaxWidth(getWidth() * .79);
            pageEditorView.getChildren().add(view);
            if (view.isSelected()) {
                view.select();
            } else {
                view.deselect();
            }
        }
        pageEditor.getChildren().add(footer);
        saveDisabled(false);

    }

    public void initSiteToolbarHandlers() {
        addPage.setOnAction(e -> {
            removePage.setDisable(false);
            currentPage = new Page();
            currentEPortfolio.addPage(currentPage);
            bannerImage = null;
            footer.setText("");
            currentPage.setTitle("Page " + (pages.size() + 1));
            Label l = new Label(currentPage.getTitle());
            l.setOnMouseClicked(d -> {
                for (int i = 0; i < pages.size(); i++) {
                    pages.get(i).getStyleClass().clear();
                }

                l.getStyleClass().add("currentPage");
                currentLabelPage = l;
                currentPage = currentEPortfolio.getPages().get(pages.indexOf(l));
                footer.setText(currentPage.getFooter());
                setBannerImage();

                reloadPane();
            });
            pages.add(l);
            for (int i = 0; i < pages.size(); i++) {
                pages.get(i).getStyleClass().clear();
            }
            l.getStyleClass().add("currentPage");
            currentLabelPage = l;
            addPage(l);
            if (pane.getCenter() == null) {
                setPageWorkSpace();
            }
            reloadPane();
        });

        removePage.setOnAction(e -> {
            int ind = currentEPortfolio.getPages().indexOf(currentPage);
            siteToolbar.getChildren().remove(pages.get(ind));
            pages.remove(ind);
            if (ind == pages.size()) {
                ind--;
            }

            currentEPortfolio.getPages().remove(currentPage);
            if (pages.isEmpty()) {
                pane.setCenter(null);
                removePage.setDisable(true);
                return;
            }
            pages.get(ind).getStyleClass().add("currentPage");
            currentLabelPage = pages.get(ind);
            currentPage = currentEPortfolio.getPages().get(ind);
            setBannerImage();
            reloadPane();

        });

    }

    public void initPageEditorHandlers() {
        ArrayList<String> components = new ArrayList<>();
        addComponent.setOnAction(e -> {
            components.clear();
            components.add("Paragraph");
            components.add("Image");
            components.add("Video");
            components.add("List");
            components.add("Slide Show");
            SelectDialog dia = new SelectDialog(components);
            dia.display("Add Component", "Select Type of"
                    + " Component");
            dia.getButton().setOnAction(c -> {
                addComponent(dia.getValue());
                dia.close();
            });

        });

        selectFont.setOnAction(e -> {
            components.clear();
            components.add("Courgette");
            components.add("Ubuntu");
            components.add("Dosis");
            components.add("Average");
            components.add("Oxygen");

            SelectDialog dia = new SelectDialog(components);
            dia.display("Select Font", "Select Font for the Page to use");
            dia.getButton().setOnAction(b -> {
                currentPage.setFont(dia.getValue());
                pageEditorView.getStyleClass().add(dia.getValue());
                reloadPane();
                dia.close();
            });

        });

        selectColorTemplate.setOnAction(e -> {
            components.clear();
            components.add("Blue");
            components.add("Red");
            components.add("Green");
            components.add("Gray");
            components.add("Yellow");
            SelectDialog dia = new SelectDialog(components);
            dia.display("Select Color", "Select Color Template for the Page");
            dia.getButton().setOnAction(a -> {
                currentPage.setColorTheme(dia.getValue());
                dia.close();
            });
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
                dia.close();
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

        removeComponent.setOnAction(e -> {
            int size = currentPage.getComponents().size();
            for (int i = 0; i < size; i++) {
                Component c = currentPage.getComponents().get(i);
                if (c.isSelected()) {
                    ComponentEditView v = new ComponentEditView(c, currentPage, this);
                    pageEditorView.getChildren().remove(v);
                    currentPage.getComponents().remove(c);
                    size--;

                    if (i == size) {
                        i--;
                    }
                    if (size == 0); else {
                        currentPage.getComponents().get(i).setSelected(true);
                    }
                    reloadPane();

                }
            }
        });
        setFooter.setOnAction(e -> {
            SetDialog d = new SetDialog();
            d.display("Set Footer", "Enter Footer Text");
            d.getButton().setOnAction(s -> {
                currentPage.setFooter(d.getValue());
                footer.setText(currentPage.getFooter());
                reloadPane();
                d.getWindow().close();
            });

        });
        selectBannerImage.setOnAction(e -> {
            AddBannerImageDialog d = new AddBannerImageDialog(currentPage, this);
            d.display("Add Banner Image");
            d.getButton().setOnAction(a -> {
                bannerImage = new ImageView();
                bannerImage.setImage(d.getImage());
                pageEditor.getChildren().clear();
                pageEditor.getChildren().addAll(studentName, bannerImage);
                d.close();

            });
        });
    }

    /**
     * initialize handlers of buttons on file toolbar.
     */
    public void initFileToolbarHandlers() {
        save.setOnAction(e -> {
            save.setDisable(controller.handleSaveEPortfolioRequest());
        });
        saveAs.setOnAction(e -> {

            SetDialog d = new SetDialog();
            d.display("Save EPortfolio", "Save EPortfolio As");
            d.getButton().setOnAction(x -> {
                currentEPortfolio.setTitle(d.getValue());
                ePortfolioTitle.setText(d.getValue());
                if (!save.isDisabled()) {
                    save.setDisable(controller.handleSaveEPortfolioRequest());
                }
                d.getWindow().close();
            });
        });
        newEPortfolio.setOnAction(e -> {
            if (!save.isDisabled()) {
                try {
                    controller.promptToSave();
                } catch (IOException ex) {
                    Logger.getLogger(EPortfolioGeneratorView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            SetDialog d = new SetDialog();
            d.display("Create EPortfolio", "Set title of EPortfolio");
            d.getButton().setOnAction(c -> {
                currentEPortfolio = new EPortfolio();
                currentEPortfolio.setTitle(d.getValue());
                makeUI();
                ePortfolioTitle.setText(d.getValue());

                d.getWindow().close();

                removePageEditSpace();
            });

        });

        changeTitle.setOnAction(e -> {
            SetDialog d = new SetDialog();
            d.display("Set Title", "Set title of EPortfolio");
            d.getButton().setOnAction(c -> {
                currentEPortfolio = new EPortfolio();
                currentEPortfolio.setTitle(d.getValue());
                saveDisabled(false);
                ePortfolioTitle.setText(d.getValue());
                d.getWindow().close();

            });
        });

        load.setOnAction(e -> {
            if (!save.isDisabled()) {
                try {
                    controller.promptToSave();
                } catch (IOException ex) {
                    Logger.getLogger(EPortfolioGeneratorView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            currentEPortfolio = new EPortfolio();
            controller.handleLoadEPortfolioRequest();
            makeUI();
            ePortfolioTitle.setText(currentEPortfolio.getTitle());
            studentName.setText(currentEPortfolio.getStudentName());
            this.pages.clear();
            siteToolbar.getChildren().clear();
            siteToolbar.getChildren().addAll(addPage, removePage);
            addMultiplePages(currentEPortfolio.getPages());
            reloadPane();
            saveDisabled(true);

        });

       

    }

    public boolean isSaveEnabled() {
        return !save.isDisabled();
    }

    public EPortfolio getEPortfolio() {
        return currentEPortfolio;
    }

    public Stage getWindow() {
        return primaryStage;
    }

    public void addComponent(String type) {
        if (type.equals("Paragraph")) {
            AddParagraphDialog d = new AddParagraphDialog(currentPage, this);
            d.display("Add Paragraph");
        } else if (type.equals("Image")) {
            AddImageDialog d = new AddImageDialog(currentPage, this);
            d.display("Add Image");
        } else if (type.equals("Video")) {
            AddVideoDialog d = new AddVideoDialog(currentPage, this);
            d.display("Add Video");
        } else if (type.equals("Slide Show")) {
            AddSlideShowDialog d = new AddSlideShowDialog(currentPage, this);
            d.display();
        } else if (type.equals("List")) {
            AddListDialog d = new AddListDialog(currentPage, this);
            d.display();

        }//add slide show component
    }

    public void removeComponents() {
        pageEditorView.getChildren().clear();
    }

    public void removeDisabled() {
        if (currentPage == null) {
            removeComponent.setDisable(true);
            return;
        }
        if (currentPage.getComponents().isEmpty()) {
            removeComponent.setDisable(true);
        }
        for (int i = 0; i < currentPage.getComponents().size(); i++) {
            if (currentPage.getComponents().get(i).isSelected()) {
                removeComponent.setDisable(false);
                return;
            }
        }
        removeComponent.setDisable(true);
    }

    public void saveDisabled(boolean saved) {
        if (currentEPortfolio == null) {
            save.setDisable(true);
            saveAs.setDisable(true);
            changeTitle.setDisable(true);
            export.setDisable(true);

        } else {
            saveAs.setDisable(false);
            changeTitle.setDisable(false);
            export.setDisable(false);
            save.setDisable(saved);
        }
    }

    public void setBannerImage() {
        if (!currentPage.getBannerImagePath().equals("")) {
            try {
                File file = new File(currentPage.getBannerImagePath());

                URL url = file.toURI().toURL();
                bannerImage = new ImageView(new Image(url.toExternalForm()));

            } catch (Exception x) {
            }
        } else {
            bannerImage = null;
        }
    }

    public void removePageEditSpace() {
        pane.getChildren().clear();
        pane.setTop(fileToolbar);
        pane.setLeft(siteToolbar);
        siteToolbar.getChildren().clear();
        siteToolbar.getChildren().addAll(addPage, removePage);
        pages.clear();

        removePage.setDisable(true);

    }
}
