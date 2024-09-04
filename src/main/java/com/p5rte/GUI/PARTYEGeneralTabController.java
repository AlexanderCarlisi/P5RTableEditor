package com.p5rte.GUI;

import com.p5rte.Classes.PartyMember;
import com.p5rte.Classes.Persona;
import com.p5rte.Utils.Enums;
import com.p5rte.Utils.Enums.Arcana;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class PARTYEGeneralTabController {


    private static class CopyHolder {
        public Enums.EPartyMemberPersona persona;
        public int indexInPersonas;

        public CopyHolder(Enums.EPartyMemberPersona persona, int indexInPersonas) {
            this.persona = persona;
            this.indexInPersonas = indexInPersonas;
        }

        @Override
        public String toString() {
            return persona.name();
        }
    }


    // FXML Elements
    @FXML private Label partyMemberName;
    @FXML private ComboBox<Enums.Arcana> arcanaComboBox;
    @FXML private ComboBox<CopyHolder> copyOfComboBox;
    @FXML private TextField lvlField;
    @FXML private TextField strengthField;
    @FXML private TextField magicField;
    @FXML private TextField enduranceField;
    @FXML private TextField agilityField;
    @FXML private TextField luckField;
    private Stage stage;


    @SuppressWarnings ("unchecked")
    private final ChangeListener<String>[] STAT_LISTENERS = new ChangeListener[6];
    private final TextField[] STAT_FIELDS = new TextField[5];

    private static PARTYEGeneralTabController s_instance;

    private Persona _currentPersona;
    private PartyMember _partyMember;
    private int _partyMemberPersonaIndex;

    private ChangeListener<Arcana> _arcanaListener;
    private ChangeListener<CopyHolder> _copyListener;


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

        // Field Listeners
        for (int i = 0; i < STAT_FIELDS.length; i++) {
            final int INDEX = i;

            // Stat Listeners
            STAT_LISTENERS[i] = (__, ___, newVal) -> {
                setStat(newVal, INDEX);
            };
            STAT_FIELDS[i].textProperty().addListener(STAT_LISTENERS[i]);
        }

        // Level Field Listener
        STAT_LISTENERS[5] = (__, ___, newVal) -> {
            if (s_instance == null) return;

            int value = getStatFromField(newVal);
            s_instance._currentPersona.setLevel(value);
        };
        lvlField.textProperty().addListener(STAT_LISTENERS[5]);

        // CopyOf ComboBox Setup
        _copyListener = (__, ___, newValue) -> {
            if (_partyMember != null && newValue != null) {
                _partyMember.personas[_partyMemberPersonaIndex].copyOfPersona = newValue.indexInPersonas;
                // This is really ugly :)
                disableEditor();
            }
        };
        copyOfComboBox.valueProperty().addListener(_copyListener);
    }


    public static void updateFields(PartyMember partyMember, int partyPersonaIndex, Persona persona) {
        if (s_instance == null) return;

        s_instance._currentPersona = persona;

        // Set Name
        s_instance.partyMemberName.setText(persona.getName());

        // Set Arcana
        s_instance.arcanaComboBox.setValue(persona.getArcana());

        // Set Stats
        s_instance.lvlField.setText(String.valueOf(persona.getLevel()));
        int[] stats = persona.getStats();
        for (int i = 0; i < stats.length; i++) {
            s_instance.STAT_FIELDS[i].setText(String.valueOf(stats[i]));
        }

        // Update CopyOf ComboBox
        CopyHolder[] copyHolders = new CopyHolder[3];
        s_instance.copyOfComboBox.getItems().clear();
        for (int i = 0; i < 3; i++) {
            copyHolders[i] = new CopyHolder(partyMember.personas[i].epartyPersona, i);
            s_instance.copyOfComboBox.getItems().add(copyHolders[i]);
        }
        s_instance._partyMember = partyMember;
        s_instance._partyMemberPersonaIndex = partyPersonaIndex;
        s_instance.copyOfComboBox.setValue(copyHolders[partyMember.personas[partyPersonaIndex].copyOfPersona]);

        // disableEditor(); // If the value doesn't change, re-disable/enable since SkillsEditor will reset
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


    private static void disableEditor() {
        boolean disable = (s_instance._partyMemberPersonaIndex != s_instance._partyMember.personas[s_instance._partyMemberPersonaIndex].copyOfPersona);
        s_instance.arcanaComboBox.setDisable(disable);
        s_instance.lvlField.setDisable(disable);
        for (TextField field : s_instance.STAT_FIELDS) {
            field.setDisable(disable);
        }
        
        PESkillsTabController.disableEditor(disable);
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
        s_instance.copyOfComboBox.valueProperty().removeListener(s_instance._copyListener);
    }
}