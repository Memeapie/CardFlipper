package com.sam.cardflipper.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.sam.cardflipper.Main.Game;
import com.sam.cardflipper.Models.GameSettings;
import com.sam.cardflipper.R;

import static com.sam.cardflipper.Main.ActivityBar.gameController;

public class GameSetting extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_game_settings, container, false);

        final Spinner cardsSpinner = view.findViewById(R.id.gameSettingsCardsSpinner);
        final Spinner livesSpinner = view.findViewById(R.id.gameSettingsLivesSpinner);
        final Spinner timerSpinner = view.findViewById(R.id.gameSettingsTimerSpinner);
        final CheckBox animateCheckbox = view.findViewById(R.id.gameSettingsCheckBoxAnimateCards);
        final CheckBox showCards = view.findViewById(R.id.gameSettingsCheckBoxShowCards);

        final Button resetToDefaultButton = view.findViewById(R.id.gameSettingsResetDefault);
        resetToDefaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardsSpinner.setSelection(0);
                livesSpinner.setSelection(0);
                timerSpinner.setSelection(0);
                animateCheckbox.setChecked(false);
                showCards.setChecked(false);
            }
        });

        final Button startGameButton = view.findViewById(R.id.gameSettingsStartButton);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameSettings gameSettings = new GameSettings();
                gameSettings.setNumberOfCards(cardsSpinner.getSelectedItem().toString());
                gameSettings.setNumberOfLives(livesSpinner.getSelectedItem().toString());
                gameSettings.setTimer(timerSpinner.getSelectedItem().toString());
                gameSettings.setAnimate(animateCheckbox.isChecked());
                gameSettings.setShowsCards(showCards.isChecked());

                gameController.holdMyGameSettings(gameSettings);

                Intent intent = new Intent(v.getContext(), Game.class);
                startActivity(intent);
            }
        });

        return view;
    }
}