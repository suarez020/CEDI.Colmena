package com.crystal.colmenacedi.retrofit.response.iniciaPinado;

import com.crystal.colmenacedi.models.Error;
import com.crystal.colmenacedi.models.Pinado;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaIniciaPinado {

    @SerializedName("iniciapinado")
    private Pinado iniciaPinado;

    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    @SerializedName("Voz")
    @Expose
    private String voz;

    @SerializedName("Error")
    @Expose
    private Error error;

    public RespuestaIniciaPinado(Pinado iniciaPinado, String mensaje, String voz, Error error) {
        this.iniciaPinado = iniciaPinado;
        this.mensaje = mensaje;
        this.voz = voz;
        this.error = error;
    }

    public Pinado getIniciaPinado() {
        return iniciaPinado;
    }

    public void setIniciaPinado(Pinado iniciaPinado) {
        this.iniciaPinado = iniciaPinado;
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

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RespuestaIniciaPinado{" +
                "iniciaPinado=" + iniciaPinado +
                ", mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", error=" + error +
                '}';
    }
}
