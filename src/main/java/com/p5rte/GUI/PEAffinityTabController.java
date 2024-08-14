package com.p5rte.GUI;

import java.util.HashMap;

import com.p5rte.Classes.Persona;
import com.p5rte.Classes.Skill;
import com.p5rte.Utils.Enums;
import com.p5rte.Utils.Enums.AffinityDataIndex;
import com.p5rte.Utils.Enums.AffinityIndex;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PEAffinityTabController {

    @FXML
    private ComboBox<AffinityIndex> elementComboBox;
    @FXML
    private ToggleButton dacToggle;
    @FXML
    private ToggleButton guaranteeToggle;
    @FXML
    private ToggleButton ailmentImmuneToggle;
    @FXML
    private ToggleButton resistToggle;
    @FXML
    private ToggleButton weakToggle;
    @FXML
    private ToggleButton drainToggle;
    @FXML
    private ToggleButton repelToggle;
    @FXML
    private ToggleButton blockToggle;
    @FXML
    private TextField multiplierField;


    private Stage stage;

    private static PEAffinityTabController instance;

    private Persona currentPersona;


    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    public void initialize() {
        instance = this;
        instance.elementComboBox.getItems().addAll(AffinityIndex.values());
        instance.elementComboBox.setValue(AffinityIndex.Physical);
        instance.elementComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateFields(instance.currentPersona));
    }


    public static void updateFields(Persona persona) {
        if (instance == null) return;
        instance.currentPersona = persona;

        AffinityIndex affinity = instance.elementComboBox.getValue();
        if (affinity == null) return;

        HashMap<AffinityDataIndex, Boolean> data = persona.getAffinity(affinity).data;
        instance.dacToggle.setSelected(data.get(AffinityDataIndex.DoubleAilmentChance));
        instance.guaranteeToggle.setSelected(data.get(AffinityDataIndex.GuaranteeAilment));
        instance.ailmentImmuneToggle.setSelected(data.get(AffinityDataIndex.AilmentImmune));
        instance.resistToggle.setSelected(data.get(AffinityDataIndex.Resist));
        instance.weakToggle.setSelected(data.get(AffinityDataIndex.Weak));
        instance.drainToggle.setSelected(data.get(AffinityDataIndex.Drain));
        instance.repelToggle.setSelected(data.get(AffinityDataIndex.Repel));
        instance.blockToggle.setSelected(data.get(AffinityDataIndex.Block));
        instance.multiplierField.setText(String.valueOf(persona.getAffinity(affinity).multiplier));
    }
}