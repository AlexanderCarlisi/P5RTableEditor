package com.p5rte.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.p5rte.Classes.PartyStream;
import com.p5rte.Classes.PersonaStream;
import com.p5rte.Classes.Skill;
import com.p5rte.GUI.GUIManager;

public class FileStreamUtil {

    public static void start() {
        initializeTableFile(Constants.Path.INPUT_PERSONA_TABLE, Constants.Path.OUTPUT_PERSONA_TABLE);
        initializeTableFile(Constants.Path.INPUT_UNIT_TABLE, Constants.Path.OUTPUT_UNIT_TABLE);
    }


    public static void initializeTableFile(String inputPath, String outputPath) {
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);

        try {

            if (!inputFile.exists()) {
                System.err.println("Input File not found: " + inputFile.getAbsolutePath());
                GUIManager.DisplayWarning(
                    "Input File Error", 
                    "Input File not found", 
                    "The input file could not be found. Please make sure the file is in the correct location and try again. \n\n" + inputFile.getAbsolutePath());
            }

            // Check if the output file already exists
            if (outputFile.exists()) GUIManager.checkOutputFile(inputFile, outputFile);
            else outputFile.createNewFile();
            
            // Copy the data from input file to output file
            Files.copy(Paths.get(inputPath), Paths.get(outputPath), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            System.err.println("An error occurred during file operations:");
            e.printStackTrace();
        }
    }


    public static Skill[] readSkills(FileInputStream inputStream, int count) throws IOException{
        Skill[] skills = new Skill[count];
       
        byte[] skillBytes = inputStream.readNBytes(count * 4); // 4 bytes per Skill
        for (int i = 0; i < count; i++) {
            int pendingLevels = skillBytes[i * 4];
            int learnability = skillBytes[i * 4 + 1];
            int id = readShort(skillBytes[i * 4 + 2], skillBytes[i * 4 + 3]); // should really look into this, im just reading a short why is it so compliated?
            skills[i] = new Skill(pendingLevels, learnability, id);
        }
        
        return skills;
    }


    public static int readShort(FileInputStream inputStream) throws IOException {
        byte[] shortBytes = inputStream.readNBytes(2);
        return ((shortBytes[0] & 0xFF) << 8) | (shortBytes[1] & 0xFF);
    }

    public static int readShort(byte byte1, byte byte2) {
        return ((byte1 & 0xFF) << 8) | (byte2 & 0xFF);
    }

    public static int readInt(FileInputStream inputStream) throws IOException {
        byte[] intBytes = inputStream.readNBytes(4);
        return ((intBytes[0] & 0xFF) << 24) | ((intBytes[1] & 0xFF) << 16) | ((intBytes[2] & 0xFF) << 8) | (intBytes[3] & 0xFF);
    }


    public static void resetToInputs() {
        start();
        PersonaStream.restart();
        PartyStream.restart();
    }


    public static void resetToOriginals() {

        try {
            Files.copy(Paths.get(Constants.Path.ORIGINAL_PERSONA_TABLE), Paths.get(Constants.Path.INPUT_PERSONA_TABLE), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(Paths.get(Constants.Path.ORIGINAL_UNIT_TABLE), Paths.get(Constants.Path.INPUT_UNIT_TABLE), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

        start();
        PersonaStream.restart();
        PartyStream.restart();
    }
}
