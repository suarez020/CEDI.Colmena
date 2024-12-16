package com.crystal.colmenacedi.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestExtraerPost {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ean")
    @Expose
    private String ean;
    @SerializedName("ubicacion")
    @Expose
    private String ubicacion;
    @SerializedName("proceso")
    @Expose
    private String proceso;

    public RequestExtraerPost(String id, String ean, String ubicacion, String proceso) {
        this.id = id;
        this.ean = ean;
        this.ubicacion = ubicacion;
        this.proceso = proceso;
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

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    @Override
    public String toString() {
        return "RequestExtraerPost{" +
                "id='" + id + '\'' +
                ", ean='" + ean + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", proceso='" + proceso + '\'' +
                '}';
    }
}
