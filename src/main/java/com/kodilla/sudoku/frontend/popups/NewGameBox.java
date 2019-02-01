package com.kodilla.sudoku.frontend.popups;

import com.kodilla.sudoku.backend.assets.InitialGameData;
import com.kodilla.sudoku.backend.enumerics.DifficultyLevel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewGameBox {

    private static final int ONE = 1;
    private static InitialGameData initialGameData;
    private static final String EXPLANATION = "Difficulty levels differs in number of pre-filled fields. \r\n" +
                                                "EASY: 30 filled fields \r\n" +
                                                "MEDIUM: 20 filled fields \r\n" +
                                                "HARD: 10 filled fields";

    public static InitialGameData getUserPreference() {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //below windows can't be entered before dealing with this one
        window.setTitle("New game");
        window.setWidth(400);
        window.setHeight(300);

        Label nameField = new Label();
        nameField.setText("Login:");

        TextField inputLogin = new TextField();
        inputLogin.setMaxWidth(window.getWidth() * 0.5);

        Label passwordField = new Label();
        passwordField.setText("Password:");

        PasswordField inputPassword = new PasswordField();
        inputPassword.setMaxWidth(window.getWidth() * 0.5);

        Label difficultyLevelChoice = new Label();
        difficultyLevelChoice.setText("Difficulty level:");

        ChoiceBox<DifficultyLevel> difficultyLevelChoiceBox = new ChoiceBox<>();
        difficultyLevelChoiceBox.getItems().addAll(DifficultyLevel.EASY, DifficultyLevel.MEDIUM, DifficultyLevel.HARD);
        difficultyLevelChoiceBox.setValue(DifficultyLevel.MEDIUM);

        Button helpButton = new Button();
        helpButton.setText("HELP");
        helpButton.setOnMouseClicked(e -> MessageBox.displayMessage("Explanation", EXPLANATION));

        Button confirmButton = new Button();
        confirmButton.setText("Confirm");
        confirmButton.setOnMouseClicked(e -> {

            if (isNotBlank(inputLogin) && isNotBlank(inputPassword)) {
                if(validateLogin()) {
                    initialGameData = new InitialGameData(inputLogin.getText(), difficultyLevelChoiceBox.getValue());
                    window.close();
                } else {
                    //TODO: when login fails
                }

            }

        });

        VBox windowLayout = new VBox(nameField, inputLogin, passwordField, inputPassword, difficultyLevelChoice,
                difficultyLevelChoiceBox, confirmButton, helpButton);
        windowLayout.setSpacing(10);
        windowLayout.setPadding(new Insets(5, 5, 5, 5));
        windowLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(windowLayout);
        window.setScene(scene);
        window.showAndWait();

        return initialGameData;
    }

    private static boolean isNotBlank(TextField inputTextField) {
        return inputTextField.getText().length() >= ONE;
    }

    private static boolean validateLogin() {
        boolean result = true;
        //TODO: result = false +  login procedure that changes value to true if successful

        return result;
    }
}