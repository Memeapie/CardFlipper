package com.sam.cardflipper.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameCompleted {

    private Boolean gameWon;
    private Integer cardsFlipped;
    private Integer cardsPaired;

    private Boolean usingTimer;
    private Integer finalTimer;

    private Boolean usingLives;
    private Integer finalLives;

}
