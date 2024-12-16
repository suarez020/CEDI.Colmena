package com.crystal.colmenacedi.retrofit.response.terminar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Terminar {
    @SerializedName("status")
    @Expose
    private Boolean status;

    public Terminar(Boolean status) {
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
        return "Terminar{" +
                "status=" + status +
                '}';
    }
}
