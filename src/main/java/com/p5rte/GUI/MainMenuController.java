package com.p5rte.GUI;

import java.io.IOException;

import com.p5rte.Utils.Constants;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleEditPersona() {
        // Load the personaeditor scene
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.Path.PERSONA_TABPANE));
            Scene personaEditorScene = new Scene(loader.load(), stage.getWidth(), stage.getHeight());
            personaEditorScene.getStylesheets().add(getClass().getResource(Constants.Path.DARK_MODE_CSS).toExternalForm());

            PersonaEditorController controller = loader.getController();
            if (controller == null) {
                throw new IllegalStateException("Failed to load PersonaEditorController.");
            }
            controller.setStage(stage);

            stage.setScene(personaEditorScene);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    private void handleEditSkills() {
        // Placeholder for handling the "Edit Skills" button
        System.out.println("Edit Skills button clicked!");
    }

    // private void openPersonaEditor() {
    //     try {
    //         // Load the Persona editor FXML
    //         FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/PersonaEditor/PersonaEditor.fxml"));
    //         Scene personaEditorScene = new Scene(loader.load(), stage.getWidth(), stage.getHeight());

    //         // Get the Persona editor controller and pass the stage
    //         PersonaEditorController controller = loader.getController();
    //         controller.setStage(stage);

    //         // Set the Persona editor scene
    //         stage.setScene(personaEditorScene);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    // private Scene createMainMenuScene() {
    //     try {
    //         return new Scene(FXMLLoader.load(getClass().getResource("MainMenu.fxml")), stage.getWidth(), stage.getHeight());
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }
}