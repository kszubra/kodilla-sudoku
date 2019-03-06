package com.kodilla.sudoku.backend.password.hasher;

public interface PasswordHasher {

    String generateHashedPassword(String originalPassword);

}
