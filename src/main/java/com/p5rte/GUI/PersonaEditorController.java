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

        personaNameLabel.setText(persona.getName());
        arcanaComboBox.setValue(persona.getArcana());
    }
}