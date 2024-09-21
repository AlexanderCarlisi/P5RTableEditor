package com.p5rte.Utils;

import java.nio.file.Paths;
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
        public static final String PERSONA_GENERAL = RESOURCE_FOLDER + "PersonaEditorGeneral.fxml";
        public static final String PERSONA_SKILLS = RESOURCE_FOLDER + "PersonaEditorSkills.fxml";
        public static final String PARTY_TABPANE = RESOURCE_FOLDER + "PartyEditorTabPane.fxml";
        public static final String ENEMY_TABPANE = RESOURCE_FOLDER + "EnemyEditorTabPane.fxml";
        public static final String DARK_MODE_CSS = RESOURCE_FOLDER + "DarkMode.css";

        // Names
        public static final String PERSONA_NAME_FILE = Paths.get(RESOURCE_FOLDER, "PersonaNames.txt").toString();
    }


    // Called at the start of Main
    public static void init() {
        readPersonaNames();
    }


    // https://amicitia.miraheze.org/wiki/Persona_5_Royal/Personas
    public static final String[] personaIDtoName = new String[464];
    public static void readPersonaNames() {
        try (Scanner scanner = new Scanner(Path.PERSONA_NAME_FILE)) {
            int index = 0;
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                personaIDtoName[index] = name;
                index++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}