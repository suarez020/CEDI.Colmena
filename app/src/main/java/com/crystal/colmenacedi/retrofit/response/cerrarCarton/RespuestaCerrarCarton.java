package com.crystal.colmenacedi.retrofit.response.cerrarCarton;

import com.crystal.colmenacedi.models.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaCerrarCarton {

    @SerializedName("cerrarcarton")
    private CerrarCarton cerrarCarton;

    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    @SerializedName("Voz")
    @Expose
    private String voz;

    @SerializedName("Error")
    @Expose
    private Error error;

    public RespuestaCerrarCarton(CerrarCarton cerrarCarton, String mensaje, String voz, Error error) {
        this.cerrarCarton = cerrarCarton;
        this.mensaje = mensaje;
        this.voz = voz;
        this.error = error;
    }

    public CerrarCarton getCerrarCarton() {
        return cerrarCarton;
    }

    public void setCerrarCarton(CerrarCarton cerrarCarton) {
        this.cerrarCarton = cerrarCarton;
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
        return "RespuestaCerrarCarton{" +
                "cerrarCarton=" + cerrarCarton +
                ", mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", error=" + error +
                '}';
    }
}
