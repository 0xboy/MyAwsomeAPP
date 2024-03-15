package com.MyAwsomeComp.MyAwsomeAPP.model.response;

public class LoginRes {

    public Boolean success;

    public String message;

    public UserRes user;

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(UserRes user) {
        this.user = user;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public UserRes getUser() {
        return user;
    }
}
