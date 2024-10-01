package com.p5rte.GUI.PartyControllers;

import com.p5rte.Classes.PartyMember;
import com.p5rte.Classes.PartyStream;
import com.p5rte.Utils.Enums.EPartyMember;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PartyThresholdsController {

    // FXML Elements
    @FXML private ToggleButton individualToggleButton;
    @FXML private TextField rangeLowerTextField;
    @FXML private TextField rangeUpperTextField;
    @FXML private TextField multiplierTextField;
    @FXML private Button multiplyButton;
    @FXML private VBox thresholdContainer;
    private Stage stage;


    private final TextField[] MANUAL_FIELDS = new TextField[98];

    @SuppressWarnings ("unchecked")
    private final ChangeListener<String>[] MANUAL_LISTENERS = new ChangeListener[98];
    
    private static PartyThresholdsController s_instance;
    private PartyMember _currentPartyMember;

    private ChangeListener<Boolean> _individualToggleListener;
    private ChangeListener<Boolean> _multiplyListener;


    public void setStage(Stage stage) {
        this.stage = stage;
    }
    

    @FXML
    public void initialize() {
        s_instance = this;

        // Initialize Manual Thresholds
        for (int i = 0; i < 98; i++) {
            HBox hbox = new HBox();
            hbox.spacingProperty().setValue(10);

            TextField textField = new TextField();
            textField.setPromptText("0");
            MANUAL_FIELDS[i] = textField;

            Label levelLabel = new Label("Level: " + (i+1));
            hbox.getChildren().add(levelLabel);
            hbox.getChildren().add(textField);

            thresholdContainer.getChildren().add(hbox);

            // Text Field Listener
            final int index = i;
            MANUAL_LISTENERS[i] = (__, ___, newValue) -> {
                _currentPartyMember.levelThreshold[index] = parseInt(newValue, 0, Integer.MAX_VALUE);
            };
            textField.textProperty().addListener(MANUAL_LISTENERS[i]);
        }

        // Individual Toggle Button
        individualToggleButton.setSelected(PartyStream.getWriteThresholds());
        _individualToggleListener = (__, ___, newValue) -> {
            PartyStream.setWriteThresholds(newValue);
            disableCheck();
        };
        individualToggleButton.selectedProperty().addListener(_individualToggleListener);

        // While the Table stores the Thresholds as an Unsigned Integer, Java doesn't do unsigned integers, 
        // so the Integer Limit is halved (womp womp).
        _multiplyListener = (__, ___, newValue) -> {
            if (!newValue) return;
            
            int start = parseInt(s_instance.rangeLowerTextField.getText(), 1, 98) - 1;
            int end = parseInt(s_instance.rangeUpperTextField.getText(), start + 1, 98) - 1;
            float mult = parseFloat(s_instance.multiplierTextField.getText(), 0, Integer.MAX_VALUE);
            
            for (int i = start; i <= end; i++) {
                try {
                    int value = s_instance._currentPartyMember.levelThreshold[i];

                    // Convert float to int carefully, using long for intermediate calculation to prevent overflow
                    long result = Math.round((float) value * mult);
                    
                    // Ensure the result fits into an int without overflow
                    if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
                        throw new ArithmeticException("Overflow occurred");
                    }
                    
                    s_instance._currentPartyMember.levelThreshold[i] = (int) result;
                } catch (ArithmeticException ex) {
                    s_instance._currentPartyMember.levelThreshold[i] = Integer.MAX_VALUE; // or some fallback value
                }
            }

            for (int i = 0; i < 98; i++) {
                s_instance.MANUAL_FIELDS[i].setText(String.valueOf(s_instance._currentPartyMember.levelThreshold[i]));
            }
        };
        multiplyButton.pressedProperty().addListener(_multiplyListener);
    }


    public static void updateFields(PartyMember partyMember) {
        if (s_instance == null) return;

        s_instance._currentPartyMember = partyMember;
        disableCheck();

        for (int i = 0; i < 98; i++) {
            s_instance.MANUAL_FIELDS[i].setText(String.valueOf(partyMember.levelThreshold[i]));
        }
    }
   

    /**
     * Releases all resources used by this Tab.
     * Should only be called when Returning to the Main Menu.
     */
    public static void releaseResources() {
        if (s_instance == null) return;

        for (int i = 0; i < s_instance.MANUAL_LISTENERS.length; i++) {
            s_instance.MANUAL_FIELDS[i].textProperty().removeListener(s_instance.MANUAL_LISTENERS[i]);
        }

        s_instance.individualToggleButton.selectedProperty().removeListener(s_instance._individualToggleListener);
        s_instance.multiplyButton.pressedProperty().removeListener(s_instance._multiplyListener);
    }


    private static int parseInt(String text, int lower, int upper) {
        try {
            int value = Integer.parseInt(text);
            return Math.min(Math.max(value, lower), upper);
        } catch (NumberFormatException e) {
            return lower;
        }
    }

    private static float parseFloat(String text, float lower, float upper) {
        try {
            float value = Float.parseFloat(text);
            return Math.min(Math.max(value, lower), upper);
        } catch (NumberFormatException e) {
            return lower;
        }
    }


    private static void disableCheck() {
        if (s_instance == null) return;

        boolean value = !s_instance.individualToggleButton.isSelected() && s_instance._currentPartyMember.member != EPartyMember.Ryuji;

        s_instance.rangeLowerTextField.setDisable(value);
        s_instance.rangeUpperTextField.setDisable(value);
        s_instance.multiplierTextField.setDisable(value);
        s_instance.multiplyButton.setDisable(value);
        for (TextField th : s_instance.MANUAL_FIELDS) {
            th.setDisable(value);
        }
    }
}