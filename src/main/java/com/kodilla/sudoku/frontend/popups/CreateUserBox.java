package com.kodilla.sudoku.frontend.popups;

import com.kodilla.sudoku.backend.assets.InitialGameData;
import com.kodilla.sudoku.backend.enumerics.DifficultyLevel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateUserBox {
    private static final int ONE = 1;

    public static void createUser() {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //below windows can't be entered before dealing with this one
        window.setTitle("Create new user");
        window.setWidth(400);
        window.setHeight(300);

        Label loginField = new Label();
        loginField.setText("Login:");

        TextField inputLogin = new TextField();
        inputLogin.setMaxWidth(window.getWidth() * 0.5);

        Label passwordField = new Label();
        passwordField.setText("Password:");

        TextField inputPassword = new TextField();
        inputPassword.setMaxWidth(window.getWidth() * 0.5);

        Label confirmPasswordField = new Label();
        confirmPasswordField.setText("Confirm password:");

        TextField inputConfirmPassword = new TextField();
        inputConfirmPassword.setMaxWidth(window.getWidth() * 0.5);

        Button confirmButton = new Button();
        confirmButton.setText("Create");
        confirmButton.setOnMouseClicked(e -> {

            if (isNotBlank(inputLogin)
                    && isNotBlank(inputPassword)
                    && isNotBlank(inputConfirmPassword)
                    && inputPassword.getText().equals(inputConfirmPassword.getText()) ) {


                //TODO: create user in database and close window

                window.close();

            } else {
                //TODO: things if passwords don't match etc
            }


        });

        VBox windowLayout = new VBox(loginField, inputLogin, passwordField, inputPassword, confirmPasswordField,
                inputConfirmPassword, confirmButton);
        windowLayout.setSpacing(10);
        windowLayout.setPadding(new Insets(5, 5, 5, 5));
        windowLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(windowLayout);
        window.setScene(scene);
        window.showAndWait();

    }

    private static boolean isNotBlank(TextField inputTextField) {
        return inputTextField.getText().length() >= ONE;
    }
}
