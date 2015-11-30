/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eportfoliogenerator;


import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Freddy Estevez
 */
public class EPortfolioGenerator extends Application {
   
    
    
      static EPortfolioGeneratorView view;

    public void start(Stage primaryStage) {
        view = new EPortfolioGeneratorView();
        view.start(primaryStage);
    }
    
    public static void main(String[] args){
        launch(args);
    }
    
   
}
