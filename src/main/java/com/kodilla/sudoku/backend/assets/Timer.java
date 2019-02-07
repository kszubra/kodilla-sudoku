package com.kodilla.sudoku.backend.assets;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalTime;


public class Timer {
    private LocalTime time;
    private Long seconds;
    private Timeline timer = null;

    public Timer(Label display) {
        this.time = LocalTime.of(00,00,00);
        this.seconds = 0L;
        System.out.println("Starting");
        this.timer = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        e -> {
                            time = time.plusSeconds(1);
                            this.seconds++;
                            display.setText(time.toString());
                        }
                )
        );
        timer.setCycleCount( Animation.INDEFINITE );
        timer.play();
    }



    public Long stop() {
        if(this.timer != null) {
            timer.pause();
            return seconds;
        } else {
            return 0L;
        }
    }

}
