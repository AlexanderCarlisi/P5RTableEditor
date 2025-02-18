package com.p5rte.GUI.PersonaControllers;

import java.util.ArrayList;
import java.util.List;

import com.p5rte.Classes.Persona;
import com.p5rte.Classes.PersonaStream;
import com.p5rte.GUI.GUIManager;
import com.p5rte.GUI.MainMenuController;
import com.p5rte.Utils.Constants;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PersonaEditorController {

    // FXML Elements
    @FXML private TextField searchField;
    @FXML private VBox catalogueContainer;
    @FXML private Tab generalTab;
    private Stage stage;


    /** Stores Buttons for Search and Filtering */
    private final List<Button> PERSONA_BUTTONS = new ArrayList<>();


    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    public void initialize() {
        // Start Loading Data from Tables
        PersonaStream.start();

        // Create and store Persona Catalogue Buttons
        createButtons(Constants.personaIDtoName);

        // Set up a listener to filter the catalogue in real-time
        searchField.textProperty().addListener((obs, oldText, newText) -> filterCatalogue(newText));

        // Set the first Persona as the default selection
        handlePersonaButtonClick(0);
        PersonaSkillsController.setToRegistryEditor();
    }


    @FXML
    private void handleBackToMainMenu() {
        try {
            // Load the main menu FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.Path.MAIN_MENU));
            Scene mainMenuScene = new Scene(loader.load(), stage.getWidth(), stage.getHeight());
            mainMenuScene.getStylesheets().add(getClass().getResource(Constants.Path.DARK_MODE_CSS).toExternalForm());

            // Get the main menu controller and pass the stage
            MainMenuController controller = loader.getController();
            controller.setStage(stage);

            // Set the main menu scene
            stage.setScene(mainMenuScene);

            // Ask to save changes before leaving
            GUIManager.SavePrompt(() -> PersonaStream.writeToTables());

            // Clear Resources
            PersonaGeneralController.releaseResources();
            PersonaSkillsController.releaseResources();
            PersonaAffinityController.releaseResources();
            PersonaStream.releaseResources();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createButtons(String[] personas) {
        catalogueContainer.getChildren().clear();
        PERSONA_BUTTONS.clear();
        
        for (int i = 0; i < personas.length; i++) {
            Button personaButton = new Button(personas[i]);
            final int index = i;
            personaButton.setOnAction(e -> handlePersonaButtonClick(index));
            catalogueContainer.getChildren().add(personaButton);
            PERSONA_BUTTONS.add(personaButton);
        }
    }


    private void filterCatalogue(String query) {
        // Filter by Number
        if (query.length() > 0 && Character.isDigit(query.charAt(0))) {
            // Check is Index is within bounds of Persona List
            int index = Integer.parseInt(query);
            if (index < 0 || index >= PERSONA_BUTTONS.size()) return;

            // Make button visible
            Button indexButton = PERSONA_BUTTONS.get(Integer.parseInt(query));
            catalogueContainer.getChildren().clear();
            catalogueContainer.getChildren().add(indexButton);
            return;
        }

        // Filter by Text
        List<Button> visibleButtons = new ArrayList<>();
        for (Button button : PERSONA_BUTTONS) {
            boolean visible = button.getText().toLowerCase().contains(query.toLowerCase());
            button.setVisible(visible);
            if (visible) visibleButtons.add(button);
        }
        catalogueContainer.getChildren().clear();
        catalogueContainer.getChildren().addAll(visibleButtons);
    }


    private void handlePersonaButtonClick(int index) {
        Persona persona = PersonaStream.getPersona(index);

        // Set General Tab Name to Persona Name
        generalTab.setText(persona.getName());

        // Update Tabs
        PersonaGeneralController.updateFields(persona, index);
        PersonaSkillsController.updateFields(persona);
        PersonaAffinityController.updateFields(persona);
    }
}