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
    @FXML
    private Label oddsTotalLabel;


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

        // Add Listeners
        viewLevelTextField.textProperty().addListener((__, ___, ____) -> {
            updateViewStatsLabel();
        });

        // Randomizer Fields
        generateStatGainsButton.setOnAction(e -> {
            randomizeStatGains();
        });

        // Manual Gain Fields
        manualLevelField.textProperty().addListener((__, ___, ____) -> {
            updateManualGains();
        });
        for (int i = 0; i < 5; i++) {
            final int INDEX = i;
            manualStatsFields[i].textProperty().addListener((__, oldValue, newValue) -> {
                if (getStatFromField(newValue.strip()) == getStatFromField(oldValue.strip())) return;
                setManualGain(INDEX);
            });

            statOddsFields[i].textProperty().addListener((__, oldValue, newValue) -> {
                if (getOddFromField(newValue.strip()) == getOddFromField(oldValue.strip())) return;
                int total = 0;
                for (int j = 0; j < 5; j++) {
                    total += getOddFromField(statOddsFields[j].getText());
                }
                oddsTotalLabel.setText("Total Odds: " + total);
            });
        }

    }


    public static void updateFields(PartyMemberPersona persona, boolean disable) {
        if (instance == null) return;

        instance.currentPersona = persona;

        instance.viewLevelTextField.setText("1");
        instance.statsPerLevelField.setText("3");
        instance.manualLevelField.setText("1");

        updateViewStatsLabel();
        updateManualGains();

        // for (int i = 0; i < 5; i++) {
        //     instance.statOddsFields[i].setDisable(disable);
        //     instance.manualStatsFields[i].setDisable(disable);
        // }
        // instance.statsPerLevelField.setDisable(disable);
        // instance.generateStatGainsButton.setDisable(disable);
        // instance.manualLevelField.setDisable(disable);
    }


    private static void randomizeStatGains() {
        if (instance == null) return;

        int[] statOdds = new int[5];
        int total = 0;
        int numbers = 0;
        for (int i = 0; i < 5; i++) {
            statOdds[i] = getOddFromField(instance.statOddsFields[i].getText());
            total += statOdds[i];
            if (statOdds[i] != 0) numbers++;
        }

        if (total != 100) {
            GUIManager.DisplayWarning (
                "Stat Gains Odds Warning", 
                "Stat Odd Total doesn't add to 100", 
                "Stat Odds must add up to 100. Please adjust the values, then try again."
            );
            return;
        }

        int statsPerLevel = getStatsPerLevelField(instance.statsPerLevelField.getText());

        if (numbers < statsPerLevel) {
            GUIManager.DisplayWarning (
                "Stat Gains Odds Warning", 
                "Not Enough Stats to Increase", 
                "A Stat only gets increased once per roll, so if the amount of ZERO odds you have is greater than statsPerLevel, then you get stuck in an infinite loop, not a bug, please adjust the values."
            );
            return;
        }

        int[][] statGain = new int[98][5];

        // Randomize Stat Gains, 
        // stats per level dictates the amount of stats to increase. eg: 3, then 3 stats will increase by 1
        // the same stat cannot be rolled again for the same level
        // the odds of a stat rolling is dictated by statOdds
        for (int l = 0; l < 98; l++) {
            boolean[] statRolled = new boolean[5];
            int remainingStats = statsPerLevel;
        
            while (remainingStats > 0) {
                int statRoll = (int) (Math.random() * 100);
                int statIndex = 0;
        
                // Find the stat to increase based on the odds
                for (int j = 0; j < 5; j++) {
                    if (statRoll < statOdds[j]) {
                        statIndex = j;
                        break;
                    }
                    statRoll -= statOdds[j];
                }
        
                // Only increase the stat if it hasn't been rolled yet
                if (!statRolled[statIndex]) {
                    statRolled[statIndex] = true;
                    statGain[l][statIndex]++;
                    remainingStats--;
                }
            }
        }

        instance.currentPersona.statGain = statGain;
        updateManualGains();
        updateViewStatsLabel();
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


    private static int getLevelFromField(String newValue) {
        int level = parseInt(newValue, 1);
        return (level < 1 || level > 98) ? 1 : level;
    }

    private static int getStatFromField(String newValue) {
        int stat = parseInt(newValue, 0);
        return (stat < 0 || stat > 99) ? 0 : stat;
    }

    private static int getOddFromField(String newValue) {
        int odd = parseInt(newValue, 0);
        return (odd < 0 || odd > 100) ? 0 : odd;
    }

    private static int getStatsPerLevelField(String newValue) {
        int statsPerLevel = parseInt(newValue, 1);
        return (statsPerLevel < 1 || statsPerLevel > 5) ? 1 : statsPerLevel;
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


    private static void setManualGain(int index) {
        if (instance == null) return;

        int level = getLevelFromField(instance.manualLevelField.getText());
        instance.currentPersona.statGain[level - 1][index] = getStatFromField(instance.manualStatsFields[index].getText());

        updateViewStatsLabel();
    }
    

    private static void updateManualGains() {
        if (instance == null) return;

        int level = getLevelFromField(instance.manualLevelField.getText());

        for (int i = 0; i < 5; i++) {
            instance.manualStatsFields[i].setText(String.valueOf(instance.currentPersona.statGain[level - 1][i]));
        }

        updateViewStatsLabel();
    }
}