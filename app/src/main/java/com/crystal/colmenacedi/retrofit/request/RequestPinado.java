package com.crystal.colmenacedi.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestPinado {

    @SerializedName("Cedula")
    @Expose
    private String cedula;

    @SerializedName("Estacion")
    @Expose
    private String estacion;

    @SerializedName("Ubicacion")
    @Expose
    private String ubicacion;

    public RequestPinado(String cedula, String estacion, String ubicacion) {
        this.cedula = cedula;
        this.estacion = estacion;
        this.ubicacion = ubicacion;
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

    @Override
    public String toString() {
        return "RequestPinado{" +
                "cedula='" + cedula + '\'' +
                ", estacion='" + estacion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
