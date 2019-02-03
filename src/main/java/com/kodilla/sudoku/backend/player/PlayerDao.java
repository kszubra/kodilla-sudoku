package com.kodilla.sudoku.backend.player;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface PlayerDao extends CrudRepository<Player, Integer> {

    @Query
    Optional<Player> getPlayerByUsername(@Param("USERNAME") String username);

}
