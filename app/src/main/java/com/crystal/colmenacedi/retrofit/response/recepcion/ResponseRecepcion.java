package com.crystal.colmenacedi.retrofit.response.recepcion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRecepcion {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaRecepcion respuesta;

    public ResponseRecepcion(RespuestaRecepcion respuesta) {
        this.respuesta = respuesta;
    }

    public RespuestaRecepcion getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaRecepcion respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseRecepcion{" +
                "respuesta=" + respuesta +
                '}';
    }
}
