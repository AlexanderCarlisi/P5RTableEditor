package com.p5rte.GUI.EnemyControllers;

import com.p5rte.Classes.Enemy;
import com.p5rte.Utils.Constants;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class EnemyDropsController {

    private class FXMLItemDrop {
        public final TextField ITEMFIELD;
        public final TextField RATEFIELD;
        public final Label ITEMLABEL;
        private final ChangeListener<String> ITEM_LISTENER;
        private final ChangeListener<String> RATE_LISTENER;

        public FXMLItemDrop(int itemDropIndex) {
            ITEMFIELD = new TextField();
            RATEFIELD = new TextField();
            ITEMLABEL = new Label();
            ITEMFIELD.setPromptText("Item ID");
            RATEFIELD.setPromptText("Drop Rate");

            final int INDEX = itemDropIndex;
            ITEM_LISTENER = (__, ___, newValue) -> {
                if (_currentEnemy == null) return;
                try {
                    short value = Short.parseShort(newValue);
                    _currentEnemy.itemDrops[INDEX].itemID = value;
                    s_instance.ITEM_DROPS[INDEX].ITEMLABEL.setText(Constants.getItemName((int) value));
                } catch (NumberFormatException e) {
                    _currentEnemy.itemDrops[INDEX].itemID = 0;
                }
            };

            RATE_LISTENER = (__, ___, newValue) -> {
                if (_currentEnemy == null) return;
                try {
                    _currentEnemy.itemDrops[INDEX].dropRate = Short.parseShort(newValue);
                } catch (NumberFormatException e) {
                    _currentEnemy.itemDrops[INDEX].dropRate = 0;
                }
            };

            ITEMFIELD.textProperty().addListener(ITEM_LISTENER);
            RATEFIELD.textProperty().addListener(RATE_LISTENER);
        }

        public void clearResources() {
            ITEMFIELD.textProperty().removeListener(ITEM_LISTENER);
            RATEFIELD.textProperty().removeListener(RATE_LISTENER);
        }
    }

    // FXML Elements
    @FXML private TextField moneyField;
    @FXML private TextField expField;
    @FXML private TextField eventItemField;
    @FXML private Label eventItemLabel;
    @FXML private TextField eventRateField;
    @FXML private TextField eventIDField;
    @FXML private VBox itemDropContainer;
    private Stage stage;

    // Listeners
    private ChangeListener<String> moneyListener;
    private ChangeListener<String> expListener;
    private ChangeListener<String> eventItemListener;
    private ChangeListener<String> eventRateListener;
    private ChangeListener<String> eventIDListener;

    private final FXMLItemDrop[] ITEM_DROPS = new FXMLItemDrop[4];

    private static EnemyDropsController s_instance;
    private Enemy _currentEnemy;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        s_instance = this;

        // Initialize itemDropContainer
        for (int i = 0; i < 4; i++) {
            ITEM_DROPS[i] = new FXMLItemDrop(i);
            itemDropContainer.getChildren().add(new Label("Drop " + (i + 1)));
            HBox hbox = new HBox();
            hbox.setSpacing(5);
            hbox.getChildren().add(ITEM_DROPS[i].ITEMFIELD);
            hbox.getChildren().add(ITEM_DROPS[i].ITEMLABEL);
            itemDropContainer.getChildren().add(hbox);
            itemDropContainer.getChildren().add(ITEM_DROPS[i].RATEFIELD);
        }

        // Initialize Listeners
        moneyListener = (__, ___, newValue) -> {
            if (_currentEnemy == null) return;
            try {
                _currentEnemy.moneyReward = Short.parseShort(newValue);
            } catch (NumberFormatException e) {
                _currentEnemy.moneyReward = 0;
            }
        };

        expListener = (__, ___, newValue) -> {
            if (_currentEnemy == null) return;
            try {
                _currentEnemy.expReward = Short.parseShort(newValue);
            } catch (NumberFormatException e) {
                _currentEnemy.expReward = 0;
            }
        };

        eventItemListener = (__, ___, newValue) -> {
            if (_currentEnemy == null) return;
            try {
                _currentEnemy.eventDrop.itemID = Short.parseShort(newValue);
            } catch (NumberFormatException e) {
                _currentEnemy.eventDrop.itemID = 0;
            }
        };

        eventRateListener = (__, ___, newValue) -> {
            if (_currentEnemy == null) return;
            try {
                _currentEnemy.eventDrop.dropRate = Short.parseShort(newValue);
            } catch (NumberFormatException e) {
                _currentEnemy.eventDrop.dropRate = 0;
            }
        };

        eventIDListener = (__, ___, newValue) -> {
            if (_currentEnemy == null) return;
            try {
                _currentEnemy.eventDrop.eventID = Short.parseShort(newValue);
            } catch (NumberFormatException e) {
                _currentEnemy.eventDrop.eventID = 0;
            }
        };

        // Add Listeners
        moneyField.textProperty().addListener(moneyListener);
        expField.textProperty().addListener(expListener);
        eventItemField.textProperty().addListener(eventItemListener);
        eventRateField.textProperty().addListener(eventRateListener);
        eventIDField.textProperty().addListener(eventIDListener);
    }


    public static void updateFields(Enemy enemy) {
        if (s_instance == null) return;
        s_instance._currentEnemy = enemy;

        s_instance.moneyField.setText(String.valueOf(enemy.moneyReward));
        s_instance.expField.setText(String.valueOf(enemy.expReward));

        s_instance.eventItemField.setText((enemy.eventDrop.itemID == 0) ? "" : String.valueOf(enemy.eventDrop.itemID));
        s_instance.eventItemLabel.setText(Constants.getItemName(enemy.eventDrop.itemID));
        s_instance.eventRateField.setText((enemy.eventDrop.dropRate == 0) ? "" : String.valueOf(enemy.eventDrop.dropRate));
        s_instance.eventIDField.setText((enemy.eventDrop.eventID == 0) ? "" : String.valueOf(enemy.eventDrop.eventID));

        for (int i = 0; i < 4; i++) {
            short itemID = enemy.itemDrops[i].itemID;
            short dropRate = enemy.itemDrops[i].dropRate;
            s_instance.ITEM_DROPS[i].ITEMFIELD.setText((itemID == 0) ? "" : String.valueOf(itemID));
            s_instance.ITEM_DROPS[i].RATEFIELD.setText((dropRate == 0) ? "" : String.valueOf(dropRate));
            s_instance.ITEM_DROPS[i].ITEMLABEL.setText(Constants.getItemName((int) itemID));
        }
    }


    /**
     * Releases all resources used by this Tab.
     * Should only be called when Returning to the Main Menu.
     */
    public static void releaseResources() {
        if (s_instance == null) return;

        s_instance.moneyField.textProperty().removeListener(s_instance.moneyListener);
        s_instance.expField.textProperty().removeListener(s_instance.expListener);
        s_instance.eventItemField.textProperty().removeListener(s_instance.eventItemListener);
        s_instance.eventRateField.textProperty().removeListener(s_instance.eventRateListener);
        s_instance.eventIDField.textProperty().removeListener(s_instance.eventIDListener);
        for (FXMLItemDrop itemDrop : s_instance.ITEM_DROPS) {
            itemDrop.clearResources();
        }
    }
}