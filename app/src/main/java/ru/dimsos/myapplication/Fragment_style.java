package ru.dimsos.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

public class Fragment_style extends Fragment implements View.OnClickListener {

    ImageButton imCloseWindowStyle;
    FragmentTransaction fragmentTransaction;
    Button btnExit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_style, null);

        imCloseWindowStyle = (ImageButton) view.findViewById(R.id.imCloseWindowStyle);
        imCloseWindowStyle.setOnClickListener(this);

        btnExit = view.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        fragmentTransaction = getFragmentManager().beginTransaction();
        if (v.getId() == R.id.imCloseWindowStyle) {
            MainActivity.playSoundPoolSnap(MainActivity.soundIdSnap);
            fragmentTransaction.remove(this);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentTransaction.commit();
        }
        if (v.getId() == R.id.btnExit) {
            MainActivity.playSoundPoolSnap(MainActivity.soundIdSnap);
            fragmentTransaction.remove(this);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentTransaction.commit();
        }
    }
}
