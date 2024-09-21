package com.p5rte.GUI;

import com.p5rte.Classes.Enemy;
import com.p5rte.Utils.Enums;
import com.p5rte.Utils.Enums.Arcana;
import com.p5rte.Utils.Enums.EnemyBitFlag;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;


public class EnemyGeneralController {

    // FXML Elements
    @FXML private Label enemyNameLabel;
    @FXML private ComboBox<Enums.Arcana> arcanaComboBox;

    @FXML private TextField hpField;
    @FXML private TextField spField;
    @FXML private TextField lvlField;
    @FXML private TextField strengthField;
    @FXML private TextField magicField;
    @FXML private TextField enduranceField;
    @FXML private TextField agilityField;
    @FXML private TextField luckField;

    @FXML private ToggleButton beggingFlag;
    @FXML private ToggleButton hideFlag1;
    @FXML private ToggleButton maskFlag;
    @FXML private ToggleButton negotiableFlag;
    @FXML private ToggleButton hideFlag2;
    @FXML private ToggleButton spFlag;

    private Stage stage;


    // Listeners
    @SuppressWarnings ("unchecked")
    private final ChangeListener<String>[] STAT_LISTENERS = new ChangeListener[5];

    @SuppressWarnings ("unchecked")
    private final ChangeListener<Boolean>[] FLAG_LISTENERS = new ChangeListener[6];

    private ChangeListener<Arcana> _arcanaListener;
    private ChangeListener<String> _hpListener;
    private ChangeListener<String> _spListener;
    private ChangeListener<String> _lvlListener;

    // Arrays
    private final TextField[] STAT_FIELDS = new TextField[5];
    private final ToggleButton[] FLAG_BUTTONS = new ToggleButton[6];

    private static EnemyGeneralController s_instance;
    private Enemy _currentEnemy;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        s_instance = this;

        // Arcana Box Setup
        arcanaComboBox.getItems().addAll(Enums.Arcana.values());
        _arcanaListener = (__, ___, newArcana) -> {
            if (_currentEnemy != null) {
                _currentEnemy.arcanaID = (short) newArcana.ID;
            }
        };
        arcanaComboBox.valueProperty().addListener(_arcanaListener);

        // Stat Fields Setup 
        STAT_FIELDS[0] = strengthField;
        STAT_FIELDS[1] = magicField;
        STAT_FIELDS[2] = enduranceField;
        STAT_FIELDS[3] = agilityField;
        STAT_FIELDS[4] = luckField;

        // Field Listeners
        for (int i = 0; i < STAT_FIELDS.length; i++) {
            final int INDEX = i;

            // Stat Listeners
            STAT_LISTENERS[i] = (__, ___, newVal) -> {
                s_instance._currentEnemy.stats[INDEX] = getStatFromField(newVal);
            };
            STAT_FIELDS[i].textProperty().addListener(STAT_LISTENERS[i]);
        }

        // Level Field Listener
        _lvlListener = (__, ___, newVal) -> {
            if (s_instance == null) return;

            int value = getStatFromField(newVal);
            s_instance._currentEnemy.level = (short) value;
        };
        lvlField.textProperty().addListener(_lvlListener);

        // Bit Flags Setup
        FLAG_BUTTONS[0] = beggingFlag;
        FLAG_BUTTONS[1] = hideFlag1;
        FLAG_BUTTONS[2] = maskFlag;
        FLAG_BUTTONS[3] = negotiableFlag;
        FLAG_BUTTONS[4] = hideFlag2;
        FLAG_BUTTONS[5] = spFlag;

        // Bitflag Listeners
        for (int i = 0; i < FLAG_BUTTONS.length; i++) {
            final int INDEX = i;
            FLAG_LISTENERS[i] = (__, ___, newVal) -> {
                if (_currentEnemy != null && s_instance._currentEnemy.getFlagAsBoolean(EnemyBitFlag.values()[INDEX].BITPOSE) != newVal) 
                    s_instance._currentEnemy.flipFlag(EnemyBitFlag.values()[INDEX].BITPOSE);
            };
            FLAG_BUTTONS[i].selectedProperty().addListener(FLAG_LISTENERS[i]);
        }

        // HP Field Listener
        _hpListener = (__, ___, newVal) -> {
            if (s_instance == null) return;
            s_instance._currentEnemy.hp = getPointFromField(newVal);
        };

        // SP Field Listener
        _spListener = (__, ___, newVal) -> {
            if (s_instance == null) return;
            s_instance._currentEnemy.sp = getPointFromField(newVal);
        };

        // Set Listeners
        hpField.textProperty().addListener(_hpListener);
        spField.textProperty().addListener(_spListener);
    }


    public static void updateFields(Enemy enemy, int personaIndex) {
        if (s_instance == null) return;

        s_instance._currentEnemy = enemy;

        // Set Name
        s_instance.enemyNameLabel.setText(enemy.name);

        // Set Arcana
        s_instance.arcanaComboBox.setValue(Arcana.getArcana(enemy.arcanaID));

        // Set Stats
        s_instance.hpField.setText(String.valueOf(enemy.hp));
        s_instance.spField.setText(String.valueOf(enemy.sp));
        s_instance.lvlField.setText(String.valueOf(enemy.level));
        for (int i = 0; i < enemy.stats.length; i++) {
            s_instance.STAT_FIELDS[i].setText(String.valueOf(enemy.stats[i]));
        }

        // Set Bit Flags
        for (EnemyBitFlag flag : EnemyBitFlag.values()) {
            s_instance.FLAG_BUTTONS[flag.ordinal()].setSelected(enemy.getFlagAsBoolean(flag.BITPOSE));
        }
    }


    private static byte getStatFromField(String newText) {
        final byte defaultReturn = 0;
        final byte limit = 99;

        if (newText.length() == 0) return defaultReturn;
        try {
            byte value = Byte.parseByte(newText);
            return (byte) Math.min(value, limit);
        } catch (NumberFormatException e) {
            return defaultReturn;
        }
    }


    private static int getPointFromField(String newText) {
        final int defaultReturn = 0;
        if (newText.length() == 0) return defaultReturn;

        try {
            return Integer.parseInt(newText);
        } catch (NumberFormatException e) {
            return defaultReturn;
        }
    }


    /**
     * Releases all resources used by this Tab.
     * Should only be called when Returning to the Main Menu.
     */
    public static void releaseResources() {
        if (s_instance == null) return;

        // Clear Listeners
        s_instance.arcanaComboBox.valueProperty().removeListener(s_instance._arcanaListener);
        s_instance.hpField.textProperty().removeListener(s_instance._hpListener);
        s_instance.spField.textProperty().removeListener(s_instance._spListener);
        s_instance.lvlField.textProperty().removeListener(s_instance._lvlListener);
        for (int i = 0; i < s_instance.STAT_FIELDS.length; i++) {
            s_instance.STAT_FIELDS[i].textProperty().removeListener(s_instance.STAT_LISTENERS[i]);
        }
        for (int i = 0; i < s_instance.FLAG_LISTENERS.length; i++) {
            s_instance.FLAG_BUTTONS[i].selectedProperty().removeListener(s_instance.FLAG_LISTENERS[i]);
        }
    }
}