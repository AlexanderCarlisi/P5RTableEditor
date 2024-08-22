package com.p5rte.GUI;

import com.p5rte.Classes.PartyMemberPersona;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class PARTYEGainsController {

    @FXML 
    private TextField viewLevelTextField;
    @FXML
    private Label viewStatsLabel;
    @FXML
    private HBox statOddsContainer;
    @FXML
    private TextField statsPerLevelField;
    @FXML
    private TextField manualLevelField;
    @FXML
    private HBox manualStatsContainer;
    @FXML
    private Button generateStatGainsButton;


    private Stage stage;

    private static PARTYEGainsController instance;
    private final TextField[] statOddsFields = new TextField[5];
    private final TextField[] manualStatsFields = new TextField[5];
    private PartyMemberPersona currentPersona;


    public void setStage(Stage stage) {
        this.stage = stage;
    }
    

    @FXML
    public void initialize() {
        instance = this;

        // Initialize Fields
        for (int i = 0; i < 5; i++) {
            TextField statOddsField = new TextField();
            statOddsContainer.getChildren().add(statOddsField);
            statOddsFields[i] = statOddsField;
            statOddsField.setText("20");

            TextField manualStatsField = new TextField();
            manualStatsContainer.getChildren().add(manualStatsField);
            manualStatsFields[i] = manualStatsField;
            manualStatsField.setText("0");
        }

        // Set Initial Values
        viewLevelTextField.setText("1");

        // Add Listeners
        viewLevelTextField.textProperty().addListener((__, oldValue, newValue) -> {
            if (getLevelFromField(newValue.strip()) == getLevelFromField(oldValue.strip())) return;
            updateViewStatsLabel();
        });
    }


    public static void updateFields(PartyMemberPersona persona, boolean disable) {
        if (instance == null) return;

        instance.currentPersona = persona;

        updateViewStatsLabel();

        // for (int i = 0; i < 5; i++) {
        //     instance.statOddsFields[i].setDisable(disable);
        //     instance.manualStatsFields[i].setDisable(disable);
        // }
        // instance.statsPerLevelField.setDisable(disable);
        // instance.generateStatGainsButton.setDisable(disable);
        // instance.manualLevelField.setDisable(disable);
    }


    // private static void randomizeStatGains() {
        // if (instance == null) return;

        // int[] statOdds = new int[5];
        // int total = 0;
        // for (int i = 0; i < 5; i++) {
        //     statOdds[i] = parseInt(instance.statOddsFields[i].getText(), 0, 100);
        //     total += statOdds[i];
        // }

        // if (total < 100) {
        //     GUIManager.DisplayWarning (
        //         "Stat Gains Odds Warning", 
        //         "Stat Odd Total doesn't add to 100", 
        //         "Stat Odds must add up to 100. Please adjust the values, then try again."
        //     );
        //     return;
        // }

        // int statsPerLevel = parseInt(instance.statsPerLevelField.getText(), 1, 5);

        // int[][] statGain = new int[98][5];

        // // Randomize Stat Gains, 
        // // stats per level dictates the amount of stats to increase. eg: 3, then 3 stats will increase by 1
        // // the same stat cannot be rolled again for the same level
        // // the odds of a stat rolling is dictated by statOdds
        // for (int l = 0; l < 98; l++) {
        //     boolean[] statRolled = new boolean[5];
        //     int remainingStats = statsPerLevel;
        
        //     while (remainingStats > 0) {
        //         int statRoll = (int) (Math.random() * 100);
        //         int statIndex = 0;
        
        //         // Find the stat to increase based on the odds
        //         for (int j = 0; j < 5; j++) {
        //             if (statRoll < statOdds[j]) {
        //                 statIndex = j;
        //                 break;
        //             }
        //             statRoll -= statOdds[j];
        //         }
        
        //         // Only increase the stat if it hasn't been rolled yet
        //         if (!statRolled[statIndex]) {
        //             statRolled[statIndex] = true;
        //             statGain[l][statIndex]++;
        //             remainingStats--;
        //         }
        //     }
        // }

        // instance.currentPersona.statGain = statGain;
        // updateStatViewer();
    // }
   

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


    private static int getLevelFromField(String newValue) {
        int level = parseInt(newValue, 1);
        return (level < 1 || level > 98) ? 1 : level;
    }

    private static int getStatFromField(String newValue) {
        int stat = parseInt(newValue, 0);
        return (stat < 0 || stat > 99) ? 0 : stat;
    }

    private static void updateViewStatsLabel() {
        if (instance == null) return;

        int level = getLevelFromField(instance.viewLevelTextField.getText());
        int[] stats = new int[5];

        for (int l = 0; l < level; l++) {
            for (int i = 0; i < 5; i++) {
                stats[i] += instance.currentPersona.statGain[l][i];
            }
        }

        String statsText = "";
        for (int i = 0; i < 5; i++) {
            statsText += stats[i] + ", ";
        }

        instance.viewStatsLabel.setText(statsText.substring(0, statsText.length() - 2));
    }
}