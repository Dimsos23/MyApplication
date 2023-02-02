package ru.dimsos.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.preference.PreferenceManager;


public class Fragment_sound extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    SharedPrefsHelper sPref;
    FragmentTransaction fragmentTransaction;
    ImageButton imCloseWindowSound;
    SwitchCompat switchClick, switchMusic;
    String stateMusic;

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

        checkStateMusic();

        return view;
    }

    void checkStateMusic() {
        stateMusic = sPref.getString(Constant.SAVED_SWITCH_MUSIC);
        if (stateMusic.equals("") || stateMusic.equals("On")) {
            switchMusic.setChecked(true);
        } else {
            switchMusic.setChecked(false);
        }
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
        switch (switchToggle.getId()) {
            case R.id.switchMusic:
                if (switchToggle.isChecked()) {
                    StartActivity.playExoPlayer(StartActivity.startTrack);
                    sPref.putString(Constant.SAVED_SWITCH_MUSIC, "On");
                } else {
                    sPref.putString(Constant.SAVED_SWITCH_MUSIC, "Off");
                    StartActivity.stopExoPlayer();
                }
                break;
            case R.id.switchClick:
                MainActivity.switchSnapState = switchToggle.isChecked();
                break;
        }
    }
}
