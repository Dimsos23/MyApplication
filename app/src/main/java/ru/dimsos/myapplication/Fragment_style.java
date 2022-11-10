package ru.dimsos.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_style, null);

        imCloseWindowStyle = (ImageButton) view.findViewById(R.id.imCloseWindowStyle);
        imCloseWindowStyle.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        fragmentTransaction = getFragmentManager().beginTransaction();
        if (v.getId() == R.id.imCloseWindowStyle) {
            fragmentTransaction.remove(this);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentTransaction.commit();
        }
    }
}
