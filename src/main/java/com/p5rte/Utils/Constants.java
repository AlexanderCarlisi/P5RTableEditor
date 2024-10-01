package com.p5rte.Utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public final class Constants {
    
    public static final class Path {
        // Folders
        public static final String SRC = "src/";
        public static final String RESOURCE_FOLDER = "/com/p5rte/";
        
        // Table Stuff
        public static final String ROOT_DIR = Paths.get("").toAbsolutePath().toString();
        public static final String INPUT = Paths.get(ROOT_DIR, "input").toString();
        public static final String OUTPUT = Paths.get(ROOT_DIR, "output").toString();
        public static final String ORIGINAL = Paths.get(ROOT_DIR, "originals").toString();

        public static final String INPUT_PERSONA_TABLE = INPUT+"/PERSONA.TBL";
        public static final String OUTPUT_PERSONA_TABLE = OUTPUT+"/PERSONA.TBL";
        public static final String ORIGINAL_PERSONA_TABLE = ORIGINAL+"/PERSONA.TBL";

        public static final String INPUT_UNIT_TABLE = INPUT+"/UNIT.TBL";
        public static final String OUTPUT_UNIT_TABLE = OUTPUT+"/UNIT.TBL";
        public static final String ORIGINAL_UNIT_TABLE = ORIGINAL+"/UNIT.TBL";

        // FXML Paths
        public static final String MAIN_MENU = RESOURCE_FOLDER+"MainMenu.fxml";
        public static final String PERSONA_TABPANE = RESOURCE_FOLDER + "PersonaEditorTabPane.fxml";
        public static final String PARTY_TABPANE = RESOURCE_FOLDER + "PartyEditorTabPane.fxml";
        public static final String ENEMY_TABPANE = RESOURCE_FOLDER + "EnemyEditorTabPane.fxml";
        public static final String DARK_MODE_CSS = RESOURCE_FOLDER + "DarkMode.css";

        // Names
        public static final String NAMES_DIR = ROOT_DIR + "/src/main/resources/com/p5rte/Names/";
        public static final String PERSONA_NAME_FILE = NAMES_DIR + "PersonaNames.txt";
        public static final String ENEMY_NAME_FILE = NAMES_DIR + "EnemyNames.txt";
        public static final String ARMOR_NAME_FILE = NAMES_DIR + "ArmorNames.txt";
        public static final String ACCESSORY_NAME_FILE = NAMES_DIR + "AccessoryNames.txt";
        public static final String CONSUMABLE_NAME_FILE = NAMES_DIR + "ConsumableNames.txt";
        public static final String KEY_ITEM_NAME_FILE = NAMES_DIR + "KeyItemNames.txt";
        public static final String MATERIAL_NAME_FILE = NAMES_DIR + "MaterialNames.txt";
        public static final String SKILL_CARD_NAME_FILE = NAMES_DIR + "SkillCardNames.txt";
        public static final String OUTFIT_NAME_FILE = NAMES_DIR + "OutfitNames.txt";
    }
    

    // https://amicitia.miraheze.org/wiki/Persona_5_Royal/Personas
    public static String[] personaIDtoName = readNamesFromFile(Path.PERSONA_NAME_FILE);

    // https://amicitia.miraheze.org/wiki/Persona_5_Royal/Enemies
    public static String[] enemyIDtoName = readNamesFromFile(Path.ENEMY_NAME_FILE);

    // Static initialization block
    // static {
    //     personaIDtoName = readNamesFromFile(Path.PERSONA_NAME_FILE);
    //     enemyIDtoName = readNamesFromFile(Path.ENEMY_NAME_FILE);
    // }

    // I really want to know how much memory this takes up but im too lazy to figure it out
    // public static final String[][] itemNames = new String[][] {
    //     readNamesFromFile(Path.ARMOR_NAME_FILE),
    //     readNamesFromFile(Path.ACCESSORY_NAME_FILE),
    //     readNamesFromFile(Path.CONSUMABLE_NAME_FILE),
    //     readNamesFromFile(Path.KEY_ITEM_NAME_FILE),
    //     readNamesFromFile(Path.MATERIAL_NAME_FILE),
    //     readNamesFromFile(Path.SKILL_CARD_NAME_FILE),
    //     readNamesFromFile(Path.OUTFIT_NAME_FILE)
    // };

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
        try (Scanner scanner = new Scanner(new File(path))) {
            List<String> names = new ArrayList<>();
            while (scanner.hasNextLine()) {
                names.add(scanner.nextLine());
            }
            return names.toArray(String[]::new);
        } catch (Exception e) {
            e.printStackTrace();
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