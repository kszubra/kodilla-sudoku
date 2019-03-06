package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.exceptions.ScoreNotFoundException;
import com.kodilla.sudoku.backend.password.hasher.PasswordHasher;
import com.kodilla.sudoku.backend.password.hasher.Sha512Hasher;
import com.kodilla.sudoku.backend.player.Player;
import com.kodilla.sudoku.backend.player.PlayerDao;
import com.kodilla.sudoku.backend.score.Score;
import com.kodilla.sudoku.backend.score.ScoreDao;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoreDaoTestSuite {
    @Autowired
    PlayerDao playerDao;
    @Autowired
    ScoreDao scoreDao;
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
    public void testCreatingScore() {
        System.out.println(" creating and saving score in database");

        PasswordHasher hasher = Sha512Hasher.getInstance();
        Player testPlayer = new Player();
        String username = "TestPlayer";
        String securePassword = hasher.generateHashedPassword("TestPassword");
        LocalDate registered = LocalDate.now();
        testPlayer.setUsername(username);
        testPlayer.setHashedPassword(securePassword);
        testPlayer.setRegistrationDate(registered);
        playerDao.save(testPlayer);

        Score testScore = new Score();
        testScore.setAchieveDate(LocalDate.now());
        testScore.setCompleted(true);
        testScore.setDuration(2300L);
        testScore.setPlayer(testPlayer);
        testScore.setDifficultyLevel("easy");

        //when
        scoreDao.save(testScore);
        int id = testScore.getScoreId();
        Score loadedScore = scoreDao.findById(id).orElseThrow(() -> new ScoreNotFoundException("Score with given ID does not exist"));

        //then
        Assert.assertTrue(loadedScore.getDuration() == 2300L);

        //cleanup
        scoreDao.delete(testScore);
        playerDao.delete(testPlayer);
    }



}
