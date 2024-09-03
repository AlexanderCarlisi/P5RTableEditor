package com.p5rte.GUI;

import com.p5rte.Classes.PartyMemberPersona;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class PARTYEGainsController {

    @FXML private TextField viewLevelTextField;
    @FXML private Label viewStatsLabel;
    @FXML private HBox statOddsContainer;
    @FXML private TextField statsPerLevelField;
    @FXML private TextField manualLevelField;
    @FXML private HBox manualStatsContainer;
    @FXML private Button generateStatGainsButton;
    @FXML private Label oddsTotalLabel;
    private Stage stage;


    private final TextField[] ODDS_FIELDS = new TextField[5];
    private final TextField[] MANUAL_FIELDS = new TextField[5];

    @SuppressWarnings ("unchecked")
    private final ChangeListener<String>[] ODDS_LISTENERS = new ChangeListener[5];

    @SuppressWarnings ("unchecked")
    private final ChangeListener<String>[] MANUAL_LISTENERS = new ChangeListener[5];

    private static PARTYEGainsController s_instance;
    private PartyMemberPersona _currentPersona;

    private ChangeListener<String> _viewLevelListener;
    private ChangeListener<String> _manualLevelListener;


    public void setStage(Stage stage) {
        this.stage = stage;
    }
    

    @FXML
    public void initialize() {
        s_instance = this;

        // Initialize Fields
        for (int i = 0; i < 5; i++) {
            TextField statOddsField = new TextField();
            statOddsContainer.getChildren().add(statOddsField);
            ODDS_FIELDS[i] = statOddsField;
            statOddsField.setText("20");

            TextField manualStatsField = new TextField();
            manualStatsContainer.getChildren().add(manualStatsField);
            MANUAL_FIELDS[i] = manualStatsField;
            manualStatsField.setText("0");
        }

        // Add Listeners
        _viewLevelListener = (__, ___, ____) -> {
            updateViewStatsLabel();
        };
        viewLevelTextField.textProperty().addListener(_viewLevelListener);

        // Randomizer Fields
        generateStatGainsButton.setOnAction(e -> {
            randomizeStatGains();
        });

        // Manual Gain Fields
        _manualLevelListener = (__, ___, ____) -> {
            updateManualGains();
        };
        manualLevelField.textProperty().addListener(_manualLevelListener);
        for (int i = 0; i < 5; i++) {
            final int INDEX = i;
            MANUAL_LISTENERS[i] = (__, oldValue, newValue) -> {
                if (getStatFromField(newValue.strip()) == getStatFromField(oldValue.strip())) return;
                setManualGain(INDEX);
            };
            MANUAL_FIELDS[i].textProperty().addListener(MANUAL_LISTENERS[i]);

            ODDS_LISTENERS[i] = (__, oldValue, newValue) -> {
                if (getOddFromField(newValue.strip()) == getOddFromField(oldValue.strip())) return;
                int total = 0;
                for (int j = 0; j < 5; j++) {
                    total += getOddFromField(ODDS_FIELDS[j].getText());
                }
                oddsTotalLabel.setText("Total Odds: " + total);
            };
            ODDS_FIELDS[i].textProperty().addListener(ODDS_LISTENERS[i]);
        }

    }


    public static void updateFields(PartyMemberPersona persona) {
        if (s_instance == null) return;

        s_instance._currentPersona = persona;

        s_instance.viewLevelTextField.setText("1");
        s_instance.statsPerLevelField.setText("3");
        s_instance.manualLevelField.setText("1");

        updateViewStatsLabel();
        updateManualGains();
    }


    private static void randomizeStatGains() {
        if (s_instance == null) return;

        int[] statOdds = new int[5];
        int total = 0;
        int numbers = 0;
        for (int i = 0; i < 5; i++) {
            statOdds[i] = getOddFromField(s_instance.ODDS_FIELDS[i].getText());
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

        int statsPerLevel = getStatsPerLevelField(s_instance.statsPerLevelField.getText());

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

        s_instance._currentPersona.statGain = statGain;
        updateManualGains();
        updateViewStatsLabel();
    }
   

    /**
     * Releases all resources used by this Tab.
     * Should only be called when Returning to the Main Menu.
     */
    public static void releaseResources() {
        if (s_instance == null) return;

        s_instance.viewLevelTextField.textProperty().removeListener(s_instance._viewLevelListener);
        s_instance.statsPerLevelField.textProperty().removeListener(s_instance._viewLevelListener);
        s_instance.manualLevelField.textProperty().removeListener(s_instance._manualLevelListener);

        for (int i = 0; i < 5; i++) {
            s_instance.ODDS_FIELDS[i].textProperty().removeListener(s_instance.ODDS_LISTENERS[i]);
            s_instance.MANUAL_FIELDS[i].textProperty().removeListener(s_instance.MANUAL_LISTENERS[i]);
        }
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
        if (s_instance == null) return;

        int level = getLevelFromField(s_instance.viewLevelTextField.getText());
        int[] stats = new int[5];

        for (int l = 0; l < level; l++) {
            for (int i = 0; i < 5; i++) {
                stats[i] += s_instance._currentPersona.statGain[l][i];
            }
        }

        String statsText = "";
        for (int i = 0; i < 5; i++) {
            statsText += stats[i] + ", ";
        }

        s_instance.viewStatsLabel.setText(statsText.substring(0, statsText.length() - 2));
    }


    private static void setManualGain(int index) {
        if (s_instance == null) return;

        int level = getLevelFromField(s_instance.manualLevelField.getText());
        s_instance._currentPersona.statGain[level - 1][index] = getStatFromField(s_instance.MANUAL_FIELDS[index].getText());

        updateViewStatsLabel();
    }


    private static void updateManualGains() {
        if (s_instance == null) return;

        int level = getLevelFromField(s_instance.manualLevelField.getText());

        for (int i = 0; i < 5; i++) {
            s_instance.MANUAL_FIELDS[i].setText(String.valueOf(s_instance._currentPersona.statGain[level - 1][i]));
        }

        updateViewStatsLabel();
    }
}