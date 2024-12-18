package com.crystal.colmenacedi.retrofit.response.terminar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Terminar {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;

    public Terminar(Boolean status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "Terminar{" +
                "status=" + status +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
