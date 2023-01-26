package ru.dimsos.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbManager {
    Context context;
    private final DbHelper dbHelper;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDatabase() {
        db = dbHelper.getWritableDatabase();
    }

    public void closeDatabase() {
        dbHelper.close();
    }

    public void insertDatabase(String userName, String password, String level) {
        ContentValues cv = new ContentValues();
        cv.put(Constant.USER_NAME, userName);
        cv.put(Constant.USER_PASSWORD, password);
        cv.put(Constant.USER_LEVEL, level);
        db.insert(Constant.TABLE_NAME, null, cv);
    }

    // Метод для получения Id по имени
    @SuppressLint("Range")
    public String getIdFromDatabase(String name) {
        String id = null;
        try {
            String[] columns = {Constant._ID};
            String selection = "user_name = ?";
            String[] selectionArgs = {name};
            Cursor cursor = db.query(Constant.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                id = cursor.getString(cursor.getColumnIndex(Constant._ID));
            }
            if (cursor != null) cursor.close();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return id;
    }

    @SuppressLint("Range")
    public String updateNameFromDatabase(String id) {
        String name = null;
        try {
            String[] columns = {Constant.USER_NAME};
            String selection = "_id = ?";
            String[] selectionArgs = {id};
            Cursor cursor = db.query(Constant.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex(Constant.USER_NAME));
            }
            if (cursor != null) cursor.close();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return name;
    }

    @SuppressLint("Range")
    public String updateLevelFromDatabase(String id) {
        String level = null;
        try {
            String[] columns = {Constant.USER_LEVEL};
            String selection = "_id = ?";
            String[] selectionArgs = {id};
            Cursor cursor = db.query(Constant.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                level = cursor.getString(cursor.getColumnIndex(Constant.USER_LEVEL));
            }
            if (cursor != null) cursor.close();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return level;
    }

    public void fillListUsersFromDatabase() {
        try {
            String[] columns = {Constant.USER_NAME, Constant.USER_LEVEL};
            Cursor cursor = db.query(Constant.TABLE_NAME, columns, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(Constant.USER_NAME));
                    @SuppressLint("Range") String level = cursor.getString(cursor.getColumnIndex(Constant.USER_LEVEL));
                    MainActivity.listUser.put(name, level);
                } while (cursor.moveToNext());
            }
            if (cursor != null) cursor.close();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    public Boolean checkPassword(String password) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("Select * from users where user_password = ?", new String[]{password});
            return cursor.getCount() > 0;
        } finally {
            assert cursor != null;
            cursor.close();
        }

    }

    public void updateLevel() {
        String name = MainActivity.tvCurrentAccount.getText().toString();
        String level = TwoActivity.levelMind.toString();
        ContentValues cv = new ContentValues();
        cv.put(Constant.USER_LEVEL, level);
        db.update(Constant.TABLE_NAME, cv, "user_name = ?", new String[]{name});
    }
}
