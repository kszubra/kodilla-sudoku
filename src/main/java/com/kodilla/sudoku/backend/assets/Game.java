package com.kodilla.sudoku.backend.assets;

import com.kodilla.sudoku.backend.player.Player;
import com.kodilla.sudoku.backend.score.Score;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    private SudokuBoard gameBoard;
    private Player gamePlayer;
    private Score gameScore;
}
