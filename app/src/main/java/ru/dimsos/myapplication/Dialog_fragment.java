package ru.dimsos.myapplication;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

public class Dialog_fragment extends DialogFragment implements View.OnClickListener {

    private MediaPlayer sound;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("dialog");
        View v = inflater.inflate(R.layout.dialog_fragment, null);
        v.findViewById(R.id.btnMenu).setOnClickListener(this);
        v.findViewById(R.id.btnNewGame).setOnClickListener(this);
        v.findViewById(R.id.btnExitGame).setOnClickListener(this);
        sound = MediaPlayer.create(getActivity(), R.raw.beng);
        sound.start();
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMenu:
                MainActivity.playSoundPoolSnap(MainActivity.soundIdSnap);
                Intent mainIntent = new Intent(getActivity(), MainActivity.class);
//                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //Флаг для того,
                // чтобы нельзя было из MainActivity вернуться в другие Activity нажимая кнопку back
//               очищает stack активностей.
                startActivity(mainIntent);
                getDialog().cancel(); // Для правного перехода на Activity
                break;
            case R.id.btnNewGame:
                MainActivity.playSoundPoolSnap(MainActivity.soundIdSnap);
                TwoActivity.max = 10;
                Intent twoIntent = new Intent(getActivity(), TwoActivity.class);
                twoIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(twoIntent);
                getDialog().cancel();  // Для правного перехода на Activity
                break;
            case R.id.btnExitGame:
                MainActivity.playSoundPoolSnap(MainActivity.soundIdSnap);
                getActivity().finishAffinity();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
