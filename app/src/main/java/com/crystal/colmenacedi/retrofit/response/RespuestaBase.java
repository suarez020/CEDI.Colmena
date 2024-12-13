package com.crystal.colmenacedi.retrofit.response;

import com.crystal.colmenacedi.models.Errors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaBase {
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;
    @SerializedName("Voz")
    @Expose
    private String voz;
    @SerializedName("Error")
    @Expose
    private Errors error;

    public RespuestaBase(String mensaje, String voz, Errors error) {
        this.mensaje = mensaje;
        this.voz = voz;
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getVoz() {
        return voz;
    }

    public void setVoz(String voz) {
        this.voz = voz;
    }

    public Errors getError() {
        return error;
    }

    public void setError(Errors error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RespuestaBase{" +
                "mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", error=" + error +
                '}';
    }
}
