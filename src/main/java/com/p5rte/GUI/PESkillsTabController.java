package com.p5rte.GUI;

import com.p5rte.Classes.Persona;
import com.p5rte.Classes.Skill;
import com.p5rte.Utils.Enums;
import com.p5rte.Utils.Enums.ESkill;
import com.p5rte.Utils.Enums.ETrait;
import com.p5rte.Utils.Enums.SkillLearnability;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PESkillsTabController {

    public class SkillHolder {
        public final ComboBox<SkillLearnability> learnability;
        public final ComboBox<ESkill> skillID;
        public final ComboBox<ETrait> traitID;
        public final TextField pendingLevels;

        private final int INDEX; // Skill index for currentPersona
        private final ChangeListener<Object> idChangeListener;
        private final ChangeListener<SkillLearnability> learnChangeListener;
        private final ChangeListener<String> pendingLevelsListener;

    
        public SkillHolder(int index) {
            INDEX = index;

            // Generate Objects
            learnability = new ComboBox<>();
            skillID = new ComboBox<>();
            traitID = new ComboBox<>();
            pendingLevels = new TextField();
            
            // Fill ComboBoxes
            learnability.getItems().addAll(SkillLearnability.values());
            skillID.getItems().addAll(ESkill.values());
            traitID.getItems().addAll(ETrait.values());

            // Setup SkillID and TraitID Listeners
            idChangeListener = (obs, oldValue, newValue) -> {
                // ESkill has more Values than ETrait,
                // So we can safely set ESkill to ETrait, but not ViseVersa
                if (newValue instanceof ESkill) {
                    ESkill eskill = (ESkill) newValue;

                    if (instance.currentPersona != null)
                        instance.currentPersona.setSkillID(INDEX, eskill.ordinal());

                    if (eskill.ordinal() < ETrait.values().length) {
                        traitID.setValue(ETrait.values()[eskill.ordinal()]);

                    } else {
                        traitID.setValue(ETrait.NoTrait);
                    }

                } else if (newValue instanceof ETrait) {
                    ETrait etrait = (ETrait) newValue;

                    if (instance.currentPersona != null)
                        instance.currentPersona.setSkillID(INDEX, etrait.ordinal());

                    // ESkill has more Values than ETrait
                    skillID.setValue(ESkill.values()[etrait.ordinal()]);
                }
            };

            skillID.valueProperty().addListener(idChangeListener);
            traitID.valueProperty().addListener(idChangeListener);

            // Setup LearnChangeListener
            learnChangeListener = (obs, oldValue, newValue) -> {

                if (instance.currentPersona != null)
                    instance.currentPersona.setSkillLearnability(INDEX, newValue);

                switch(newValue) {
                    case Skill:
                        skillID.setDisable(false);
                        traitID.setDisable(true);
                        pendingLevels.setDisable(false);
                        break;
                    case Trait:
                        skillID.setDisable(true);
                        traitID.setDisable(false);
                        pendingLevels.setDisable(true);

                        if (oldValue == SkillLearnability.Nothing) {
                            pendingLevels.setText(String.valueOf(INDEX));
                        }

                        break;
                    default:
                        skillID.setDisable(true);
                        traitID.setDisable(true);
                        pendingLevels.setDisable(true);

                        // Reset Values, and trigger their listeners to update Current Persona
                        skillID.setValue(ESkill.None); // also does traitID via Listener
                        pendingLevels.setText("0");
                        break;
                }
            };

            learnability.valueProperty().addListener(learnChangeListener);

            // Setup PendingLevels Listener
            pendingLevelsListener = (obs, oldValue, newValue) -> {
                if (instance.currentPersona != null)
                    instance.currentPersona.setSkillPendingLevel(INDEX, readPendingLevels(newValue));
            };

            pendingLevels.textProperty().addListener(pendingLevelsListener);

            // Set starting values
            learnability.setValue(SkillLearnability.Nothing);
            skillID.setValue(ESkill.None); // Sets TraitID via Listener
            pendingLevels.setText("0");
    
            // // Set ComboBox to be editable
            // this.skillID.setEditable(true);
    
            // // FilteredList for filtering items based on user input
            // FilteredList<Enums.ESkill> filteredSkills = new FilteredList<>(FXCollections.observableArrayList(Enums.ESkill.values()), p -> true);
    
            // // Set the ComboBox items to the filtered list
            // this.skillID.setItems(filteredSkills);
    
            // // Add a listener to the ComboBox's editor (TextField) to filter items
            // this.skillID.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                
            //     final TextField editor = this.skillID.getEditor();
            //     final ESkill selected = this.skillID.getSelectionModel().getSelectedItem();
        
            //     // Only proceed if the text actually changes
            //     if (newValue == null || !newValue.equals(oldValue)) {
            //         // If no item is selected or the editor text doesn't match the selected item, filter the list
            //         if (selected == null || !selected.name().equals(editor.getText())) {
            //             filteredSkills.setPredicate(skill -> {
            //                 // Show all skills if the editor is empty
            //                 if (newValue == null || newValue.isEmpty()) {
            //                     return true;
            //                 }
            //                 // Filter by the skill name
            //                 String lowerCaseFilter = newValue.toLowerCase();
            //                 return skill.name().toLowerCase().contains(lowerCaseFilter);
            //             });
            //         }
            //     }   
            // });
    
            // // Update the editor's text when an item is selected
            // this.skillID.setConverter(new StringConverter<Enums.ESkill>() {
            //     @Override
            //     public String toString(Enums.ESkill skill) {
            //         return skill == null ? "" : skill.name();   
            //     }
    
            //     @Override
            //     public Enums.ESkill fromString(String string) {
            //         return skillID.getItems().stream().filter(skill -> skill.name().equals(string)).findFirst().orElse(null);   
            //     }
            // });
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
            // Generate Skill Row elements, and add them to the container
            skillHolders[i] = new SkillHolder(i);
            HBox skillRow = new HBox();
            skillRow.getChildren().addAll(skillHolders[i].learnability, skillHolders[i].skillID, skillHolders[i].traitID, skillHolders[i].pendingLevels);
            skillContainer.getChildren().add(skillRow);
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
            instance.skillHolders[i].learnability.setValue(s.getLearnability());
            instance.skillHolders[i].skillID.setValue(s.getESkill()); // updates Trait from Listener
            instance.skillHolders[i].pendingLevels.setText(String.valueOf(s.getPendingLevels()));
        }
    }


    private static int readPendingLevels(String value) {
        if (value == null || value.isEmpty()) return 0;
        for (char c : value.toCharArray()) {
            if (!Character.isDigit(c)) return 0;
        }

        return Integer.parseInt(value);
    }
}