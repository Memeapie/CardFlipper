package com.sam.cardflipper.Services;

import com.sam.cardflipper.Models.Card;

import java.util.List;
import java.util.Map;

public interface GameController {

    List<Card> createCardList(int cards);

    Integer getCardImage(String cardName);
}
