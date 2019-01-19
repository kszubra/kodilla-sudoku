package com.kodilla.sudoku.backend.exceptions;

public class TooManyItemsException extends Exception {

    private static final long serialVersionUID = -1436599053365718029L;

    public TooManyItemsException (String message) {
        super(message);
    }
}
