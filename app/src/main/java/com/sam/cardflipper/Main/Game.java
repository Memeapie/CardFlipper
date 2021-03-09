package com.sam.cardflipper.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sam.cardflipper.Models.Card;
import com.sam.cardflipper.Models.GameCompleted;
import com.sam.cardflipper.Models.GameSettings;
import com.sam.cardflipper.R;
import com.sam.cardflipper.Services.Impl.GameControllerServiceImpl;
import com.sam.cardflipper.Services.Impl.SoundEffectServiceImpl;
import com.sam.cardflipper.Services.SoundEffectService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.sam.cardflipper.Main.ActivityBar.gameController;

public class Game extends AppCompatActivity {

    private final SoundEffectService soundEffectService = new SoundEffectServiceImpl();

    private Card flippedCard = null;
    private Boolean canFlipCard = true;
    private Integer pairsFound = 0;
    private Game gameContext;
    private Integer lives = -1;
    private Integer timer = 0;
    private Boolean timerSet = false;
    private Integer cardsFlipped = 0;
    private Boolean gameCompleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.gameCompleted = false;

        setScore();
        setLives(0);

        final TextView timerText = findViewById(R.id.timerText);
        timerText.setText("Timer: "+gameController.getMyGameSettings().getTimer());

        List<Integer> ids = gameController.createButtonReferences();
        List<Card> cards = gameController.createCardList(gameController.getMyGameSettings().getNumberOfCards());

        // Disable all Buttons
        for (final Integer buttonId : ids) {
            final ImageButton button = findViewById(buttonId);
            button.setClickable(false);
        }

        // Re-Enable Buttons with the right values
        for (final Card card : cards) {

            card.setButtonID(ids.get(card.getPosition().getX() * 4 + card.getPosition().getY()));
            final ImageButton button = findViewById(card.getButtonID());
            button.setClickable(true);

            // Show Cards Before Game Starts
            if (gameController.getMyGameSettings().getShowsCards()) {

                button.setImageResource(gameController.getCardImage(card.getCardName()));
                canFlipCard = false;

                if (gameController.getMyGameSettings().getAnimate()) {

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
                            canFlipCard = true;
                        }
                    };

                    Timer timer = new Timer();
                    timer.schedule(task, 2000);
                }
            } else {
                button.setImageResource(R.drawable.cardrear);
            }

            this.gameContext = this;

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (canFlipCard && !card.getIsCardUsed() && !card.getIsCardFlipped()) {
                        canFlipCard = false;

                        // Initiate Timer on First Card Pick
                        if (!timerSet){
                            timerSet = true;
                            setTimer();
                        }

                        soundEffectService.playSoundEffect(gameContext, R.raw.cardflip);

                        if (gameController.getMyGameSettings().getAnimate()) {
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

        // End Game Button
        Button endGameButton = findViewById(R.id.endGameButton);
        endGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerGameOver(true);
            }
        });
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
                            soundEffectService.playSoundEffect(gameContext, R.raw.incorrectcard);
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

                            soundEffectService.playSoundEffect(gameContext, R.raw.correctcard);

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
                            cardsFlipped += 1;
                            afterPairFlippedToRear(flippedCard, card);
                            setScore();

                            if (pairsFound == gameController.getMyGameSettings().getNumberOfCards()/2){
                                triggerGameOver(true);
                            }
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

            if (lives == 0){
                triggerGameOver(false);
            }
        }
    }

    private void setTimer() {
        final TextView timerText = findViewById(R.id.timerText);
        if (gameController.getMyGameSettings().getTimer() == -1){
            timerText.setText("Timer: Infinite");
        } else {
            timer = gameController.getMyGameSettings().getTimer();
            final Handler timerHandler = new Handler();
            final Runnable timerRunnable = new Runnable() {

                @Override
                public void run() {
                    timer -= 1;
                    timerText.setText("Timer: "+timer);

                    if (gameCompleted) {
                        System.out.println("Done");
                    } else if (timer == 0){
                        triggerGameOver(false);
                    } else {
                        timerHandler.postDelayed(this, 1000);
                    }
                }
            };
            timerRunnable.run();
        }
    }

    private void triggerGameOver(Boolean didThePlayerWin) {
        this.gameCompleted = true;

        gameController.setGameCompleted(new GameCompleted(
                didThePlayerWin,
                cardsFlipped,
                pairsFound,
                !gameController.getMyGameSettings().getTimer().equals(-1),
                timer,
                !gameController.getMyGameSettings().getNumberOfLives().equals(-1),
                lives
        ));

        Intent intent = new Intent(gameContext, GameOver.class);
        startActivity(intent);

    }
}