package ru.dimsos.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;


public class Fragment_account extends Fragment implements View.OnClickListener {

    String level = "0";

    SharedPreferences sPref;

    static Button btnEnter;
    static EditText edTextName, edTextPassword;
    ImageButton imCloseWindow;
    FragmentTransaction fragmentTransaction;
    static TextInputLayout tilName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_new, null);

        imCloseWindow = view.findViewById(R.id.imCloseWindow);
        imCloseWindow.setOnClickListener(this);

        btnEnter = view.findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(this);
        btnEnter.setEnabled(false);

        edTextName = view.findViewById(R.id.edTextName);
        edTextName.setFilters(new InputFilter[]{filter});

        edTextPassword = view.findViewById(R.id.edTextPassword);

        tilName = view.findViewById(R.id.textInputLayout);

        // Данные для внутреннего класса CustomTextWatcher
        EditText[] edList = {edTextName, edTextPassword};
        CustomTextWatcher textWatcher = new CustomTextWatcher(edList, btnEnter);
        for (EditText editText : edList) editText.addTextChangedListener(textWatcher);

        return view;
    }

    // Создаем фильтр, чтобы нельзя было вводить пробел в edTextName
    InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (isCharAllowed(c)) // put your condition here
                    sb.append(c);
                else
                    keepOriginal = false;
            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }

        private boolean isCharAllowed(char c) {
            return c != ' ';
        }
    };

    void saveText() {
        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(Constant.SAVED_NAME, edTextName.getText().toString());
        ed.putString(Constant.SAVED_LEVEL, level);
        ed.apply();
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
                    if (MainActivity.listUser.containsKey(user)) {
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

        Snackbar snackbar = Snackbar.make(btnEnter, "Успешный вход пользователя", 2000);
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
                        level = MainActivity.listUser.get(name);
                        MainActivity.tvCurrentAccount.setText(name);
                        MainActivity.tvLevelMind.setText(level);
                        saveText();
                        closeFragWindow();
                    } else {
                        snackbar.setText("Не верный пароль или имя пользователя!");
                        snackbar.show();
                    }
                }
                if (btnEnter.getText().equals("Register")) {
                    level = "0";
                    MainActivity.dbManager.insertDatabase(name, password, level);
                    MainActivity.dbManager.readDatabase();
                    String userLevel = MainActivity.listUser.get(name);
                    MainActivity.tvCurrentAccount.setText(name);
                    MainActivity.tvLevelMind.setText(userLevel);
                    saveText();
                    snackbar.setText("Регистрация прошла успешно");
                    snackbar.show();
                    closeFragWindow();
                }
                break;
        }
    }
}
