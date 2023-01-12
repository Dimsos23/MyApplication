package ru.dimsos.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    final String LOG_TAG = "myLogs";

    static SoundPool soundPool;
    static int soundIdSnap;
    static boolean switchSnapState = true;

    SharedPreferences sPref;
    SharedPreferences sPrefMusic;

    public static DbManager dbManager;

    public static Map<String, String> listUser = new HashMap<>();

    SingleListFragment listFragment;
    Fragment_account fragment_account;
    Fragment_sound fragment_sound;
    Fragment_level fragment_level;
    FragmentTransaction fTrans;

    FrameLayout fragContMain;
    static ViewGroup.LayoutParams layoutParams;

    ObjectAnimator pulseAnimationX;
    ObjectAnimator pulseAnimationY;

    Button btnPlay, btnRules;
    ImageButton imageButtonClose;
    static ImageView imBrain;
    static TextView tvCurrentAccount;
    static TextView tvLevelMind;

    String strLevelMind = TwoActivity.levelMind.toString();
    int intLevelMindTwo = TwoActivity.levelMind;
    static String savedRadioButtonLevelGame;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        Log.d(LOG_TAG, "MainActivity onCreate");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        // Убираем заголовок в ActionBar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        dbManager = new DbManager(this);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundIdSnap = soundPool.load(this, R.raw.click9, 1);

        fragContMain = findViewById(R.id.fragContMain);

        listFragment = new SingleListFragment();
        fragment_account = new Fragment_account();
        fragment_sound = new Fragment_sound();
        fragment_level = new Fragment_level();

        imBrain = findViewById(R.id.imBrain);

        imageButtonClose = findViewById(R.id.imageButtonClose);
        imageButtonClose.setOnClickListener(this);

        btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this);

        btnRules = findViewById(R.id.btnRules);
        btnRules.setOnClickListener(this);

        tvLevelMind = findViewById(R.id.tvMindLevel);

        tvCurrentAccount = findViewById(R.id.tvCurrentAccount);
        tvCurrentAccount.setOnClickListener(this);

        dbManager.openDatabase();
        dbManager.readDatabase();

        // Если база данных пуста, то мы не загружаем данные с SharedPreferences
//        if (!listUser.isEmpty()) loadText();

        int currentLevelAccount = Integer.parseInt(tvLevelMind.getText().toString());

        // Сохраняем в SharedPreference уровень только если он стал больше, чем был.
        if (currentLevelAccount < intLevelMindTwo) saveText();

        setImageBrainSize();
        startPulseAnimationImageBrain();
        StartActivity.resumeExoPlayer();

    }

    public void startPulseAnimationImageBrain() {
        AnimatorSet animatorSet = new AnimatorSet();

        pulseAnimationX = ObjectAnimator.ofFloat(imBrain, "scaleX", 1f, 1.1f);
        pulseAnimationX.setDuration(500);
        pulseAnimationX.setRepeatCount(ValueAnimator.INFINITE);
        pulseAnimationX.setRepeatMode(ValueAnimator.REVERSE);

        pulseAnimationY = ObjectAnimator.ofFloat(imBrain, "scaleY", 1f, 1.1f);
        pulseAnimationY.setDuration(500);
        pulseAnimationY.setRepeatCount(ValueAnimator.INFINITE);
        pulseAnimationY.setRepeatMode(ValueAnimator.REVERSE);

        animatorSet.playTogether(pulseAnimationX, pulseAnimationY);
        animatorSet.start();
    }

    public static void setImageBrainSize() {
        layoutParams = imBrain.getLayoutParams();
        int intLevelMind = Integer.parseInt(tvLevelMind.getText().toString());
        if (intLevelMind == 0) {
            layoutParams.width = 150;
            layoutParams.height = 150;
        }
        if (intLevelMind >= 5) {
            layoutParams.width = 200;
            layoutParams.height = 200;
        }
        if (intLevelMind >= 10) {
            layoutParams.width = 400;
            layoutParams.height = 400;
        }
        if (intLevelMind >= 15) {
            layoutParams.width = 600;
            layoutParams.height = 600;
        }
        if (intLevelMind >= 20) {
            layoutParams.width = 800;
            layoutParams.height = 800;
        }
        if (intLevelMind >= 25) {
            layoutParams.width = 1000;
            layoutParams.height = 1000;
        }
        if (intLevelMind >= 30) {
            layoutParams.width = 1200;
            layoutParams.height = 1200;
        }
        if (intLevelMind >= 35) {
            layoutParams.width = 1500;
            layoutParams.height = 1500;
        }
        imBrain.setLayoutParams(layoutParams);
    }

    public static void playSoundPoolSnap(int soundID) {
        if (switchSnapState) {
            soundPool.play(soundID, 1, 1, 0, 0, 1);
        }
    }


    void saveText() {
        Log.d(LOG_TAG, "save SharedPreference MainActivity");
        sPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(Constant.SAVED_LEVEL, strLevelMind);
        ed.apply();
    }

    void loadText() {
        Log.d(LOG_TAG, "load SharedPreference MainActivity");
        sPref = getPreferences(Context.MODE_PRIVATE);
        String savedName = sPref.getString(Constant.SAVED_NAME, "");
        String savedLevel = sPref.getString(Constant.SAVED_LEVEL, "");
        savedRadioButtonLevelGame = sPref.getString(Constant.SAVED_RADIO, "");
        tvCurrentAccount.setText(savedName);
        tvLevelMind.setText(savedLevel);
    }

    void saveTrackPref() {
        Log.d(LOG_TAG, "save SharedPreference with MainActivity");
        sPrefMusic = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefMusic.edit();
        editor.putString(Constant.SAVED_TRACK, String.valueOf(StartActivity.startUriMusic));
        editor.apply();

    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDatabase();
        dbManager.readDatabase();
        loadText();
        setImageBrainSize();
        StartActivity.resumeExoPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        StartActivity.pauseExoPlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveTrackPref();
        dbManager.updateLevel();
        dbManager.closeDatabase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        fTrans = getFragmentManager().beginTransaction();
        fTrans.remove(listFragment);
        fTrans.remove(fragment_sound);
        fTrans.remove(fragment_account);
        fTrans.remove(fragment_level);
        switch (item.getItemId()) {
            case R.id.account_menu:
                fTrans.add(R.id.fragContMain, fragment_account);
                break;
            case R.id.sound_menu:
                fTrans.add(R.id.fragContMain, fragment_sound);
                break;
            case R.id.level_menu:
                fTrans.add(R.id.fragContMain, fragment_level);
                break;
            case R.id.soundtrack_menu:
                imageButtonClose.setVisibility(View.VISIBLE);
                fTrans.add(R.id.fragContMain, listFragment);
                break;
            case R.id.exit_menu:
                finishAffinity();
                break;
        }
        fTrans.commit();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnPlay) {
            playSoundPoolSnap(soundIdSnap);
            // Загружаем данные если в случае, когда MainActivity не перезапускается.
            loadText();
            TwoActivity.levelMind = 0;
            Intent intent = new Intent(this, TwoActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.tvCurrentAccount) {
            playSoundPoolSnap(soundIdSnap);
            fTrans = getFragmentManager().beginTransaction();
            fTrans.add(R.id.fragContMain, fragment_account);
            fTrans.commit();
        }
        if (v.getId() == R.id.btnRules) {
            playSoundPoolSnap(soundIdSnap);
            Toast.makeText(this, "else not realized", Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.imageButtonClose) { // Закрытие ListFragment
            getFragmentManager().beginTransaction().remove(listFragment).commit();
            imageButtonClose.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}