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
    private String playerName;
    private Score gameScore = new Score();
    private boolean isComplete;

    public Game(InitialGameData initialData) {
        this.isComplete = false;
        this.gameBoard.preFill(initialData.getDifficultyLevel());
        this.difficultyLevel = initialData.getDifficultyLevel();
        this.playerName = initialData.getName();
    }


}
