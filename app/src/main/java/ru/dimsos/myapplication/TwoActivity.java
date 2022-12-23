package ru.dimsos.myapplication;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TwoActivity extends AppCompatActivity implements View.OnClickListener {

    SoundPool soundPool;
    int soundIdPressed;
    int soundIdNotPressed;

    CountDownTimer timer;
    Dialog_fragment dialog_fragment;

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    TextView tvGuessNumber;
    TextView tvTimer;

    public static Integer levelMind = 0;
    int currentInt = 0;
    int min = 1;
    static int max = 10;
    Integer number = 10;
    static long countDownPeriod = 11000;
    static long addCountDownPeriod = 4000;
    boolean[] buttonsGameStatus = new boolean[] {false, false, false, false, false, false, false, false, false};
    Button[] buttonsGame;
    int checkIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two);
        setTitle("");
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundIdPressed = soundPool.load(this, R.raw.click4, 1);
        soundIdNotPressed = soundPool.load(this, R.raw.click5, 1);

        dialog_fragment = new Dialog_fragment();

        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        btn6 = findViewById(R.id.button6);
        btn7 = findViewById(R.id.button7);
        btn8 = findViewById(R.id.button8);
        btn9 = findViewById(R.id.button9);

        buttonsGame = new Button[] {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};

        tvTimer = findViewById(R.id.tvTimer);
        tvGuessNumber = findViewById(R.id.tvResult);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);

        checkStateFragmentLevel();

        startGame();
    }

    public void playSoundPool(int soundID) {
        soundPool.play(soundID, 1, 1, 0, 0, 1);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        timer.cancel();
        recreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.dbManager.updateLevel();
        timer.cancel();
    }

    // Метод для уровня игры hard.
    public void checkResultHardLevel(int index) {
        if (MainActivity.savedRadioButtonLevelGame.equals("hard")) {
            if (currentInt > number) {
                buttonsGame[index].setBackgroundColor(getResources().getColor(R.color.pink));
                timer.cancel();
                dialog_fragment.show(getFragmentManager(), "dialog");
            }
        }
    }

    public void startGame() {

        checkIndex = 0;
        currentInt = 0;
        max = 10;

        if (levelMind >= 10) max += 2;

        if (levelMind >= 15) max += 2;

        if (levelMind >= 20) max += 2;

        String strNumber = number.toString();
        tvGuessNumber.setText(strNumber);

        List<Integer> RandomNumbers = checkCorrectGenerationRandomNumbers();

        Animation animButton = AnimationUtils.loadAnimation(this, R.anim.my_scale);

        startTimer();

        // Обнуляем список  btnCheck
        for (Button button : buttonsGame) {
            button.setBackgroundColor(getResources().getColor(R.color.not_pressed));
            Arrays.fill(buttonsGameStatus, false);
        }

        // Инициализируем кнопки данными из checkList()
        for (int i = 0; i < buttonsGame.length; i++) {
            buttonsGame[i].setText(String.valueOf(RandomNumbers.get(i)));
        }

        for (Button button : buttonsGame) {
            button.startAnimation(animButton);
        }
    }

    // Метод проверяющий корректную генерацию чисел для кнопок
    public List<Integer> checkCorrectGenerationRandomNumbers() {
        int sum;
        List<Integer> randomNumbers = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            double randomNumber = (Math.random() * (max - min)) + min;
            randomNumbers.add((int) randomNumber);
        }
        // Проверяем, чтобы в списке рандомных чисел была сумма числа, которое мы отгадываем.
        for (int i = 0; i < randomNumbers.size(); i++) {
            sum = randomNumbers.get(i);
            for (int j = 1; j < randomNumbers.size(); j++) {
                sum += randomNumbers.get(j);
                if (sum == Integer.parseInt(tvGuessNumber.getText().toString())) {
                    return randomNumbers;
                }
            }
        }
        return checkCorrectGenerationRandomNumbers();
    }

    public void startTimer() {
        timer = new CountDownTimer(countDownPeriod, 1000) {

            public void onTick(long millisUntilFinished) {
                String str = " " + millisUntilFinished / 1000;
                tvTimer.setText(str);
            }

            public void onFinish() {
                dialog_fragment.setCancelable(false); // позволяет диалогу не реагировать на лишние нажатия
                dialog_fragment.show(getFragmentManager(), "dialog");
            }
        }.start();
    }

    public void checkStateFragmentLevel() {
        if (MainActivity.savedRadioButtonLevelGame.equals("easy")) {
            countDownPeriod = 11000;
            addCountDownPeriod = 4000;
        }
        if (MainActivity.savedRadioButtonLevelGame.equals("medium")) {
            countDownPeriod = 8000;
            addCountDownPeriod = 3000;
        }
        if (MainActivity.savedRadioButtonLevelGame.equals("hard")) {
            countDownPeriod = 7000;
            addCountDownPeriod = 3000;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                if (!buttonsGameStatus[0]) {
                    playSoundPool(soundIdPressed);
                    currentInt = currentInt + Integer.parseInt(btn1.getText().toString());
                    btn1.setBackgroundColor(getResources().getColor(R.color.pressed));
                    checkResultHardLevel(0);
                    buttonsGameStatus[0] = true;
                } else {
                    playSoundPool(soundIdNotPressed);
                    btn1.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn1.getText().toString());
                    buttonsGameStatus[0] = false;
                }
                break;
            case R.id.button2:
                if (!buttonsGameStatus[1]) {
                    playSoundPool(soundIdPressed);
                    currentInt = currentInt + Integer.parseInt(btn2.getText().toString());
                    btn2.setBackgroundColor(getResources().getColor(R.color.pressed));
                    checkResultHardLevel(1);
                    buttonsGameStatus[1] = true;
                } else {
                    playSoundPool(soundIdNotPressed);
                    btn2.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn2.getText().toString());
                    buttonsGameStatus[1] = false;
                }
                break;
            case R.id.button3:
                if (!buttonsGameStatus[2]) {
                    playSoundPool(soundIdPressed);
                    currentInt = currentInt + Integer.parseInt(btn3.getText().toString());
                    btn3.setBackgroundColor(getResources().getColor(R.color.pressed));
                    checkResultHardLevel(2);
                    buttonsGameStatus[2] = true;
                } else {
                    playSoundPool(soundIdNotPressed);
                    btn3.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn3.getText().toString());
                    buttonsGameStatus[2] = false;
                }
                break;
            case R.id.button4:
                if (!buttonsGameStatus[3]) {
                    playSoundPool(soundIdPressed);
                    currentInt = currentInt + Integer.parseInt(btn4.getText().toString());
                    btn4.setBackgroundColor(getResources().getColor(R.color.pressed));
                    checkResultHardLevel(3);
                    buttonsGameStatus[3] = true;
                } else {
                    playSoundPool(soundIdNotPressed);
                    btn4.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn4.getText().toString());
                    buttonsGameStatus[3] = false;
                }
                break;
            case R.id.button5:
                if (!buttonsGameStatus[4]) {
                    playSoundPool(soundIdPressed);
                    currentInt = currentInt + Integer.parseInt(btn5.getText().toString());
                    btn5.setBackgroundColor(getResources().getColor(R.color.pressed));
                    checkResultHardLevel(4);
                    buttonsGameStatus[4] = true;
                } else {
                    playSoundPool(soundIdNotPressed);
                    btn5.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn5.getText().toString());
                    buttonsGameStatus[4] = false;
                }
                break;
            case R.id.button6:
                if (!buttonsGameStatus[5]) {
                    playSoundPool(soundIdPressed);
                    currentInt = currentInt + Integer.parseInt(btn6.getText().toString());
                    btn6.setBackgroundColor(getResources().getColor(R.color.pressed));
                    checkResultHardLevel(5);
                    buttonsGameStatus[5] = true;
                } else {
                    playSoundPool(soundIdNotPressed);
                    btn6.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn6.getText().toString());
                    buttonsGameStatus[5] = false;
                }
                break;
            case R.id.button7:
                if (!buttonsGameStatus[6]) {
                    playSoundPool(soundIdPressed);
                    currentInt = currentInt + Integer.parseInt(btn7.getText().toString());
                    btn7.setBackgroundColor(getResources().getColor(R.color.pressed));
                    checkResultHardLevel(6);
                    buttonsGameStatus[6] = true;
                } else {
                    playSoundPool(soundIdNotPressed);
                    btn7.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn7.getText().toString());
                    buttonsGameStatus[6] = false;
                }
                break;
            case R.id.button8:
                if (!buttonsGameStatus[7]) {
                    playSoundPool(soundIdPressed);
                    currentInt = currentInt + Integer.parseInt(btn8.getText().toString());
                    btn8.setBackgroundColor(getResources().getColor(R.color.pressed));
                    checkResultHardLevel(7);
                    buttonsGameStatus[7] = true;
                } else {
                    playSoundPool(soundIdNotPressed);
                    btn8.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn8.getText().toString());
                    buttonsGameStatus[7] = false;
                }
                break;
            case R.id.button9:
                if (!buttonsGameStatus[8]) {
                    playSoundPool(soundIdPressed);
                    currentInt = currentInt + Integer.parseInt(btn9.getText().toString());
                    btn9.setBackgroundColor(getResources().getColor(R.color.pressed));
                    checkResultHardLevel(8);
                    buttonsGameStatus[8] = true;
                } else {
                    playSoundPool(soundIdNotPressed);
                    btn9.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn9.getText().toString());
                    buttonsGameStatus[8] = false;
                }
            default:
                break;
        }

        if (currentInt == number) {

            Animation animResult = AnimationUtils.loadAnimation(this, R.anim.my_rotate_scale);
            tvGuessNumber.startAnimation(animResult);

            number++;
            levelMind++;

            String strNewTimer = tvTimer.getText().toString().replace(" ", "");
            int intIntentNewTimer = Integer.parseInt(strNewTimer);
            countDownPeriod = (intIntentNewTimer * 1000L) + addCountDownPeriod;
            timer.cancel();

            startGame();
        }
    }
}
