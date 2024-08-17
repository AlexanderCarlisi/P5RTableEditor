package com.p5rte.GUI;

import com.p5rte.Classes.Persona;
import com.p5rte.Classes.Skill;
import com.p5rte.Utils.Enums;
import com.p5rte.Utils.Enums.ESkill;
import com.p5rte.Utils.Enums.ETrait;
import com.p5rte.Utils.Enums.SkillLearnability;
import com.p5rte.Utils.FxUtil;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;


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
            idChangeListener = (__, ___, newValue) -> {
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
            learnChangeListener = (__, oldValue, newValue) -> {
                if (instance.currentPersona != null)
                    instance.currentPersona.setSkillLearnability(INDEX, newValue);

                switch(newValue) {
                    case Skill: 
                        setToSkill();
                        pendingLevels.setText(String.valueOf(INDEX)); 
                        break;
                    case Trait:
                        setToTrait();
                        pendingLevels.setText("0");
                        break;
                    default:
                        setToNothing();
                        skillID.setValue(ESkill.None); // also does traitID via Listener
                        pendingLevels.setText("0");
                        break;
                }
            };

            learnability.valueProperty().addListener(learnChangeListener);

            // Setup PendingLevels Listener
            pendingLevelsListener = (__, ___, newValue) -> {
                if (instance.currentPersona != null)
                    instance.currentPersona.setSkillPendingLevel(INDEX, readPendingLevels(newValue));
            };

            pendingLevels.textProperty().addListener(pendingLevelsListener);

            // Set starting values
            learnability.setValue(SkillLearnability.Nothing);
            skillID.setValue(ESkill.None); // Sets TraitID via Listener
            pendingLevels.setText("0");

            // Add Search to SkillID and TraitID
            FxUtil.autoCompleteComboBoxPlus(skillID, (typedText, itemToCompare) -> itemToCompare.name().toLowerCase().contains(typedText.toLowerCase()) || String.valueOf(itemToCompare.ordinal()).equals(typedText));
            skillID.setConverter(new StringConverter<ESkill>() {
                @Override
                public String toString(ESkill object) {
                    return object != null ? object.name() : "";
                }
            
                @Override
                public ESkill fromString(String string) {
                    return skillID.getItems().stream().filter(object ->
                        object.name().equals(string)).findFirst().orElse(null);
                }
            });

            FxUtil.autoCompleteComboBoxPlus(traitID, (typedText, itemToCompare) -> itemToCompare.name().toLowerCase().contains(typedText.toLowerCase()) || String.valueOf(itemToCompare.ordinal()).equals(typedText));
            traitID.setConverter(new StringConverter<ETrait>() {
                @Override
                public String toString(ETrait object) {
                    return object != null ? object.name() : "";
                }
            
                @Override
                public ETrait fromString(String string) {
                    return traitID.getItems().stream().filter(object ->
                        object.name().equals(string)).findFirst().orElse(null);
                }
            });
        }


        private void setToSkill() {
            skillID.setDisable(false);
            traitID.setDisable(true);
            pendingLevels.setDisable(false);
        }

        private void setToTrait() {
            skillID.setDisable(true);
            traitID.setDisable(false);
            pendingLevels.setDisable(true);
        }

        private void setToNothing() {
            skillID.setDisable(true);
            traitID.setDisable(true);
            pendingLevels.setDisable(true);
        }


        public void clearListeners() {
            // Leaving the Listener on the ComboBoxes will cause a memory leak
            skillID.valueProperty().removeListener(idChangeListener);
            traitID.valueProperty().removeListener(idChangeListener);
            learnability.valueProperty().removeListener(learnChangeListener);
            pendingLevels.textProperty().removeListener(pendingLevelsListener);
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
    private ChangeListener<Enums.SkillInheritance> inheritanceListener;


    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    public void initialize() {
        instance = this;
        populateSkillContainer();
        inheritanceComboBox.getItems().addAll(Enums.SkillInheritance.values());
        inheritanceListener = (__, ___, newValue) -> {
            if (currentPersona != null) {
                currentPersona.setSkillInheritance(newValue);
            }
        };
        inheritanceComboBox.valueProperty().addListener(inheritanceListener);
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


    /**
     * Releases all resources used by this Tab.
     * Should only be called when Returning to the Main Menu.
     */
    public static void releaseResources() {
        if (instance == null) return;

        instance.inheritanceComboBox.valueProperty().removeListener(instance.inheritanceListener);
        for (SkillHolder holder : instance.skillHolders) {
            holder.clearListeners();
        }
    }
}