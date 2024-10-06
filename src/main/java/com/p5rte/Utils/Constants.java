package com.p5rte.Utils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public final class Constants {
    
    public static final class Path {
        // Folders
        public static final String ROOT_DIR = Paths.get("").toAbsolutePath().toString();
        public static final String RESOURCES_FOLDER = Paths.get(ROOT_DIR, "src/main/resources/com/p5rte").toString();
        public static final String FXML_FOLDER = Paths.get(RESOURCES_FOLDER, "FXMLs").toString();
        public static final String NAMES_FOLDER = Paths.get(RESOURCES_FOLDER, "Names").toString();
        public static final String INPUT = Paths.get(ROOT_DIR, "input").toString();
        public static final String OUTPUT = Paths.get(ROOT_DIR, "output").toString();
        public static final String ORIGINAL = Paths.get(ROOT_DIR, "originals").toString();

        // Persona Table
        public static final String INPUT_PERSONA_TABLE = Paths.get(INPUT, "PERSONA.TBL").toString();
        public static final String OUTPUT_PERSONA_TABLE = Paths.get(OUTPUT, "PERSONA.TBL").toString();
        public static final String ORIGINAL_PERSONA_TABLE = Paths.get(ORIGINAL, "PERSONA.TBL").toString();

        // Unit Table
        public static final String INPUT_UNIT_TABLE = Paths.get(INPUT, "UNIT.TBL").toString();
        public static final String OUTPUT_UNIT_TABLE = Paths.get(OUTPUT, "UNIT.TBL").toString();
        public static final String ORIGINAL_UNIT_TABLE = Paths.get(ORIGINAL, "UNIT.TBL").toString();

        // FXML Paths
        public static final String DARK_MODE_CSS = "/com/p5rte/DarkMode.css";
        public static final String MAIN_MENU = "/com/p5rte/FXMLs/MainMenu.fxml";
        public static final String PERSONA_TABPANE = "/com/p5rte/FXMLs/PersonaEditor/PersonaEditorTabPane.fxml";
        public static final String PARTY_TABPANE = "/com/p5rte/FXMLs/PartyEditor/PartyEditorTabPane.fxml";
        public static final String ENEMY_TABPANE = "/com/p5rte/FXMLs/EnemyEditor/EnemyEditorTabPane.fxml";

        // Names
        public static final String PERSONA_NAME_FILE = "/com/p5rte/Names/PersonaNames.txt";
        public static final String ENEMY_NAME_FILE = "/com/p5rte/Names/EnemyNames.txt";
        public static final String ARMOR_NAME_FILE = "/com/p5rte/Names/ArmorNames.txt";
        public static final String ACCESSORY_NAME_FILE = "/com/p5rte/Names/AccessoryNames.txt";
        public static final String CONSUMABLE_NAME_FILE = "/com/p5rte/Names/ConsumableNames.txt";
        public static final String KEY_ITEM_NAME_FILE = "/com/p5rte/Names/KeyItemNames.txt";
        public static final String MATERIAL_NAME_FILE = "/com/p5rte/Names/MaterialNames.txt";
        public static final String SKILL_CARD_NAME_FILE = "/com/p5rte/Names/SkillCardNames.txt";
        public static final String OUTFIT_NAME_FILE = "/com/p5rte/Names/OutfitNames.txt";
    }
    

    // https://amicitia.miraheze.org/wiki/Persona_5_Royal/Personas
    public static final String[] personaIDtoName = readNamesFromFile(Path.PERSONA_NAME_FILE);

    // https://amicitia.miraheze.org/wiki/Persona_5_Royal/Enemies
    public static final String[] enemyIDtoName = readNamesFromFile(Path.ENEMY_NAME_FILE);


    // Persona Modding Discord ITEMID Thread -> https://discord.com/channels/746211612981198989/1219490995209637919 (links wonky)
    private static final HashMap<Character, String> ITEMID_TO_NAME_MAP = new HashMap<>() {
        {
            put('1', Path.ARMOR_NAME_FILE);
            put('2', Path.ACCESSORY_NAME_FILE);
            put('3', Path.CONSUMABLE_NAME_FILE);
            put('4', Path.KEY_ITEM_NAME_FILE);
            put('5', Path.MATERIAL_NAME_FILE);
            put('6', Path.SKILL_CARD_NAME_FILE);
            put('7', Path.OUTFIT_NAME_FILE);
        }
    };


    private static String[] readNamesFromFile(String path) {
        try (InputStream inputStream = Constants.class.getResourceAsStream(path);
             Scanner scanner = new Scanner(inputStream)) {
            List<String> names = new ArrayList<>();
            while (scanner.hasNextLine()) {
                names.add(scanner.nextLine());
            }
            return names.toArray(String[]::new);
        } catch (Exception e) {
            FileStreamUtil.logError(e.getMessage(), e);
        }
        return null;
    }


    public static String getItemName(int itemID) {
        String hexString = Integer.toHexString(itemID).toUpperCase();
        char fileKey = hexString.charAt(0);
        int baseValue = Integer.parseInt(fileKey + "000", 16);
        int targetIndex = itemID - baseValue;
        String filePath = ITEMID_TO_NAME_MAP.getOrDefault(fileKey, null);
        
        if (targetIndex < 0) return "None";
        if (filePath == null) return "Undocumented Item";
    
        try (Scanner scanner = new Scanner(new File(filePath))) {
            int index = 0;
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                if (index == targetIndex) {
                    return name;
                }
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return "Item not Found";
    }
}