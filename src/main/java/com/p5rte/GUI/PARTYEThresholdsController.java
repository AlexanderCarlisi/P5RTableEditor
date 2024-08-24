package com.p5rte.GUI;

import javafx.fxml.FXML;
import javafx.stage.Stage;


public class PARTYEThresholdsController {

  


    private Stage stage;

    private static PARTYEThresholdsController instance;


    public void setStage(Stage stage) {
        this.stage = stage;
    }
    

    @FXML
    public void initialize() {
        instance = this;
    }


    public static void updateFields() {
        if (instance == null) return;

        
    }


   
   

    /**
     * Releases all resources used by this Tab.
     * Should only be called when Returning to the Main Menu.
     */
    public static void releaseResources() {
        if (instance == null) return;
    }


    private static int parseInt(String text, int min) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return min;
        }
    }
}