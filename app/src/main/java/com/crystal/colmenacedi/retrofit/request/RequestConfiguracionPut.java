package com.crystal.colmenacedi.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestConfiguracionPut {
    @SerializedName("mac")
    @Expose
    private String mac;
    @SerializedName("estacion")
    @Expose
    private String estacion;

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

    public RequestConfiguracionPut(String mac, String estacion) {
        this.mac = mac;
        this.estacion = estacion;
    }

    @Override
    public String toString() {
        return "RequestConfiguracionPost{" +
                "mac='" + mac + '\'' +
                ", estacion='" + estacion + '\'' +
                '}';
    }
}
