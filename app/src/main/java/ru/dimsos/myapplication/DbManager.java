package ru.dimsos.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

     public Boolean insertDatabase(String userName, String password, String level) {
          ContentValues cv = new ContentValues();
          cv.put(Constant.USER_NAME, userName);
          cv.put(Constant.USER_PASSWORD, password);
          cv.put(Constant.USER_LEVEL, level);
          long result = db.insert(Constant.TABLE_NAME, null, cv);
          return result != -1;
     }

     public List<String> readDatabase() {

          Cursor cursor = db.query(Constant.TABLE_NAME, null, null, null, null, null, null);
          while (cursor.moveToNext()) {
               @SuppressLint("Range") String nameUser = cursor.getString(cursor.getColumnIndex(Constant.USER_NAME));
               Fragment_account.listUser.add(nameUser);
          }
          cursor.close();

          return Fragment_account.listUser;
     }

     public Boolean chekPassword(String password) {
          Cursor c = db.rawQuery("Select * from users where user_password = ?", new String[] {password});
//          c.close();
          return c.getCount() > 0;
     }
}
