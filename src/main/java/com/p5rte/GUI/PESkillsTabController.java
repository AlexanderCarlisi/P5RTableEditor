package com.p5rte.GUI;

import com.p5rte.Classes.Persona;
import com.p5rte.Classes.Skill;
import com.p5rte.Utils.Enums;
import com.p5rte.Utils.Enums.ESkill;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.util.StringConverter;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PESkillsTabController {

    public class SkillHolder {
        /*
         * TODO: Add Traits 
         */
        public ComboBox<Enums.ESkill> skillID;
        public ComboBox<Enums.SkillLearnability> learnability;
        public TextField pendingLevels;
    
        public SkillHolder(ComboBox<Enums.ESkill> skillID, ComboBox<Enums.SkillLearnability> learnability, TextField pendingLevels) {
            this.skillID = skillID;
            this.learnability = learnability;
            this.pendingLevels = pendingLevels;
    
            // Set ComboBox to be editable
            this.skillID.setEditable(true);
    
            // FilteredList for filtering items based on user input
            FilteredList<Enums.ESkill> filteredSkills = new FilteredList<>(FXCollections.observableArrayList(Enums.ESkill.values()), p -> true);
    
            // Set the ComboBox items to the filtered list
            this.skillID.setItems(filteredSkills);
    
            // Add a listener to the ComboBox's editor (TextField) to filter items
            this.skillID.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                
                final TextField editor = this.skillID.getEditor();
                final ESkill selected = this.skillID.getSelectionModel().getSelectedItem();
        
                // Only proceed if the text actually changes
                if (newValue == null || !newValue.equals(oldValue)) {
                    // If no item is selected or the editor text doesn't match the selected item, filter the list
                    if (selected == null || !selected.name().equals(editor.getText())) {
                        filteredSkills.setPredicate(skill -> {
                            // Show all skills if the editor is empty
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }
                            // Filter by the skill name
                            String lowerCaseFilter = newValue.toLowerCase();
                            return skill.name().toLowerCase().contains(lowerCaseFilter);
                        });
                    }
                }   
            });
    
            // Update the editor's text when an item is selected
            this.skillID.setConverter(new StringConverter<Enums.ESkill>() {
                @Override
                public String toString(Enums.ESkill skill) {
                    return skill == null ? "" : skill.name();   
                }
    
                @Override
                public Enums.ESkill fromString(String string) {
                    return skillID.getItems().stream().filter(skill -> skill.name().equals(string)).findFirst().orElse(null);   
                }
            });
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

    private Persona currentPersona;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        instance = this;
        populateSkillContainer();
        inheritanceComboBox.getItems().addAll(Enums.SkillInheritance.values());
        inheritanceComboBox.setOnHidden(e -> {
            currentPersona.setSkillInheritance(inheritanceComboBox.getValue());
        });
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
            
            final int index = i;
            skillID.setOnHidden(e -> {
                currentPersona.setSkill(index, skillID.getValue().ordinal(), learnability.getValue(), readPendingLevels(pendingLevels.getText()));
            });
            learnability.setOnHidden(e -> {
                currentPersona.setSkill(index, skillID.getValue().ordinal(), learnability.getValue(), readPendingLevels(pendingLevels.getText()));
            });
            pendingLevels.textProperty().addListener((obs, oldValue, newValue) -> {
                currentPersona.setSkill(index, skillID.getValue().ordinal(), learnability.getValue(), readPendingLevels(pendingLevels.getText()));
            });
        }
    }


    public static void updateFields(Persona persona) {
        if (instance == null) return;

        // Update Current Persona
        instance.currentPersona = persona;

        // Set Skill Inheritance
        instance.inheritanceComboBox.setValue(persona.getSkillInheritance());

        // Set Skills
        Skill[] skills = persona.getSkills();
        for (int i = 0; i < skills.length; i++) {
            Skill s = skills[i];
            instance.skillHolders[i].skillID.setValue(s.getESkill());
            instance.skillHolders[i].learnability.setValue(s.getLearnability());
            instance.skillHolders[i].pendingLevels.setText(String.valueOf(s.getPendingLevels()));
        }
    }


    private static int readPendingLevels(String value) {
        if (value.isEmpty()) return 0;
        for (char c : value.toCharArray()) {
            if (!Character.isDigit(c)) return 0;
        }

        return Integer.parseInt(value);
    }
}