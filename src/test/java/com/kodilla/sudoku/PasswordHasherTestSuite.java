package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.password.hasher.PasswordHasher;
import com.kodilla.sudoku.backend.password.hasher.Sha512Hasher;
import org.junit.*;

public class PasswordHasherTestSuite {
    private static int testNumber = 0;

    @Before
    public void beforeTest() {
        testNumber++;
        System.out.println("---------- STARTING TEST NR. " + testNumber + " ----------");
        System.out.print("Testing: ");
    }

    @After
    public void afterTest() {
        System.out.println("---------- TEST NR. " + testNumber + " FINISHED ----------\r\n");
    }

    @Test
    public void testHashing() {
        System.out.println("whether hashing method gives same result for same password");

        //given
        PasswordHasher testHasher = Sha512Hasher.getInstance();

        //when
        String testPassword = "password";
        String resultOne = testHasher.generateHashedPassword(testPassword);
        String resultTwo = testHasher.generateHashedPassword(testPassword);

        //then
        System.out.println(resultOne);
        System.out.println(resultTwo);
        Assert.assertEquals(resultOne, resultTwo);
    }

}
