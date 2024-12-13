package com.crystal.colmenacedi.retrofit.response.loginGet;
import com.crystal.colmenacedi.models.Errors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ResponseLoginGet {
    @SerializedName("login")
    @Expose
    private LoginGet loginGet;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    public ResponseLoginGet(LoginGet loginGet, Errors errors) {
        this.loginGet = loginGet;
        this.errors = errors;
    }

    public LoginGet getLoginGet() {
        return loginGet;
    }

    public void setLoginGet(LoginGet loginGet) {
        this.loginGet = loginGet;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ResponseLoginGet{" +
                "loginGet=" + loginGet +
                ", errors=" + errors +
                '}';
    }
}
