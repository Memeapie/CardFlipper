package com.sam.cardflipper.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sam.cardflipper.R;
import com.sam.cardflipper.Services.Impl.ScoreBoardServiceImpl;
import com.sam.cardflipper.Services.Impl.SoundEffectServiceImpl;
import com.sam.cardflipper.Services.ScoreBoardService;
import com.sam.cardflipper.Services.SoundEffectService;

import static com.sam.cardflipper.Main.ActivityBar.gameController;

public class GameOver extends AppCompatActivity {

    private final SoundEffectService soundEffectService = new SoundEffectServiceImpl();
    private final ScoreBoardService scoreBoardService = new ScoreBoardServiceImpl();

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
        if (gameController.getGameCompleted().getUsingTimer()) {
            remainingTimerTextView.setText("Remaining Time: " + gameController.getGameCompleted().getFinalTimer());
            remainingTimerSubTextView.setText("+" + timerScore());
        } else {
            remainingTimerTextView.setText("Used Infinite Time!");
            remainingTimerSubTextView.setText("+0");
        }

        TextView remainingPairsTextView = findViewById(R.id.remainingPairs);
        TextView remainingPairsSubTextView = findViewById(R.id.remainingPairsSubText);
        remainingPairsTextView.setText("Remaining Pairs: " + (gameController.getMyGameSettings().getNumberOfCards() - gameController.getGameCompleted().getCardsPaired()));
        remainingPairsSubTextView.setText("+" + pairsScore());

        TextView finalScoreTextView = findViewById(R.id.finalScoreSubtext);
        finalScoreTextView.setText(String.valueOf(totalScore()));

        if (gameController.getGameCompleted().getGameWon()){
            soundEffectService.playSoundEffect(this, R.raw.gamewin);
        } else {
            soundEffectService.playSoundEffect(this, R.raw.gamelose);
        }

        EditText usernameEntry = findViewById(R.id.editTextPersonName);
        Button returnToMainMenuButton = findViewById(R.id.backToMainMenu);
        returnToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameEntry.getText().length() == 0) {
                    usernameEntry.setText("Username");
                }

                scoreBoardService.writeScore(getBaseContext(), usernameEntry.getText().toString(), totalScore());
                Toast.makeText(GameOver.this, "Score Saved", Toast.LENGTH_LONG).show();

                startActivity(new Intent(GameOver.this, ActivityBar.class));
            }
        });

        Toast.makeText(GameOver.this, "Game Finished", Toast.LENGTH_LONG).show();

    }

    private Integer totalScore() {
        return pairsScore() + livesScore() + timerScore();
    }

    private Integer pairsScore() {
        return gameController.getGameCompleted().getCardsPaired() * 20;
    }

    private Integer livesScore() {
        if (gameController.getGameCompleted().getUsingLives()) {
            return gameController.getGameCompleted().getFinalLives() * 10;
        }
        return 0;
    }

    private Integer timerScore() {
        if (gameController.getGameCompleted().getUsingTimer()) {
            Integer timeUsed = gameController.getMyGameSettings().getTimer() - gameController.getGameCompleted().getFinalTimer();
            return (timeUsed + gameController.getMyGameSettings().getTimer()) / 3;
        }
        return 0;
    }
}