package com.kodilla.sudoku.backend.assets;

import com.kodilla.sudoku.backend.enumerics.DifficultyLevel;
import com.kodilla.sudoku.backend.player.Player;
import com.kodilla.sudoku.backend.score.Score;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {

    private SudokuBoard gameBoard = new SudokuBoard();
    private DifficultyLevel difficultyLevel;
    private Player gamePlayer = new Player();
    private Score gameScore = new Score();
    private boolean isComplete;

    public Game(InitialGameData initialData) {
        this.isComplete = false;
        this.gameBoard.preFill(initialData.getDifficultyLevel());
        this.difficultyLevel = initialData.getDifficultyLevel();
    }

    public Game() {
        this.isComplete = false;
        this.gamePlayer = new Player();
        this.gameBoard = new SudokuBoard();
        this.gameScore = new Score();
        this.gameScore.setPlayer(gamePlayer);
    }
}
