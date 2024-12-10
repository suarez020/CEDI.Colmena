package com.crystal.colmenacedi.retrofit.response.lecturaEan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLecturaEan {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaLecturaEan respuesta;

    public ResponseLecturaEan(RespuestaLecturaEan respuesta) {
        this.respuesta = respuesta;
    }

    public RespuestaLecturaEan getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaLecturaEan respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseLecturaEan{" +
                "respuesta=" + respuesta +
                '}';
    }
}
