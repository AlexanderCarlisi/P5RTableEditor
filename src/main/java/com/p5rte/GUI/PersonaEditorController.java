package com.p5rte.GUI;

import java.util.ArrayList;
import java.util.List;

import com.p5rte.Classes.Persona;
import com.p5rte.Classes.PersonaTable;
import com.p5rte.Classes.Skill;
import com.p5rte.Utils.Constants;
import com.p5rte.Utils.Enums;
import com.p5rte.Utils.Enums.ESkill;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PersonaEditorController {

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

    @FXML
    private TextField searchField;
    @FXML
    private ScrollPane catalogueScrollPane;
    @FXML
    private VBox catalogueContainer;

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

    // Skill Fields
    @FXML
    private ComboBox<Enums.SkillInheritance> inheritanceComboBox;
    @FXML
    private VBox skillContainer;


    private Stage stage;

    /** Stores Buttons for Search and Filtering */
    private List<Button> personaButtons = new ArrayList<>();

    /** Stores Skill Fields for Persona Skills */
    private SkillHolder[] skillHolders = new SkillHolder[16];


    public void setStage(Stage stage) {
        this.stage = stage;

        // Initialize Persona Data
        PersonaTable.startPersonaStream();
        PersonaTable.readPersonas();

        // Create and store buttons
        createButtons(Constants.personaIDtoName);

        // Populate Fields
        arcanaComboBox.getItems().addAll(Enums.Arcana.values());
        inheritanceComboBox.getItems().addAll(Enums.SkillInheritance.values());
        populateSkillContainer();

        // Set up a listener to filter the catalogue in real-time
        searchField.textProperty().addListener((obs, oldText, newText) -> filterCatalogue(newText));
    }


    @FXML
    private void handleBackToMainMenu() {
        try {
            // Load the main menu FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.Path.MAIN_MENU));
            Scene mainMenuScene = new Scene(loader.load(), stage.getWidth(), stage.getHeight());

            // Get the main menu controller and pass the stage
            MainMenuController controller = loader.getController();
            controller.setStage(stage);

            // Set the main menu scene
            stage.setScene(mainMenuScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createButtons(String[] personas) {
        catalogueContainer.getChildren().clear();
        personaButtons.clear();
        
        for (int i = 0; i < personas.length; i++) {
            Button personaButton = new Button(personas[i]);
            final int index = i;
            personaButton.setOnAction(e -> handlePersonaButtonClick(index));
            catalogueContainer.getChildren().add(personaButton);
            personaButtons.add(personaButton);
        }
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


    private void filterCatalogue(String query) {
        List<Button> visibleButtons = new ArrayList<>();
        for (Button button : personaButtons) {
            boolean visible = button.getText().toLowerCase().contains(query.toLowerCase());
            button.setVisible(visible);
            if (visible) visibleButtons.add(button);
        }
        catalogueContainer.getChildren().clear();
        catalogueContainer.getChildren().addAll(visibleButtons);
    }


    private void handlePersonaButtonClick(int index) {
        Persona persona = PersonaTable.getPersona(index);

        // Set Name
        personaNameLabel.setText(persona.getName());

        // Set Arcana
        arcanaComboBox.setValue(persona.getArcana());

        // resistances are in Unit.TBL
        // Two Combo boxes for resistances
        // one displays the current element, the other displays the affinity to the element (weak, resist, etc.)

        // Set Stats
        lvlField.setText(String.valueOf(persona.getLevel()));
        int[] stats = persona.getStats();
        strengthField.setText(String.valueOf(stats[0]));
        magicField.setText(String.valueOf(stats[1]));
        enduranceField.setText(String.valueOf(stats[2]));
        agilityField.setText(String.valueOf(stats[3]));
        luckField.setText(String.valueOf(stats[4]));

        // Set Bit Flags
        boolean[] flags = persona.getBitFlags();
        DLCFlag.setSelected(flags[0]);
        treasureFlag.setSelected(flags[1]);
        partyFlag.setSelected(flags[4]);
        storyFlag.setSelected(flags[5]);
        nRegFlag.setSelected(flags[6]);
        fusionFlag.setSelected(flags[8]);
        evolvedFlag.setSelected(flags[9]);

        // Set Skill Inheritance
        inheritanceComboBox.setValue(persona.getSkillInheritance());

        // Set Stat Weights
        int[] statWeights = persona.getStatWeights();
        strWeightField.setText(String.valueOf(statWeights[0]));
        magWeightField.setText(String.valueOf(statWeights[1]));
        endWeightField.setText(String.valueOf(statWeights[2]));
        agiWeightField.setText(String.valueOf(statWeights[3]));
        lukWeightField.setText(String.valueOf(statWeights[4]));

        // Set Skills
        Skill[] skills = persona.getSkills();
        for (int i = 0; i < skills.length; i++) {
            Skill s = skills[i];
            skillHolders[i].skillID.setValue(s.getSkillEnum());
            skillHolders[i].learnability.setValue(s.getLearnability());
            skillHolders[i].pendingLevels.setText(String.valueOf(s.getPendingLevels()));
        }

        // System.out.println("Weighted Stats");
        // for (int weightedStat : persona.getStatWeights()) {
        //     System.out.println("Weighted Stat: " + weightedStat);
        //     System.out.println("\n");
        // }

        // System.out.println("Skills");
        // for (Skill s : persona.getSkills()) {
        //     System.out.println("Skill ID: " + s.getID());
        //     System.out.println("learnability: " + s.getLearnability());
        //     System.out.println("Pending Levels: " + s.getPendingLevels());
        //     System.out.println("\n");
        // }
    }
}