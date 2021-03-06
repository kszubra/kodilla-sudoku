package com.kodilla.sudoku.backend.score;

import com.kodilla.sudoku.backend.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Table(name = "SCORES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Score implements Comparable {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name="ID")
    private int scoreId;

    @ManyToOne
    @NotNull
    @JoinColumn(name="PLAYER_ID")
    private Player player;

    @NotNull
    @Column(name="DATE")
    private LocalDate achieveDate;

    @NotNull
    @Column
    private String difficultyLevel;

    @NotNull
    @Column(name="DURATION")
    private long duration;

    @NotNull
    @Column(name="COMPLETED")
    private boolean isCompleted;

    public String toString() {
        return "player id: " + player.getUserID() + " time: " + duration + " finished: " + isCompleted;
    }

    @Override
    public int compareTo(Object o) {
        Score a = (Score)o;
        if (this.duration < a.getDuration()) {return -1;}
        if (this.duration > a.getDuration()) {return 1;}
        else return 0;
    }
}
