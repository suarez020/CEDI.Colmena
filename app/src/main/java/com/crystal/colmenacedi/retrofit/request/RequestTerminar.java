package com.crystal.colmenacedi.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestTerminar {
    @SerializedName("respuesta")
    @Expose
    private boolean respuesta;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ubicacion")
    @Expose
    private String ubicacion;
    @SerializedName("proceso")
    @Expose
    private String proceso;

    public RequestTerminar(boolean respuesta, String id, String ubicacion, String proceso) {
        this.respuesta = respuesta;
        this.id = id;
        this.ubicacion = ubicacion;
        this.proceso = proceso;
    }

    public boolean isRespuesta() {
        return respuesta;
    }

    public void setRespuesta(boolean respuesta) {
        this.respuesta = respuesta;
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
                "respuesta=" + respuesta +
                ", id='" + id + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", proceso='" + proceso + '\'' +
                '}';
    }
}
