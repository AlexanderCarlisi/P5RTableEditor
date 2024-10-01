package com.p5rte.GUI;

import java.io.IOException;

import com.p5rte.GUI.EnemyControllers.EnemyEditorController;
import com.p5rte.GUI.PartyControllers.PartyEditorController;
import com.p5rte.GUI.PersonaControllers.PersonaEditorController;
import com.p5rte.Utils.Constants;
import com.p5rte.Utils.FileStreamUtil;

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
    private void handleEditPartyMembers() {
        // Load the partyeditor scene
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.Path.PARTY_TABPANE));
            Scene partyEditorScene = new Scene(loader.load(), stage.getWidth(), stage.getHeight());
            partyEditorScene.getStylesheets().add(getClass().getResource(Constants.Path.DARK_MODE_CSS).toExternalForm());

            PartyEditorController controller = loader.getController();
            if (controller == null) {
                throw new IllegalStateException("Failed to load PartyEditorController.");
            }
            controller.setStage(stage);

            stage.setScene(partyEditorScene);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    private void handleEditEnemies() {
        // Load the EnemyEditor scene
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.Path.ENEMY_TABPANE));
            Scene partyEditorScene = new Scene(loader.load(), stage.getWidth(), stage.getHeight());
            partyEditorScene.getStylesheets().add(getClass().getResource(Constants.Path.DARK_MODE_CSS).toExternalForm());

            EnemyEditorController controller = loader.getController();
            if (controller == null) {
                throw new IllegalStateException("Failed to load EnemyEditorController.");
            }
            controller.setStage(stage);

            stage.setScene(partyEditorScene);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    private void handleResetToInputs() {
        FileStreamUtil.resetToInputs();
    }

    @FXML
    private void handleResetToOriginals() {
        FileStreamUtil.resetToOriginals();
    }
}