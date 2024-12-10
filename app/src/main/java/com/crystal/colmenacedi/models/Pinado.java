package com.crystal.colmenacedi.models;

import com.google.gson.annotations.SerializedName;

public class Pinado {
    @SerializedName("Ubicacion")
    private String Ubicacion;

    public Pinado(String ubicacion) {
        this.Ubicacion = ubicacion;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.Ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Pinado{" +
                "Ubicacion='" + Ubicacion + '\'' +
                '}';
    }
}
