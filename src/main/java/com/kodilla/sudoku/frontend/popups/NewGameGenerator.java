package com.kodilla.sudoku.frontend.popups;

import com.kodilla.sudoku.backend.assets.InitialGameData;
import com.kodilla.sudoku.backend.enumerics.DifficultyLevel;
import com.kodilla.sudoku.backend.exceptions.PlayerNotFoundException;
import com.kodilla.sudoku.backend.password.hasher.PasswordHasher;
import com.kodilla.sudoku.backend.password.hasher.Sha512Hasher;
import com.kodilla.sudoku.backend.player.Player;
import com.kodilla.sudoku.backend.player.PlayerDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewGameGenerator {
    @Autowired
    private PlayerDao playerDao;

    private final int ONE = 1;
    private InitialGameData initialGameData;
    private final String EXPLANATION = "Difficulty levels differs in number of pre-filled fields. \r\n" +
                                                "EASY: 30 filled fields \r\n" +
                                                "MEDIUM: 20 filled fields \r\n" +
                                                "HARD: 10 filled fields";

    private TextField inputLogin = new TextField();
    private PasswordField inputPassword = new PasswordField();


    public InitialGameData getUserPreference() {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //below windows can't be entered before dealing with this one
        window.setTitle("New game");
        window.setWidth(400);
        window.setHeight(300);

        Label nameField = new Label();
        nameField.setText("Login:");

        inputLogin.setMaxWidth(window.getWidth() * 0.5);

        Label passwordField = new Label();
        passwordField.setText("Password:");

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
                    MessageBox.displayMessage("Login Failed", "Given login does not exist or password is not correct");
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

    private boolean isNotBlank(TextField inputTextField) {
        return inputTextField.getText().length() >= ONE;
    }

    private boolean validateLogin() {
        PasswordHasher hasher = Sha512Hasher.getInstance();

        String givenLogin = inputLogin.getText();
        String givenPassword = inputPassword.getText();
        String givenPasswordHashed = hasher.generateHashedPassword(givenPassword);

        Player player = playerDao.getPlayerByUsername(givenLogin).orElseThrow(() -> new PlayerNotFoundException("Given player does not exist"));
        String expectedPasswordHashed = player.getHashedPassword();

        if (givenPasswordHashed.equals(expectedPasswordHashed)) {
            return true;
        } else {
            return false;
        }

    }
}
