package ru.dimsos.myapplication;

public class Constant {

    final static String SAVED_NAME = "saved_name";
    final static String SAVED_LEVEL = "saved_level";
    final static String SAVED_RADIO = "saved_radioButton";

    public static final String TABLE_NAME = "users";
    public static final String _ID = "_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_LEVEL = "user_level";

    public static final String TABLE_STRUCTURE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY," + USER_NAME + " TEXT," + USER_PASSWORD + " TEXT,"
             + USER_LEVEL + " TEXT)";
    public static final String DROP_TABLE = "DROP TABLE IF EXIST " + TABLE_NAME;
}
