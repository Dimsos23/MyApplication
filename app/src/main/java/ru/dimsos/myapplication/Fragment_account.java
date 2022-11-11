package ru.dimsos.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fragment_account extends Fragment implements View.OnClickListener {

    static Button btnEnter, btnClear, btnRead;
    static EditText edTextName, edTextPassword;
    ImageButton imCloseWindow;
    FragmentTransaction fragmentTransaction;
    static TextInputLayout tilName;

    public static Map<String, String> listUser = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_new, null);

        imCloseWindow = view.findViewById(R.id.imCloseWindow);
        imCloseWindow.setOnClickListener(this);

        btnEnter = view.findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(this);
        btnEnter.setEnabled(false);

        btnClear = view.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnRead = view.findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        edTextName = view.findViewById(R.id.edTextName);
        edTextPassword = view.findViewById(R.id.edTextPassword);

        tilName = view.findViewById(R.id.textInputLayout);

        // Данные для внутреннего класса CustomTextWatcher
        EditText[] edList = {edTextName, edTextPassword};
        CustomTextWatcher textWatcher = new CustomTextWatcher(edList, btnEnter);
        for (EditText editText : edList) editText.addTextChangedListener(textWatcher);

        MainActivity.dbManager.readDatabase();

        return view;
    }

    // Внутренний класс для  проверки нависанного в EditText
    public static class CustomTextWatcher implements TextWatcher {

        View v;
        EditText[] edList;

        public CustomTextWatcher(EditText[] edList, Button v) {
            this.v = v;
            this.edList = edList;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String user = edTextName.getText().toString();
            for (EditText editText : edList) {
                if (editText.getText().toString().trim().length() <= 0) {
                    v.setEnabled(false);
                    break;
                } else {
                    // Смена названия кнопки
                    if (listUser.containsKey(user)) {
                        btnEnter.setText(R.string.enter);
                        tilName.setEndIconDrawable(R.drawable.ic_check);
                    } else {
                        btnEnter.setText(R.string.register);
                        tilName.setEndIconDrawable(R.drawable.ic_clear);
                    }
                    v.setEnabled(true);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void closeFragWindow() {
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.commit();

        edTextName.getText().clear();
        edTextPassword.getText().clear();
    }

    @Override
    public void onClick(View v) {
        String name = edTextName.getText().toString();
        String password = edTextPassword.getText().toString();
        String level = "0";

        Snackbar snackbar = Snackbar.make(btnClear, "Успешный вход пользователя", 2000);
        snackbar.setAnchorView(imCloseWindow);
        snackbar.setTextColor(getResources().getColor(R.color.letters_gray));
        snackbar.setBackgroundTint(getResources().getColor(R.color.frag_color));

        switch (v.getId()) {

            case R.id.imCloseWindow:
                closeFragWindow();
                break;
            case R.id.btnEnter:
                if (btnEnter.getText().equals("Enter")) {
                    Boolean check = MainActivity.dbManager.chekPassword(password);
                    if (check) {
                        snackbar.show();
                        String userLevel = listUser.get(name);
                        MainActivity.tvCurrentAccount.setText(name);
                        MainActivity.tvLevelMind.setText(userLevel);
                        closeFragWindow();
                    } else {
                        snackbar.setText("Не верный пароль или имя пользователя!");
                        snackbar.show();
                    }
                }
                if (btnEnter.getText().equals("Register")) {
                    MainActivity.dbManager.insertDatabase(name, password, level);
                    MainActivity.dbManager.readDatabase();
                    String userLevel = listUser.get(name);
                    MainActivity.tvCurrentAccount.setText(name);
                    MainActivity.tvLevelMind.setText(userLevel);
                    snackbar.setText("Регистрация прошла успешно");
                    snackbar.show();
                    closeFragWindow();
                }
                break;

            case R.id.btnRead:

            case R.id.btnClear:
                break;
        }
    }
}
