package com.crystal.colmenacedi.retrofit.response.configuracion;

import com.crystal.colmenacedi.models.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaConfiguracion {
    @SerializedName("configuracion")
    @Expose
    private Configuracion configuracion;

    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    @SerializedName("Voz")
    @Expose
    private String voz;

    @SerializedName("Error")
    @Expose
    private Error error;

    @SerializedName("Estacion")
    @Expose
    private String estacion;

    public RespuestaConfiguracion(Configuracion configuracion, String mensaje, String voz, Error error, String estacion) {
        this.configuracion = configuracion;
        this.mensaje = mensaje;
        this.voz = voz;
        this.error = error;
        this.estacion = estacion;
    }

    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
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
        return "RespuestaConfiguracion{" +
                "configuracion=" + configuracion +
                ", mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", error=" + error +
                ", estacion='" + estacion + '\'' +
                '}';
    }
}
