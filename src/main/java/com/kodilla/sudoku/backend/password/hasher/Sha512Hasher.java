package com.kodilla.sudoku.backend.password.hasher;

import com.kodilla.sudoku.backend.logger.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Sha512Hasher implements PasswordHasher {

    private byte[] salt;
    private static Sha512Hasher sha512Hasher;

    private Sha512Hasher() {

        try {
            this.salt = getSalt();
        } catch(NoSuchAlgorithmException e) {
            Logger.getInstance().log(e.getMessage());
        }

    }

    public static Sha512Hasher getInstance() {
        if (sha512Hasher == null) {
            sha512Hasher = new Sha512Hasher();
        }
        return sha512Hasher;
    }

    @Override
    public String generateHashedPassword(String originalPassword) {

        String generatedPassword = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);
            byte[] bytes = messageDigest.digest(originalPassword.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return generatedPassword;
    }

    private byte[] getSalt() throws NoSuchAlgorithmException {

        /**
         * Proper way would be to randomize it with:
         *
         * SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
         * byte[] salt = new byte[16];
         * secureRandom.nextBytes(salt);
         *
         * But for testing purpose using hard-coded version
         */

        byte[] salt = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

        return salt;
    }
}
