package com.kodilla.sudoku.frontend.popups;

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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RankingGenerator {
    @Autowired
    PlayerDao playerDao;
    @Autowired
    ScoreDao scoreDao;

    private final double WINDOW_WIDTH = 450;
    private final double WINDOW_HEIGHT = 400;
    private final double COLUMN_WIDTH = WINDOW_WIDTH/3;
    private Label easyModeLabel, mediumModeLabel, hardModeLabel, playerBestEasyScore, playerBestMediumScore, playerBestHardScore;
    private Text easyRanking, mediumRanking, hardRanking;
    private Button closeButton;
    private HBox difficultyLabels, rankings, playerScores, buttons;
    private VBox windowLayout;
    private Stage window;
    private HashMap<Integer, String> idAndUsernameMap;
    private Player currentPlayer;

    private void initializeWindowLayout() {
        easyModeLabel = new Label("EASY");
        easyModeLabel.setPrefWidth(COLUMN_WIDTH);
        easyModeLabel.setAlignment(Pos.CENTER);
        mediumModeLabel = new Label("MEDIUM");
        mediumModeLabel.setPrefWidth(COLUMN_WIDTH);
        mediumModeLabel.setAlignment(Pos.CENTER);
        hardModeLabel = new Label("HARD");
        hardModeLabel.setPrefWidth(COLUMN_WIDTH);
        hardModeLabel.setAlignment(Pos.CENTER);

        difficultyLabels = new HBox(easyModeLabel, mediumModeLabel, hardModeLabel);

        easyRanking = new Text();
        easyRanking.prefWidth(COLUMN_WIDTH);
        easyRanking.setTextAlignment(TextAlignment.LEFT);

        mediumRanking = new Text();
        mediumRanking.prefWidth(COLUMN_WIDTH);
        mediumRanking.setTextAlignment(TextAlignment.LEFT);


        hardRanking = new Text();
        hardRanking.prefWidth(COLUMN_WIDTH);
        hardRanking.setTextAlignment(TextAlignment.LEFT);

        rankings = new HBox(easyRanking, mediumRanking, hardRanking);

        playerBestEasyScore = new Label();
        playerBestEasyScore.setPrefWidth(COLUMN_WIDTH);
        playerBestMediumScore = new Label();
        playerBestMediumScore.setPrefWidth(COLUMN_WIDTH);
        playerBestHardScore = new Label();
        playerBestHardScore.setPrefWidth(COLUMN_WIDTH);

        playerScores = new HBox(playerBestEasyScore, playerBestMediumScore, playerBestHardScore);

        closeButton = new Button("Close");
        closeButton.setPrefWidth(COLUMN_WIDTH);
        closeButton.setOnMouseClicked(e -> window.close());

        buttons = new HBox(closeButton);

        windowLayout = new VBox(difficultyLabels, rankings, playerScores, buttons);

    }

    private void prepareScores(String username) {
        idAndUsernameMap = new HashMap<>();
        List<Player> allPlayers = playerDao.findAll();
        currentPlayer = playerDao.getPlayerByUsername(username).orElseThrow(() -> new PlayerNotFoundException("Given player does not exist"));

        for(Player player : allPlayers) {
            idAndUsernameMap.put(player.getUserID(), player.getUsername());
        }

        List<Score> easyScores = scoreDao.findAll().stream()
                .filter(e -> e.getDifficultyLevel().equals("easy"))
                .collect(Collectors.toList());
        easyScores.sort(Comparator.comparing(Score::getDuration));

        List<Score> mediumScores = scoreDao.findAll().stream()
                .filter(e -> e.getDifficultyLevel().equals("medium"))
                .collect(Collectors.toList());
        mediumScores.sort(Comparator.comparing(Score::getDuration));

        List<Score> hardScores = scoreDao.findAll().stream()
                .filter(e -> e.getDifficultyLevel().equals("hard"))
                .collect(Collectors.toList());
        hardScores.sort(Comparator.comparing(Score::getDuration));

        StringBuilder easyScoreBuilder = new StringBuilder();
        StringBuilder mediumScoreBuilder = new StringBuilder();
        StringBuilder hardScoreBuilder = new StringBuilder();

        for(Score score : easyScores) {
            easyScoreBuilder.append( idAndUsernameMap.get(score.getPlayer().getUserID()) + ": " + score.getDuration() + " finished: " + score.isCompleted() + "\r\n" );
        }
        easyRanking.setText(easyScoreBuilder.toString());

        for(Score score : mediumScores) {
            mediumScoreBuilder.append( idAndUsernameMap.get(score.getPlayer().getUserID()) + ": " + score.getDuration() + " finished: " + score.isCompleted() + "\r\n" );
        }
        mediumRanking.setText(mediumScoreBuilder.toString());

        for(Score score : hardScores) {
            hardScoreBuilder.append( idAndUsernameMap.get(score.getPlayer().getUserID()) + ": " + score.getDuration() + " finished: " + score.isCompleted() + "\r\n" );
        }
        hardRanking.setText(hardScoreBuilder.toString());

        playerBestEasyScore.setText("Player's best score");
        playerBestMediumScore.setText("Player's best score");
        playerBestHardScore.setText("Player's best score");

    }

    public void displayRanking(String playerUsername) {

        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Ranking");
        window.setWidth(WINDOW_WIDTH);
        window.setHeight(WINDOW_HEIGHT);

        initializeWindowLayout();
        prepareScores(playerUsername);

        Scene scene = new Scene(windowLayout);
        scene.getStylesheets().add("Sudoku.css");
        window.setScene(scene);
        window.showAndWait();
    }
}
