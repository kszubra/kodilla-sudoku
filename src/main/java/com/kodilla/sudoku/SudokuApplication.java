package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.assets.*;
import com.kodilla.sudoku.backend.assets.Timer;
import com.kodilla.sudoku.backend.autosolving.AutoSolver;
import com.kodilla.sudoku.backend.autosolving.brutesolving.BSolver;
import com.kodilla.sudoku.backend.enumerics.DifficultyLevel;
import com.kodilla.sudoku.backend.exceptions.PlayerNotFoundException;
import com.kodilla.sudoku.backend.player.Player;
import com.kodilla.sudoku.backend.player.PlayerDao;
import com.kodilla.sudoku.backend.score.Score;
import com.kodilla.sudoku.backend.score.ScoreDao;
import com.kodilla.sudoku.frontend.popups.ProfileGenerator;
import com.kodilla.sudoku.frontend.popups.LoginOrRegisterBox;
import com.kodilla.sudoku.frontend.popups.MessageBox;
import com.kodilla.sudoku.frontend.popups.NewGameGenerator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class SudokuApplication extends Application {
    private static ConfigurableApplicationContext context;
    @Autowired
    ProfileGenerator profileGenerator;
    @Autowired
    NewGameGenerator newGameGenerator;
    @Autowired
    ScoreDao scoreDao;
    @Autowired
    PlayerDao playerDao;

    private static final int BOARD_LINE_SIZE = 9;
    private final double BUTTON_WIDTH = 150;
    private final double BOTTOM_BUTTONS_SPACING = 10;
    private final double KEYBOARD_SPACING = 5;
    private final double KEYBOARD_KEY_WIDTH = 50;
    private final double FIELD_WIDTH = 40;
    private final double WINDOW_HEIGHT = 300;
    private final double WINDOW_WIDTH = (9 * FIELD_WIDTH) + (3 * KEYBOARD_KEY_WIDTH) + (4 * KEYBOARD_SPACING);

    private Timer timer = null;

    private Map<BoardCoordinates, Label> userInterfaceFieldsMap = new HashMap<>();
    private BoardCoordinates currentlyChoosenFieldCoordinates = new BoardCoordinates(0,0);
    private List<BoardCoordinates> preFilledFieldCoordinates;
    private Game currentGame;
    private AutoSolver solver = new BSolver();

    private BorderPane windowMainGridPane = new BorderPane();
    private GridPane boardFieldsPane = new GridPane();
    private GridPane virtualKeyboardPane = new GridPane();

    private Button solveButton, newBoardButton, changeUserButton, exitButton;
    private Button[] virtualKeys = new Button[10];

    private VBox rightPanel, topPanel;
    private HBox bottomPanel;
    private Label playerDisplay, timerDisplay;


    private void initializeRightPanel() {


        initializeVirtualKeys();

        rightPanel = new VBox(virtualKeyboardPane);
        rightPanel.setAlignment(Pos.TOP_CENTER);
    }

    private void initializeBottomPanel() {
        NewGameGenerator newGameGenerator = context.getBean(NewGameGenerator.class);

        solveButton = new Button("Solve");
        solveButton.setOnMouseClicked(e -> {
            handleEndGame(false);
            solveBoard();
        });
        solveButton.setPrefWidth(BUTTON_WIDTH);
        //solveButton.setTextFill(Color.web("#b299e6"));
        //solveButton.setStyle("-fx-background-color: #403F40, linear-gradient(#403F40, #331D45)");

        changeUserButton = new Button("Switch user");
        changeUserButton.setOnMouseClicked(e -> {
            handleEndGame(false);
            startNewGame(newGameGenerator.getUserPreference());
        });
        changeUserButton.setPrefWidth(BUTTON_WIDTH);
        changeUserButton.setTextFill(Color.web("#b299e6"));
        changeUserButton.setStyle("-fx-background-color: #403F40, linear-gradient(#403F40, #331D45)");

        newBoardButton = new Button("New board");
        newBoardButton.setOnMouseClicked(e -> {
            handleEndGame(false);
            setNewBoard();
            this.timer = new Timer(timerDisplay);
        });
        newBoardButton.setPrefWidth(BUTTON_WIDTH);
        newBoardButton.setTextFill(Color.web("#b299e6"));
        newBoardButton.setStyle("-fx-background-color: #403F40, linear-gradient(#403F40, #331D45)");

        exitButton = new Button("Exit");
        exitButton.setTextFill(Color.web("#b299e6"));
        exitButton.setStyle("-fx-background-color: #403F40, linear-gradient(#403F40, #331D45)");
        exitButton.setOnMouseClicked(e-> {
            System.exit(0);
        });
        exitButton.setPrefWidth(BUTTON_WIDTH);

        bottomPanel = new HBox(solveButton, newBoardButton, changeUserButton, exitButton);
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.setSpacing(BOTTOM_BUTTONS_SPACING);
    }

    private void initializeVirtualKeys() {

        for (int i = 0; i<virtualKeys.length; i++) {
            virtualKeys[i] = new Button(String.valueOf(i));
            virtualKeys[i].setTextFill(Color.web("#b299e6"));
            virtualKeys[i].setStyle("-fx-background-color: #403F40, linear-gradient(#403F40, #331D45)");
            virtualKeys[i].setPrefWidth(KEYBOARD_KEY_WIDTH);
            virtualKeys[i].setAlignment(Pos.CENTER);
            virtualKeys[i].setOnMouseClicked(e -> handleKeyClick(e));

        }

        virtualKeyboardPane.setAlignment(Pos.CENTER);
        virtualKeyboardPane.setHgap(KEYBOARD_SPACING);
        virtualKeyboardPane.setVgap(KEYBOARD_SPACING);
        virtualKeyboardPane.add( virtualKeys[0],1 ,3);
        virtualKeyboardPane.add( virtualKeys[1],0 ,2);
        virtualKeyboardPane.add( virtualKeys[2],1 ,2);
        virtualKeyboardPane.add( virtualKeys[3],2 ,2);
        virtualKeyboardPane.add( virtualKeys[4],0 ,1);
        virtualKeyboardPane.add( virtualKeys[5],1 ,1);
        virtualKeyboardPane.add( virtualKeys[6],2 ,1);
        virtualKeyboardPane.add( virtualKeys[7],0 ,0);
        virtualKeyboardPane.add( virtualKeys[8],1, 0);
        virtualKeyboardPane.add( virtualKeys[9],2 ,0);

    }

    private void initializeTopPanel() {
        playerDisplay = new Label();
        playerDisplay.setTextFill(Color.web("#b299e6"));
        playerDisplay.setStyle("-fx-background-color: #29293d; -fx-font-size: 20px");

        timerDisplay = new Label();
        timerDisplay.setTextFill(Color.web("#b299e6"));
        timerDisplay.setStyle("-fx-background-color: #29293d; -fx-font-size: 20px");


        topPanel = new VBox(playerDisplay, timerDisplay);
        topPanel.setAlignment(Pos.CENTER);
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

        boardFieldsPane.setAlignment(Pos.TOP_CENTER);
        boardValuesToUi();
        setFieldsBorders();

    }

    private void generateInputGameField(BoardCoordinates fieldCoordinates) {
        Label inputField = new Label();
        userInterfaceFieldsMap.put(fieldCoordinates, inputField);
        boardFieldsPane.add(inputField, fieldCoordinates.getColumn(), fieldCoordinates.getRow());
        inputField.setText("0");
        inputField.setAlignment(Pos.CENTER);
        inputField.setTextFill(Color.web("#b299e6"));
        inputField.setStyle("-fx-background-color: #29293d;");
        inputField.setMinSize(FIELD_WIDTH, FIELD_WIDTH);

        inputField.setOnMouseClicked(e->handleFieldSelection(e));
    }

    private void setFieldsBorders() {
        for(Map.Entry<BoardCoordinates, Label> entry : userInterfaceFieldsMap.entrySet()) {

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

    private void handleKeyClick(MouseEvent event) {
        Button eventObject = (Button) event.getSource();

        /**
         * Allow actions only on editable fields
         */
        if( !preFilledFieldCoordinates.contains(currentlyChoosenFieldCoordinates) ) {
            int row = currentlyChoosenFieldCoordinates.getRow();
            int column = currentlyChoosenFieldCoordinates.getColumn();
            int keyValue = Integer.parseInt(eventObject.getText());

            if( keyValue == 0 ) {
                currentGame.getGameBoard().ereaseFieldValue(row, column);
                userInterfaceFieldsMap.get(currentlyChoosenFieldCoordinates).setText("0");
            }

            /**
             * if value is not 0 - try to putting it on the game board
             */

            else if (currentGame.getGameBoard().setFieldValue(row, column, keyValue)) {
                userInterfaceFieldsMap.get(currentlyChoosenFieldCoordinates).setText(eventObject.getText());

            }

            /**
             * if value is not available for the field, show error
             */

            else {
                MessageBox.displayMessage("Value not available", "This value is not available in this field");
            }

            if(isGameComplete()) {
                MessageBox.displayMessage("Congratulations!", "Congratulations, you completed the puzzle");
                handleEndGame(true);
            }
        }
    }

    private void handleFieldSelection(MouseEvent event) {
        Label eventObject = (Label) event.getSource();
        int fieldRow = GridPane.getRowIndex(eventObject);
        int fieldColumn = GridPane.getColumnIndex(eventObject);
        BoardCoordinates selectedCoordinates = new BoardCoordinates(fieldRow, fieldColumn);

        //remove bg from previous selection. Using weird construction because changing background with CSS changes border too
        userInterfaceFieldsMap.get(currentlyChoosenFieldCoordinates)
                .setBackground(new Background(new BackgroundFill(Color.rgb(41, 41, 61), CornerRadii.EMPTY, Insets.EMPTY)));//#29293d
        //set current selection
        currentlyChoosenFieldCoordinates = selectedCoordinates;

        /**
         * Allow actions only on editable fields
         */

        if( !preFilledFieldCoordinates.contains(currentlyChoosenFieldCoordinates) ) {

            eventObject.setBackground(new Background(new BackgroundFill(Color.rgb(76, 76, 119), CornerRadii.EMPTY, Insets.EMPTY)));

            String inputValue = eventObject.getText();
            System.out.println(inputValue);

        }

    }

    private void handleEndGame(boolean playerSolved) {
        Long gameTime = timer.stop();
        String playerName = currentGame.getPlayerName();

        if (!currentGame.isSaved()) {
            Player playerToSave = playerDao.getPlayerByUsername(playerName).orElseThrow(() -> new PlayerNotFoundException("Player was not found"));
            Score scoreToSave = new Score();
            scoreToSave.setPlayer(playerToSave);
            scoreToSave.setDuration(gameTime);
            scoreToSave.setCompleted(playerSolved);
            scoreToSave.setAchieveDate(LocalDate.now());
            scoreDao.save(scoreToSave);
            currentGame.setSaved(true);
        }

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

    private void boardValuesToUi() {
        preFilledFieldCoordinates = new ArrayList<>();
        SudokuBoard sourceBoard = currentGame.getGameBoard();
        int[][] array = sourceBoard.getStartingBoard();
        Arrays.stream(array)
                .forEach(e-> System.out.println(Arrays.toString(e)));

        for(Map.Entry<BoardCoordinates, Label> entry : userInterfaceFieldsMap.entrySet()) {
            int row = entry.getKey().getRow();
            int column = entry.getKey().getColumn();
            int value = array[row][column];

            System.out.println("Placing value: " + value + " in row: " + row + " column: " + column + " from source board into UI");

            entry.getValue().setText(String.valueOf(value));
            if(value !=0) {
                BoardCoordinates preFilledCoordinates = new BoardCoordinates(entry.getKey());
                preFilledFieldCoordinates.add(preFilledCoordinates);
            }
        }

    }

    public void startNewGame(InitialGameData initalData) {
        currentGame = new Game(initalData);
        boardValuesToUi();
        this.timer = new Timer(timerDisplay);
        playerDisplay.setText("Player: " + currentGame.getPlayerName());

    }

    @Override
    public void start(Stage window) {
        ProfileGenerator profileGenerator = context.getBean(ProfileGenerator.class);
        NewGameGenerator newGameGenerator = context.getBean(NewGameGenerator.class);
        playerDao = context.getBean(PlayerDao.class);
        scoreDao = context.getBean(ScoreDao.class);


        initializeTopPanel();

        boolean login = LoginOrRegisterBox.getDecision();
        if(login) {
            startNewGame(newGameGenerator.getUserPreference());
        } else {
            profileGenerator.createUser();
            startNewGame(newGameGenerator.getUserPreference());
        }

        initializeUiBoard();
        initializeBottomPanel();
        initializeRightPanel();
        windowMainGridPane.setBottom(bottomPanel);
        windowMainGridPane.setCenter(boardFieldsPane);
        windowMainGridPane.setRight(rightPanel);
        windowMainGridPane.setTop(topPanel);
        windowMainGridPane.setStyle("-fx-background-color: #29293d;");

        Scene scene = new Scene(windowMainGridPane, 300, 300);
        scene.getStylesheets().add("com/kodilla/sudoku/Sudoku.css");

        window.setTitle("Sudoku");
        window.setResizable(true);
        window.setHeight(400);
        window.setMinWidth(WINDOW_WIDTH);
        window.initStyle(StageStyle.DECORATED);
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        context = SpringApplication.run(SudokuApplication.class, args);
        launch(args);

    }

}

