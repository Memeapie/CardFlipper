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
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
    private Game gameContext;
    private Integer lives = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setScore();
        setLives(0);

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
            button.setClickable(true);

            // Show Cards Before Game Starts
            if (gameController.getMyGameSettings().getShowsCards()){
                button.setImageResource(gameController.getCardImage(card.getCardName()));
                canFlipCard = false;
                if(gameController.getMyGameSettings().getAnimate()) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            AnimatorSet flipSide = (AnimatorSet) AnimatorInflater.loadAnimator(gameContext, R.animator.cardflipback);
                            flipSide.setTarget(button);
                            flipSide.start();

                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            button.setImageResource(R.drawable.cardrear);
                                            AnimatorSet flipSide = (AnimatorSet) AnimatorInflater.loadAnimator(gameContext, R.animator.cardflipbacksecond);
                                            flipSide.setTarget(button);
                                            flipSide.start();

                                            TimerTask task = new TimerTask() {
                                                @Override
                                                public void run() {
                                                    canFlipCard = true;
                                                }
                                            };

                                            Timer timer = new Timer();
                                            timer.schedule(task, 250);
                                        }
                                    });
                                }
                            };

                            Timer timer = new Timer();
                            timer.schedule(task, 250);
                        }
                    }, 2000);
                } else {
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            button.setImageResource(R.drawable.cardrear);
                        }
                    };

                    Timer timer = new Timer();
                    timer.schedule(task, 2000);
                }
            } else {
                button.setImageResource(R.drawable.cardrear);
            }

            this.gameContext = this;

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
                                            afterAnimationCardShowFront(card);
                                        }
                                    };

                                    Timer timer = new Timer();
                                    timer.schedule(task, 250);
                                }
                            }, 300);


                        } else {
                            button.setImageResource(gameController.getCardImage(card.getCardName()));
                            afterAnimationCardShowFront(card);
                        }
                    }
                }
            });
        }
    }

    private void afterAnimationCardShowFront(Card card){
        if (flippedCard == null) {
            flippedCard = card;
            flippedCard.setIsCardFlipped(true);
            canFlipCard = true;
        } else {
            if (flippedCard.getCardName().equals(card.getCardName())) {
                flipCardAndScore(card, 1);
            } else {
                flipCardAndScore(card, 0);
            }
        }
    }

    private void flipCardAndScore(final Card card, final Integer score){
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
                            setLives(1);
                            if(gameController.getMyGameSettings().getAnimate()) {
                                AnimatorSet flipSide = (AnimatorSet) AnimatorInflater.loadAnimator(gameContext, R.animator.cardflipback);
                                flipSide.setTarget(flippedCardButton);
                                flipSide.start();

                                AnimatorSet flipSideCard2 = (AnimatorSet) AnimatorInflater.loadAnimator(gameContext, R.animator.cardflipback);
                                flipSideCard2.setTarget(cardButton);
                                flipSideCard2.start();

                                TimerTask task = new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                flippedCardButton.setImageResource(R.drawable.cardrear);
                                                AnimatorSet flipSide = (AnimatorSet) AnimatorInflater.loadAnimator(gameContext, R.animator.cardflipbacksecond);
                                                flipSide.setTarget(flippedCardButton);
                                                flipSide.start();

                                                cardButton.setImageResource(R.drawable.cardrear);
                                                AnimatorSet flipSideCard2 = (AnimatorSet) AnimatorInflater.loadAnimator(gameContext, R.animator.cardflipbacksecond);
                                                flipSideCard2.setTarget(cardButton);
                                                flipSideCard2.start();

                                                TimerTask task = new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        afterPairFlippedToRear(flippedCard, card);
                                                    }
                                                };

                                                Timer timer = new Timer();
                                                timer.schedule(task, 250);
                                            }
                                        });
                                    }
                                };

                                Timer timer = new Timer();
                                timer.schedule(task, 250);
                            } else {
                                flippedCardButton.setImageResource(R.drawable.cardrear);
                                cardButton.setImageResource(R.drawable.cardrear);
                                afterPairFlippedToRear(flippedCard, card);
                            }

                        } else {

                            if (gameController.getMyGameSettings().getAnimate()) {
                                AlphaAnimation animationFade = new AlphaAnimation(1.0f, 0.0f);
                                animationFade.setDuration(250);
                                animationFade.setStartOffset(0);
                                animationFade.setFillAfter(true);
                                flippedCardButton.startAnimation(animationFade);
                                cardButton.startAnimation(animationFade);
                            } else {
                                flippedCardButton.setImageResource(R.drawable.cardblank);
                                cardButton.setImageResource(R.drawable.cardblank);
                            }

                            flippedCardButton.setClickable(false);
                            cardButton.setClickable(false);
                            flippedCard.setIsCardUsed(true);
                            card.setIsCardUsed(true);
                            pairsFound += score;
                            afterPairFlippedToRear(flippedCard, card);
                            setScore();
                        }
                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 500);
    }

    private void afterPairFlippedToRear(Card flippedCard, Card card){
        flippedCard.setIsCardFlipped(false);
        card.setIsCardFlipped(false);
        this.canFlipCard = true;
        this.flippedCard = null;
    }

    private void setScore(){
        final TextView scoreText = findViewById(R.id.scoreText);
        scoreText.setText("Score: " + pairsFound);
    }

    private void setLives(Integer modifyLives){
        if((lives == -1) && (gameController.getMyGameSettings().getNumberOfLives() != -1)){
            lives = gameController.getMyGameSettings().getNumberOfLives();
        }

        final TextView lifeText = findViewById(R.id.lifeText);
        if (gameController.getMyGameSettings().getNumberOfLives() == -1){
            lifeText.setText("Lives: Infinite");
        } else {
            lives -= modifyLives;
            lifeText.setText("Lives: " + lives.toString());
        }
    }
}