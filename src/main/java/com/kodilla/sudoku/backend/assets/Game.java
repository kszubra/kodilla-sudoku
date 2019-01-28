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

    public Game(Player gamePlayer) {
        this.gamePlayer = gamePlayer;
        this.gameBoard = new SudokuBoard();
        this.gameScore = new Score();
        this.gameScore.setPlayer(gamePlayer);
    }

    public Game() {
        this.gamePlayer = new Player();
        this.gameBoard = new SudokuBoard();
        this.gameScore = new Score();
        this.gameScore.setPlayer(gamePlayer);
    }
}
