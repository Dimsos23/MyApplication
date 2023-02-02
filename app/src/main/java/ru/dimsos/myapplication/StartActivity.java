package ru.dimsos.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartActivity extends AppCompatActivity {
    private static final String LOG_TAG = "myLogs";
    SharedPrefsHelper sPref;
    static ExoPlayer exoPlayer;
    static Uri startUriMusic;
    static MediaItem startTrack;
    static MediaItem mediaItem1;
    static MediaItem mediaItem2;
    static MediaItem mediaItem3;
    static MediaItem mediaItem4;
    static MediaItem mediaItem5;

    TextView tvSmartPhrase, tvAuthor;
    String[] phrases;
    String[] authors;
    Map<String, String> smartPhrases;
    int max;
    int min = 0;
    Animation animText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        Log.d(LOG_TAG, "onCreate StartActivity");

        tvSmartPhrase = findViewById(R.id.tvSmartPhrases);
        tvAuthor = findViewById(R.id.tvAuthor);

        initHashMapSmartPhrases();

        max = smartPhrases.size();
        animText = AnimationUtils.loadAnimation(this, R.anim.scale_text_smart_phrase);

        exoPlayer = new ExoPlayer.Builder(this).build();
        int repeatModeAll = exoPlayer.REPEAT_MODE_ALL;
        exoPlayer.setRepeatMode(repeatModeAll);

        Uri music = RawResourceDataSource.buildRawResourceUri(R.raw.music);
        Uri track = RawResourceDataSource.buildRawResourceUri(R.raw.track1);
        Uri nanorobot = RawResourceDataSource.buildRawResourceUri(R.raw.nanorobot_tune);
        Uri track4 = RawResourceDataSource.buildRawResourceUri(R.raw.track4);
        Uri track5 = RawResourceDataSource.buildRawResourceUri(R.raw.track5);

        mediaItem1 = MediaItem.fromUri(music);
        mediaItem2 = MediaItem.fromUri(track);
        mediaItem3 = MediaItem.fromUri(nanorobot);
        mediaItem4 = MediaItem.fromUri(track4);
        mediaItem5 = MediaItem.fromUri(track5);

        loadTrackPref();
        checkSwitchCompactMusic();

        showSmartPhrases();
        startMainActivity();
    }

    void loadTrackPref() {
        Log.d(LOG_TAG, "load SharedPreference StartActivity");
        sPref = new SharedPrefsHelper(this);
        String stringUri = sPref.getString(Constant.SAVED_TRACK);
        if (stringUri.equals("")) {
            startTrack = mediaItem1;
        } else {
            startUriMusic = Uri.parse(stringUri);
            startTrack = MediaItem.fromUri(startUriMusic);
        }
    }

    void checkSwitchCompactMusic() {
        String stateMusic = sPref.getString(Constant.SAVED_SWITCH_MUSIC);
        if (stateMusic.equals("On") || stateMusic.equals("")) playExoPlayer(startTrack);
    }

    private void initHashMapSmartPhrases() {
        phrases = getResources().getStringArray(R.array.phrases);
        authors = getResources().getStringArray(R.array.authors);
        smartPhrases = new HashMap<>();
        for (int i = 0; i < phrases.length; i++) {
            smartPhrases.put(phrases[i], authors[i]);
        }
    }

    public static void playExoPlayer(MediaItem track) {
        Log.d(LOG_TAG, "play player StartActivity");
        exoPlayer.setMediaItem(track);
        exoPlayer.prepare();
        exoPlayer.play();
    }

    public static void stopExoPlayer() {
        Log.d(LOG_TAG, "stop player StartActivity");
        exoPlayer.stop();
    }

    public static void pauseExoPlayer() {
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.getPlaybackState();
    }

    public static void resumeExoPlayer() {
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.getPlaybackState();
    }

    public static void releaseExoPlayer() {
        if (exoPlayer != null) {
            try {
                exoPlayer.release();
                exoPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showSmartPhrases() {
        double randomNumber = (Math.random() * (max - min)) + min;
        List<String> keys = new ArrayList<>(smartPhrases.keySet());
        String text = keys.get((int) randomNumber);
        String textAuthor = smartPhrases.get(keys.get((int) randomNumber));
        tvSmartPhrase.setText(text);
        tvAuthor.setText(textAuthor);
        tvSmartPhrase.startAnimation(animText);
        tvAuthor.startAnimation(animText);
    }

    public void startMainActivity() {
        new MyThread("myThread").start();
    }

    public class MyThread extends Thread {
        MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                Thread.sleep(5300); // 5300
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(main);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "save SharedPreference StartActivity with onDestroy");
        releaseExoPlayer();
        Log.d(LOG_TAG, "onDestroy StartActivity");
    }
}