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

        private final int INDEX; // Skill index for _currentPersona
        private final ChangeListener<Object> SKILLID_LISTENER;
        private final ChangeListener<SkillLearnability> LEARN_LISTENER;
        private final ChangeListener<String> PENDING_LISTENER;

    
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
            SKILLID_LISTENER = (__, ___, newValue) -> {
                // ESkill has more Values than ETrait,
                // So we can safely set ESkill to ETrait, but not ViseVersa
                if (newValue instanceof ESkill) {
                    ESkill eskill = (ESkill) newValue;

                    if (s_instance._currentPersona != null)
                        s_instance._currentPersona.setSkillID(INDEX, eskill.ordinal());

                    if (eskill.ordinal() < ETrait.values().length) {
                        setTraitBox(ETrait.values()[eskill.ordinal()]);

                    } else {
                        setTraitBox(ETrait.NoTrait);
                    }

                } else if (newValue instanceof ETrait) {
                    ETrait etrait = (ETrait) newValue;

                    if (s_instance._currentPersona != null)
                        s_instance._currentPersona.setSkillID(INDEX, etrait.ordinal());

                    // ESkill has more Values than ETrait
                    setSkillBox(ESkill.values()[etrait.ordinal()]);
                }
            };

            skillID.valueProperty().addListener(SKILLID_LISTENER);
            traitID.valueProperty().addListener(SKILLID_LISTENER);

            // Setup LearnChangeListener
            LEARN_LISTENER = (__, oldValue, newValue) -> {
                if (s_instance._currentPersona != null)
                    s_instance._currentPersona.setSkillLearnability(INDEX, newValue);

                enableHolder();
            };

            learnability.valueProperty().addListener(LEARN_LISTENER);

            // Setup PendingLevels Listener
            PENDING_LISTENER = (__, ___, newValue) -> {
                if (s_instance._currentPersona != null)
                    s_instance._currentPersona.setSkillPendingLevel(INDEX, readPendingLevels(newValue));
            };

            pendingLevels.textProperty().addListener(PENDING_LISTENER);

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


        private void setSkillBox(ESkill newValue) {
            skillID.valueProperty().removeListener(SKILLID_LISTENER);
            skillID.setValue(newValue);
            skillID.valueProperty().addListener(SKILLID_LISTENER);
        }

        private void setTraitBox(ETrait newValue) {
            traitID.valueProperty().removeListener(SKILLID_LISTENER);
            traitID.setValue(newValue);
            traitID.valueProperty().addListener(SKILLID_LISTENER);
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

        private void disableHolder() {
            learnability.setDisable(true);
            skillID.setDisable(true);
            traitID.setDisable(true);
            pendingLevels.setDisable(true);
        }

        private void enableHolder() {
            switch(learnability.getValue()) {
                case Skill: 
                    setToSkill();
                    // pendingLevels.setText(String.valueOf(INDEX)); 
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
        }


        public void clearListeners() {
            // Leaving the Listener on the ComboBoxes will cause a memory leak
            skillID.valueProperty().removeListener(SKILLID_LISTENER);
            traitID.valueProperty().removeListener(SKILLID_LISTENER);
            learnability.valueProperty().removeListener(LEARN_LISTENER);
            pendingLevels.textProperty().removeListener(PENDING_LISTENER);
        }
    }    



    // FXML Elements
    @FXML private ComboBox<Enums.SkillInheritance> inheritanceComboBox;
    @FXML private VBox skillContainer;
    @FXML private VBox overallContainer;
    @FXML private HBox inheritanceHBox;
    private Stage stage;

    private final SkillHolder[] SKILL_HOLDERS = new SkillHolder[32];
    private static PESkillsTabController s_instance;
    private Persona _currentPersona;
    private ChangeListener<Enums.SkillInheritance> _inheritanceListener;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        s_instance = this;
        populateSkillContainer();
        inheritanceComboBox.getItems().addAll(Enums.SkillInheritance.values());
        _inheritanceListener = (__, ___, newValue) -> {
            if (_currentPersona != null) {
                _currentPersona.setSkillInheritance(newValue);
            }
        };
        inheritanceComboBox.valueProperty().addListener(_inheritanceListener);

        setToRegistryEditor();
    }


    private void populateSkillContainer() {
        // Clear the Container
        skillContainer.getChildren().clear();

        for (int i = 0; i < SKILL_HOLDERS.length; i++) {
            // Generate Skill Row elements, and add them to the container
            SKILL_HOLDERS[i] = new SkillHolder(i);
            HBox skillRow = new HBox();
            skillRow.getChildren().addAll(SKILL_HOLDERS[i].learnability, SKILL_HOLDERS[i].skillID, SKILL_HOLDERS[i].traitID, SKILL_HOLDERS[i].pendingLevels);
            skillContainer.getChildren().add(skillRow);
        }
    }


    public static void updateFields(Persona persona) {
        updateFields(persona, false);
    }

    public static void updateFields(Persona persona, boolean setDisable) {
        if (s_instance == null) return;

        // Update Current Persona
        s_instance._currentPersona = persona;

        // Set Skill Inheritance
        s_instance.inheritanceComboBox.setValue(persona.getSkillInheritance());

        // Set Skills
        Skill[] skills = persona.getSkills();
        for (int i = 0; i < s_instance.SKILL_HOLDERS.length; i++) {
            if (i < skills.length) {
                if (setDisable) s_instance.SKILL_HOLDERS[i].disableHolder();
                else s_instance.SKILL_HOLDERS[i].enableHolder();

                Skill s = skills[i];
                s_instance.SKILL_HOLDERS[i].learnability.setValue(s.getLearnability());
                s_instance.SKILL_HOLDERS[i].skillID.setValue(s.getESkill()); // updates Trait from Listener
                s_instance.SKILL_HOLDERS[i].pendingLevels.setText(String.valueOf(s.getPendingLevels()));

            } else {
                s_instance.SKILL_HOLDERS[i].disableHolder();
            }
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
        if (s_instance == null) return;

        s_instance.inheritanceComboBox.valueProperty().removeListener(s_instance._inheritanceListener);
        for (SkillHolder holder : s_instance.SKILL_HOLDERS) {
            holder.clearListeners();
        }
    }


    public static void setToPartyEditor() {
        if (s_instance == null) return;

        if(s_instance.overallContainer.getChildren().contains(s_instance.inheritanceHBox)) {
            s_instance.overallContainer.getChildren().remove(s_instance.inheritanceHBox);
        }
    }


    public static void setToRegistryEditor() {
        if (s_instance == null) return;

        if(!s_instance.overallContainer.getChildren().contains(s_instance.inheritanceHBox)) {
            s_instance.overallContainer.getChildren().add(s_instance.inheritanceHBox);
        }
    }


    public static void disableEditor(boolean disable) {
        if (s_instance == null) return;

        s_instance.inheritanceComboBox.setDisable(disable);
        for (SkillHolder sh : s_instance.SKILL_HOLDERS) {
            if (disable) sh.disableHolder();
            else sh.enableHolder();
        }
    }
}