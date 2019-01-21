package com.kodilla.sudoku.backend.assets;

public enum Block {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9");

    private String description;

    Block(final String description) {
        this.description = description;
    }

    public String getValue() {
        return description;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
