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
        Player testPlayer = new Player();
        String username = "TestPlayer";
        String securePassword = hasher.generateHashedPassword("TestPassword");
        LocalDate registered = LocalDate.now();
        testPlayer.setUsername(username);
        testPlayer.setHashedPassword(securePassword);
        testPlayer.setRegistrationDate(registered);

        //when
        playerDao.save(testPlayer);
        Player loadedPlayer = playerDao.findById(2).orElseThrow(()-> new PlayerNotFoundException("Player with given ID does not exist"));

        //then
        Assert.assertTrue(loadedPlayer.getUsername().equals("TestPlayer"));

        //cleanup
        playerDao.delete(testPlayer);

    }

    @Test
    public void testGettingPlayerByUsername() {
        //given
        PasswordHasher hasher = Sha512Hasher.getInstance();
        Player testPlayer = new Player();
        String username = "TestPlayer";
        String securePassword = hasher.generateHashedPassword("TestPassword");
        LocalDate registered = LocalDate.now();
        testPlayer.setUsername(username);
        testPlayer.setHashedPassword(securePassword);
        testPlayer.setRegistrationDate(registered);

        //when
        playerDao.save(testPlayer);
        Player loadedPlayer = playerDao.getPlayerByUsername("TestPlayer").orElseThrow(() -> new PlayerNotFoundException("Player of given username doesn not exist"));

        //then
        Assert.assertTrue(loadedPlayer.getUsername().equals("TestPlayer"));

        //cleanup
        playerDao.delete(testPlayer);
    }

    @Test
    public void testUpdatingLoginDate() {
        //given
        PasswordHasher hasher = Sha512Hasher.getInstance();
        Player testPlayer = new Player();
        String username = "JohnRambo";
        String securePassword = hasher.generateHashedPassword("TestPassword");
        LocalDate registered = LocalDate.now();
        testPlayer.setUsername(username);
        testPlayer.setHashedPassword(securePassword);
        testPlayer.setRegistrationDate(registered);

        //when
        playerDao.save(testPlayer);
        Player loadedPlayer = playerDao.getPlayerByUsername("JohnRambo").orElseThrow(() -> new PlayerNotFoundException("Player of given username doesn not exist"));
        int id = loadedPlayer.getUserID();

        playerDao.updateLastLoginById(id);


        //then
        Assert.assertTrue(loadedPlayer.getUsername().equals("TestPlayer"));

        //cleanup
        //playerDao.delete(testPlayer);


    }
}
