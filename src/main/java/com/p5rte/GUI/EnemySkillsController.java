package com.p5rte.GUI;

import com.p5rte.Classes.Enemy;
import com.p5rte.Utils.Enums.ESkill;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EnemySkillsController {

    @FXML private VBox skillContainer;

    @SuppressWarnings("unchecked")
    private final ChangeListener<ESkill>[] SKILL_LISTENERS = new ChangeListener[8]; 

    @SuppressWarnings("unchecked")
    private final ComboBox<ESkill>[] SKILL_BOXES = new ComboBox[8];

    private static EnemySkillsController s_instance;
    private Enemy _currentEnemy;

    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    public void initialize() {
        s_instance = this;
        for (int i = 0; i < 8; i++) {
            ComboBox<ESkill> skillComboBox = new ComboBox<>();
            skillComboBox.getItems().addAll(ESkill.values());

            final int INDEX = i;
            SKILL_LISTENERS[INDEX] = ((__, ___, newValue) -> {
                s_instance._currentEnemy.skillIDs[INDEX] = (short) newValue.ordinal();
            });
            skillComboBox.valueProperty().addListener(SKILL_LISTENERS[INDEX]);
            skillContainer.getChildren().add(skillComboBox);
            SKILL_BOXES[i] = skillComboBox;
        }
    }


    public static void updateFields(Enemy enemy) {
        s_instance._currentEnemy = enemy;
        for (int i = 0; i < 8; i++) {
            s_instance.SKILL_BOXES[i].setValue(ESkill.values()[enemy.skillIDs[i]]);
        }
    }


    public static void releaseResources() {
        for (int i = 0; i < 8; i++) {
            s_instance.SKILL_BOXES[i].valueProperty().removeListener(s_instance.SKILL_LISTENERS[i]);
        }
    }
}
