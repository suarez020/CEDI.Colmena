package com.crystal.colmenacedi.retrofit.request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestLecturaEan {

    @SerializedName("Cedula")
    @Expose
    private String cedula;

    @SerializedName("Estacion")
    @Expose
    private String equipo;

    @SerializedName("EAN")
    @Expose
    private String ean;

    @SerializedName("Ubicacion")
    @Expose
    private String ubicacion;

    public RequestLecturaEan(String cedula, String equipo, String ean, String ubicacion) {
        this.cedula = cedula;
        this.equipo = equipo;
        this.ean = ean;
        this.ubicacion = ubicacion;
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

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "RequestLectura{" +
                "cedula='" + cedula + '\'' +
                ", equipo='" + equipo + '\'' +
                ", ean='" + ean + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
