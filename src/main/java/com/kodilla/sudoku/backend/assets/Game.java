package com.kodilla.sudoku.backend.assets;

import com.kodilla.sudoku.backend.enumerics.DifficultyLevel;
import com.kodilla.sudoku.backend.score.Score;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {

    private SudokuBoard gameBoard = new SudokuBoard();
    private DifficultyLevel difficultyLevel;
    private String playerName;
    private Score gameScore;
    private boolean isComplete;
    private boolean isSaved;

    public Game(InitialGameData initialData) {
        this.gameScore = new Score();
        this.isComplete = false;
        this.gameBoard.preFill(initialData.getDifficultyLevel());
        this.difficultyLevel = initialData.getDifficultyLevel();
        this.playerName = initialData.getName();
        this.isSaved = false;
    }


}
