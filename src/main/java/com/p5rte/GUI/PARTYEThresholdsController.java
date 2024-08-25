package com.p5rte.GUI;

import com.p5rte.Classes.PartyMember;
import com.p5rte.Classes.PartyStream;
import com.p5rte.Utils.Enums.EPartyMember;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PARTYEThresholdsController {

    @FXML
    private ToggleButton individualToggleButton;
    @FXML
    private TextField rangeLowerTextField;
    @FXML
    private TextField rangeUpperTextField;

    @FXML
    private TextField multiplierTextField;
    @FXML
    private Button multiplyButton;

    @FXML
    private VBox thresholdContainer;


    private Stage stage;
    private static PARTYEThresholdsController instance;
    private PartyMember currentPartyMember;
    private PartyMember individualPartyMember = PartyStream.getPartyMember(EPartyMember.Ryuji);
    private PartyMember selectedPartyMember;

    private TextField[] manualThresholds = new TextField[98];
    // private ChangeListener[] manualThresholdListeners = new ChangeListener[98];


    public void setStage(Stage stage) {
        this.stage = stage;
    }
    

    @FXML
    public void initialize() {
        instance = this;

        // Initialize Manual Thresholds
        for (int i = 0; i < 98; i++) {
            HBox hbox = new HBox();
            hbox.spacingProperty().setValue(10);

            TextField textField = new TextField();
            textField.setPromptText("0");
            manualThresholds[i] = textField;

            Label levelLabel = new Label("Level: " + (i+1));
            hbox.getChildren().add(levelLabel);
            hbox.getChildren().add(textField);

            thresholdContainer.getChildren().add(hbox);

            final int index = i;
            textField.textProperty().addListener((__, ___, newValue) -> {
                currentPartyMember.levelThreshold[index] = parseInt(newValue, 0, Integer.MAX_VALUE);
            });
        }

        individualToggleButton.setSelected(PartyStream.getWriteThresholds());
        individualToggleButton.selectedProperty().addListener((__, ___, selected) -> {
            PartyStream.setWriteThresholds(selected);
        });
    }


    public static void updateFields(PartyMember partyMember) {
        if (instance == null) return;

        instance.currentPartyMember = partyMember;
    }
   

    /**
     * Releases all resources used by this Tab.
     * Should only be called when Returning to the Main Menu.
     */
    public static void releaseResources() {
        if (instance == null) return;
    }


    private static int parseInt(String text, int lower, int upper) {
        try {
            int value = Integer.parseInt(text);
            return Math.min(Math.max(value, lower), upper);
        } catch (NumberFormatException e) {
            return lower;
        }
    }
}