package com.crystal.colmenacedi.retrofit.response.loginGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginGet {
    @SerializedName("status")
    @Expose
    private Boolean status;

    public LoginGet(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginGet{" +
                "status=" + status +
                '}';
    }
}
