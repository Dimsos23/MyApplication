package ru.dimsos.myapplication;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

public class Fragment_level extends Fragment implements View.OnClickListener {

    SharedPrefsHelper sPrefLevelState;
    static String stateRadioButton;

    ImageButton imCloseWindowLevel;
    ImageView imageViewEasy, imageViewMedium, imageViewHard;
    FragmentTransaction fragmentTransaction;
    RadioGroup radioGroup;
    static RadioButton radioButtonEasy, radioButtonMedium, radioButtonHard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_level, null);

        imCloseWindowLevel = view.findViewById(R.id.imCloseWindowLevel);
        imCloseWindowLevel.setOnClickListener(this);

//        Snackbar snackbar = Snackbar.make(imageViewEasy, "Успешный вход пользователя", 1700);
//        snackbar.setAnchorView(imCloseWindowLevel);
//        snackbar.setTextColor(getResources().getColor(R.color.letters_gray));
//        snackbar.setBackgroundTint(getResources().getColor(R.color.frag_color));

        sPrefLevelState = new SharedPrefsHelper(getActivity());
        stateRadioButton = sPrefLevelState.getString(Constant.SAVED_RADIO);

        imageViewEasy = view.findViewById(R.id.imageViewEasy);
        imageViewEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(imCloseWindowLevel,
                        "Изначально дается 10 секунд и +4 за правильный ответ.", 4000);
                snackbar.setAnchorView(imCloseWindowLevel);
                snackbar.setTextColor(getResources().getColor(R.color.black));
                snackbar.setBackgroundTint(getResources().getColor(R.color.frag_color));
                snackbar.show();
            }
        });
        imageViewMedium = view.findViewById(R.id.imageViewMedium);
        imageViewMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(imCloseWindowLevel,
                        "Изначально дается 7 секунд и +3 за правильный ответ.", 4000);
                snackbar.setAnchorView(imCloseWindowLevel);
                snackbar.setTextColor(getResources().getColor(R.color.black));
                snackbar.setBackgroundTint(getResources().getColor(R.color.frag_color));
                snackbar.show();
            }
        });
        imageViewHard = view.findViewById(R.id.imageViewHard);
        imageViewHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(imCloseWindowLevel,
                        "Изначально дается 6 секунд, +3 за верный ответ и вы не имеете право на ошибку.", 4000);
                snackbar.setAnchorView(imCloseWindowLevel);
                snackbar.setTextColor(getResources().getColor(R.color.black));
                snackbar.setBackgroundTint(getResources().getColor(R.color.frag_color));
                snackbar.show();
            }
        });

        radioButtonEasy = view.findViewById(R.id.radioButtonEasy);
        radioButtonMedium = view.findViewById(R.id.radioButtonMedium);
        radioButtonHard = view.findViewById(R.id.radioButtonHard);

        if (stateRadioButton.equals("easy"))  radioButtonEasy.setChecked(true);
        if (stateRadioButton.equals("medium")) radioButtonMedium.setChecked(true);
        if (stateRadioButton.equals("hard")) radioButtonHard.setChecked(true);

        radioGroup = view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonEasy:
                        MainActivity.playSoundPoolSnap(MainActivity.soundIdSnap);
                        stateRadioButton = "easy";
                        sPrefLevelState.putString(Constant.SAVED_RADIO, stateRadioButton);
                        break;
                    case R.id.radioButtonMedium:
                        MainActivity.playSoundPoolSnap(MainActivity.soundIdSnap);
                        stateRadioButton = "medium";
                        sPrefLevelState.putString(Constant.SAVED_RADIO, stateRadioButton);
                        break;
                    case R.id.radioButtonHard:
                        MainActivity.playSoundPoolSnap(MainActivity.soundIdSnap);
                        stateRadioButton = "hard";
                        sPrefLevelState.putString(Constant.SAVED_RADIO, stateRadioButton);
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        MainActivity.playSoundPoolSnap(MainActivity.soundIdSnap);
        fragmentTransaction = getFragmentManager().beginTransaction();
        if (view.getId() == R.id.imCloseWindowLevel) {
            fragmentTransaction.remove(this);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
