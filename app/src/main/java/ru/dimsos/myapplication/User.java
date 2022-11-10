package ru.dimsos.myapplication;

public class User {
    public final String name;
    public final String password;
    public final String level = "0";

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String level) {
        this.name = name;
        this.password = password;
    }
}
