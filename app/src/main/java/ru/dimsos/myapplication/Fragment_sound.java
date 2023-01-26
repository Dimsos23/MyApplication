package ru.dimsos.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.preference.PreferenceManager;

import com.google.android.exoplayer2.upstream.RawResourceDataSource;


public class Fragment_sound extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String LOG_TAG = "myLogs";
    SharedPreferences sPrefMusic;

    FragmentTransaction fragmentTransaction;
    ImageButton imCloseWindowSound;
    SwitchCompat switchClick, switchMusic;

    public Fragment_sound() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sound, null);
        imCloseWindowSound = (ImageButton) view.findViewById(R.id.imCloseWindowSound);
        imCloseWindowSound.setOnClickListener(this);

        switchClick = view.findViewById(R.id.switchClick);
        switchClick.setOnCheckedChangeListener(this);
        switchMusic = view.findViewById(R.id.switchMusic);
        switchMusic.setOnCheckedChangeListener(this);
        return view;
    }

    void saveTrackPref() {
        Log.d(LOG_TAG, "save SharedPreference StartActivity");
        sPrefMusic = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        SharedPreferences.Editor editor = sPrefMusic.edit();
        editor.putString(Constant.SAVED_TRACK, String.valueOf(StartActivity.startUriMusic));
        editor.apply();

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
                } else {
                    StartActivity.stopExoPlayer();
                }
                break;
            case R.id.switchClick:
                MainActivity.switchSnapState = switchToggle.isChecked();
                break;
        }
    }
}
