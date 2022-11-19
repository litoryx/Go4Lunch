package com.example.go4lunch.models;

import androidx.annotation.Nullable;

public class User {

    private String uid;
    private String username;
    private String style;
    private String resto;

    public User() { }

    public User(String uid, String username, String style, String resto) {
        this.uid = uid;
        this.username = username;
        this.style = style;
        this.resto = resto;
    }

    public String getResto() {
        return resto;
    }

    public void setResto(String resto) {
        this.resto = resto;
    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getUsername() { return username; }
    public String getStyle() { return style; }
    // --- SETTERS ---
    public void setUsername(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setStyle(String style) { this.style = style; }
}
