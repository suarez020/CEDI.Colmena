package com.crystal.colmenacedi.retrofit.response.tamano;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.crystal.colmenacedi.models.Error;

public class RespuestaTamano {

    @SerializedName("Mensaje")
    @Expose
    public String mensaje;
    @SerializedName("Voz")
    @Expose
    public String voz;
    @SerializedName("Error")
    @Expose
    public Error error;
    @SerializedName("Estacion")
    @Expose
    public String estacion;

    public RespuestaTamano(String mensaje, String voz, Error error, String estacion) {
        this.mensaje = mensaje;
        this.voz = voz;
        this.error = error;
        this.estacion = estacion;
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

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    @Override
    public String toString() {
        return "RespuestaTamano{" +
                "mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", error=" + error +
                ", estacion='" + estacion + '\'' +
                '}';
    }
}

