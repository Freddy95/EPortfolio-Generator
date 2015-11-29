
package File;

import eportfoliogenerator.EPortfolio;
import eportfoliogenerator.EPortfolioGenerator;
import java.io.File;
import java.io.IOException;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This class handles the saving and loading of eportfolios.
 * @author Freddy Estevez
 */
public class FileController {
     // WE WANT TO KEEP TRACK OF WHEN SOMETHING HAS NOT BEEN SAVED
    private boolean saved;
    boolean saveWork;
    //WE WANT TO KEEP TRACK OF IF THE USER SUPPLIED A NAME OF THE NEW SLIDESHOW
    static boolean cont;

    // THE APP UI
    private EPortfolioGenerator ui;

    // THIS GUY KNOWS HOW TO READ AND WRITE SLIDE SHOW DATA
    private FileManager ePortfolioIO;

    /**
     * This default constructor starts the program without a eportfolio file
     * being edited.
     *
     * @param ePortfolio The object that will be reading and writing slide
     * show data.
     */
    public FileController(EPortfolioGenerator initUI, FileManager initEPortfolio) {
        // NOTHING YET
        saved = true;
        ui = initUI;
        ePortfolioIO = initEPortfolio;
    }

    public void markAsEdited() {
        saved = false;
        //ui.updateToolbarControls(saved);
    }

    /**
     * This method starts the process of editing a new eportfolio. If a pose is
     * already being edited, it will prompt the user to save it first.
     */
    public void handleNewSlideShowRequest() {
        getSaved(ui.isSaveEnabled());
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToMakeNew = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToMakeNew = promptToSave();
            }

            // IF THE USER REALLY WANTS TO MAKE A NEW COURSE
            if (continueToMakeNew) {
                // RESET THE DATA, WHICH SHOULD TRIGGER A RESET OF THE UI
                EPortfolio ePortfolio = ui.getEPortfolio();
               // ePortfolio.reset();

                saved = false;
                ePortfolio.setTitle(getNewTitle());
                // REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
                // THE APPROPRIATE CONTROLS
                //ui.updateToolbarControls(saved);

                // TELL THE USER THE SLIDE SHOW HAS BEEN CREATED
                // @todo
            }
        } catch (IOException ioe) {
            //ErrorHandler eH = ui.getErrorHandler();
            // @todo provide error message
        }
    }

    /**
     * This method lets the user open a eportfolio saved to a file. It will also
     * make sure data for the current eportfolio is not lost.
     */
    public void handleLoadSlideShowRequest() {
        getSaved(ui.isSaveEnabled());
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToOpen = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToOpen = promptToSave();
            }

            // IF THE USER REALLY WANTS TO OPEN A POSE
            if (continueToOpen) {
                // GO AHEAD AND PROCEED MAKING A NEW POSE
                promptToOpen();
            }
        } catch (IOException ioe) {
           // ErrorHandler eH = ui.getErrorHandler();
            //@todo provide error message
        }
    }

    /**
     * This method will save the current eportfolio to a file. Note that we
     * already know the name of the file, so we won't need to prompt the user.
     */
    public boolean handleSaveEPortfolioRequest() {
        try {
            // GET THE SLIDE SHOW TO SAVE
            EPortfolio ePortfolioToSave = ui.getEPortfolio();
            //ui.saveCaps();
            System.out.print(ePortfolioToSave.getTitle());
            // SAVE IT TO A FILE
            ePortfolioIO.saveEPortfolio(ePortfolioToSave);

            // MARK IT AS SAVED
            saved = true;

            // AND REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
            // THE APPROPRIATE CONTROLS
            //ui.updateToolbarControls(saved);
            return true;
        } catch (IOException ioe) {
            //ErrorHandler eH = ui.getErrorHandler();
            // @todo
            return false;
        }
    }

    /**
     * This method will exit the application, making sure the user doesn't lose
     * any data first.
     */
    public void handleExitRequest() {
        getSaved(ui.isSaveEnabled());
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToExit = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE
                continueToExit = promptToSave();
            }

            // IF THE USER REALLY WANTS TO EXIT THE APP
            if (continueToExit) {
                // EXIT THE APPLICATION
                System.exit(0);
            }
        } catch (IOException ioe) {
            //ErrorHandler eH = ui.getErrorHandler();
            // @todo
        }
    }

    /**
     * This helper method verifies that the user really wants to save their
     * unsaved work, which they might not want to do. Note that it could be used
     * in multiple contexts before doing other actions, like creating a new
     * pose, or opening another pose, or exiting. Note that the user will be
     * presented with 3 options: YES, NO, and CANCEL. YES means the user wants
     * to save their work and continue the other action (we return true to
     * denote this), NO means don't save the work but continue with the other
     * action (true is returned), CANCEL means don't save the work and don't
     * continue with the other action (false is retuned).
     *
     * @return true if the user presses the YES option to save, true if the user
     * presses the NO option to not save, false if the user presses the CANCEL
     * option to not continue.
     */
    private boolean promptToSave() throws IOException {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        saveWork = true;
        Stage stage = new Stage();
        VBox layout = new VBox(15);       
        layout.setPadding(new Insets(15,15,15,15));
        HBox layoutBtn = new HBox(10);
        Label label = new Label("Would you like to save the Current EPortfolio?");
        label.setPadding(new Insets(0,10,0,0));
        Button ok = new Button("YES");
        ok.setAlignment(Pos.CENTER_RIGHT);
        Button cancel = new Button("No");
        cancel.setAlignment(Pos.CENTER);
        layoutBtn.getChildren().addAll(ok, cancel);
        layoutBtn.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, layoutBtn);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("dialog");
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("EPortfolioGeneratorStyle.css");
        stage.setScene(scene);
        stage.setTitle("Save EPortfolio");
        ok.setOnAction(e -> {
            saveWork = true;
            stage.close();
        });
        cancel.setOnAction(e -> {
            saveWork = false;
            stage.close();
        });
        stage.setOnCloseRequest(e ->{
            e.consume();                
        });
        stage.showAndWait();
    
         // @todo change this to prompt

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (saveWork) {
            EPortfolio ePortfolio = ui.getEPortfolio();
            //ui.saveCaps();
            ePortfolioIO.saveEPortfolio(ePortfolio);
            saved = true;
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (!true) {
            return false;
        }

        // IF THE USER SAID NO, WE JUST GO ON WITHOUT SAVING
        // BUT FOR BOTH YES AND NO WE DO WHATEVER THE USER
        // HAD IN MIND IN THE FIRST PLACE
        return true;
    }

    /**
     * This helper method asks the user for a file to open. The user-selected
     * file is then loaded and the GUI updated. Note that if the user cancels
     * the open process, nothing is done. If an error occurs loading the file, a
     * message is displayed, but nothing changes.
     */
    private void promptToOpen() {
        // AND NOW ASK THE USER FOR THE COURSE TO OPEN
        FileChooser ePortfolioFileChooser = new FileChooser();
        ePortfolioFileChooser.setInitialDirectory(new File("EPortfolios/"));
        File selectedFile = ePortfolioFileChooser.showOpenDialog(ui.getWindow());

        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                EPortfolio ePortfolioToLoad = ui.getEPortfolio();
                ePortfolioIO.loadEPortfolio(ePortfolioToLoad, selectedFile.getAbsolutePath());
                //ui.reloadPane(ePortfolioToLoad);
                saved = true;
                //ui.updateToolbarControls(saved);
            } catch (Exception e) {
                //ErrorHandler eH = ui.getErrorHandler();
                Stage stage = new Stage();
                VBox layout = new VBox(10);
                layout.setPadding(new Insets(10, 10, 10, 10));
                layout.getStyleClass().add("dialog");
                Label errorLabel = new Label("Please select another file.");
                Button btn = new Button("OK");
                btn.setAlignment(Pos.CENTER);
                layout.getChildren().addAll(errorLabel, btn); 
                layout.setAlignment(Pos.CENTER);
                btn.setOnAction(d -> stage.close());
                Scene scene = new Scene(layout);
                scene.getStylesheets().add("EPortfolioGeneratorStyle");
                stage.setScene(scene);
                stage.showAndWait();
                promptToOpen();
                // @todo
            }

        }
    }

    /**
     * This mutator method marks the file as not saved, which means that when
     * the user wants to do a file-type operation, we should prompt the user to
     * save current work first. Note that this method should be called any time
     * the pose is changed in some way.
     */
    public void markFileAsNotSaved() {
        saved = false;
    }

    /**
     * Accessor method for checking to see if the current pose has been saved
     * since it was last editing. If the current file matches the pose data,
     * we'll return true, otherwise false.
     *
     * @return true if the current pose is saved to the file, false otherwise.
     */
    public boolean isSaved() {
        return saved;
    }

    /*
     * Prompts user for title of the eportfolio
     * @return title of eportfolio
     */
    private static String getNewTitle() {
        Label lab = new Label("Set Title of EPortfolio");
        TextField field = new TextField();
        Button btn = new Button("OK");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        HBox box = new HBox(15);
        box.getChildren().addAll(field, btn);
        layout.getChildren().addAll(lab, box);
        layout.getStyleClass().add("dialog");
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("EPortfolioGeneratorStyle");
        Stage stage = new Stage();
        stage.setScene(scene);
        cont = true;
        btn.setOnAction(e -> {
            if (field.getText() == null); else {
                cont = false;
                stage.close();
            }
        });
        Platform.setImplicitExit(false);
        stage.setOnCloseRequest((WindowEvent event) -> {
            event.consume();
        });
        stage.showAndWait();

        return field.getText();
    }
    
    public void getSaved(Boolean saveButton){
        saved = !saveButton;
    }
}
