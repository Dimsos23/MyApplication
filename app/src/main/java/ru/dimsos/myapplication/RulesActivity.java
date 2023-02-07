package ru.dimsos.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class RulesActivity extends AppCompatActivity {

    TextView tvExplainRules1, tvExplainRules2, tvExplainRules3;
    Button btn2, btn8;
    TextView tvTimer;
    TextView tvGuessNumber;
    CountDownTimer timer;
    Intent intent;
    SoundPool soundPool;
    int soundIdPressed;
    Animation animText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        setTitle("");

        tvExplainRules1 = findViewById(R.id.tvExplainRules1);
        tvExplainRules1.setVisibility(View.INVISIBLE);
        tvExplainRules2 = findViewById(R.id.tvExplainRules2);
        tvExplainRules2.setVisibility(View.INVISIBLE);
        tvExplainRules3 = findViewById(R.id.tvExplainRules3);
        tvExplainRules3.setVisibility(View.INVISIBLE);

        btn2 = findViewById(R.id.btn2Rules);
        btn8 = findViewById(R.id.btn8Rules);

        tvTimer = findViewById(R.id.tvTimerRules);
        tvGuessNumber = findViewById(R.id.tvResultRules);

        intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundIdPressed = soundPool.load(this, R.raw.click4, 1);

        animText = AnimationUtils.loadAnimation(this, R.anim.scale_text_smart_phrase);

        StartActivity.resumeExoPlayer();
        startTimer();
    }

    public void playSoundPool(int soundID) {
        if (MainActivity.switchClickState) {
            soundPool.play(soundID, 1, 1, 0, 0, 1);
        }
    }

    public void startTimer() {
        timer = new CountDownTimer(12000, 1000) {

            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished / 1000 == 10) {
                    tvExplainRules1.startAnimation(animText);
                }
                if (millisUntilFinished / 1000 == 7) {
                    tvExplainRules1.clearAnimation();
                    tvExplainRules2.startAnimation(animText);
                }
                if (millisUntilFinished / 1000 == 4) {
                    tvExplainRules2.clearAnimation();
                    tvExplainRules3.startAnimation(animText);
                }
                if (millisUntilFinished / 1000 == 2) {
                    playSoundPool(soundIdPressed);
                    btn2.setBackgroundColor(getResources().getColor(R.color.pressed));
                }
                if (millisUntilFinished / 1000 == 1) {
                    playSoundPool(soundIdPressed);
                    btn8.setBackgroundColor(getResources().getColor(R.color.pressed));
                }
                if (millisUntilFinished / 1000 == 0) {
                    tvGuessNumber.setBackgroundColor(getResources().getColor(R.color.pressed));
                }
            }

            public void onFinish() {
                startActivity(intent);
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
    }
}