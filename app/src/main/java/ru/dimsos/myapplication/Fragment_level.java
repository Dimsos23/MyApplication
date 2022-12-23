package ru.dimsos.myapplication;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

public class Fragment_level extends Fragment implements View.OnClickListener {

    SharedPreferences sPref;
    static String stateRadioButton;

    ImageButton imCloseWindowLevel;
    FragmentTransaction fragmentTransaction;
    RadioGroup radioGroup;
    static RadioButton radioButtonEasy, radioButtonMedium, radioButtonHard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_level, null);

        loadText();

        imCloseWindowLevel = view.findViewById(R.id.imCloseWindowLevel);
        imCloseWindowLevel.setOnClickListener(this);

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
                        TwoActivity.countDownPeriod = 11000;
                        TwoActivity.addCountDownPeriod = 4000;
                        stateRadioButton = "easy";
                        break;
                    case R.id.radioButtonMedium:
                        MainActivity.playSoundPoolSnap(MainActivity.soundIdSnap);
                        TwoActivity.countDownPeriod = 9000;
                        TwoActivity.addCountDownPeriod = 3000;
                        stateRadioButton = "medium";
                        break;
                    case R.id.radioButtonHard:
                        MainActivity.playSoundPoolSnap(MainActivity.soundIdSnap);
                        TwoActivity.countDownPeriod = 8000;
                        TwoActivity.addCountDownPeriod = 3000;
                        stateRadioButton = "hard";
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
        saveText();
        fragmentTransaction = getFragmentManager().beginTransaction();
        if (view.getId() == R.id.imCloseWindowLevel) {
            fragmentTransaction.remove(this);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentTransaction.commit();
        }
    }

    void saveText() {
        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(Constant.SAVED_RADIO, stateRadioButton);
        ed.apply();
    }

    void loadText() {
        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        stateRadioButton = sPref.getString(Constant.SAVED_RADIO,"");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveText();
    }
}
