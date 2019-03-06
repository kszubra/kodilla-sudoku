package com.kodilla.sudoku.backend.exceptions;

public class WrongInputException extends RuntimeException {
    public WrongInputException (String message) {
        super(message);
    }
}
