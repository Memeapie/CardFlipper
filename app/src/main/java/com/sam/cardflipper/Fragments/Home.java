package com.sam.cardflipper.Fragments;

import androidx.fragment.app.Fragment;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sam.cardflipper.R;

import java.util.Timer;
import java.util.TimerTask;

public class Home extends Fragment {

    private Integer currentPage = 1;
    private Boolean changingPage = false;
    private final Integer maxPages = 9;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        final Button nextButton = view.findViewById(R.id.homeNextButton);
        final Button previousButton = view.findViewById(R.id.homePreviousButton);
        final Button startButton = view.findViewById(R.id.homeBackToStart);
        final TextView pageText = view.findViewById(R.id.homePageNumber);

        pageText.setText("Page "+currentPage+"/"+maxPages);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!changingPage) {
                    swipe(R.animator.swipeleft, currentPage+1);
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!changingPage) {
                    swipe(R.animator.swiperight, currentPage-1);
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!changingPage) {
                    swipe(R.animator.swiperight, 1);
                }
            }
        });

        return view;
    }

    private void swipe(Integer animation, Integer pageNumber){
        currentPage = pageNumber;
        changingPage = true;

        final ImageView welcomeImage = getView().findViewById(R.id.homeInformationImage);
        final TextView welcomeTextTitle = getView().findViewById(R.id.homeTitle);
        final TextView welcomeText = getView().findViewById(R.id.homeBody);
        final TextView pageText = getView().findViewById(R.id.homePageNumber);
        final Button nextButton = getView().findViewById(R.id.homeNextButton);
        final Button previousButton = getView().findViewById(R.id.homePreviousButton);

        AnimatorSet swipe1 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), animation);
        swipe1.setTarget(welcomeImage);
        swipe1.start();

        AnimatorSet swipe2 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), animation);
        swipe2.setTarget(welcomeText);
        swipe2.start();

        AnimatorSet swipe3 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), animation);
        swipe3.setTarget(welcomeTextTitle);
        swipe3.start();

        if (currentPage == 1) {
            previousButton.setVisibility(View.INVISIBLE);
        } else {
            previousButton.setVisibility(View.VISIBLE);
        }

        if (maxPages.equals(currentPage)) {
            nextButton.setVisibility(View.INVISIBLE);
        } else {
            nextButton.setVisibility(View.VISIBLE);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                changingPage = false;

                welcomeText.setText(getWelcomeTextBody());
                welcomeTextTitle.setText(getWelcomeTextTitle());
                welcomeImage.setImageResource(getWelcomeImage());
                pageText.setText("Page "+currentPage+"/"+maxPages);
            }
        }, 200);

    }

    private int getWelcomeImage() {
        switch (currentPage) {
            case 2:
                return R.drawable.welcome2;
            case 3:
                return R.drawable.welcome3;
            case 4:
                return R.drawable.welcome4;
            case 5:
                return R.drawable.welcome5;
            case 6:
                return R.drawable.welcome6;
            case 7:
                return R.drawable.welcome7;
            case 8:
                return R.drawable.welcome8;
            case 9:
                return R.drawable.welcome9;
            default:
                return R.drawable.welcome1;
        }
    }


    private int getWelcomeTextBody() {
        switch (currentPage){
            case 1:
                return R.string.welcomeTextBody1;
            case 2:
                return R.string.welcomeTextBody2;
            case 3:
                return R.string.welcomeTextBody3;
            case 4:
                return R.string.welcomeTextBody4;
            case 5:
                return R.string.welcomeTextBody5;
            case 6:
                return R.string.welcomeTextBody6;
            case 7:
                return R.string.welcomeTextBody7;
            case 8:
                return R.string.welcomeTextBody8;
            case 9:
                return R.string.welcomeTextBody9;
            default:
                return R.string.placeholder;
        }
    }

    private int getWelcomeTextTitle() {
        switch (currentPage){
            case 1:
                return R.string.welcomeTextTitle1;
            case 2:
            case 3:
            case 4:
            case 5:
                return R.string.welcomeTextTitle2;
            case 6:
            case 7:
            case 8:
            case 9:
                return R.string.welcomeTextTitle3;
            default:
                return R.string.placeholder;
        }
    }
}