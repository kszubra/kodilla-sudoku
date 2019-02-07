package com.kodilla.sudoku.backend.assets;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Timer {
    private LocalTime time;
    private Long seconds;
    private Timeline timer = null;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Timer() {
        this.time = LocalTime.of(00,00,00);
        this.seconds = 0L;
        System.out.println("Starting");
        this.timer = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        e -> {
                            time = time.plusSeconds(1);
                            this.seconds++;
                            System.out.println(time.toString());
                            //timerText.setText(start.format(formatter));
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
