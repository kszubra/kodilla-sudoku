package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.assets.BoardCoordinates;
import com.kodilla.sudoku.backend.assets.Game;
import com.kodilla.sudoku.backend.assets.InitialGameData;
import com.kodilla.sudoku.backend.assets.SudokuBoard;
import com.kodilla.sudoku.backend.autosolving.AutoSolver;
import com.kodilla.sudoku.backend.autosolving.brutesolving.BSolver;
import com.kodilla.sudoku.backend.enumerics.DifficultyLevel;
import com.kodilla.sudoku.backend.exceptions.WrongInputException;
import com.kodilla.sudoku.frontend.popups.ProfileGenerator;
import com.kodilla.sudoku.frontend.popups.LoginOrRegisterBox;
import com.kodilla.sudoku.frontend.popups.MessageBox;
import com.kodilla.sudoku.frontend.popups.NewGameGenerator;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
public class SudokuApplication extends Application {
    private static ConfigurableApplicationContext context;
    @Autowired
    ProfileGenerator profileGenerator;
    @Autowired
    NewGameGenerator newGameGenerator;

    private static final int BOARD_LINE_SIZE = 9;
    private final double BUTTON_WIDTH = 150;

    private Map<BoardCoordinates, TextField> userInterfaceFieldsMap = new HashMap<>();
    private Game currentGame;
    private AutoSolver solver = new BSolver();

    private BorderPane windowMainGridPane = new BorderPane();
    private GridPane boardFieldsPane = new GridPane();

    private Button solveButton, newBoardButton, changeUserButton, exitButton;
    private VBox rightPanel;


    private void initializeRightPanel() {
        NewGameGenerator newGameGenerator = context.getBean(NewGameGenerator.class);

        solveButton = new Button("Solve");
        solveButton.setOnMouseClicked(e -> solveBoard());
        solveButton.setPrefWidth(BUTTON_WIDTH);

        changeUserButton = new Button("Switch user");
        changeUserButton.setOnMouseClicked(e -> startNewGame(newGameGenerator.getUserPreference()));
        changeUserButton.setPrefWidth(BUTTON_WIDTH);

        newBoardButton = new Button("Generate new board");
        newBoardButton.setOnMouseClicked(e -> setNewBoard());
        newBoardButton.setPrefWidth(BUTTON_WIDTH);

        exitButton = new Button("Exit");
        exitButton.setOnMouseClicked(e->System.exit(0));
        exitButton.setPrefWidth(BUTTON_WIDTH);

        rightPanel = new VBox(solveButton, newBoardButton, changeUserButton, exitButton);
        rightPanel.setAlignment(Pos.CENTER);
    }

    private void solveBoard() {
        solver.solveBoard(currentGame.getGameBoard());
        boardValuesToUi();
    }

    private void setNewBoard() {
        currentGame.getGameBoard().preFill(DifficultyLevel.MEDIUM);
        boardValuesToUi();
    }


    private void initializeUiBoard() {

        for(int row = 0; row<BOARD_LINE_SIZE; row++) {
            for(int column = 0; column<BOARD_LINE_SIZE; column++) {
                generateInputGameField(new BoardCoordinates(row, column));
            }
        }

        boardFieldsPane.setAlignment(Pos.CENTER);
        boardValuesToUi();
        setFieldsBorders();

    }

    private void generateInputGameField(BoardCoordinates fieldCoordinates) {
        TextField inputField = new TextField();
        userInterfaceFieldsMap.put(fieldCoordinates, inputField);
        boardFieldsPane.add(inputField, fieldCoordinates.getColumn(), fieldCoordinates.getRow());
        inputField.setPromptText("0");
        inputField.setAlignment(Pos.CENTER);
        inputField.setStyle("-fx-text-inner-color: #b299e6; -fx-background-color: #29293d; -fx-border-color: #29293d;");

        inputField.setOnKeyReleased(e->handleFieldInput(e));
    }

    private void setFieldsBorders() {
        for(Map.Entry<BoardCoordinates, TextField> entry : userInterfaceFieldsMap.entrySet()) {

            int row = entry.getKey().getRow();
            int column = entry.getKey().getColumn();

            if(column == 2 || column == 5) {
                entry.getValue().setStyle("-fx-text-inner-color: #b299e6; -fx-background-color: #29293d;  -fx-border-width: 1px; " +
                        "-fx-border-color: #29293d #e5db9c #29293d #29293d ;");

                if( (column == 2 && row == 2) || (column == 2 && row == 5) || (column == 5 && row == 5) || (column == 5 && row == 2) ) {
                    entry.getValue().setStyle("-fx-text-inner-color: #b299e6; -fx-background-color: #29293d;  -fx-border-width: 1px; " +
                            "-fx-border-color: #29293d #e5db9c #e5db9c #29293d ;");
                }
            }

            if( ( (row == 2) || (row == 5) )
                    && !(row == 2 && column == 2)
                    && !(row == 2 && column == 5)
                    && !(row == 5 && column == 2)
                    && !(row == 5 && column == 5) ) {

                entry.getValue().setStyle("-fx-text-inner-color: #b299e6; -fx-background-color: #29293d;  -fx-border-width: 1px; " +
                        "-fx-border-color: #29293d #29293d #e5db9c #29293d ;");
            }
        }
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

    public void startNewGame(InitialGameData initalData) {
        currentGame = new Game(initalData);
        boardValuesToUi();
    }

    @Override
    public void start(Stage window) {
        ProfileGenerator profileGenerator = context.getBean(ProfileGenerator.class);
        NewGameGenerator newGameGenerator = context.getBean(NewGameGenerator.class);

        boolean login = LoginOrRegisterBox.getDecision();
        if(login) {
            startNewGame(newGameGenerator.getUserPreference());
        } else {
            profileGenerator.createUser();
            startNewGame(newGameGenerator.getUserPreference());
        }

        initializeUiBoard();
        initializeRightPanel();
        windowMainGridPane.setCenter(boardFieldsPane);
        windowMainGridPane.setRight(rightPanel);
        windowMainGridPane.setStyle("-fx-background-color: #121221");

        Scene scene = new Scene(windowMainGridPane, 300, 300);

        window.setTitle("Sudoku");
        window.setResizable(true);
        window.setHeight(400);
        window.setWidth(450);
        window.initStyle(StageStyle.DECORATED);
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        context = SpringApplication.run(SudokuApplication.class, args);
        launch(args);

    }

}

