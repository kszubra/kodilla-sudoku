package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.exceptions.ScoreNotFoundException;
import com.kodilla.sudoku.backend.password.hasher.PasswordHasher;
import com.kodilla.sudoku.backend.password.hasher.Sha512Hasher;
import com.kodilla.sudoku.backend.player.Player;
import com.kodilla.sudoku.backend.player.PlayerDao;
import com.kodilla.sudoku.backend.score.Score;
import com.kodilla.sudoku.backend.score.ScoreDao;
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

    @Test
    public void testCreatingScore() {
        System.out.println(" creating and saving score in database");

        //given
        Player testPlayer = new Player();
        PasswordHasher hasher = Sha512Hasher.getInstance();
        testPlayer.setUsername("Rambo");
        testPlayer.setRegistrationDate(LocalDate.now());
        testPlayer.setHashedPassword( hasher.generateHashedPassword("123") );
        playerDao.save(testPlayer);

        Score testScore = new Score();
        testScore.setPlayer(testPlayer);
        testScore.setAchieveDate(LocalDate.now());
        testScore.setCompleted(true);
        testScore.setDuration(2300L);

        //when
        scoreDao.save(testScore);
        Score loadedScore = scoreDao.findById(2).orElseThrow(() -> new ScoreNotFoundException("Score with given ID does not exist"));

        //then
        Assert.assertTrue(loadedScore.getDuration() == 2300L);

        //cleanup
        scoreDao.delete(testScore);
        playerDao.delete(testPlayer);
    }



}
