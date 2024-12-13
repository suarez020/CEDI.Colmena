package com.crystal.colmenacedi.retrofit.response.ubicacion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ubicacion {
    @SerializedName("status")
    @Expose
    private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Ubicacion(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "status=" + status +
                '}';
    }
}
