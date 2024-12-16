package com.crystal.colmenacedi.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestUbicacion {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ean")
    @Expose
    private String ean;
    @SerializedName("ubicacion")
    @Expose
    private String ubicacion;

    public RequestUbicacion(String id, String ean, String ubicacion) {
        this.id = id;
        this.ean = ean;
        this.ubicacion = ubicacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "RequestFinUbicacion{" +
                "id='" + id + '\'' +
                ", ean='" + ean + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
