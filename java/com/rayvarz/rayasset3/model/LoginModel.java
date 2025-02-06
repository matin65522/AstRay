package com.rayvarz.rayasset3.model;

public class LoginModel {
    private final String username;
    private final String password;

    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isValid() {
        return "admin".equals(username) && "1".equals(password);
    }
}