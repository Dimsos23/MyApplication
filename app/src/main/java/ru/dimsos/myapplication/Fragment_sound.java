package ru.dimsos.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;


public class Fragment_sound extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    final String LOG_TAG = "myLogs";
    SharedPrefsHelper sPref;
    FragmentTransaction fragmentTransaction;
    ImageButton imCloseWindowSound;
    static SwitchCompat switchClick, switchMusic;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sound, null);
        sPref = new SharedPrefsHelper(getActivity());
        imCloseWindowSound = (ImageButton) view.findViewById(R.id.imCloseWindowSound);
        imCloseWindowSound.setOnClickListener(this);
        switchClick = view.findViewById(R.id.switchClick);
        switchClick.setOnCheckedChangeListener(this);
        switchMusic = view.findViewById(R.id.switchMusic);
        switchMusic.setOnCheckedChangeListener(this);
        checkStateClick();
        checkStateMusic();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.flagForSwitchMusicListener = true;
        checkStateMusic();
    }

    void checkStateClick() {
        switchClick.setChecked(sPref.getBoolean(Constant.SAVED_SWITCH_CLICK));
    }

    void checkStateMusic() {
        Log.d(LOG_TAG, "Сработал checkStateMusic() в Fragment_sound");
        switchMusic.setChecked(MainActivity.switchMusicState);
    }

    @Override
    public void onClick(View v) {
        fragmentTransaction = getFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.imCloseWindowSound:
                MainActivity.playSoundPoolSnap(MainActivity.soundIdSnap);
                fragmentTransaction.remove(this);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentTransaction.commit();
                break;
            case R.id.switchClick:
            case R.id.switchMusic:
                MainActivity.playSoundPoolSnap(MainActivity.soundIdSnap);
                break;
            default:
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton switchToggle, boolean b) {
        Log.d(LOG_TAG, "Сработал метод onCheckedChanger");
        switch (switchToggle.getId()) {
            case R.id.switchMusic:
                if (switchToggle.isChecked()) {
                    StartActivity.playExoPlayer(StartActivity.startTrack);
                    sPref.putBoolean(Constant.SAVED_SWITCH_MUSIC, true);
                } else {
                    sPref.putBoolean(Constant.SAVED_SWITCH_MUSIC, false);
                    StartActivity.stopExoPlayer();
                }
                if (MainActivity.flagForSwitchMusicListener) {
                    MainActivity.switchMusicState = switchToggle.isChecked();
                }
                break;
            case R.id.switchClick:
                sPref.putBoolean(Constant.SAVED_SWITCH_CLICK, switchToggle.isChecked());
                MainActivity.switchClickState = switchToggle.isChecked();
                break;
        }
    }
}
