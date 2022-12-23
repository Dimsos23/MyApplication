package ru.dimsos.myapplication;

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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.navigation.NavController;
import androidx.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    final String LOG_TAG = "myLogs";

    static SoundPool soundPool;
    static int soundIdSnap;
    static boolean switchSnapState = true;

    SharedPreferences sPref;
    SharedPreferences sPrefMusic;

    public static DbManager dbManager;

    public static Map<String, String> listUser = new HashMap<>();

    Fragment_account fragment_account;
    Fragment_sound fragment_sound;
    Fragment_level fragment_level;
    Fragment_style fragment_style;
    FragmentTransaction fTrans;

    FrameLayout fragContMain;

    Button btnPlay, btnRules;
    static TextView tvCurrentAccount;
    static TextView tvLevelMind;

    String strLevelMind = TwoActivity.levelMind.toString();
    int intLevelMindTwo = TwoActivity.levelMind;
    static String savedRadioButtonLevelGame;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        Log.d(LOG_TAG, "MainActivity onCreate");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setTitle(""); // Убираем заголовок в ActionBar

        dbManager = new DbManager(this);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundIdSnap = soundPool.load(this, R.raw.click9, 1);

        fragContMain = findViewById(R.id.fragContMain);

        fragment_account = new Fragment_account();
        fragment_sound = new Fragment_sound();
        fragment_level = new Fragment_level();
        fragment_style = new Fragment_style();

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
        if (!listUser.isEmpty()) loadText();

        int currentLevelAccount = Integer.parseInt(tvLevelMind.getText().toString());

        // Сохраняем в SharedPreference уровень только если он стал больше, чем был.
        if (currentLevelAccount < intLevelMindTwo) saveText();
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
//        sPrefMusic = getPreferences(MODE_PRIVATE);
        sPrefMusic = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sPrefMusic.edit();
        editor.putString(Constant.SAVED_TRACK, String.valueOf(StartActivity.startUriMusic));
        editor.apply();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDatabase();
        dbManager.readDatabase();
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
        fTrans.remove(fragment_sound);
        fTrans.remove(fragment_account);
        fTrans.remove(fragment_style);
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
            case R.id.style_menu:
                fTrans.add(R.id.fragContMain, fragment_style);
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}