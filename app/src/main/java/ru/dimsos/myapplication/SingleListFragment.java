package ru.dimsos.myapplication;


import android.app.ListFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;


public class SingleListFragment extends ListFragment {

    SharedPreferences sPrefMusic;
    String[] soundtracks = new String[] {"music", "track2", "nanorobot-tune", "track4", "track5"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_list_item, null);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, soundtracks);
        setListAdapter(adapter);
    }

    void saveTrackPref() {
        sPrefMusic = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        SharedPreferences.Editor editor = sPrefMusic.edit();
        editor.putString(Constant.SAVED_TRACK, String.valueOf(StartActivity.startUriMusic));
        editor.apply();

    }

    public void closeFragment() {
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                StartActivity.playExoPlayer(StartActivity.mediaItem1);
                StartActivity.startTrack = StartActivity.mediaItem1;
                StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.music);
                saveTrackPref();
                break;
            case 1:
                StartActivity.playExoPlayer(StartActivity.mediaItem2);
                StartActivity.startTrack = StartActivity.mediaItem2;
                StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.track1);
                saveTrackPref();
                break;
            case 2:
                StartActivity.playExoPlayer(StartActivity.mediaItem3);
                StartActivity.startTrack = StartActivity.mediaItem3;
                StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.nanorobot_tune);
                StartActivity.playExoPlayer(StartActivity.startTrack);
                saveTrackPref();
                break;
            case 3:
                StartActivity.playExoPlayer(StartActivity.mediaItem4);
                StartActivity.startTrack = StartActivity.mediaItem4;
                StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.track4);
                StartActivity.playExoPlayer(StartActivity.startTrack);
                saveTrackPref();
                break;
            case 4:
                StartActivity.playExoPlayer(StartActivity.mediaItem5);
                StartActivity.startTrack = StartActivity.mediaItem5;
                StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.track5);
                StartActivity.playExoPlayer(StartActivity.startTrack);
                saveTrackPref();
                break;
        }
    }
}
