package com.crystal.colmenacedi.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestConfiguracion {
    @SerializedName("MAC")
    @Expose
    private String mac;
    @SerializedName("Estacion")
    @Expose
    private String estacion;

    public RequestConfiguracion(String mac, String estacion) {
        this.mac = mac;
        this.estacion = estacion;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    @Override
    public String toString() {
        return "RequestConfiguracion{" +
                "mac='" + mac + '\'' +
                ", estacion='" + estacion + '\'' +
                '}';
    }
}
