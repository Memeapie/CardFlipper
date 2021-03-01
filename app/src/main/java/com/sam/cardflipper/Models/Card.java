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
    Boolean isCardUsed;
    Position position;
    Integer buttonID;

    public Card(String cardName, Boolean isCardUsed, Position position) {
        this.cardName = cardName;
        this.isCardUsed = isCardUsed;
        this.position = position;
    }
}
