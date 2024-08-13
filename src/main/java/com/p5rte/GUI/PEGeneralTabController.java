package com.p5rte.GUI;

import com.p5rte.Classes.Persona;
import com.p5rte.Utils.Enums;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
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

    // Skills Tab Fields
    @FXML
    private ComboBox<Enums.SkillInheritance> inheritanceComboBox;
    @FXML
    private VBox skillContainer;

    private Stage stage;

    private static PEGeneralTabController instance;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        arcanaComboBox.getItems().addAll(Enums.Arcana.values());

        instance = this;
    }


    public static void updateFields(Persona persona) {
        if (instance == null) return;

        // Set Name
        instance.personaNameLabel.setText(persona.getName());

        // Set Arcana
        instance.arcanaComboBox.setValue(persona.getArcana());

        // Set Stats
        instance.lvlField.setText(String.valueOf(persona.getLevel()));
        int[] stats = persona.getStats();
        instance.strengthField.setText(String.valueOf(stats[0]));
        instance.magicField.setText(String.valueOf(stats[1]));
        instance.enduranceField.setText(String.valueOf(stats[2]));
        instance.agilityField.setText(String.valueOf(stats[3]));
        instance.luckField.setText(String.valueOf(stats[4]));

        // Set Bit Flags
        boolean[] flags = persona.getBitFlags();
        instance.DLCFlag.setSelected(flags[0]);
        instance.treasureFlag.setSelected(flags[1]);
        instance.partyFlag.setSelected(flags[4]);
        instance.storyFlag.setSelected(flags[5]);
        instance.nRegFlag.setSelected(flags[6]);
        instance.fusionFlag.setSelected(flags[8]);
        instance.evolvedFlag.setSelected(flags[9]);

        // Set Stat Weights
        int[] statWeights = persona.getStatWeights();
        instance.strWeightField.setText(String.valueOf(statWeights[0]));
        instance.magWeightField.setText(String.valueOf(statWeights[1]));
        instance.endWeightField.setText(String.valueOf(statWeights[2]));
        instance.agiWeightField.setText(String.valueOf(statWeights[3]));
        instance.lukWeightField.setText(String.valueOf(statWeights[4]));
    }
}