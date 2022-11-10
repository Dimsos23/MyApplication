package ru.dimsos.myapplication;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;


public class TwoActivity extends AppCompatActivity implements View.OnClickListener {

    CountDownTimer timer;
    Dialog_fragment dialog_fragment;

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    TextView tvResult;
    TextView tvTimer;

    private MediaPlayer onSound, offSound;
    public static Integer levelMind = 0;
    int currentInt = 0;
    int min = 1;
    static int max = 10;
    Integer number = 10;
    long countDownPeriod = 13000;
    Button[] btn;
    boolean[] btnCheck = new boolean[]{false, false, false, false, false, false, false, false, false};
    int checkIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        dialog_fragment = new Dialog_fragment();

        onSound = MediaPlayer.create(this, R.raw.sound_on);
        offSound = MediaPlayer.create(this, R.raw.sound_off);

        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        btn6 = findViewById(R.id.button6);
        btn7 = findViewById(R.id.button7);
        btn8 = findViewById(R.id.button8);
        btn9 = findViewById(R.id.button9);

        tvTimer = findViewById(R.id.tvTimer);
        tvResult = findViewById(R.id.tvResult);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);

        btn = new Button[]{btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};

        startGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    public void startGame() {

        checkIndex = 0;
        currentInt = 0;
        int index = 0;
        max = 10;

        if (levelMind >= 10) max += 2;

        if (levelMind >= 15) max += 2;

        if (levelMind >= 20) max += 2;

        String strNumber = number.toString();
        tvResult.setText(strNumber);

        ArrayList<Integer> list = checkList();

        Animation animButton = AnimationUtils.loadAnimation(this, R.anim.my_scale);

        startTimer();

        int check = 0;

        // Обнуляем список  btnCheck
        for (Button button : btn) {
            button.setBackgroundColor(getResources().getColor(R.color.not_pressed));
            btnCheck[check] = false;
            check++;
        }

        // Инициализируем кнопки данными из checkList()
        for (Button button : btn) {
            button.setText(String.valueOf(list.get(index)));
            index++;

        }
        for (Button button : btn) {
            button.startAnimation(animButton);
        }
    }

    // Метод проверяющий корректную генерацию чисел для кнопок
    public ArrayList<Integer> checkList() {
        int sum;
        ArrayList<Integer> checkList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            double random = (Math.random() * (max - min)) + min;
            checkList.add((int) random);
        }

        for (int i = 0; i < checkList.size(); i++) {
            sum = checkList.get(i);
            for (int j = 1; j < checkList.size(); j++) {
                sum += checkList.get(j);
                if (sum == Integer.parseInt(tvResult.getText().toString())) {
                    return  checkList;
                }
            }
        }
        return checkList();
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
//                showDialog(DIALOG_END_GAME);
            }
        }.start();
    }

    public void SoundPlay(MediaPlayer sound) {
        sound.start();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                if (!btnCheck[0]) {
                    SoundPlay(onSound);
                    btn1.setBackgroundColor(getResources().getColor(R.color.pressed));
                    currentInt = currentInt + Integer.parseInt(btn1.getText().toString());
                    btnCheck[0] = true;
                } else {
                    SoundPlay(offSound);
                    btn1.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn1.getText().toString());
                    btnCheck[0] = false;
                }
                break;
            case R.id.button2:
                if (!btnCheck[1]) {
                    SoundPlay(onSound);
                    btn2.setBackgroundColor(getResources().getColor(R.color.pressed));
                    currentInt = currentInt + Integer.parseInt(btn2.getText().toString());
                    btnCheck[1] = true;
                } else {
                    SoundPlay(offSound);
                    btn2.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn2.getText().toString());
                    btnCheck[1] = false;
                }
                break;
            case R.id.button3:
                if (!btnCheck[2]) {
                    SoundPlay(onSound);
                    btn3.setBackgroundColor(getResources().getColor(R.color.pressed));
                    currentInt = currentInt + Integer.parseInt(btn3.getText().toString());
                    btnCheck[2] = true;
                } else {
                    SoundPlay(offSound);
                    btn3.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn3.getText().toString());
                    btnCheck[2] = false;
                }
                break;
            case R.id.button4:
                if (!btnCheck[3]) {
                    SoundPlay(onSound);
                    btn4.setBackgroundColor(getResources().getColor(R.color.pressed));
                    currentInt = currentInt + Integer.parseInt(btn4.getText().toString());
                    btnCheck[3] = true;
                } else {
                    SoundPlay(offSound);
                    btn4.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn4.getText().toString());
                    btnCheck[3] = false;
                }
                break;
            case R.id.button5:
                if (!btnCheck[4]) {
                    SoundPlay(onSound);
                    btn5.setBackgroundColor(getResources().getColor(R.color.pressed));
                    currentInt = currentInt + Integer.parseInt(btn5.getText().toString());
                    btnCheck[4] = true;
                } else {
                    SoundPlay(offSound);
                    btn5.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn5.getText().toString());
                    btnCheck[4] = false;
                }
                break;
            case R.id.button6:
                if (!btnCheck[5]) {
                    SoundPlay(onSound);
                    btn6.setBackgroundColor(getResources().getColor(R.color.pressed));
                    currentInt = currentInt + Integer.parseInt(btn6.getText().toString());
                    btnCheck[5] = true;
                } else {
                    SoundPlay(offSound);
                    btn6.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn6.getText().toString());
                    btnCheck[5] = false;
                }
                break;
            case R.id.button7:
                if (!btnCheck[6]) {
                    SoundPlay(onSound);
                    btn7.setBackgroundColor(getResources().getColor(R.color.pressed));
                    currentInt = currentInt + Integer.parseInt(btn7.getText().toString());
                    btnCheck[6] = true;
                } else {
                    SoundPlay(offSound);
                    btn7.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn7.getText().toString());
                    btnCheck[6] = false;
                }
                break;
            case R.id.button8:
                if (!btnCheck[7]) {
                    SoundPlay(onSound);
                    btn8.setBackgroundColor(getResources().getColor(R.color.pressed));
                    currentInt = currentInt + Integer.parseInt(btn8.getText().toString());
                    btnCheck[7] = true;
                } else {
                    SoundPlay(offSound);
                    btn8.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn8.getText().toString());
                    btnCheck[7] = false;
                }
                break;
            case R.id.button9:
                if (!btnCheck[8]) {
                    SoundPlay(onSound);
                    btn9.setBackgroundColor(getResources().getColor(R.color.pressed));
                    currentInt = currentInt + Integer.parseInt(btn9.getText().toString());
                    btnCheck[8] = true;
                } else {
                    SoundPlay(offSound);
                    btn9.setBackgroundColor(getResources().getColor(R.color.not_pressed));
                    currentInt = currentInt - Integer.parseInt(btn9.getText().toString());
                    btnCheck[8] = false;
                }
            default:
                break;
        }
        if (currentInt == number) {

            Animation animResult = AnimationUtils.loadAnimation(this, R.anim.my_rotate_scale);
            tvResult.startAnimation(animResult);

            number++;
            levelMind++;

            String strNewTimer = tvTimer.getText().toString().replace(" ", "");
            int intIntentNewTimer = Integer.parseInt(strNewTimer);
            countDownPeriod = (intIntentNewTimer * 1000L) + 4000;
            timer.cancel();

            startGame();
        }
    }
}
