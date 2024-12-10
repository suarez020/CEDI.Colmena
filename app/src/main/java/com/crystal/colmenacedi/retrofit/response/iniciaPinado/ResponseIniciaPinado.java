package com.crystal.colmenacedi.retrofit.response.iniciaPinado;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseIniciaPinado {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaIniciaPinado respuesta;

    public ResponseIniciaPinado(RespuestaIniciaPinado respuesta) {
        this.respuesta = respuesta;
    }

    public RespuestaIniciaPinado getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaIniciaPinado respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseIniciaPinado{" +
                "respuesta=" + respuesta +
                '}';
    }
}
