package com.sam.cardflipper.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import com.sam.cardflipper.Models.Card;
import com.sam.cardflipper.R;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.sam.cardflipper.Main.ActivityBar.gameController;

public class Game extends AppCompatActivity {

    private Card flippedCard = null;
    private Boolean canFlipCard = true;
    private Integer pairsFound = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        List<Integer> ids = gameController.createButtonReferences();
        List<Card> cards = gameController.createCardList(gameController.getMyGameSettings().getNumberOfCards());

        // Disable all Buttons
        for (final Integer buttonId: ids) {
            final ImageButton button = findViewById(buttonId);
            button.setClickable(false);
        }

        // Re-Enable Buttons with the right values
        for (final Card card: cards){
            card.setButtonID(ids.get(card.getPosition().getX()*4 + card.getPosition().getY()));
            final ImageButton button = findViewById(card.getButtonID());
            button.setImageResource(R.drawable.cardrear);
            button.setClickable(true);

            final Game gameContext = this;

            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(canFlipCard && !card.getIsCardUsed() && !card.getIsCardFlipped()) {
                        canFlipCard = false;

                        if(gameController.getMyGameSettings().getAnimate()){
                            AnimatorSet flipSide = (AnimatorSet) AnimatorInflater.loadAnimator(gameContext, R.animator.cardflip);
                            flipSide.setTarget(button);
                            flipSide.start();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    button.setImageResource(gameController.getCardImage(card.getCardName()));
                                    AnimatorSet flipSide = (AnimatorSet) AnimatorInflater.loadAnimator(gameContext, R.animator.cardflipsecond);
                                    flipSide.setTarget(button);
                                    flipSide.start();

                                    TimerTask task = new TimerTask() {
                                        @Override
                                        public void run() {
                                            afterAnimations(card);
                                        }
                                    };

                                    Timer timer = new Timer();
                                    timer.schedule(task, 250);
                                }
                            }, 300);


                        } else {
                            button.setImageResource(gameController.getCardImage(card.getCardName()));
                            afterAnimations(card);
                        }
                    }
                }
            });
        }
    }

    private void afterAnimations(Card card){
        if (flippedCard == null) {
            flippedCard = card;
            flippedCard.setIsCardFlipped(true);
        } else {
            if (flippedCard.getCardName().equals(card.getCardName())) {
                flipCard(card, 1);
            } else {
                flipCard(card, 0);
            }
        }
        canFlipCard = true;
    }

    private void flipCard(final Card card, final Integer score){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                canFlipCard = false;
            }
        });

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final ImageButton flippedCardButton = findViewById(flippedCard.getButtonID());
                        final ImageButton cardButton = findViewById(card.getButtonID());
                        if (score.equals(0)){
                            flippedCardButton.setImageResource(R.drawable.cardrear);
                            cardButton.setImageResource(R.drawable.cardrear);
                        } else {
                            flippedCardButton.setImageResource(R.drawable.cardblank);
                            cardButton.setImageResource(R.drawable.cardblank);
                            flippedCardButton.setClickable(false);
                            cardButton.setClickable(false);
                            flippedCard.setIsCardUsed(true);
                            card.setIsCardUsed(true);
                        }
                        flippedCard.setIsCardFlipped(false);
                        card.setIsCardFlipped(false);
                        canFlipCard = true;
                        flippedCard = null;
                        pairsFound += score;
                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 1000);
    }
}