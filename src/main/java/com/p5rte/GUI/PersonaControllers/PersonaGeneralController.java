package com.p5rte.GUI.PersonaControllers;

import com.p5rte.Classes.Persona;
import com.p5rte.Utils.Enums;
import com.p5rte.Utils.Enums.Arcana;
import com.p5rte.Utils.Enums.BitFlag;
import com.p5rte.Utils.Enums.EPartyMemberPersona;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;


public class PersonaGeneralController {

    // FXML Elements
    @FXML private Label personaNameLabel;
    @FXML private ComboBox<Enums.Arcana> arcanaComboBox;

    @FXML private TextField lvlField;
    @FXML private TextField strengthField;
    @FXML private TextField magicField;
    @FXML private TextField enduranceField;
    @FXML private TextField agilityField;
    @FXML private TextField luckField;

    @FXML private ToggleButton DLCFlag;
    @FXML private ToggleButton treasureFlag;
    @FXML private ToggleButton partyFlag;
    @FXML private ToggleButton storyFlag;
    @FXML private ToggleButton nRegFlag;
    @FXML private ToggleButton fusionFlag;
    @FXML private ToggleButton evolvedFlag;

    @FXML private TextField strWeightField;
    @FXML private TextField magWeightField;
    @FXML private TextField endWeightField;
    @FXML private TextField agiWeightField;
    @FXML private TextField lukWeightField;

    @FXML private Label warningLabel;
    private Stage stage;


    @SuppressWarnings ("unchecked")
    private final ChangeListener<String>[] STAT_LISTENERS = new ChangeListener[6];

    @SuppressWarnings ("unchecked")
    private final ChangeListener<String>[] STAT_WEIGHT_LISTENERS = new ChangeListener[5];

    @SuppressWarnings ("unchecked")
    private final ChangeListener<Boolean>[] FLAG_LISTENERS = new ChangeListener[7];

    private final TextField[] STAT_FIELDS = new TextField[5];
    private final TextField[] WEIGHT_FIELDS = new TextField[5];
    private final ToggleButton[] FLAG_BUTTONS = new ToggleButton[7];


    private static PersonaGeneralController s_instance;
    private Persona _currentPersona;
    private ChangeListener<Arcana> _arcanaListener;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        s_instance = this;

        // Arcana Box Setup
        arcanaComboBox.getItems().addAll(Enums.Arcana.values());
        _arcanaListener = (__, ___, newArcana) -> {
            if (_currentPersona != null) {
                _currentPersona.setArcana(newArcana);
            }
        };
        arcanaComboBox.valueProperty().addListener(_arcanaListener);

        // Stat Fields Setup 
        STAT_FIELDS[0] = strengthField;
        STAT_FIELDS[1] = magicField;
        STAT_FIELDS[2] = enduranceField;
        STAT_FIELDS[3] = agilityField;
        STAT_FIELDS[4] = luckField;

        WEIGHT_FIELDS[0] = strWeightField;
        WEIGHT_FIELDS[1] = magWeightField;
        WEIGHT_FIELDS[2] = endWeightField;
        WEIGHT_FIELDS[3] = agiWeightField;
        WEIGHT_FIELDS[4] = lukWeightField;

        // Field Listeners
        for (int i = 0; i < STAT_FIELDS.length; i++) {
            final int INDEX = i;

            // Stat Listeners
            STAT_LISTENERS[i] = (__, ___, newVal) -> {
                setStat(newVal, INDEX);
            };
            STAT_FIELDS[i].textProperty().addListener(STAT_LISTENERS[i]);

            // Stat Weight Listeners
            STAT_WEIGHT_LISTENERS[i] = (__, ___, newVal) -> {
                setWeightedStat(newVal, INDEX);
            };
            WEIGHT_FIELDS[i].textProperty().addListener(STAT_WEIGHT_LISTENERS[i]);
        }

        // Level Field Listener
        STAT_LISTENERS[5] = (__, ___, newVal) -> {
            if (s_instance == null) return;

            int value = getStatFromField(newVal);
            s_instance._currentPersona.setLevel(value);
        };
        lvlField.textProperty().addListener(STAT_LISTENERS[5]);

        // Bit Flags Setup
        FLAG_BUTTONS[0] = DLCFlag;
        FLAG_BUTTONS[1] = treasureFlag;
        FLAG_BUTTONS[2] = partyFlag;
        FLAG_BUTTONS[3] = storyFlag;
        FLAG_BUTTONS[4] = nRegFlag;
        FLAG_BUTTONS[5] = fusionFlag;
        FLAG_BUTTONS[6] = evolvedFlag;

        // Bitflag Listeners
        for (int i = 0; i < FLAG_BUTTONS.length; i++) {
            final int INDEX = i;
            FLAG_LISTENERS[i] = (__, ___, newVal) -> {
                if (_currentPersona != null) 
                    s_instance._currentPersona.setBitFlag(BitFlag.values()[INDEX].INDEX, newVal);
            };
            FLAG_BUTTONS[i].selectedProperty().addListener(FLAG_LISTENERS[i]);
        }

        warningLabel.setText("");
    }


    public static void updateFields(Persona persona, int personaIndex) {
        if (s_instance == null) return;

        s_instance._currentPersona = persona;

        // Set Name
        s_instance.personaNameLabel.setText(persona.getName());

        // Set Arcana
        s_instance.arcanaComboBox.setValue(persona.getArcana());

        // Set Stats
        s_instance.lvlField.setText(String.valueOf(persona.getLevel()));
        int[] stats = persona.getStats();
        for (int i = 0; i < stats.length; i++) {
            s_instance.STAT_FIELDS[i].setText(String.valueOf(stats[i]));
        }

        // Set Bit Flags
        boolean[] flags = persona.getBitFlags();
        for (BitFlag flag : BitFlag.values()) {
            s_instance.FLAG_BUTTONS[flag.ordinal()].setSelected(flags[flag.INDEX]);
        }

        // Set Stat Weights
        int[] statWeights = persona.getStatWeights();
        for (int i = 0; i < statWeights.length; i++) {
            s_instance.WEIGHT_FIELDS[i].setText(String.valueOf(statWeights[i]));
        }

        // Warn user if the Persona is a PartyPersona
        boolean caught = false;
        for (EPartyMemberPersona partyPersona : EPartyMemberPersona.values()) {
            if (partyPersona.PERSONA_INDEX == personaIndex) {
                s_instance.warningLabel.setText("Warning: This is a Party Member Persona.\nEdit skills in PartyEditor for it to take effect.");
                caught = true;
            }
        }
        if (!caught) s_instance.warningLabel.setText("");
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
        if (s_instance == null) return;

        int value = getStatFromField(newText);
        s_instance._currentPersona.setStat(index, value);
    }
    private static void setWeightedStat(String newText, int index) {
        if (s_instance == null) return;

        int value = getStatFromField(newText);
        s_instance._currentPersona.setStatWeight(index, value);
    }


    /**
     * Releases all resources used by this Tab.
     * Should only be called when Returning to the Main Menu.
     */
    public static void releaseResources() {
        if (s_instance == null) return;

        // Clear Listeners
        s_instance.arcanaComboBox.valueProperty().removeListener(s_instance._arcanaListener);
        for (int i = 0; i < s_instance.STAT_FIELDS.length; i++) {
            s_instance.STAT_FIELDS[i].textProperty().removeListener(s_instance.STAT_LISTENERS[i]);
        }
        s_instance.lvlField.textProperty().removeListener(s_instance.STAT_LISTENERS[5]);
        for (int i = 0; i < s_instance.STAT_WEIGHT_LISTENERS.length; i++) {
            s_instance.WEIGHT_FIELDS[i].textProperty().removeListener(s_instance.STAT_WEIGHT_LISTENERS[i]);
        }
        for (int i = 0; i < s_instance.FLAG_LISTENERS.length; i++) {
            s_instance.FLAG_BUTTONS[i].selectedProperty().removeListener(s_instance.FLAG_LISTENERS[i]);
        }
    }
}