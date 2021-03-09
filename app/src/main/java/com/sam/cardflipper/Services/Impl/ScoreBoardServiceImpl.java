package com.sam.cardflipper.Services.Impl;

import android.content.Context;

import com.sam.cardflipper.Models.Score;
import com.sam.cardflipper.Services.ScoreBoardService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class ScoreBoardServiceImpl implements ScoreBoardService {

    @Override
    public List<Score> getScores(Context context) {
        String value1 = "";
        String value2 = "";
        List<Score> scores = new ArrayList<>();
        for (char c : getScoresRaw(context).toCharArray()) {
            if (c == ',') {
                if (value2.isEmpty()) {
                    value2 = value1;
                    value1 = "";
                } else {
                    Score score = new Score(value2, value1);
                    scores.add(score);
                    value1 = "";
                    value2 = "";
                }
            } else {
                value1 = value1 + c;
            }
        }
        return scores;
    }

    @Override
    @SneakyThrows
    public String getScoresRaw(Context context) {
        FileInputStream fis = context.openFileInput("score");
        InputStreamReader inputStreamReader =
                new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            stringBuilder.append(line);
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        }

        return stringBuilder.toString();
    }

    @Override
    @SneakyThrows
    public void writeScore(Context context, String username, Integer score) {
        String filename = "score";
        String fileContents = getScoresRaw(context) + username + "," + score + ",";
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(fileContents.getBytes());
        }
    }
}
