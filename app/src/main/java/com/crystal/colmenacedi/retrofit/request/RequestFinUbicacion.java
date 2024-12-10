package com.crystal.colmenacedi.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestFinUbicacion {
    @SerializedName("Cedula")
    @Expose
    private String cedula;

    @SerializedName("Estacion")
    @Expose
    private String equipo;

    @SerializedName("Ubicacion")
    @Expose
    private String ubicacion;

    @SerializedName("Clave")
    @Expose
    private String clave;

    public RequestFinUbicacion(String cedula, String equipo, String ubicacion, String clave) {
        this.cedula = cedula;
        this.equipo = equipo;
        this.ubicacion = ubicacion;
        this.clave = clave;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "RequestFinUbicacion{" +
                "cedula='" + cedula + '\'' +
                ", equipo='" + equipo + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", clave='" + clave + '\'' +
                '}';
    }
}
