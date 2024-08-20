package com.p5rte.GUI;

import com.p5rte.Classes.PartyStream;
import com.p5rte.Classes.Persona;
import com.p5rte.Utils.Constants;
import com.p5rte.Utils.Enums.EPartyMember;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PartyEditorController {

    @FXML
    private VBox partyMemberContainer;

    @FXML
    private VBox personaContainer;


    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    public void initialize() {
        // Generate Party Member Buttons
        for (int i = 0; i < 9; i++) {
            Button partyMemberButton = new Button();
            final int INDEX = i+1;
            partyMemberButton.setText(EPartyMember.values()[INDEX].name());
            partyMemberButton.setOnAction(e -> partyMemberButtonClick(INDEX));
            partyMemberContainer.getChildren().add(partyMemberButton);
        }

        partyMemberButtonClick(1);
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

            // Clear Resources from Tabs
            PEGeneralTabController.releaseResources();
            PESkillsTabController.releaseResources();
            PEAffinityTabController.releaseResources();

            // Ask to save changes before leaving
            // GUIManager.SavePrompt(() -> PartyStream.writeToTables());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void partyMemberButtonClick(int index) {
        EPartyMember partyMember = EPartyMember.values()[index];

        personaContainer.getChildren().clear();
        for (int i = 0; i < 3; i++) {
            Button personaButton = new Button();
            final int INDEX = i;
            personaButton.setText(PartyStream.getPersona(partyMember, i).getName());
            personaButton.setOnAction(e -> personaButtonClick(partyMember, INDEX));
            personaContainer.getChildren().add(personaButton);
        }

        Persona persona = PartyStream.getPersona(partyMember, 0);

        PEGeneralTabController.updateFields(persona);
        PESkillsTabController.updateFields(persona);
        PEAffinityTabController.updateFields(persona);
    }


    private void personaButtonClick(EPartyMember partyMember, int index) {
        Persona persona = PartyStream.getPersona(partyMember, index);

        PEGeneralTabController.updateFields(persona);
        PESkillsTabController.updateFields(persona);
        PEAffinityTabController.updateFields(persona);
    }
}
