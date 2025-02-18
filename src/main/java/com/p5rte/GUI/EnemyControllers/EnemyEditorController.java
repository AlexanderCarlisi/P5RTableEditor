package com.p5rte.GUI.EnemyControllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import com.p5rte.Classes.Enemy;
import com.p5rte.Classes.EnemyStream;
import com.p5rte.GUI.GUIManager;
import com.p5rte.GUI.MainMenuController;
import com.p5rte.Utils.Constants;
import com.p5rte.Utils.Enums.EnemyMassEditableStat;

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
    private int _index;
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
        createButtons(Constants.enemyIDtoName);

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
            GUIManager.SavePrompt(() -> EnemyStream.writeToTables());

            // Clear Resources
            EnemyGeneralController.releaseResources();
            EnemySkillsController.releaseResources();
            EnemyAffinityController.releaseResources();
            EnemyDropsController.releaseResources();
            EnemyStream.releaseResources();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleMassEdit() {
        // result[0] = stat, result[1] = multiplier
        String[] result = GUIManager.showComboBoxTextFieldPopup("Mass Edit Enemies", EnemyMassEditableStat.class, "Stat:", "Multiplier:");

        EnemyMassEditableStat stat = EnemyMassEditableStat.valueOf(result[0]);
        double multiplier = Double.parseDouble(result[1]);

        // Create a map to define actions for each stat type
        Map<EnemyMassEditableStat, BiConsumer<Enemy, Double>> statActions = new HashMap<>();
        statActions.put(EnemyMassEditableStat.Hp, (enemy, mult) -> enemy.hp *= mult);
        statActions.put(EnemyMassEditableStat.Sp, (enemy, mult) -> enemy.sp *= mult);
        statActions.put(EnemyMassEditableStat.Level, (enemy, mult) -> enemy.level *= mult);
        statActions.put(EnemyMassEditableStat.Strength, (enemy, mult) -> enemy.stats[0] *= mult);
        statActions.put(EnemyMassEditableStat.Magic, (enemy, mult) -> enemy.stats[1] *= mult);
        statActions.put(EnemyMassEditableStat.Endurance, (enemy, mult) -> enemy.stats[2] *= mult);
        statActions.put(EnemyMassEditableStat.Agility, (enemy, mult) -> enemy.stats[3] *= mult);
        statActions.put(EnemyMassEditableStat.Luck, (enemy, mult) -> enemy.stats[4] *= mult);
        statActions.put(EnemyMassEditableStat.AttackDamage, (enemy, mult) -> enemy.attackDamage *= mult);
        statActions.put(EnemyMassEditableStat.AttackAccuracy, (enemy, mult) -> enemy.attackAccuracy *= mult);

        // Apply the selected action to each enemy in the stream
        if (statActions.containsKey(stat)) {
            for (Enemy enemy : EnemyStream.getEnemies()) {
                statActions.get(stat).accept(enemy, multiplier);
            }
        }
        handleEnemyButtonClick(_index); // Refresh the current enemy
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
        _index = index;
        Enemy enemy = EnemyStream.getEnemy(index);

        // Set General Tab Name to Persona Name
        generalTab.setText(enemy.name);

        // Update Tabs
        EnemyGeneralController.updateFields(enemy);
        EnemySkillsController.updateFields(enemy);
        EnemyAffinityController.updateFields(enemy);
        EnemyDropsController.updateFields(enemy);
    }
}