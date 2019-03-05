package com.kodilla.sudoku.backend.player;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface PlayerDao extends CrudRepository<Player, Integer> {

    @Override
    List<Player> findAll();

    @Query
    Optional<Player> getPlayerByUsername(@Param("USERNAME") String username);

    @Modifying
    void updateLastLoginById(@Param("PLAYER_ID") int id);

    Boolean existsByUsername(String username);




}
