package ru.dimsos.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "my_database.db";
    public static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constant.TABLE_STRUCTURE);
        ContentValues values = new ContentValues();
        values.put(Constant.USER_NAME, "Admin");
        values.put(Constant.USER_PASSWORD, "0000");
        values.put(Constant.USER_LEVEL, "0");
        db.insert(Constant.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constant.DROP_TABLE);
        onCreate(db);
    }
}
