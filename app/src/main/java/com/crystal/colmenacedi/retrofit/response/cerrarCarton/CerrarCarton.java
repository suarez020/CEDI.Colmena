package com.crystal.colmenacedi.retrofit.response.cerrarCarton;

import com.google.gson.annotations.SerializedName;

public class CerrarCarton {

    @SerializedName("Ubicacion")
    private String ubicacion;

    @SerializedName("Leidos")
    private String leidos;

    @SerializedName("Faltantes")
    private String faltantes;

    public CerrarCarton(String ubicacion, String leidos, String faltantes) {
        this.ubicacion = ubicacion;
        this.leidos = leidos;
        this.faltantes = faltantes;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getLeidos() {
        return leidos;
    }

    public void setLeidos(String leidos) {
        this.leidos = leidos;
    }

    public String getFaltantes() {
        return faltantes;
    }

    public void setFaltantes(String faltantes) {
        this.faltantes = faltantes;
    }

    @Override
    public String toString() {
        return "CerrarCarton{" +
                "ubicacion='" + ubicacion + '\'' +
                ", leidos='" + leidos + '\'' +
                ", faltantes='" + faltantes + '\'' +
                '}';
    }
}
