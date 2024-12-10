package com.crystal.colmenacedi.retrofit.response.configuracion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Configuracion {
    @SerializedName("Cambio Estacion")
    @Expose
    private Boolean cambioEstacion;

    public Boolean getCambioEstacion() {
        return cambioEstacion;
    }

    public void setCambioEstacion(Boolean cambioEstacion) {
        this.cambioEstacion = cambioEstacion;
    }

    @Override
    public String toString() {
        return "Configuracion{" +
                "cambioEstacion=" + cambioEstacion +
                '}';
    }
}
