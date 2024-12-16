package com.crystal.colmenacedi.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestTerminar {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ubicacion")
    @Expose
    private String ubicacion;
    @SerializedName("proceso")
    @Expose
    private String proceso;

    public RequestTerminar(String id, String ubicacion, String proceso) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.proceso = proceso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "RequestTerminar{" +
                "id='" + id + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", proceso='" + proceso + '\'' +
                '}';
    }
}
