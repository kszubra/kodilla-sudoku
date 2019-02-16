package com.kodilla.sudoku.frontend.popups;

import com.kodilla.sudoku.backend.exceptions.PlayerNotFoundException;
import com.kodilla.sudoku.backend.password.hasher.Sha512Hasher;
import com.kodilla.sudoku.backend.player.Player;

import com.kodilla.sudoku.backend.player.PlayerDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProfileGenerator {
    @Autowired
    PlayerDao playerDao;

    private final int MINIMUM_TEXT_LENGTH = 1;
    private final int MAXIMUM_TEXT_LENGTH = 20;
    private final String INSTRUCTION = "Login and password need to contain " + MINIMUM_TEXT_LENGTH + " - " + MAXIMUM_TEXT_LENGTH + " characters";
    private final String FAILED_LOGIN_MESSAGE = "Something went wrong! Possible reasons are: \r\n" +
                                                    "- login is already taken \r\n" +
                                                    "- login or password don't match required length \r\n" +
                                                    "- password and its confirmation don't match";
    private boolean availableLogin = false;


    public void createUser() {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //below windows can't be entered before dealing with this one
        window.setTitle("Create new user");
        window.setWidth(400);
        window.setHeight(300);

        Label instructionField = new Label();
        instructionField.setText(INSTRUCTION);

        Label loginField = new Label();
        loginField.setText("Login:");
        loginField.setTextFill(Color.web("#b299e6"));

        TextField inputLogin = new TextField();
        inputLogin.textProperty().addListener((obs, oldText, newText) -> {

            try {
                Player player = playerDao.getPlayerByUsername(newText).orElseThrow(() -> new PlayerNotFoundException("Given login does not exist"));
                availableLogin=false;
            } catch (PlayerNotFoundException e) {
                availableLogin=true;
            }

            if (availableLogin && hasProperLength(inputLogin)) {
                inputLogin.setStyle("-fx-background-color: PaleGreen");
            } else {
                inputLogin.setStyle("-fx-background-color: LightCoral");
            }

        });
        inputLogin.setMaxWidth(window.getWidth() * 0.5);

        Label passwordField = new Label();
        passwordField.setText("Password:");
        passwordField.setTextFill(Color.web("#b299e6"));

        PasswordField inputPassword = new PasswordField();
        inputPassword.textProperty().addListener((obs, oldText, newText) -> {

            if ( hasProperLength(inputPassword) ) {
                inputPassword.setStyle("-fx-background-color: PaleGreen");
            } else {
                inputPassword.setStyle("-fx-background-color: LightCoral");
            }

        });
        inputPassword.setMaxWidth(window.getWidth() * 0.5);

        Label confirmPasswordField = new Label();
        confirmPasswordField.setText("Confirm password:");
        confirmPasswordField.setTextFill(Color.web("#b299e6"));

        PasswordField inputConfirmPassword = new PasswordField();
        inputConfirmPassword.textProperty().addListener((obs, oldText, newText) -> {

            if ( newText.equals(inputPassword.getText()) ) {
                inputConfirmPassword.setStyle("-fx-background-color: PaleGreen");
            } else {
                inputConfirmPassword.setStyle("-fx-background-color: LightCoral");
            }

        });
        inputConfirmPassword.setMaxWidth(window.getWidth() * 0.5);

        Button confirmButton = new Button();
        confirmButton.setText("Create");
        confirmButton.setOnMouseClicked(e -> {

            if (hasProperLength(inputLogin)
                    && availableLogin
                    && hasProperLength(inputPassword)
                    && hasProperLength(inputConfirmPassword)
                    && inputPassword.getText().equals(inputConfirmPassword.getText()) ) {

                System.out.println("Passwords match");
                Player player = new Player();
                player.setUsername(inputLogin.getText());
                player.setHashedPassword( Sha512Hasher.getInstance().generateHashedPassword(inputPassword.getText())  );
                player.setRegistrationDate(LocalDate.now());
                playerDao.save(player);

                window.close();

            } else {
                MessageBox.displayMessage("Registration failed", FAILED_LOGIN_MESSAGE);
            }

        });

        VBox windowLayout = new VBox(instructionField, loginField, inputLogin, passwordField, inputPassword, confirmPasswordField,
                inputConfirmPassword, confirmButton);
        windowLayout.setSpacing(10);
        windowLayout.setPadding(new Insets(5, 5, 5, 5));
        windowLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(windowLayout);
        scene.getStylesheets().add("Sudoku.css");
        window.setScene(scene);
        window.showAndWait();

    }

    private boolean hasProperLength(TextField inputTextField) {
        return (inputTextField.getText().length() >= MINIMUM_TEXT_LENGTH) && (inputTextField.getText().length() <= MAXIMUM_TEXT_LENGTH);
    }
}
