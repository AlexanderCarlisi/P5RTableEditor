package com.p5rte.GUI;

import com.p5rte.Classes.PartyMemberPersona;
import com.p5rte.Classes.PartyStream;
import com.p5rte.Classes.Persona;
import com.p5rte.Classes.PersonaStream;
import com.p5rte.Utils.Constants;
import com.p5rte.Utils.Enums.EPartyMember;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PartyEditorController {

    @FXML
    private VBox partyMemberContainer;

    @FXML
    private VBox personaContainer;

    @FXML
    private Tab generalTab;

    @FXML
    private ToggleButton skillsToggleButton; // turn this into one button that makes everything Individually editable.


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
        PESkillsTabController.setToPartyEditor();
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
            PARTYEGeneralTabController.releaseResources();
            PESkillsTabController.releaseResources();
            PEAffinityTabController.releaseResources();

            // Ask to save changes before leaving
            GUIManager.SavePrompt(() -> {
                PersonaStream.writeToTables(); // Save Reg data
                PartyStream.writeToTables(false, skillsToggleButton.isSelected()); // Save party data
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleSkillsToggle() {
        GUIManager.ConfirmationPrompt("Read Skills Individually?", "Toggle reading each Party Member's Persona evolution's skills individually.", "You will lose any unsaved changes.", 
        () -> {
            if (skillsToggleButton.isSelected()) {
                PartyStream.restart(false, true);
                
            } else {
                PartyStream.restart(false, false);
            }
        });
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

        personaButtonClick(partyMember, 0);
    }


    private void personaButtonClick(EPartyMember partyMember, int index) {
        Persona registryPersona = PartyStream.getPersona(partyMember, index);
        PartyMemberPersona partyPersona = PartyStream.getPartyMember(partyMember).personas[(skillsToggleButton.isSelected()) ? index : 0];

        generalTab.setText(registryPersona.getName());
        PARTYEGeneralTabController.updateFields(registryPersona, index != 0);
        PESkillsTabController.updateFields(partyPersona, index != 0 && !skillsToggleButton.isSelected());
        PEAffinityTabController.updateFields(registryPersona);
        PARTYEGainsController.updateFields(partyPersona, index != 0 && !skillsToggleButton.isSelected());
    }
}
