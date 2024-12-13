package com.crystal.colmenacedi.retrofit.response.cerradoRFID;

import com.crystal.colmenacedi.models.Errors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaCerradoRFID {

    @SerializedName("Genericos")
    public GenericosCerradoRFID genericos;

    @SerializedName("Mensaje")
    @Expose
    public String mensaje;
    @SerializedName("Voz")
    @Expose
    public String voz;
    @SerializedName("Error")
    @Expose
    public Errors error;
    @SerializedName("Estacion")
    @Expose
    public String estacion;



    public RespuestaCerradoRFID(String mensaje, String voz, Errors error, String estacion, GenericosCerradoRFID genericos) {
        this.mensaje = mensaje;
        this.voz = voz;
        this.error = error;
        this.estacion = estacion;
        this.genericos = genericos;
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

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public GenericosCerradoRFID getGenericos() {
        return genericos;
    }

    public void setGenericos(GenericosCerradoRFID genericos) {
        this.genericos = genericos;
    }

    @Override
    public String toString() {
        return "RespuestaCerradoRFID{" +
                "mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", error=" + error +
                ", estacion='" + estacion + '\'' +
                ", genericos=" + genericos +
                '}';
    }
}
