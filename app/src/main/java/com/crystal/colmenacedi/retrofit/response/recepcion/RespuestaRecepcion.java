package com.crystal.colmenacedi.retrofit.response.recepcion;

import com.crystal.colmenacedi.models.Error;
import com.crystal.colmenacedi.retrofit.response.RespuestaBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaRecepcion extends RespuestaBase {
    @SerializedName("Recepcion")
    @Expose
    private Recepcion recepcion;

    public RespuestaRecepcion(String mensaje, String voz, Recepcion recepcion, Error error) {
        super(mensaje, voz, error);
        this.recepcion = recepcion;
    }

    public Recepcion getRecepcion() {
        return recepcion;
    }

    public void setRecepcion(Recepcion recepcion) {
        this.recepcion = recepcion;
    }

    @Override
    public String toString() {
        return "ResRecepcion{" +
                "mensaje='" + getMensaje() + '\'' +
                ", voz='" + getVoz() + '\'' +
                ", recepcion=" + recepcion +
                ", error=" + getError() +
                '}';
    }
}
