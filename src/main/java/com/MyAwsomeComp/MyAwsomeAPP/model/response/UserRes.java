package com.MyAwsomeComp.MyAwsomeAPP.model.response;

public class UserRes {
    public String email;
    public String username;
    public String token;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
