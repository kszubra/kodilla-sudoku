package com.kodilla.sudoku.frontend.popups;

import com.kodilla.sudoku.backend.exceptions.NoScoreToDisplayException;
import com.kodilla.sudoku.backend.exceptions.PlayerNotFoundException;
import com.kodilla.sudoku.backend.player.Player;
import com.kodilla.sudoku.backend.player.PlayerDao;
import com.kodilla.sudoku.backend.score.Score;
import com.kodilla.sudoku.backend.score.ScoreDao;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class RankingGenerator {
    @Autowired
    PlayerDao playerDao;
    @Autowired
    ScoreDao scoreDao;

    private final double WINDOW_WIDTH = 460;
    private final double WINDOW_HEIGHT = 320;
    private final double BETWEEN_COLUMNS_SPACING = 5;
    private final double IN_COLUMN_SPACING = 5;
    private final double COLUMN_WIDTH = (WINDOW_WIDTH - 2*BETWEEN_COLUMNS_SPACING)/3;
    private final double LABEL_BUTTON_HEIGHT = (WINDOW_HEIGHT - 3*IN_COLUMN_SPACING)*0.075;
    private final double RANKING_HEIGHT = (WINDOW_HEIGHT - 3*IN_COLUMN_SPACING)*0.60;
    private Label easyModeLabel, mediumModeLabel, hardModeLabel, playerBestEasyScore, playerBestMediumScore, playerBestHardScore, easyRanking, mediumRanking, hardRanking;
    private Button closeButton;
    private VBox easyColumn, mediumcolumn, hardColumn;
    private HBox windowLayout;
    private Stage window;
    private HashMap<Integer, String> idAndUsernameMap;
    private Player currentPlayer;

    private void initializeWindowLayout() {
        easyModeLabel = new Label("EASY");
        easyModeLabel.setPrefWidth(COLUMN_WIDTH);
        easyModeLabel.setAlignment(Pos.CENTER);
        easyModeLabel.setPrefHeight(LABEL_BUTTON_HEIGHT);

        mediumModeLabel = new Label("MEDIUM");
        mediumModeLabel.setPrefWidth(COLUMN_WIDTH);
        mediumModeLabel.setAlignment(Pos.CENTER);
        mediumModeLabel.setPrefHeight(LABEL_BUTTON_HEIGHT);

        hardModeLabel = new Label("HARD");
        hardModeLabel.setPrefWidth(COLUMN_WIDTH);
        hardModeLabel.setAlignment(Pos.CENTER);
        hardModeLabel.setPrefHeight(LABEL_BUTTON_HEIGHT);

        easyRanking = new Label();
        easyRanking.setMinWidth(COLUMN_WIDTH);
        easyRanking.setTextAlignment(TextAlignment.JUSTIFY);
        easyRanking.setAlignment(Pos.TOP_LEFT);
        easyRanking.setPrefHeight(RANKING_HEIGHT);

        mediumRanking = new Label();
        mediumRanking.setMinWidth(COLUMN_WIDTH);
        mediumRanking.setTextAlignment(TextAlignment.JUSTIFY);
        mediumRanking.setAlignment(Pos.TOP_LEFT);
        mediumRanking.setPrefHeight(RANKING_HEIGHT);

        hardRanking = new Label();
        hardRanking.setMinWidth(COLUMN_WIDTH);
        hardRanking.setTextAlignment(TextAlignment.JUSTIFY);
        hardRanking.setAlignment(Pos.TOP_LEFT);
        hardRanking.setPrefHeight(RANKING_HEIGHT);

        playerBestEasyScore = new Label();
        playerBestEasyScore.setPrefWidth(COLUMN_WIDTH);
        playerBestEasyScore.setPrefHeight(LABEL_BUTTON_HEIGHT);

        playerBestMediumScore = new Label();
        playerBestMediumScore.setPrefWidth(COLUMN_WIDTH);
        playerBestMediumScore.setPrefHeight(LABEL_BUTTON_HEIGHT);

        playerBestHardScore = new Label();
        playerBestHardScore.setPrefWidth(COLUMN_WIDTH);
        playerBestHardScore.setPrefHeight(LABEL_BUTTON_HEIGHT);

        closeButton = new Button("Close");
        closeButton.setPrefWidth(COLUMN_WIDTH);
        closeButton.setPrefHeight(LABEL_BUTTON_HEIGHT);
        closeButton.setOnMouseClicked(e -> window.close());

        easyColumn = new VBox(easyModeLabel, easyRanking, playerBestEasyScore);
        easyColumn.setSpacing(IN_COLUMN_SPACING);
        mediumcolumn = new VBox(mediumModeLabel, mediumRanking, playerBestMediumScore);
        mediumcolumn.setSpacing(IN_COLUMN_SPACING);
        hardColumn = new VBox(hardModeLabel, hardRanking, playerBestHardScore, closeButton);
        hardColumn.setSpacing(IN_COLUMN_SPACING);

        windowLayout = new HBox(easyColumn, mediumcolumn, hardColumn);
        windowLayout.setSpacing(BETWEEN_COLUMNS_SPACING);

    }

    private void prepareScores(String username) {
        idAndUsernameMap = new HashMap<>();
        List<Player> allPlayers = playerDao.findAll();
        currentPlayer = playerDao.getPlayerByUsername(username).orElseThrow(() -> new PlayerNotFoundException("Given player does not exist"));

        for(Player player : allPlayers) {
            idAndUsernameMap.put(player.getUserID(), player.getUsername());
        }

        prepareEasyScores();
        prepareMediumScores();
        prepareHardScores();
    }

    private void prepareEasyScores () {
        List<Score> easyScores = scoreDao.findAll().stream()
                .filter(e -> e.getDifficultyLevel().equals("easy"))
                .filter(e -> e.isCompleted())
                .collect(Collectors.toList());
        easyScores.sort(Comparator.comparing(Score::getDuration));
        StringBuilder easyScoreBuilder = new StringBuilder();

        for(Score score : easyScores) {
            easyScoreBuilder.append( idAndUsernameMap.get(score.getPlayer().getUserID()) + ": " + score.getDuration() + " own: " + score.isCompleted() + "\r\n" );
        }
        easyRanking.setText(easyScoreBuilder.toString());

        Optional<Score> bestEasyScore = easyScores.stream()
                .filter(e -> e.getPlayer().getUserID() == currentPlayer.getUserID())
                .filter(e -> e.isCompleted())
                .min(Comparator.comparing(Score::getDuration));

        playerBestEasyScore.setText(currentPlayer.getUsername() + "'s best score: \r\n" + ( (bestEasyScore.isPresent()) ? bestEasyScore.get().getDuration() : "not present" ) );
    }

    private void prepareMediumScores () {
        List<Score> mediumScores = scoreDao.findAll().stream()
                .filter(e -> e.getDifficultyLevel().equals("medium"))
                .filter(e -> e.isCompleted())
                .collect(Collectors.toList());
        mediumScores.sort(Comparator.comparing(Score::getDuration));
        StringBuilder mediumScoreBuilder = new StringBuilder();

        for(Score score : mediumScores) {
            mediumScoreBuilder.append( idAndUsernameMap.get(score.getPlayer().getUserID()) + ": " + score.getDuration() + " own: " + score.isCompleted() + "\r\n" );
        }
        mediumRanking.setText(mediumScoreBuilder.toString());

        Optional<Score> bestMediumScore = mediumScores.stream()
                .filter(e -> e.getPlayer().getUserID() == currentPlayer.getUserID())
                .filter(e -> e.isCompleted())
                .min(Comparator.comparing(Score::getDuration));

        playerBestMediumScore.setText(currentPlayer.getUsername() + "'s best score: \r\n" + ( (bestMediumScore.isPresent()) ? bestMediumScore.get().getDuration() : "not present" ) );
    }

    private void prepareHardScores () {
        List<Score> hardScores = scoreDao.findAll().stream()
                .filter(e -> e.getDifficultyLevel().equals("hard"))
                .filter(e -> e.isCompleted())
                .collect(Collectors.toList());
        hardScores.sort(Comparator.comparing(Score::getDuration));
        StringBuilder hardScoreBuilder = new StringBuilder();

        for(Score score : hardScores) {
            hardScoreBuilder.append( idAndUsernameMap.get(score.getPlayer().getUserID()) + ": " + score.getDuration() + " own: " + score.isCompleted() + "\r\n" );
        }
        hardRanking.setText(hardScoreBuilder.toString());

        Optional<Score> bestHardScore = hardScores.stream()
                .filter(e -> e.getPlayer().getUserID() == currentPlayer.getUserID())
                .filter(e -> e.isCompleted())
                .min(Comparator.comparing(Score::getDuration));

        playerBestHardScore.setText(currentPlayer.getUsername() + "'s best score: \r\n" + ( (bestHardScore.isPresent()) ? bestHardScore.get().getDuration() : "not present" ) );
    }

    public void displayRanking(String playerUsername) {

        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Ranking");
        window.setWidth(WINDOW_WIDTH);
        window.setMinWidth(WINDOW_WIDTH);
        window.setHeight(WINDOW_HEIGHT);
        window.setMinHeight(WINDOW_HEIGHT);

        initializeWindowLayout();
        prepareScores(playerUsername);

        Scene scene = new Scene(windowLayout);
        scene.getStylesheets().add("Sudoku.css");
        window.setScene(scene);
        window.showAndWait();
    }
}
