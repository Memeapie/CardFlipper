package com.sam.cardflipper.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.sam.cardflipper.Models.Score;
import com.sam.cardflipper.R;
import com.sam.cardflipper.Services.Impl.ScoreBoardServiceImpl;
import com.sam.cardflipper.Services.ScoreBoardService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Leaderboard extends Fragment {

    List<Score> scores = new ArrayList<>();
    ScoreBoardService scoreBoardService = new ScoreBoardServiceImpl();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        final TableLayout table = view.findViewById(R.id.leaderboardTable);

        this.scores = scoreBoardService.getScores(getContext()).stream()
                .sorted(Comparator.comparing(Score::getScore))
                .collect(Collectors.toList());

        int position = 1;
        for (Score score : this.scores) {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));

            TextView textViewPosition = new TextView(getContext());
            textViewPosition.setText(Integer.toString(position));
            textViewPosition.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textViewPosition.setPadding(0, 10, 0, 10);
            textViewPosition.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));

            TextView textViewName = new TextView(getContext());
            textViewName.setText(score.getName());
            textViewName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textViewName.setPadding(0, 10, 0, 10);
            textViewName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));

            TextView textViewScore = new TextView(getContext());
            textViewScore.setText(score.getScore());
            textViewScore.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textViewScore.setPadding(0, 10, 0, 10);
            textViewScore.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));

            tableRow.addView(textViewPosition);
            tableRow.addView(textViewName);
            tableRow.addView(textViewScore);
            table.addView(tableRow, new TableLayout.LayoutParams());

            position += 1;

            if (position == 21) {
                break;
            }
        }

        return view;
    }
}