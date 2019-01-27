package com.kodilla.sudoku.backend.exceptions;

public class UnableToSolveException extends RuntimeException {
    public UnableToSolveException (String message) {
        super(message);
    }
}
