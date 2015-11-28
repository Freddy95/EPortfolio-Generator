/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eportfoliogenerator;

import EPortfolioGeneratorUI.Page;
import java.util.ArrayList;

/**
 * Holds information on each eportfolio such as the pages and their content, as 
 * well as the eportfolio title.
 * @author andrewestevez
 */
public class EPortfolio {
    String title;
    ArrayList<Page> pages;

    public ArrayList<Page> getPages() {
        return pages;
    }
    
    public void addPage(Page c){
        pages.add(c);
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String s){
        title = s;
    }
    
}
