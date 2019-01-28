package com.kodilla.sudoku.backend.assets;

import com.kodilla.sudoku.backend.player.Player;
import com.kodilla.sudoku.backend.score.Score;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {

    private SudokuBoard gameBoard;
    private Player gamePlayer;
    private Score gameScore;
    private boolean isComplete;

    public Game(Player gamePlayer) {
        this.isComplete = false;
        this.gamePlayer = gamePlayer;
        this.gameBoard = new SudokuBoard();
        this.gameScore = new Score();
        this.gameScore.setPlayer(this.gamePlayer);
    }

    public Game() {
        this.isComplete = false;
        this.gamePlayer = new Player();
        this.gameBoard = new SudokuBoard();
        this.gameScore = new Score();
        this.gameScore.setPlayer(gamePlayer);
    }
}
