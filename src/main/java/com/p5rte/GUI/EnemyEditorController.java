package com.p5rte.GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.p5rte.Classes.Enemy;
import com.p5rte.Classes.EnemyStream;
import com.p5rte.Utils.Constants;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class EnemyEditorController {

    // FXML Elements
    @FXML private TextField searchField;
    @FXML private VBox catalogueContainer;
    @FXML private Tab generalTab;
    private Stage stage;


    /** Stores Buttons for Search and Filtering */
    private final List<Button> ENEMY_BUTTONS = new ArrayList<>();


    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    public void initialize() {
        // Start Loading Data from Tables
        EnemyStream.start();

        // Create and store Persona Catalogue Buttons
        createButtons(Constants.personaIDtoName);

        // Set up a listener to filter the catalogue in real-time
        searchField.textProperty().addListener((obs, oldText, newText) -> filterCatalogue(newText));

        // Set the first Persona as the default selection
        handleEnemyButtonClick(0);
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
            // GUIManager.SavePrompt(() -> EnemyStream.writeToTables());

            // Clear Resources
            EnemyGeneralController.releaseResources();
            // PESkillsTabController.releaseResources();
            // PEAffinityTabController.releaseResources();
            EnemyStream.releaseResources();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createButtons(String[] enemies) {
        catalogueContainer.getChildren().clear();
        ENEMY_BUTTONS.clear();
        
        for (int i = 0; i < enemies.length; i++) {
            Button enemyButton = new Button(enemies[i]);
            final int index = i;
            enemyButton.setOnAction(e -> handleEnemyButtonClick(index));
            catalogueContainer.getChildren().add(enemyButton);
            ENEMY_BUTTONS.add(enemyButton);
        }
    }


    private void filterCatalogue(String query) {
        // Filter by Number
        if (query.length() > 0 && Character.isDigit(query.charAt(0))) {
            // Check is Index is within bounds of Persona List
            int index = Integer.parseInt(query);
            if (index < 0 || index >= ENEMY_BUTTONS.size()) return;

            // Make button visible
            Button indexButton = ENEMY_BUTTONS.get(Integer.parseInt(query));
            catalogueContainer.getChildren().clear();
            catalogueContainer.getChildren().add(indexButton);
            return;
        }

        // Filter by Text
        List<Button> visibleButtons = new ArrayList<>();
        for (Button button : ENEMY_BUTTONS) {
            boolean visible = button.getText().toLowerCase().contains(query.toLowerCase());
            button.setVisible(visible);
            if (visible) visibleButtons.add(button);
        }
        catalogueContainer.getChildren().clear();
        catalogueContainer.getChildren().addAll(visibleButtons);
    }


    private void handleEnemyButtonClick(int index) {
        Enemy enemy = EnemyStream.getEnemy(index);

        // Set General Tab Name to Persona Name
        generalTab.setText(enemy.name);

        // Update Tabs
        EnemyGeneralController.updateFields(enemy);
        // PESkillsTabController.updateFields(persona);
        // PEAffinityTabController.updateFields(persona);
    }
}