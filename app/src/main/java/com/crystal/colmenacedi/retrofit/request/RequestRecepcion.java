package com.crystal.colmenacedi.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestRecepcion {

    @SerializedName("Cedula")
    @Expose
    private String cedula;
    @SerializedName("Estacion")
    @Expose
    private String estacion;
    @SerializedName("CartonG")
    @Expose
    private String cartonG;

    public RequestRecepcion(String cedula, String estacion, String cartonG) {
        this.cedula = cedula;
        this.estacion = estacion;
        this.cartonG = cartonG;
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

    public String getCartonG() {
        return cartonG;
    }

    public void setCartonG(String cartonG) {
        this.cartonG = cartonG;
    }

    @Override
    public String toString() {
        return "RequestRecepcion{" +
                "cedula='" + cedula + '\'' +
                ", estacion='" + estacion + '\'' +
                ", cartonG='" + cartonG + '\'' +
                '}';
    }
}
