package com.p5rte.GUI;

import java.util.HashMap;

import com.p5rte.Classes.Enemy;
import com.p5rte.Utils.Enums.AffinityDataIndex;
import com.p5rte.Utils.Enums.AffinityIndex;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;


public class EnemyAffinityController {

    // FXML Elements
    @FXML private ComboBox<AffinityIndex> elementComboBox;
    @FXML private ToggleButton dacToggle;
    @FXML private ToggleButton guaranteeToggle;
    @FXML private ToggleButton ailmentImmuneToggle;
    @FXML private ToggleButton resistToggle;
    @FXML private ToggleButton weakToggle;
    @FXML private ToggleButton drainToggle;
    @FXML private ToggleButton repelToggle;
    @FXML private ToggleButton blockToggle;
    @FXML private TextField multiplierField;
    private Stage stage;


    private final ToggleButton[] TOGGLE_BUTTONS = new ToggleButton[8];

    @SuppressWarnings("unchecked")
    private final ChangeListener<Boolean>[] toggleChangeListeners = new ChangeListener[8];

    private static EnemyAffinityController s_instance;
    private Enemy _currentEnemy;

    private final ChangeListener<AffinityIndex> ELEMENT_LISTENER = (__, ___, ____) -> updateFields(_currentEnemy);
    private final ChangeListener<String> MULTIPLIER_LISTENER = (__, ___, newVal) -> {
        if (_currentEnemy == null) return;
        AffinityIndex affinity = elementComboBox.getValue();
        if (affinity == null) return;

        _currentEnemy.affinities.get(affinity).multiplier = readMultiplierField(newVal);
    };
    

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    public void initialize() {
        s_instance = this;
        s_instance.elementComboBox.getItems().addAll(AffinityIndex.values());
        s_instance.elementComboBox.setValue(AffinityIndex.Physical);
        s_instance.elementComboBox.valueProperty().addListener(ELEMENT_LISTENER);

        TOGGLE_BUTTONS[0] = dacToggle;
        TOGGLE_BUTTONS[1] = guaranteeToggle;
        TOGGLE_BUTTONS[2] = ailmentImmuneToggle;
        TOGGLE_BUTTONS[3] = resistToggle;
        TOGGLE_BUTTONS[4] = weakToggle;
        TOGGLE_BUTTONS[5] = drainToggle;
        TOGGLE_BUTTONS[6] = repelToggle;
        TOGGLE_BUTTONS[7] = blockToggle;

        for (int i = 0; i < 8; i++) {
            final int index = i;
            toggleChangeListeners[i] = (__, ___, newVal) -> {
                if (_currentEnemy == null) return;
                AffinityIndex affinity = elementComboBox.getValue();
                if (affinity == null) return;

                _currentEnemy.affinities.get(affinity).data.put(AffinityDataIndex.values()[index], newVal);
            };
            TOGGLE_BUTTONS[i].selectedProperty().addListener(toggleChangeListeners[i]);
        }

        s_instance.multiplierField.textProperty().addListener(MULTIPLIER_LISTENER);
    }


    public static void updateFields(Enemy enemy) {
        if (s_instance == null) return;
        s_instance._currentEnemy = enemy;

        AffinityIndex affinity = s_instance.elementComboBox.getValue();
        if (affinity == null) return;

        HashMap<AffinityDataIndex, Boolean> data = enemy.affinities.get(affinity).data;
        for (int i = 0; i < 8; i++) {
            s_instance.TOGGLE_BUTTONS[i].setSelected(data.get(AffinityDataIndex.values()[i]));
        }
        s_instance.multiplierField.setText(String.valueOf(enemy.affinities.get(affinity).multiplier));
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
        if (s_instance == null) return;
        s_instance.elementComboBox.valueProperty().removeListener(s_instance.ELEMENT_LISTENER);
        s_instance.multiplierField.textProperty().removeListener(s_instance.MULTIPLIER_LISTENER);
        for (int i = 0; i < 8; i++) {
            s_instance.TOGGLE_BUTTONS[i].selectedProperty().removeListener(s_instance.toggleChangeListeners[i]);
        }
    }
}