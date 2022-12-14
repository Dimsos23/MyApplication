package ru.dimsos.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
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

    static SharedPreferences sPrefMusic;

    private static final String LOG_TAG = "myLogs";

    static ExoPlayer exoPlayer;

    static Uri startUriMusic;
    static MediaItem startTrack;
    static MediaItem mediaItem1;
    static MediaItem mediaItem2;

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

        mediaItem1 = MediaItem.fromUri(music);
        mediaItem2 = MediaItem.fromUri(track);

        loadTrackPref();

        if (startUriMusic == null) startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.music);
        startTrack = MediaItem.fromUri(startUriMusic);

        playExoPlayer(startTrack);

        showSmartPhrases();
        startMainActivity();
    }

     void saveTrackPref() {
        Log.d(LOG_TAG, "save SharedPreference StartActivity");
        sPrefMusic = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sPrefMusic.edit();
        editor.putString(Constant.SAVED_TRACK, String.valueOf(startUriMusic));
        editor.apply();
    }

    void loadTrackPref() {
        Log.d(LOG_TAG, "load SharedPreference StartActivity");
        sPrefMusic = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String stringUri = sPrefMusic.getString(Constant.SAVED_TRACK, "");
        startUriMusic = Uri.parse(stringUri);
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
        exoPlayer.pause();
    }

    public static void resumeExoPlayer() {
        exoPlayer.play();
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
        List<String> keys = new ArrayList<String>(smartPhrases.keySet());
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
                Thread.sleep(5300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent main = new Intent(getApplicationContext(), MainActivity.class);
//            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "save SharedPreference StartActivity with onDestroy");
        saveTrackPref();
        releaseExoPlayer();
        Log.d(LOG_TAG, "onDestroy StartActivity");
    }
}