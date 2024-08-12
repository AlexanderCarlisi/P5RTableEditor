package com.p5rte.GUI;

import java.io.File;
import java.io.IOException;

import com.p5rte.Classes.PersonaTable;
import com.p5rte.Utils.Constants;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
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

        // Set up the primary stage
        primaryStage.setTitle("P5R Table Editor");
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(STARTING_WINDOW_WIDTH);
        primaryStage.setMinHeight(STARTING_WINDOW_HEIGHT);
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> primaryStage.setWidth(newVal.doubleValue()));
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> primaryStage.setHeight(newVal.doubleValue()));

        primaryStage.show();
    }


    public static void checkAndDeleteOutputFile(File outputFile) {
        if (outputFile.exists()) {
            // Create a confirmation alert
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(outputFile.getName() + " Warning");
            alert.setHeaderText("Output file already exists");
            alert.setContentText("Do you want to delete the existing file and create a new one?");

            // Add "Yes" and "No" buttons
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No (Exits Program)", ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            // Show the alert and wait for user response
            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeYes) {
                    // Proceed with deleting the old file
                    if (outputFile.delete()) {
                        System.out.println("Old output file deleted.");
                    } else {
                        System.out.println("Failed to delete the old output file.");
                    }
                    
                    // Now that the old file is deleted, proceed with creating a new file
                    PersonaTable.startPersonaStream();
                } else {
                    // User chose not to delete the file
                    System.out.println("User chose not to delete the file.");
                    
                    // Quit the program
                    System.exit(0);
                }
            });
        } else {
            // File doesn't exist, proceed with creating a new file
            // You can add your logic for creating the new file here
        }
    }
}