package ru.dimsos.myapplication;

public class Constant {

    final static String SAVED_NAME = "saved_name";
    final static String SAVED_LEVEL = "saved_level";
    final static String SAVED_USER_ID = "saved_user_id";
    final static String SAVED_TRACK = "saved_track";
    final static String SAVED_RADIO = "saved_radioButton";

    public static final String TABLE_NAME = "users";
    public static final String _ID = "_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_LEVEL = "user_level";

    public static final String TABLE_STRUCTURE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY," + USER_NAME + " TEXT NOT NULL," + USER_PASSWORD + " TEXT NOT NULL,"
             + USER_LEVEL + " TEXT NOT NULL)";
    public static final String DROP_TABLE = "DROP TABLE IF EXIST " + Constant.TABLE_NAME;
}
