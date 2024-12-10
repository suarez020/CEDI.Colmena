package com.crystal.colmenacedi.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFinAuditoria {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaBase respuesta;

    public ResponseFinAuditoria(RespuestaBase respuesta) {
        this.respuesta = respuesta;
    }

    public RespuestaBase getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaBase respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseFinAuditoria{" +
                "respuesta=" + respuesta +
                '}';
    }
}
