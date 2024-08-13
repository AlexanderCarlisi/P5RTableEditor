package com.p5rte.GUI;

import java.util.ArrayList;
import java.util.List;

import com.p5rte.Classes.Persona;
import com.p5rte.Classes.PersonaTable;
import com.p5rte.Utils.Constants;
import com.p5rte.Utils.Enums;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PersonaEditorController {

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

    private Stage stage;

    /** Stores Buttons for Search and Filtering */
    private List<Button> personaButtons = new ArrayList<>();


    public void setStage(Stage stage) {
        this.stage = stage;

        // Initialize Persona Data
        PersonaTable.startPersonaStream();
        PersonaTable.readPersonas();

        // Create and store buttons once
        createButtons(Constants.personaIDtoName);
        arcanaComboBox.getItems().addAll(Enums.Arcana.values());

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
    }
}