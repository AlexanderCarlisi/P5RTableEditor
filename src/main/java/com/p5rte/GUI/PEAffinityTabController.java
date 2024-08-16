package com.p5rte.GUI;

import java.util.HashMap;

import com.p5rte.Classes.Persona;
import com.p5rte.Utils.Enums.AffinityDataIndex;
import com.p5rte.Utils.Enums.AffinityIndex;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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
    private final ToggleButton[] toggleButtons = new ToggleButton[8];

    private final ChangeListener<AffinityIndex> elementChangeListener = (__, ___, ____) -> updateFields(currentPersona);
    private final ChangeListener<String> multiplierChangeListener = (__, ___, newVal) -> {
        if (currentPersona == null) return;
        AffinityIndex affinity = elementComboBox.getValue();
        if (affinity == null) return;

        currentPersona.getAffinity(affinity).multiplier = readMultiplierField(newVal);
    };
    private final ChangeListener<Boolean>[] toggleChangeListeners = new ChangeListener[8]; 


    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    public void initialize() {
        instance = this;
        instance.elementComboBox.getItems().addAll(AffinityIndex.values());
        instance.elementComboBox.setValue(AffinityIndex.Physical);
        instance.elementComboBox.valueProperty().addListener(elementChangeListener);

        toggleButtons[0] = dacToggle;
        toggleButtons[1] = guaranteeToggle;
        toggleButtons[2] = ailmentImmuneToggle;
        toggleButtons[3] = resistToggle;
        toggleButtons[4] = weakToggle;
        toggleButtons[5] = drainToggle;
        toggleButtons[6] = repelToggle;
        toggleButtons[7] = blockToggle;

        for (int i = 0; i < 8; i++) {
            final int index = i;
            toggleChangeListeners[i] = (__, ___, newVal) -> {
                if (currentPersona == null) return;
                AffinityIndex affinity = elementComboBox.getValue();
                if (affinity == null) return;

                currentPersona.getAffinity(affinity).data.put(AffinityDataIndex.values()[index], newVal);
            };
            toggleButtons[i].selectedProperty().addListener(toggleChangeListeners[i]);
        }

        instance.multiplierField.textProperty().addListener(multiplierChangeListener);
    }


    public static void updateFields(Persona persona) {
        if (instance == null) return;
        instance.currentPersona = persona;

        AffinityIndex affinity = instance.elementComboBox.getValue();
        if (affinity == null) return;

        HashMap<AffinityDataIndex, Boolean> data = persona.getAffinity(affinity).data;
        for (int i = 0; i < 8; i++) {
            instance.toggleButtons[i].setSelected(data.get(AffinityDataIndex.values()[i]));
        }
        instance.multiplierField.setText(String.valueOf(persona.getAffinity(affinity).multiplier));
    }


    private static int readMultiplierField(String text) {
        if (text == null || text.isEmpty()) return 20;

        if (text.matches("\\d+")) {
            return Integer.parseInt(text);
        } else {
            return 20;
        }
    }


    /**
     * Release all resources used by this controller
     * This method should be called when the tab is no longer in use
     */
    public static void releaseResources() {
        if (instance == null) return;
        instance.elementComboBox.valueProperty().removeListener(instance.elementChangeListener);
        instance.multiplierField.textProperty().removeListener(instance.multiplierChangeListener);
        for (int i = 0; i < 8; i++) {
            instance.toggleButtons[i].selectedProperty().removeListener(instance.toggleChangeListeners[i]);
        }
    }
}