package com.crystal.colmenacedi.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestLogin {

    @SerializedName("Cedula")
    @Expose
    private String cedula;

    @SerializedName("Estacion")
    @Expose
    private String estacion;

    public RequestLogin(String cedula, String estacion) {
        this.cedula = cedula;
        this.estacion = estacion;
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

    @Override
    public String toString() {
        return "RequestLogin{" +
                "cedula='" + cedula + '\'' +
                ", estacion='" + estacion + '\'' +
                '}';
    }
}
