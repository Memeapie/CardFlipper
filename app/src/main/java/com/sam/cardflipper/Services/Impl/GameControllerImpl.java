package com.sam.cardflipper.Services.Impl;

import com.sam.cardflipper.Models.Card;
import com.sam.cardflipper.Models.Position;
import com.sam.cardflipper.Services.GameController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameControllerImpl implements GameController {

    @Override
    public List<Card> createCardList(int cards) {
        List<String> cardList = pickCards(cards/2);
        List<Card> cardObjectList = new ArrayList<>();
        List<Position> positionList = populatePositions(cards);

        for (String card: cardList){
            // Add First Card
            int random = ThreadLocalRandom.current().nextInt(0, positionList.size());
            Card cardObject = new Card(card, false, positionList.get(random));
            positionList.remove(random);
            cardObjectList.add(cardObject);

            // Add Second Card
            int random2 = ThreadLocalRandom.current().nextInt(0, positionList.size());
            Card cardObject2 = new Card(card, false, positionList.get(random2));
            positionList.remove(random2);
            cardObjectList.add(cardObject2);
        }

        return cardObjectList;
    }

    private List<Position> populatePositions(int cards) {
        List<Position> positionList = new ArrayList<>();

        for(int x = 0; x < cards/4; x++){
            for(int y = 0; y < cards/4; y++) {
                positionList.add(new Position(x, y));
            }
        }

        return  positionList;
    }

    private List<String> pickCards(int numOfPairs){
        List<String> cards = new ArrayList<>();
        for (int x = 0; x < 13; x++){
            cards.add(x+1+"c");
            cards.add(x+1+"s");
            cards.add(x+1+"d");
            cards.add(x+1+"h");
        }

        List<String> response = new ArrayList<>();
        for (int y = 0; y < numOfPairs; y++){
            int random = ThreadLocalRandom.current().nextInt(0, cards.size());
            response.add(cards.get(random));
            cards.remove(random);
        }

        return response;
    }
}
