package com.sam.cardflipper.Services.Impl;

import com.sam.cardflipper.Models.Card;
import com.sam.cardflipper.Models.Position;
import com.sam.cardflipper.R;
import com.sam.cardflipper.Services.GameController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class GameControllerImpl implements GameController {

    private Map<String, Integer> cardList = new ConcurrentHashMap<>();

    @Override
    public List<Integer> createButtonReferences() {
        List<Integer> response = new ArrayList<>();
        response.add(R.id.button11);
        response.add(R.id.button12);
        response.add(R.id.button13);
        response.add(R.id.button14);
        response.add(R.id.button21);
        response.add(R.id.button22);
        response.add(R.id.button23);
        response.add(R.id.button24);
        response.add(R.id.button31);
        response.add(R.id.button32);
        response.add(R.id.button33);
        response.add(R.id.button34);
        response.add(R.id.button41);
        response.add(R.id.button42);
        response.add(R.id.button43);
        response.add(R.id.button44);
        response.add(R.id.button51);
        response.add(R.id.button52);
        response.add(R.id.button53);
        response.add(R.id.button54);
        return response;
    }

    @Override
    public Integer getCardImage(String cardName) {
        if (cardList.size() == 0){
            Map<String, Integer> response = new ConcurrentHashMap<>();
            response.put("1c", R.drawable.card1c);
            response.put("2c", R.drawable.card2c);
            response.put("3c", R.drawable.card3c);
            response.put("4c", R.drawable.card4c);
            response.put("5c", R.drawable.card5c);
            response.put("6c", R.drawable.card6c);
            response.put("7c", R.drawable.card7c);
            response.put("8c", R.drawable.card8c);
            response.put("9c", R.drawable.card9c);
            response.put("10c", R.drawable.card10c);
            response.put("11c", R.drawable.card11c);
            response.put("12c", R.drawable.card12c);
            response.put("13c", R.drawable.card13c);
            response.put("1h", R.drawable.card1h);
            response.put("2h", R.drawable.card2h);
            response.put("3h", R.drawable.card3h);
            response.put("4h", R.drawable.card4h);
            response.put("5h", R.drawable.card5h);
            response.put("6h", R.drawable.card6h);
            response.put("7h", R.drawable.card7h);
            response.put("8h", R.drawable.card8h);
            response.put("9h", R.drawable.card9h);
            response.put("10h", R.drawable.card10h);
            response.put("11h", R.drawable.card11h);
            response.put("12h", R.drawable.card12h);
            response.put("13h", R.drawable.card13h);
            response.put("1s", R.drawable.card1s);
            response.put("2s", R.drawable.card2s);
            response.put("3s", R.drawable.card3s);
            response.put("4s", R.drawable.card4s);
            response.put("5s", R.drawable.card5s);
            response.put("6s", R.drawable.card6s);
            response.put("7s", R.drawable.card7s);
            response.put("8s", R.drawable.card8s);
            response.put("9s", R.drawable.card9s);
            response.put("10s", R.drawable.card10s);
            response.put("11s", R.drawable.card11s);
            response.put("12s", R.drawable.card12s);
            response.put("13s", R.drawable.card13s);
            response.put("1d", R.drawable.card1d);
            response.put("2d", R.drawable.card2d);
            response.put("3d", R.drawable.card3d);
            response.put("4d", R.drawable.card4d);
            response.put("5d", R.drawable.card5d);
            response.put("6d", R.drawable.card6d);
            response.put("7d", R.drawable.card7d);
            response.put("8d", R.drawable.card8d);
            response.put("9d", R.drawable.card9d);
            response.put("10d", R.drawable.card10d);
            response.put("11d", R.drawable.card11d);
            response.put("12d", R.drawable.card12d);
            response.put("13d", R.drawable.card13d);
            cardList = response;
        }
        return cardList.get(cardName);
    }

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
            for(int y = 0; y < 4; y++) {
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
