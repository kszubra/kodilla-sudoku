package com.kodilla.sudoku.backend.exceptions;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException (String message) {
        super(message);
    }
}
