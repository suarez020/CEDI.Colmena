package com.crystal.colmenacedi.retrofit.response.finPinado;

import com.crystal.colmenacedi.models.Errors;
import com.crystal.colmenacedi.models.Pinado;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaFinPinado{
    @SerializedName("finpinado")
    private Pinado finPinado;

    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    @SerializedName("Voz")
    @Expose
    private String voz;

    @SerializedName("Error")
    @Expose
    private Errors error;

    public RespuestaFinPinado(Pinado finPinado, String mensaje, String voz, Errors error) {
        this.finPinado = finPinado;
        this.mensaje = mensaje;
        this.voz = voz;
        this.error = error;
    }

    public Pinado getFinPinado() {
        return finPinado;
    }

    public void setFinPinado(Pinado finPinado) {
        this.finPinado = finPinado;
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
        return "RespuestaFinPinado{" +
                "finPinado=" + finPinado +
                ", mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", error=" + error +
                '}';
    }
}
