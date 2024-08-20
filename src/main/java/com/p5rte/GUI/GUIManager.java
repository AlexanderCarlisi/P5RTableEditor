package com.p5rte.GUI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.p5rte.Classes.PartyStream;
import com.p5rte.Classes.PersonaStream;
import com.p5rte.Utils.Constants;
import com.p5rte.Utils.FileStreamUtil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class GUIManager extends Application {

    private static final int STARTING_WINDOW_WIDTH = 600;
    private static final int STARTING_WINDOW_HEIGHT = 400;

    
    @Override
    public void start(Stage primaryStage) {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.Path.MAIN_MENU));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set up the controller and pass the stage to it
        MainMenuController controller = loader.getController();
        controller.setStage(primaryStage);

        // Create the main scene
        Scene scene = new Scene(root, STARTING_WINDOW_WIDTH, STARTING_WINDOW_HEIGHT);
        scene.getStylesheets().add(getClass().getResource(Constants.Path.DARK_MODE_CSS).toExternalForm());

        // Set up the primary stage
        primaryStage.setTitle("P5R Table Editor");
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(STARTING_WINDOW_WIDTH);
        primaryStage.setMinHeight(STARTING_WINDOW_HEIGHT);
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> primaryStage.setWidth(newVal.doubleValue()));
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> primaryStage.setHeight(newVal.doubleValue()));

        primaryStage.show();

        // Start Loading Data from Tables
        FileStreamUtil.start();
        PersonaStream.start();
        PartyStream.start(false, false);

        // Debugging
        
        // Persona persona = PersonaStream.getPersona(0);
        // // AffinityElement ele = persona.getAffinity(AffinityIndex.Nuke);
        // // for (AffinityDataIndex index : AffinityDataIndex.values()) {
        // //     System.out.println(index + " : " + ele.data.get(index));
        // // }
        // // System.out.println("multiplier : " + ele.multiplier);
        // Skill[] skills = persona.getSkills();
        // System.out.println(skills[0].getLearnability());
        // System.out.println(persona.getBitFlags()[9]);
    }


    /**
     * Check if the output file already exists.
     * If it does, ask the user if they want to :
     * [1] Overwrite the file, using the current Input File as a base.
     * [2] Use the Output file as a new Input file.
     * [3] Cancel the operation and Close the Program.
     * @param outputFile
     */
    public static void checkOutputFile(File inputfile, File outputFile) {
        if (outputFile.exists()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(outputFile.getName() + " Warning");
            alert.setHeaderText("The output file already exists.");
            alert.setContentText("What would you like to do?");

            ButtonType overwriteButton = new ButtonType("Overwrite");
            ButtonType useAsInputButton = new ButtonType("Use as Input");
            ButtonType cancelButton = new ButtonType("Close Program");

            alert.getButtonTypes().setAll(overwriteButton, useAsInputButton, cancelButton);

            alert.showAndWait().ifPresent(buttonType -> {
                try {
                    if (buttonType == overwriteButton) { // Overwrite
                        Files.copy(Paths.get(inputfile.getAbsolutePath()), Paths.get(outputFile.getAbsolutePath()), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    
                    } else if (buttonType == useAsInputButton) { // Use as Input
                        Files.copy(Paths.get(outputFile.getAbsolutePath()), Paths.get(inputfile.getAbsolutePath()), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    
                    } else if (buttonType == cancelButton) { // Cancel and Close Program
                        System.exit(0);
                    }   
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    public static void DisplayWarning(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public static void SavePrompt(Runnable saveFunction) {
        ConfirmationPrompt("Save Changes", "Save Changes to Tables?", "Would you like to save the changes you made to the tables?", saveFunction);
    }


    public static void ConfirmationPrompt(String title, String header, String content, Runnable function) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType confirmButton = new ButtonType("Confirm");
        ButtonType cancelButton = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == confirmButton) {
                function.run();
            }
        });
    }
}