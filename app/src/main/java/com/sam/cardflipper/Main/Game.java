package com.sam.cardflipper.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.sam.cardflipper.Models.Card;
import com.sam.cardflipper.R;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sam.cardflipper.Main.ActivityBar.gameController;

public class Game extends AppCompatActivity {

    final Integer cardCount = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        List<Integer> ids = createButtonReferences();
        List<Card> cards = gameController.createCardList(cardCount);

        for (final Card card: cards){
            card.setButtonID(ids.get(card.getPosition().getX()*4 + card.getPosition().getY()));
            final ImageButton button = findViewById(card.getButtonID());
            button.setImageResource(R.drawable.cardrear);

            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    button.setImageResource(gameController.getCardImage(card.getCardName()));
                }
            });
        }
    }

    private List<Integer> createButtonReferences() {
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
}