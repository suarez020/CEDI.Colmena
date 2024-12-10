package com.crystal.colmenacedi.retrofit.response.finPinado;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFinPinado {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaFinPinado respuesta;

    public ResponseFinPinado(RespuestaFinPinado respuesta) {
        this.respuesta = respuesta;
    }

    public RespuestaFinPinado getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaFinPinado respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseFinPinado{" +
                "respuesta=" + respuesta +
                '}';
    }
}
