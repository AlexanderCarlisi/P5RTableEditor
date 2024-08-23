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


    // General Tab Fields
    @FXML
    private Label partyMemberName;
    @FXML
    private ComboBox<Enums.Arcana> arcanaComboBox;
    @FXML
    private ComboBox<CopyHolder> copyOfComboBox;

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

    private Stage stage;

    private Persona currentPersona;
    private PartyMember _partyMember;
    private int _partyMemberPersonaIndex;

    private ChangeListener<Arcana> arcanaListener;

    @SuppressWarnings ("unchecked")
    private final ChangeListener<String>[] statListeners = new ChangeListener[6];

    private ChangeListener<CopyHolder> copyOfListener;

    private final TextField[] statFields = new TextField[5];

    private static PARTYEGeneralTabController instance;


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

        // Field Listeners
        for (int i = 0; i < statFields.length; i++) {
            final int INDEX = i;

            // Stat Listeners
            statListeners[i] = (__, ___, newVal) -> {
                setStat(newVal, INDEX);
            };
            statFields[i].textProperty().addListener(statListeners[i]);
        }

        // Level Field Listener
        statListeners[5] = (__, ___, newVal) -> {
            if (instance == null) return;

            int value = getStatFromField(newVal);
            instance.currentPersona.setLevel(value);
        };
        lvlField.textProperty().addListener(statListeners[5]);

        // CopyOf ComboBox Setup
        copyOfListener = (__, ___, newValue) -> {
            if (_partyMember != null && newValue != null) {
                _partyMember.personas[_partyMemberPersonaIndex].copyOfPersona = newValue.indexInPersonas;
            }
        };
        copyOfComboBox.valueProperty().addListener(copyOfListener);
    }


    public static void updateFields(PartyMember partyMember, int partyPersonaIndex, Persona persona) {
        if (instance == null) return;

        instance.currentPersona = persona;

        // Set Name
        instance.partyMemberName.setText(persona.getName());

        // Set Arcana
        instance.arcanaComboBox.setValue(persona.getArcana());

        // Set Stats
        instance.lvlField.setText(String.valueOf(persona.getLevel()));
        int[] stats = persona.getStats();
        for (int i = 0; i < stats.length; i++) {
            instance.statFields[i].setText(String.valueOf(stats[i]));
        }

        // Update CopyOf ComboBox
        CopyHolder[] copyHolders = new CopyHolder[3];
        instance.copyOfComboBox.getItems().clear();
        for (int i = 0; i < 3; i++) {
            copyHolders[i] = new CopyHolder(partyMember.personas[i].epartyPersona, i);
            instance.copyOfComboBox.getItems().add(copyHolders[i]);
        }
        instance._partyMember = partyMember;
        instance._partyMemberPersonaIndex = partyPersonaIndex;
        instance.copyOfComboBox.setValue(copyHolders[partyMember.personas[partyPersonaIndex].copyOfPersona]);
        
        // instance.lvlField.setDisable(disableStats);
        // for (TextField field : instance.statFields) {
        //     field.setDisable(disableStats);
        // }
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
    }
}