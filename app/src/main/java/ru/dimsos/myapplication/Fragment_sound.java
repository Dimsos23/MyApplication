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
    Button btnSoundtrack;
    SwitchCompat switchClick, switchMusic;

    PopupMenu popup;

    public Fragment_sound() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sound, null);
        imCloseWindowSound = (ImageButton) view.findViewById(R.id.imCloseWindowSound);
        imCloseWindowSound.setOnClickListener(this);

        btnSoundtrack = view.findViewById(R.id.soundtrack);
        btnSoundtrack.setOnClickListener(this);

        switchClick = view.findViewById(R.id.switchClick);
        switchClick.setOnCheckedChangeListener(this);
        switchMusic = view.findViewById(R.id.switchMusic);
        switchMusic.setOnCheckedChangeListener(this);

        popup = new PopupMenu(getActivity(), btnSoundtrack);
        popup.getMenu().add(Menu.CATEGORY_CONTAINER, 0, Menu.NONE, "music");
        popup.getMenu().add(Menu.NONE, 1, Menu.NONE, "track2");
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case 0:
                        if (switchMusic.isChecked()) {
                            StartActivity.playExoPlayer(StartActivity.mediaItem1);
                            StartActivity.startTrack = StartActivity.mediaItem1;
//                            StartActivity.numberSaveTrack = "1";
                            StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.music);
                            saveTrackPref();
                            Log.d(LOG_TAG, "Save sPref in Fragment sound");
                        }
                        break;
                    case 1:
                        if (switchMusic.isChecked()) {
                            StartActivity.playExoPlayer(StartActivity.mediaItem2);
//                            StartActivity.numberSaveTrack = "2";
                            StartActivity.startTrack = StartActivity.mediaItem2;
                            StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.track1);
                            saveTrackPref();
                            Log.d(LOG_TAG, "Save sPref in Fragment sound");
                        }
                        break;
                }
                return true;
            }
        });

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
            case R.id.soundtrack:
                popup.show();
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
