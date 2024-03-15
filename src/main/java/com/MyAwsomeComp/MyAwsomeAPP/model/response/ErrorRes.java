package com.MyAwsomeComp.MyAwsomeAPP.model.response;

public class ErrorRes {
    public Boolean success;
    public String message;

    public ErrorRes(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
