package com.kodilla.sudoku.backend.player;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PlayerDao extends CrudRepository<Player, Integer> {
}
