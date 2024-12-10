package com.crystal.colmenacedi.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestCerradoCarton {

    @SerializedName("Cedula")
    @Expose
    private String cedula;

    @SerializedName("Estacion")
    @Expose
    private String equipo;

    @SerializedName("Ubicacion")
    @Expose
    private String ubicacion;

    @SerializedName("Generico")
    @Expose
    private String generico;

    public RequestCerradoCarton(String cedula, String equipo, String ubicacion, String generico) {
        this.cedula = cedula;
        this.equipo = equipo;
        this.ubicacion = ubicacion;
        this.generico = generico;
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

    public String getGenerico() {
        return generico;
    }

    public void setGenerico(String generico) {
        this.generico = generico;
    }

    @Override
    public String toString() {
        return "RequestCerradoCarton{" +
                "cedula='" + cedula + '\'' +
                ", equipo='" + equipo + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", generico='" + generico + '\'' +
                '}';
    }
}
