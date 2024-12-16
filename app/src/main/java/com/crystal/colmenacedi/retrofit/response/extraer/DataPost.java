package com.crystal.colmenacedi.retrofit.response.extraer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataPost {
    @SerializedName("unidadesLeidas")
    @Expose
    private Integer unidadesLeidas;
    @SerializedName("paquetesLeidos")
    @Expose
    private Integer paquetesLeidos;

    public DataPost(Integer unidadesLeidas, Integer paquetesLeidos) {
        this.unidadesLeidas = unidadesLeidas;
        this.paquetesLeidos = paquetesLeidos;
    }

    public Integer getUnidadesLeidas() {
        return unidadesLeidas;
    }

    public void setUnidadesLeidas(Integer unidadesLeidas) {
        this.unidadesLeidas = unidadesLeidas;
    }

    public Integer getPaquetesLeidos() {
        return paquetesLeidos;
    }

    public void setPaquetesLeidos(Integer paquetesLeidos) {
        this.paquetesLeidos = paquetesLeidos;
    }

    @Override
    public String toString() {
        return "DataPost{" +
                "unidadesLeidas=" + unidadesLeidas +
                ", paquetesLeidos=" + paquetesLeidos +
                '}';
    }
}
