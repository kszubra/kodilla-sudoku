package com.kodilla.sudoku.backend.exceptions;

public class ValueNotAvailableException extends Exception {
    private static final long serialVersionUID = -1432462413109600938L;

    public ValueNotAvailableException (String message) {
        super(message);
    }
}
