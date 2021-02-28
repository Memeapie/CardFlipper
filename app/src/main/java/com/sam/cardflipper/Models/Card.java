package com.sam.cardflipper.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode()
public class Card {

    String cardName;
    Boolean isCardFlipped;
    Position position;
    Integer buttonID;

    public Card(String cardName, Boolean isCardFlipped, Position position) {
        this.cardName = cardName;
        this.isCardFlipped = isCardFlipped;
        this.position = position;
    }
}
