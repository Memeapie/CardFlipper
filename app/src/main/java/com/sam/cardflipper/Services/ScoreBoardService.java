package com.sam.cardflipper.Services;

import android.content.Context;

import com.sam.cardflipper.Models.Score;

import java.util.List;

import lombok.SneakyThrows;

public interface ScoreBoardService {

    @SneakyThrows
    String getScoresRaw(Context context);

    @SneakyThrows
    List<Score> getScores(Context context);

    @SneakyThrows
    void writeScore(Context context, String name, Integer score);

}
