package com.kodilla.sudoku.backend.player;


import com.kodilla.sudoku.backend.score.Score;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name="Player.getPlayerByUsername",
                query = "FROM Player WHERE username = :USERNAME"
        )
})


@NamedNativeQueries({
        @NamedNativeQuery(
                name="Player.updateLastLoginById",
                query="UPDATE players SET LAST_LOGIN = current_date WHERE id= :PLAYER_ID, '; commit;')"
        )

})

@Entity
@Table(name="PLAYERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name="ID")
    private int userID;

    @NotNull
    @Column(name="USERNAME", unique = true)
    private String username;

    @NotNull
    @Column(name="SECURE_PASSWORD")
    private String hashedPassword;

    @NotNull
    @Column(name="REGISTERED")
    private LocalDate registrationDate;

    @Column(name="LAST_LOGIN")
    private LocalDate lastLogin;

    @OneToMany(
            targetEntity = Score.class,
            mappedBy = "player",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Score> playerScores = new ArrayList<>();


}
