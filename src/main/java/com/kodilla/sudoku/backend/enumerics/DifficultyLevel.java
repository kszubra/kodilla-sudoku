package com.kodilla.sudoku.backend.enumerics;

public enum DifficultyLevel {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    private String description;

    DifficultyLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.getDescription();
    }
}
