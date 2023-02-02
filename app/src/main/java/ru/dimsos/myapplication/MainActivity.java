package ru.dimsos.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
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
    SharedPrefsHelper sPref;
    public static DbManager dbManager;
    public static Map<String, String> listUser;

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

    static String currentUserId;
    String savedRadioButtonLevelGame;


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

        dbManager = new DbManager(this);
        dbManager.openDatabase();
        listUser = new HashMap<>();
        dbManager.fillListUsersFromDatabase();

        sPref = new SharedPrefsHelper(this);

        loadAccountId();
        loadLevelGame();
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

    public static void playSoundPoolSnap(int soundID) {
        if (switchSnapState) {
            soundPool.play(soundID, 1, 1, 0, 0, 1);
        }
    }

    void loadLevelGame() {
        savedRadioButtonLevelGame = sPref.getString(Constant.SAVED_RADIO);
        if (savedRadioButtonLevelGame.equals("")) {
            savedRadioButtonLevelGame = "easy";
            sPref.putString(Constant.SAVED_RADIO, savedRadioButtonLevelGame);
        }
    }

    void initTextFieldsUser() {
        tvCurrentAccount.setText(dbManager.updateNameFromDatabase(currentUserId));
        tvLevelMind.setText(dbManager.updateLevelFromDatabase(currentUserId));
    }

    void loadAccountId() {
        currentUserId = sPref.getString(Constant.SAVED_USER_ID);
        if (currentUserId.equals("")) {
            currentUserId = "1";
            sPref.putString(Constant.SAVED_USER_ID, currentUserId);
        }
        initTextFieldsUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvLevelMind.setText(dbManager.updateLevelFromDatabase(currentUserId));
        loadAccountId();
        setImageBrainSize();
        StartActivity.resumeExoPlayer();
    }

    public static void setImageBrainSize() {
        layoutParams = imBrain.getLayoutParams();
        int intLevelMind = Integer.parseInt(tvLevelMind.getText().toString());
        int layoutParamsSize = 150;
        for (int i = 0; i < intLevelMind; i++) {
            layoutParamsSize += 15;
        }

        layoutParams.height = layoutParamsSize;
        layoutParams.width = layoutParamsSize;
        imBrain.setLayoutParams(layoutParams);
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
            Intent intent = new Intent(this, RulesActivity.class);
            startActivity(intent);
//            Toast.makeText(this, "else not realized", Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.imageButtonClose) { // Закрытие ListFragment
            getFragmentManager().beginTransaction().remove(listFragment).commit();
            imageButtonClose.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dbManager.closeDatabase();
        finishAffinity();
    }
}