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
        switch (timer){
            case "Infinite":
                this.timer = -1;
                break;
            case "20 Seconds":
                this.timer = 20;
                break;
            case "40 Seconds":
                this.timer = 40;
                break;
            case "1 Minute":
                this.timer = 60;
                break;
            case "2 Minutes":
                this.timer = 120;
                break;
        }
    }

    public void setAnimate(Boolean animate) {
        this.animate = animate;
    }

    public void setShowsCards(Boolean showsCards) {
        this.showsCards = showsCards;
    }
}
