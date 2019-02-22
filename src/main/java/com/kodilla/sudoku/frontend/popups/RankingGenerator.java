package com.kodilla.sudoku.frontend.popups;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        mediumRanking = new Text();
        mediumRanking.prefWidth(COLUMN_WIDTH);
        hardRanking = new Text();
        hardRanking.prefWidth(COLUMN_WIDTH);

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
        List<Score> easyScores = scoreDao.findAll().stream()
                .filter(e -> e.getDifficultyLevel().equals("easy"))
                .collect(Collectors.toList());
        List<Score> mediumScores = scoreDao.findAll().stream()
                .filter(e -> e.getDifficultyLevel().equals("medium"))
                .collect(Collectors.toList());
        List<Score> hardScores = scoreDao.findAll().stream()
                .filter(e -> e.getDifficultyLevel().equals("hard"))
                .collect(Collectors.toList());

        for(Score score : hardScores) {
            System.out.println(score.toString());
        }

        System.out.println("easy score size: " + easyScores.size());
        System.out.println("medium score size: " + mediumScores.size());
        System.out.println("hard score size: " + hardScores.size());
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
