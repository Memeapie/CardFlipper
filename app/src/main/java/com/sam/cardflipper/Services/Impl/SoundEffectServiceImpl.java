package com.sam.cardflipper.Services.Impl;

import android.content.Context;
import android.media.MediaPlayer;
import com.sam.cardflipper.Services.SoundEffectService;

public class SoundEffectServiceImpl implements SoundEffectService {

    MediaPlayer channel1 = new MediaPlayer();
    MediaPlayer channel2 = new MediaPlayer();
    MediaPlayer channel3 = new MediaPlayer();

    @Override
    public void playSoundEffect(final Context context, Integer soundEffect) {

        if (!channel1.isPlaying()){
            channel1 = MediaPlayer.create(context,soundEffect);
            channel1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    channel1.reset();
                }
            });
            channel1.start();

        } else {
            if (!channel2.isPlaying()) {
                channel2 = MediaPlayer.create(context, soundEffect);
                channel2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        channel2.reset();
                    }
                });
                channel2.start();
            } else {
                if (!channel3.isPlaying()){
                    channel3 = MediaPlayer.create(context, soundEffect);
                    channel3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            channel3.reset();
                        }
                    });
                    channel3.start();
                } else {
                    System.out.println("Dropped Sound Effect as there are no free channels");
                }
            }
        }
    }
}
