package com.kodilla.sudoku.backend.assets;

import com.kodilla.sudoku.backend.enumerics.DifficultyLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InitialGameData {
    private String name;
    private DifficultyLevel difficultyLevel;

}
