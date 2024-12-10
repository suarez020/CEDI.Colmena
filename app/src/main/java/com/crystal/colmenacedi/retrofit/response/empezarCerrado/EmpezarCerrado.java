package com.crystal.colmenacedi.retrofit.response.empezarCerrado;

import com.google.gson.annotations.SerializedName;

public class EmpezarCerrado {
    @SerializedName("Leidos")
    private String leidos;

    @SerializedName("Faltantes")
    private String faltantes;

    public EmpezarCerrado(String leidos, String faltantes) {
        this.leidos = leidos;
        this.faltantes = faltantes;
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
        return "EmpezarCerrado{" +
                "leidos='" + leidos + '\'' +
                ", faltantes='" + faltantes + '\'' +
                '}';
    }
}
