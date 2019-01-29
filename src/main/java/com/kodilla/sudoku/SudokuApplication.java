package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.assets.BoardCoordinates;
import com.kodilla.sudoku.backend.assets.Game;
import com.kodilla.sudoku.backend.assets.SudokuBoard;
import com.kodilla.sudoku.backend.exceptions.WrongInputException;
import com.kodilla.sudoku.frontend.popups.MessageBox;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
public class SudokuApplication extends Application {

    private static final int BOARD_LINE_SIZE = 9;
    private static final int BOARD_SIZE = 81;

    private Map<BoardCoordinates, TextField> userInterfaceFieldsMap = new HashMap<>();
    private Game currentGame = new Game();

    BorderPane windowMainGridPane = new BorderPane();
    GridPane boardFieldsPane = new GridPane();


    private void initializeUiBoard() {

        for(int row = 0; row<BOARD_LINE_SIZE; row++) {
            for(int column = 0; column<BOARD_LINE_SIZE; column++) {
                generateInputGameField(new BoardCoordinates(row, column));
            }
        }

        boardFieldsPane.setAlignment(Pos.CENTER);
        boardValuesToUi();

    }

    private void generateInputGameField(BoardCoordinates fieldCoordinates) {
        TextField inputField = new TextField();
        userInterfaceFieldsMap.put(fieldCoordinates, inputField);
        boardFieldsPane.add(inputField, fieldCoordinates.getColumn(), fieldCoordinates.getRow());
        inputField.setPromptText("0");
        inputField.setAlignment(Pos.CENTER);
        inputField.setStyle("-fx-text-inner-color: #b299e6; -fx-background-color: #29293d; -fx-border-width: 1px; -fx-border-color: #efc35d;");

        inputField.setOnKeyReleased(e->handleFieldInput(e));
    }

    private void handleFieldInput(KeyEvent event) {
        TextField eventObject = (TextField) event.getSource();

        /**
         * Allow actions only on editable fields
         */

        if( eventObject.isEditable() ) {
            int fieldRow = GridPane.getRowIndex(eventObject);
            int fieldColumn = GridPane.getColumnIndex(eventObject);

            String inputValue = eventObject.getText();
            System.out.println(inputValue);


            /**
             * Check if value has proper format, if not - set it to 0 in UI
             */
            if(! validateFieldInputFormat(inputValue) ) {
                MessageBox.displayMessage("Wrong value", "Input value should be a single number (1-9)");
                eventObject.setText("0");
                currentGame.getGameBoard().ereaseFieldValue(fieldRow, fieldColumn);

                throw new WrongInputException("Input value should be a single number (1-9)");

            }

            /**
             * if input is 0 or null - reset field in the board
             */

            else if( inputValue.isEmpty() | inputValue.equals("0") ) {
                eventObject.setText("0");
                currentGame.getGameBoard().ereaseFieldValue(fieldRow, fieldColumn);

            }

            /**
             * if value is proper and not 0 - try to putting it on the game board
             */

            else if (currentGame.getGameBoard().setFieldValue(fieldRow, fieldColumn, Integer.parseInt(inputValue))) {

            }

            /**
             * if value is not available for the field, display 0 on UI
             */

            else {
                eventObject.setText("0");
                MessageBox.displayMessage("Value not available", "This value is not available in this field");
            }

            if(isGameComplete()) {
                MessageBox.displayMessage("Congratulations!", "Congratulations, you completed the puzzle");
                handleEndGame();
            }
        }

    }

    private void handleEndGame() {
        // TODO: handle setting score, saving it ect. etc.
    }

    private boolean isGameComplete() {

        if(currentGame.getGameBoard().isComplete()) {
            currentGame.setComplete(true);
            return true;
        } else {
            currentGame.setComplete(false);
            return false;
        }
    }

    private boolean validateFieldInputFormat(String input) {

        if(input.isEmpty()) {
            return true;
        }

        char firstCharacter = input.charAt(0);

        if( !Character.isDigit(firstCharacter) ) {
            return false;
        } else if (input.length() > 1) {
            return false;
        } else {
            return true;
        }
    }

    private void boardValuesToUi() {
        SudokuBoard sourceBoard = currentGame.getGameBoard();
        int[][] array = sourceBoard.getStartingBoard();
        Arrays.stream(array)
                .forEach(e-> System.out.println(Arrays.toString(e)));

        for(Map.Entry<BoardCoordinates, TextField> entry : userInterfaceFieldsMap.entrySet()) {
            int row = entry.getKey().getRow();
            int column = entry.getKey().getColumn();
            int value = array[row][column];

            System.out.println("Placing value: " + value + " in row: " + row + " column: " + column + " from source board into UI");

            entry.getValue().setText(String.valueOf(value));
            if(value !=0) {
                entry.getValue().setEditable(false);
            }
        }

    }

    @Override
    public void start(Stage window) {

        initializeUiBoard();
        windowMainGridPane.setCenter(boardFieldsPane);
        windowMainGridPane.setStyle("-fx-background-color: #121221");

        Scene scene = new Scene(windowMainGridPane, 300, 300);

        window.setTitle("Sudoku");
        window.setResizable(true);
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {

        SpringApplication.run(SudokuApplication.class, args);
        launch(args);

    }

}

