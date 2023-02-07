package ru.dimsos.myapplication;


import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;


public class SingleListFragment extends ListFragment {

    final String LOG_TAG = "myLogs";
    SharedPrefsHelper sPref;
    String[] soundtracks = new String[] {"music", "track2", "nanorobot-tune", "track4", "track5"};


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_list_item, null);
        sPref = new SharedPrefsHelper(getActivity());
        return view;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, soundtracks);
        setListAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.imageButtonClose.setVisibility(View.GONE);
    }

    void saveTrackPref() {
        sPref.putString(Constant.SAVED_TRACK, String.valueOf(StartActivity.startUriMusic));
    }

    void saveStateSwitchMusic() {
        Log.d(LOG_TAG, "Сработал saveStateSwitchMusic() в SingleListFragment");
        sPref.putBoolean(Constant.SAVED_SWITCH_MUSIC, true);
        MainActivity.switchMusicState = true;
        MainActivity.flagForSwitchMusicListener = false;
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                StartActivity.startTrack = StartActivity.mediaItem1;
                StartActivity.playExoPlayer(StartActivity.startTrack);
                StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.music);
                saveStateSwitchMusic();
                saveTrackPref();
                break;
            case 1:
                StartActivity.startTrack = StartActivity.mediaItem2;
                StartActivity.playExoPlayer(StartActivity.startTrack);
                StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.track1);
                saveStateSwitchMusic();
                saveTrackPref();
                break;
            case 2:
                StartActivity.startTrack = StartActivity.mediaItem3;
                StartActivity.playExoPlayer(StartActivity.startTrack);
                StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.nanorobot_tune);
                saveStateSwitchMusic();
                saveTrackPref();
                break;
            case 3:
                StartActivity.startTrack = StartActivity.mediaItem4;
                StartActivity.playExoPlayer(StartActivity.startTrack);
                StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.track4);
                saveStateSwitchMusic();
                saveTrackPref();
                break;
            case 4:
                StartActivity.startTrack = StartActivity.mediaItem5;
                StartActivity.playExoPlayer(StartActivity.startTrack);
                StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.track5);
                saveStateSwitchMusic();
                saveTrackPref();
                break;
        }
    }
}
