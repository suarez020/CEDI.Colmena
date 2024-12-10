package com.crystal.colmenacedi.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestTamano {
    @SerializedName("Cedula")
    @Expose
    public String cedula;
    @SerializedName("Estacion")
    @Expose
    public String estacion;
    @SerializedName("Ubicacion")
    @Expose
    public String ubicacion;
    @SerializedName("Generico")
    @Expose
    public String generico;
    @SerializedName ( "Tamano" )
    @Expose
    public String tamano;

    public RequestTamano(String cedula, String estacion, String ubicacion, String generico, String tamano) {
        this.cedula = cedula;
        this.estacion = estacion;
        this.ubicacion = ubicacion;
        this.generico = generico;
        this.tamano = tamano;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getGenerico() {
        return generico;
    }

    public void setGenerico(String generico) {
        this.generico = generico;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    @Override
    public String toString() {
        return "RequestTamano{" +
                "cedula='" + cedula + '\'' +
                ", estacion='" + estacion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", generico='" + generico + '\'' +
                ", tamano='" + tamano + '\'' +
                '}';
    }
}
