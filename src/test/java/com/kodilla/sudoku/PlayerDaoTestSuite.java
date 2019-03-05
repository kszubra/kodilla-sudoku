package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.exceptions.PlayerNotFoundException;
import com.kodilla.sudoku.backend.password.hasher.PasswordHasher;
import com.kodilla.sudoku.backend.password.hasher.Sha512Hasher;
import com.kodilla.sudoku.backend.player.Player;
import com.kodilla.sudoku.backend.player.PlayerDao;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
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
        int id = testPlayer.getUserID();
        Player loadedPlayer = playerDao.findById(id).orElseThrow(()-> new PlayerNotFoundException("Player with given ID does not exist"));

        //then
        Assert.assertTrue(loadedPlayer.getUsername().equals("TestPlayer"));

        //cleanup
        playerDao.delete(testPlayer);

    }

    @Transactional
    @Test
    public void testIfExistsByUsername() {
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

        //then
        Assert.assertTrue(playerDao.existsByUsername("TestPlayer"));

        //cleanup
        playerDao.delete(testPlayer);

    }

    @Transactional
    @Test
    public void testGettingPlayerByUsername() {
        //given
        PasswordHasher hasher = Sha512Hasher.getInstance();
        Player testPlayer = new Player();
        String username = "UniqueTestPlayer";
        String securePassword = hasher.generateHashedPassword("TestPassword");
        LocalDate registered = LocalDate.now();
        testPlayer.setUsername(username);
        testPlayer.setHashedPassword(securePassword);
        testPlayer.setRegistrationDate(registered);

        //when
        playerDao.save(testPlayer);
        Player loadedPlayer = playerDao.getPlayerByUsername("UniqueTestPlayer").orElseThrow(() -> new PlayerNotFoundException("Player of given username does not exist"));

        //then
        Assert.assertTrue(loadedPlayer.getUsername().equals("UniqueTestPlayer"));

        //cleanup
        playerDao.delete(testPlayer);
    }

}
