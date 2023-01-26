package ru.dimsos.myapplication;


import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;


public class SingleListFragment extends ListFragment {

    SharedPrefsHelper sPref;
    String[] soundtracks = new String[] {"music", "track2", "nanorobot-tune", "track4", "track5"};

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

    void saveTrackPref() {
        sPref.putString(Constant.SAVED_TRACK, String.valueOf(StartActivity.startUriMusic));
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch (position) {
            case 0:
                StartActivity.startTrack = StartActivity.mediaItem1;
                StartActivity.playExoPlayer(StartActivity.startTrack);
                StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.music);
                saveTrackPref();
                break;
            case 1:
                StartActivity.startTrack = StartActivity.mediaItem2;
                StartActivity.playExoPlayer(StartActivity.startTrack);
                StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.track1);
                saveTrackPref();
                break;
            case 2:
                StartActivity.startTrack = StartActivity.mediaItem3;
                StartActivity.playExoPlayer(StartActivity.startTrack);
                StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.nanorobot_tune);
                StartActivity.playExoPlayer(StartActivity.startTrack);
                saveTrackPref();
                break;
            case 3:
                StartActivity.startTrack = StartActivity.mediaItem4;
                StartActivity.playExoPlayer(StartActivity.startTrack);
                StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.track4);
                StartActivity.playExoPlayer(StartActivity.startTrack);
                saveTrackPref();
                break;
            case 4:
                StartActivity.startTrack = StartActivity.mediaItem5;
                StartActivity.playExoPlayer(StartActivity.startTrack);
                StartActivity.startUriMusic = RawResourceDataSource.buildRawResourceUri(R.raw.track5);
                StartActivity.playExoPlayer(StartActivity.startTrack);
                saveTrackPref();
                break;
        }
    }
}
