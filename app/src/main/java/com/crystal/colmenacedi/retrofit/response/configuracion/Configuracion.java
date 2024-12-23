package com.crystal.colmenacedi.retrofit.response.configuracion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Configuracion {
    @SerializedName("status")
    @Expose
    private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Configuracion(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Configuracion{" +
                "status=" + status +
                '}';
    }
}
