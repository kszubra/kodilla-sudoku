package com.kodilla.sudoku.backend.score;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface ScoreDao extends CrudRepository<Score, Integer> {
}
