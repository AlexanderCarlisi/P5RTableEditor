package com.p5rte.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.p5rte.Classes.Skill;
import com.p5rte.GUI.GUIManager;

public class FileStreamUtil {

    public static InputStream startInputStream(String inputPath, String outputPath) {
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);

        try {

            if (!inputFile.exists()) {
                System.err.println("Input File not found: " + inputFile.getAbsolutePath());
                GUIManager.DisplayWarning(
                    "Input File Error", 
                    "Input File not found", 
                    "The input file could not be found. Please make sure the file is in the correct location and try again. \n\n" + inputFile.getAbsolutePath());
                return null;
            }

            // Check if the output file already exists
            if (outputFile.exists()) GUIManager.checkOutputFile(inputFile, outputFile);
            else outputFile.createNewFile();
            
            // Copy the data from input file to output file
            Files.copy(Paths.get(inputPath), Paths.get(outputPath), StandardCopyOption.REPLACE_EXISTING);

            // Generate new Inputstream
            return new FileInputStream(outputFile);

        } catch (IOException e) {
            System.err.println("An error occurred during file operations:");
            e.printStackTrace();
            return null;
        }
    }


    public static Skill[] readSkills(InputStream inputStream, int count) throws IOException{
        Skill[] skills = new Skill[count];
       
        byte[] skillBytes = inputStream.readNBytes(count * 4); // 4 bytes per Skill
        for (int i = 0; i < count; i++) {
            int pendingLevels = skillBytes[i * 4];
            int learnability = skillBytes[i * 4 + 1];
            int id = ((skillBytes[i * 4 + 2] & 0xFF) << 8) | (skillBytes[i * 4 + 3] & 0xFF); // should really look into this, im just reading a short why is it so compliated?
            skills[i] = new Skill(pendingLevels, learnability, id);
        }
        
        return skills;
    }


    public static int readShort(InputStream inputStream) throws IOException {
        byte[] shortBytes = inputStream.readNBytes(2);
        return ((shortBytes[0] & 0xFF) << 8) | (shortBytes[1] & 0xFF);
    }
}
