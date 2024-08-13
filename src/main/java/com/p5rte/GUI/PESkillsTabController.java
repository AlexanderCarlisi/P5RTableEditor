package com.p5rte.GUI;

import com.p5rte.Classes.Persona;
import com.p5rte.Classes.Skill;
import com.p5rte.Utils.Enums;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PESkillsTabController {

    private class SkillHolder {
        public ComboBox<Enums.ESkill> skillID;
        public ComboBox<Enums.SkillLearnability> learnability;
        public TextField pendingLevels;

        public SkillHolder(ComboBox<Enums.ESkill> skillID, ComboBox<Enums.SkillLearnability> learnability, TextField pendingLevels) {
            this.skillID = skillID;
            this.learnability = learnability;
            this.pendingLevels = pendingLevels;
        }
    }
    

    // Skills Tab Fields
    @FXML
    private ComboBox<Enums.SkillInheritance> inheritanceComboBox;

    @FXML
    private VBox skillContainer;

    /** Stores Skill Fields for Persona Skills */
    private final SkillHolder[] skillHolders = new SkillHolder[16];

    private Stage stage;

    private static PESkillsTabController instance;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        instance = this;
        populateSkillContainer();
        inheritanceComboBox.getItems().addAll(Enums.SkillInheritance.values());
    }


    private void populateSkillContainer() {
        // Clear the Container
        skillContainer.getChildren().clear();

        for (int i = 0; i < skillHolders.length; i++) {
            HBox skillRow = new HBox();

            ComboBox<Enums.ESkill> skillID = new ComboBox<>();
            skillID.getItems().addAll(Enums.ESkill.values());

            ComboBox<Enums.SkillLearnability> learnability = new ComboBox<>();
            learnability.getItems().addAll(Enums.SkillLearnability.values());

            TextField pendingLevels = new TextField();

            skillRow.getChildren().addAll(skillID, learnability, pendingLevels);
            skillContainer.getChildren().add(skillRow);
            skillHolders[i] = new SkillHolder(skillID, learnability, pendingLevels);
        }
    }


    public static void updateFields(Persona persona) {
        if (instance == null) return;

        // Set Skill Inheritance
        instance.inheritanceComboBox.setValue(persona.getSkillInheritance());

        // Set Skills
        Skill[] skills = persona.getSkills();
        for (int i = 0; i < skills.length; i++) {
            Skill s = skills[i];
            instance.skillHolders[i].skillID.setValue(s.getSkillEnum());
            instance.skillHolders[i].learnability.setValue(s.getLearnability());
            instance.skillHolders[i].pendingLevels.setText(String.valueOf(s.getPendingLevels()));
        }
    }
}