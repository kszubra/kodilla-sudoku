package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.assets.BoardCoordinates;
import com.kodilla.sudoku.backend.assets.Game;
import com.kodilla.sudoku.backend.exceptions.WrongInputException;
import com.kodilla.sudoku.frontend.popups.MessageBox;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


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

    }

    private void generateInputGameField(BoardCoordinates fieldCoordinates) {
        TextField inputField = new TextField();
        userInterfaceFieldsMap.put(fieldCoordinates, inputField);
        boardFieldsPane.add(inputField, fieldCoordinates.getRow(), fieldCoordinates.getColumn());
        inputField.setPromptText("0");
        inputField.setAlignment(Pos.CENTER);

        inputField.setOnKeyReleased(e->handleFieldInput(e));


    }

    /**
     * Validates input. If it's proper, adds to the field and backend board
     */

    private void handleFieldInput(KeyEvent event){
        TextField eventObject = (TextField) event.getSource();

        String inputValue = eventObject.getText();
        System.out.println(inputValue);
        if(! validateFieldInputFormat(inputValue) ) {
            MessageBox.displayMessage("Wrong value", "Input value should be a single number (1-9)");
            eventObject.setText("0");
            throw new WrongInputException("Input value should be a single number (1-9)");
        }

    }


    private boolean validateFieldInputFormat(String input) {
        char firstCharacter = input.charAt(0);

        if( !Character.isDigit(firstCharacter) ) {
            return false;
        } else if (input.length() > 1) {
            return false;
        } else {
            return true;
        }
    }



    @Override
    public void start(Stage window) throws Exception {

        initializeUiBoard();
        windowMainGridPane.setCenter(boardFieldsPane);

        Scene scene = new Scene(windowMainGridPane, 300, 300, Color.BLACK);

        window.setTitle("Sudoku 0.0.1");
        window.setResizable(true);
        window.setScene(scene);
        window.show();


    }

    public static void main(String[] args) {
        launch(args);
        SpringApplication.run(SudokuApplication.class, args);
    }

}

