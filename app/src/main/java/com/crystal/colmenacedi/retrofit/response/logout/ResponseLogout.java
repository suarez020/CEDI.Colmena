package com.crystal.colmenacedi.retrofit.response.logout;

import com.crystal.colmenacedi.models.Errors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogout {
    @SerializedName("logout")
    @Expose
    private Logout logout;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    public ResponseLogout(Logout logout, Errors errors) {
        this.logout = logout;
        this.errors = errors;
    }

    public Logout getLogout() {
        return logout;
    }

    public void setLogout(Logout logout) {
        this.logout = logout;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ResponseLogout{" +
                "logout=" + logout +
                ", errors=" + errors +
                '}';
    }
}
