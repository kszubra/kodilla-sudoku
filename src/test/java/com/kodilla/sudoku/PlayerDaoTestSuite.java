package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.exceptions.PlayerNotFoundException;
import com.kodilla.sudoku.backend.password.hasher.PasswordHasher;
import com.kodilla.sudoku.backend.password.hasher.Sha512Hasher;
import com.kodilla.sudoku.backend.player.Player;
import com.kodilla.sudoku.backend.player.PlayerDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerDaoTestSuite {
    @Autowired
    PlayerDao playerDao;

    private static int testNumber;


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
    public void testAddingNewPlayer() {
        //given
        PasswordHasher hasher = Sha512Hasher.getInstance();
        String username = "testName";
        String securePassword = hasher.generateHashedPassword("Password123");
        LocalDate registered = LocalDate.now();
        Player testPlayer = new Player(1, username, securePassword, registered, null, null);

        //when
        playerDao.save(testPlayer);
        Player loadedPlayer = playerDao.findById(1).orElseThrow(()-> new PlayerNotFoundException("Given player was not found"));

        //then
        Assert.assertTrue(loadedPlayer.getUsername().equals("testName"));

    }
}
