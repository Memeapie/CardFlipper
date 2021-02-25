package com.sam.cardflipper.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sam.cardflipper.Models.Card;
import com.sam.cardflipper.R;
import com.sam.cardflipper.Services.GameController;

import java.util.List;

import static com.sam.cardflipper.Main.ActivityBar.gameController;

public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        List<Card> cards = gameController.createCardList(16);

    }
}