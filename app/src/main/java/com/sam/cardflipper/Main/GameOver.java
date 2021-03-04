package com.sam.cardflipper.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sam.cardflipper.R;
import com.sam.cardflipper.Services.Impl.SoundEffectServiceImpl;
import com.sam.cardflipper.Services.SoundEffectService;

import static com.sam.cardflipper.Main.ActivityBar.gameController;

public class GameOver extends AppCompatActivity {

    private final SoundEffectService soundEffectService = new SoundEffectServiceImpl();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView cardsFlippedTextView = findViewById(R.id.cardsFlippedText);
        cardsFlippedTextView.setText("Cards Flipped: "+ gameController.getGameCompleted().getCardsFlipped());

        TextView remainingLivesTextView = findViewById(R.id.remainingLivesText);
        TextView remainingLivesSubTextView = findViewById(R.id.remainingLivesSubText);
        if (gameController.getGameCompleted().getUsingLives()) {
            remainingLivesTextView.setText("Remaining Lives: " + gameController.getGameCompleted().getFinalLives());
            remainingLivesSubTextView.setText("+" + livesScore());
        } else {
            remainingLivesTextView.setText("Used Infinite Lives!");
            remainingLivesSubTextView.setText("+0");
        }

        TextView remainingTimerTextView = findViewById(R.id.remainingTimer);
        TextView remainingTimerSubTextView = findViewById(R.id.remainingTimerSubText);
        if (gameController.getGameCompleted().getUsingLives()) {
            remainingTimerTextView.setText("Remaining Lives: " + gameController.getGameCompleted().getFinalTimer());
            remainingTimerSubTextView.setText("+" + gameController.getGameCompleted().getFinalTimer());
        } else {
            remainingTimerTextView.setText("Used Infinite Time!");
            remainingTimerSubTextView.setText("+0");
        }

        TextView remainingPairsTextView = findViewById(R.id.remainingPairs);
        TextView remainingPairsSubTextView = findViewById(R.id.remainingPairsSubText);
        remainingPairsTextView.setText("Remaining Pairs: " + (gameController.getMyGameSettings().getNumberOfCards() - gameController.getGameCompleted().getCardsPaired()));
        remainingPairsSubTextView.setText("+" + pairsScore());

        TextView finalScoreTextView = findViewById(R.id.finalScoreSubtext);
        finalScoreTextView.setText(String.valueOf(pairsScore() + livesScore() + gameController.getGameCompleted().getFinalTimer()));

        if (gameController.getGameCompleted().getGameWon()){
            soundEffectService.playSoundEffect(this, R.raw.gamewin);
        } else {
            soundEffectService.playSoundEffect(this, R.raw.gamelose);
        }

        Button returnToMainMenuButton = findViewById(R.id.backToMainMenu);
        returnToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameOver.this, ActivityBar.class));
            }
        });

    }

    private Integer pairsScore(){
        return gameController.getGameCompleted().getCardsPaired() * 5;
    }

    private Integer livesScore(){
        return gameController.getGameCompleted().getFinalLives() * 10;
    }
}