package ru.dimsos.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;


public class Fragment_sound extends Fragment implements View.OnClickListener {

    Button btnBackGroundMusic, btnMusic, btnClick;
    TextView tvMusic, tvClick;
    FragmentTransaction fragmentTransaction;
    ImageButton imCloseWindowSound;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sound, null);

        tvMusic =(TextView) view.findViewById(R.id.tvMusic);
        tvClick =(TextView) view.findViewById(R.id.tvClick);

        imCloseWindowSound = (ImageButton) view.findViewById(R.id.imCloseWindowSound);
        imCloseWindowSound.setOnClickListener(this);

        btnBackGroundMusic = (Button) view.findViewById(R.id.btnBackGroundMusic);
        btnMusic = (Button) view.findViewById(R.id.btnMusic);
        btnClick = (Button) view.findViewById(R.id.btnClick);

        btnBackGroundMusic.setOnClickListener(this);
        btnMusic.setOnClickListener(this);
        btnClick.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        fragmentTransaction = getFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.imCloseWindowSound:
                fragmentTransaction.remove(this);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentTransaction.commit();
                break;
            default:
                break;
        }

    }
}
