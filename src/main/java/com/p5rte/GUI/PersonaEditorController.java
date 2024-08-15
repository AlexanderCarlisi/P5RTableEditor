package com.p5rte.GUI;

import java.util.ArrayList;
import java.util.List;

import com.p5rte.Classes.Persona;
import com.p5rte.Classes.PersonaStream;
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

    // Tab Fields
    @FXML
    private TextField searchField;
    @FXML
    private VBox catalogueContainer;

    @FXML
    private Tab generalTab;


    /** Stores Buttons for Search and Filtering */
    private final List<Button> personaButtons = new ArrayList<>();

    private Stage stage;


    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    public void initialize() {
        // Create and store Persona Catalogue Buttons
        createButtons(Constants.personaIDtoName);

        // Set up a listener to filter the catalogue in real-time
        searchField.textProperty().addListener((obs, oldText, newText) -> filterCatalogue(newText));

        // Set the first Persona as the default selection
        handlePersonaButtonClick(0);
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

            // Ask to save changes before leaving
            GUIManager.SavePrompt(() -> PersonaStream.writeToTables());

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
        // Filter by Number
        if (query.length() > 0 && Character.isDigit(query.charAt(0))) {
            // Check is Index is within bounds of Persona List
            int index = Integer.parseInt(query);
            if (index < 0 || index >= personaButtons.size()) return;

            // Make button visible
            Button indexButton = personaButtons.get(Integer.parseInt(query));
            catalogueContainer.getChildren().clear();
            catalogueContainer.getChildren().add(indexButton);
            return;
        }

        // Filter by Text
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
        Persona persona = PersonaStream.getPersona(index);

        // Set General Tab Name to Persona Name
        generalTab.setText(persona.getName());

        // Update Tabs
        PEGeneralTabController.updateFields(persona);
        PESkillsTabController.updateFields(persona);
        PEAffinityTabController.updateFields(persona);
    }
}