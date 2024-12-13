package com.crystal.colmenacedi.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestLogin {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("estacion")
    @Expose
    private String estacion;

    public RequestLogin(String id, String estacion) {
        this.id = id;
        this.estacion = estacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    @Override
    public String toString() {
        return "RequestLogin{" +
                "id='" + id + '\'' +
                ", estacion='" + estacion + '\'' +
                '}';
    }
}
