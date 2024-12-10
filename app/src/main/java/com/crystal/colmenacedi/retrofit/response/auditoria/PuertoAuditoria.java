package com.crystal.colmenacedi.retrofit.response.auditoria;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PuertoAuditoria {
    @SerializedName("Carton")
    @Expose
    private String carton;

    @SerializedName("Isla")
    @Expose
    private String isla;

    @SerializedName("Puerto")
    @Expose
    private String puerto;

    @SerializedName("Sobrantes")
    @Expose
    private String sobrantes;

    @SerializedName("Faltantes")
    @Expose
    private String faltantes;

    @SerializedName("TFaltantes")
    @Expose
    private List<List<String>> tFaltantes;

    public PuertoAuditoria(String carton, String isla, String puerto, String sobrantes, String faltantes, List<List<String>> tFaltantes) {
        this.carton = carton;
        this.isla = isla;
        this.puerto = puerto;
        this.sobrantes = sobrantes;
        this.faltantes = faltantes;
        this.tFaltantes = tFaltantes;
    }

    public String getCarton() {
        return carton;
    }

    public void setCarton(String carton) {
        this.carton = carton;
    }

    public String getIsla() {
        return isla;
    }

    public void setIsla(String isla) {
        this.isla = isla;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getSobrantes() {
        return sobrantes;
    }

    public void setSobrantes(String sobrantes) {
        this.sobrantes = sobrantes;
    }

    public String getFaltantes() {
        return faltantes;
    }

    public void setFaltantes(String faltantes) {
        this.faltantes = faltantes;
    }

    public List<List<String>> gettFaltantes() {
        return tFaltantes;
    }

    public void settFaltantes(List<List<String>> tFaltantes) {
        this.tFaltantes = tFaltantes;
    }

    @Override
    public String toString() {
        return "PuertoAuditoria{" +
                "carton='" + carton + '\'' +
                ", isla='" + isla + '\'' +
                ", puerto='" + puerto + '\'' +
                ", sobrantes='" + sobrantes + '\'' +
                ", faltantes='" + faltantes + '\'' +
                ", tFaltantes=" + tFaltantes +
                '}';
    }
}
