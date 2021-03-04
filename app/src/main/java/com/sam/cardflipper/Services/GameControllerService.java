package com.sam.cardflipper.Services;

import com.sam.cardflipper.Models.Card;
import com.sam.cardflipper.Models.GameCompleted;
import com.sam.cardflipper.Models.GameSettings;

import java.util.List;
import java.util.Map;

public interface GameControllerService {

    List<Card> createCardList(int cards);

    GameCompleted getGameCompleted();

    void setGameCompleted(GameCompleted gameCompleted);

    void holdMyGameSettings(GameSettings settingsNew);

    GameSettings getMyGameSettings();

    List<Integer> createButtonReferences();

    Integer getCardImage(String cardName);
}
