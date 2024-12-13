package com.crystal.colmenacedi.retrofit.response.login;

import com.crystal.colmenacedi.models.Errors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("login")
    @Expose
    private Login login;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    public ResponseLogin(Login login, Errors errors) {
        this.login = login;
        this.errors = errors;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ResponseLogin{" +
                "login=" + login +
                ", errors=" + errors +
                '}';
    }
}
