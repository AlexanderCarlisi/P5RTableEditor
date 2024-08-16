package com.p5rte.GUI;

import com.p5rte.Classes.Persona;
import com.p5rte.Utils.Enums;
import com.p5rte.Utils.Enums.Arcana;
import com.p5rte.Utils.Enums.BitFlag;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;


public class PEGeneralTabController {

    // General Tab Fields
    @FXML
    private Label personaNameLabel;
    @FXML
    private ComboBox<Enums.Arcana> arcanaComboBox;

    // Stat Fields
    @FXML
    private TextField lvlField;
    @FXML
    private TextField strengthField;
    @FXML
    private TextField magicField;
    @FXML
    private TextField enduranceField;
    @FXML
    private TextField agilityField;
    @FXML
    private TextField luckField;

    // Bit Flag Toggle Buttons
    @FXML
    private ToggleButton DLCFlag;
    @FXML
    private ToggleButton treasureFlag;
    @FXML
    private ToggleButton partyFlag;
    @FXML
    private ToggleButton storyFlag;
    @FXML
    private ToggleButton nRegFlag;
    @FXML
    private ToggleButton fusionFlag;
    @FXML
    private ToggleButton evolvedFlag;

    // Stat Weight Fields
    @FXML
    private TextField strWeightField;
    @FXML
    private TextField magWeightField;
    @FXML
    private TextField endWeightField;
    @FXML
    private TextField agiWeightField;
    @FXML
    private TextField lukWeightField;

    private Stage stage;

    private Persona currentPersona;

    private ChangeListener<Arcana> arcanaListener;

    @SuppressWarnings ("unchecked")
    private final ChangeListener<String>[] statListeners = new ChangeListener[6];

    @SuppressWarnings ("unchecked")
    private final ChangeListener<String>[] statWeightListeners = new ChangeListener[5];

    @SuppressWarnings ("unchecked")
    private final ChangeListener<Boolean>[] bitFlagListeners = new ChangeListener[7];

    private final TextField[] statFields = new TextField[5];
    private final TextField[] statWeightFields = new TextField[5];
    private final ToggleButton[] bitFlagButtons = new ToggleButton[7];

    private static PEGeneralTabController instance;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        instance = this;

        // Arcana Box Setup
        arcanaComboBox.getItems().addAll(Enums.Arcana.values());
        arcanaListener = (__, ___, newArcana) -> {
            if (currentPersona != null) {
                currentPersona.setArcana(newArcana);
            }
        };
        arcanaComboBox.valueProperty().addListener(arcanaListener);

        // Stat Fields Setup 
        statFields[0] = strengthField;
        statFields[1] = magicField;
        statFields[2] = enduranceField;
        statFields[3] = agilityField;
        statFields[4] = luckField;

        statWeightFields[0] = strWeightField;
        statWeightFields[1] = magWeightField;
        statWeightFields[2] = endWeightField;
        statWeightFields[3] = agiWeightField;
        statWeightFields[4] = lukWeightField;

        // Field Listeners
        for (int i = 0; i < statFields.length; i++) {
            final int INDEX = i;

            // Stat Listeners
            statListeners[i] = (__, ___, newVal) -> {
                setStat(newVal, INDEX);
            };
            statFields[i].textProperty().addListener(statListeners[i]);

            // Stat Weight Listeners
            statWeightListeners[i] = (__, ___, newVal) -> {
                setWeightedStat(newVal, INDEX);
            };
            statWeightFields[i].textProperty().addListener(statWeightListeners[i]);
        }

        // Level Field Listener
        statListeners[5] = (__, ___, newVal) -> {
            if (instance == null) return;

            int value = getStatFromField(newVal);
            instance.currentPersona.setLevel(value);
        };
        lvlField.textProperty().addListener(statListeners[5]);

        // Bit Flags Setup
        bitFlagButtons[0] = DLCFlag;
        bitFlagButtons[1] = treasureFlag;
        bitFlagButtons[2] = partyFlag;
        bitFlagButtons[3] = storyFlag;
        bitFlagButtons[4] = nRegFlag;
        bitFlagButtons[5] = fusionFlag;
        bitFlagButtons[6] = evolvedFlag;

        // Bitflag Listeners
        for (int i = 0; i < bitFlagButtons.length; i++) {
            final int INDEX = i;
            bitFlagListeners[i] = (__, ___, newVal) -> {
                if (currentPersona != null) 
                    instance.currentPersona.setBitFlag(BitFlag.values()[INDEX].INDEX, newVal);
            };
            bitFlagButtons[i].selectedProperty().addListener(bitFlagListeners[i]);
        }
    }


    public static void updateFields(Persona persona) {
        if (instance == null) return;

        instance.currentPersona = persona;

        // Set Name
        instance.personaNameLabel.setText(persona.getName());

        // Set Arcana
        instance.arcanaComboBox.setValue(persona.getArcana());

        // Set Stats
        instance.lvlField.setText(String.valueOf(persona.getLevel()));
        int[] stats = persona.getStats();
        for (int i = 0; i < stats.length; i++) {
            instance.statFields[i].setText(String.valueOf(stats[i]));
        }

        // Set Bit Flags
        boolean[] flags = persona.getBitFlags();
        for (BitFlag flag : BitFlag.values()) {
            instance.bitFlagButtons[flag.ordinal()].setSelected(flags[flag.INDEX]);
        }

        // Set Stat Weights
        int[] statWeights = persona.getStatWeights();
        for (int i = 0; i < statWeights.length; i++) {
            instance.statWeightFields[i].setText(String.valueOf(statWeights[i]));
        }
    }


    private static int getStatFromField(String newText) {
        final int defaultReturn = 0;
        final int min = 0;
        final int max = 99;

        if (!newText.isEmpty() && newText.matches("\\d+")) {
            int value = Integer.parseInt(newText);
            return Math.min(Math.max(value, min), max);
        }

        return defaultReturn;
    }


    private static void setStat(String newText, int index) {
        if (instance == null) return;

        int value = getStatFromField(newText);
        instance.currentPersona.setStat(index, value);
    }
    private static void setWeightedStat(String newText, int index) {
        if (instance == null) return;

        int value = getStatFromField(newText);
        instance.currentPersona.setStatWeight(index, value);
    }


    /**
     * Releases all resources used by this Tab.
     * Should only be called when Returning to the Main Menu.
     */
    public static void releaseResources() {
        if (instance == null) return;

        // Clear Listeners
        instance.arcanaComboBox.valueProperty().removeListener(instance.arcanaListener);
        for (int i = 0; i < instance.statFields.length; i++) {
            instance.statFields[i].textProperty().removeListener(instance.statListeners[i]);
        }
        instance.lvlField.textProperty().removeListener(instance.statListeners[5]);
        for (int i = 0; i < instance.statWeightListeners.length; i++) {
            instance.statWeightFields[i].textProperty().removeListener(instance.statWeightListeners[i]);
        }
        for (int i = 0; i < instance.bitFlagListeners.length; i++) {
            instance.bitFlagButtons[i].selectedProperty().removeListener(instance.bitFlagListeners[i]);
        }
    }
}