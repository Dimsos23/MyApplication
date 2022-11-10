package ru.dimsos.myapplication;

public class Constant {

    public static final String TABLE_NAME = "users";
    public static final String _ID = "_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_LEVEL = "user_level";

    public static final String TABLE_STRUCTURE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY," + USER_NAME + " TEXT," + USER_PASSWORD + " TEXT,"
             + USER_LEVEL + " TEXT)";
    public static final String TABLE_STRUCTURE2 = "CREATE TABLE " + TABLE_NAME + " (" +
             USER_NAME + " TEXT PRIMARY KEY," + USER_PASSWORD + " TEXT,"
            + USER_LEVEL + " TEXT)";
    public static final String DROP_TABLE = "DROP TABLE IF EXIST " + TABLE_NAME;
}
