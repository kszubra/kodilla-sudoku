package com.kodilla.sudoku.backend.score;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface ScoreDao extends CrudRepository<Score, Integer> {
    @Override
    List<Score> findAll();

    Long getBestEasyScoreTimeOf(@Param("PLAYER_ID") int id);
    Long getBestMediumScoreTimeOf(@Param("PLAYER_ID") int id);
    Long getBestHardScoreTimeOf(@Param("PLAYER_ID") int id);
}
