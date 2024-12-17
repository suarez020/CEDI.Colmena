package com.crystal.colmenacedi.retrofit.response.logout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Logout {
    @SerializedName("status")
    @Expose
    private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Logout(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Logout{" +
                "status=" + status +
                '}';
    }
}
