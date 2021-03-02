package com.sam.cardflipper.Models;

import lombok.Data;

@Data
public class GameSettings {

    private Integer numberOfCards;
    private Integer numberOfLives;
    private Integer timer;
    private Boolean animate;
    private Boolean showsCards;

    public void setNumberOfCards(String numberOfCards) {
        this.numberOfCards = Integer.valueOf(numberOfCards);
    }

    public void setNumberOfLives(String numberOfLives) {
        if (numberOfLives.equals("Infinite")){
            this.numberOfLives = -1;
        } else {
            this.numberOfLives = Integer.valueOf(numberOfLives);
        }
    }

    public void setTimer(String timer) {
        if (timer.equals("Infinite")){
            this.timer = -1;
        } else {
            this.timer = Integer.valueOf(timer);
        }
    }

    public void setAnimate(Boolean animate) {
        this.animate = animate;
    }

    public void setShowsCards(Boolean showsCards) {
        this.showsCards = showsCards;
    }
}
