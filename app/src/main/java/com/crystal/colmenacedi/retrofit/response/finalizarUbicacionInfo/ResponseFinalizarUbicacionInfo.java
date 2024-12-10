package com.crystal.colmenacedi.retrofit.response.finalizarUbicacionInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFinalizarUbicacionInfo {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaFinalizarUbicacionInfo respuesta;

    public ResponseFinalizarUbicacionInfo(RespuestaFinalizarUbicacionInfo respuesta) {
        this.respuesta = respuesta;
    }

    public RespuestaFinalizarUbicacionInfo getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaFinalizarUbicacionInfo respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseFinalizarUbicacionInfo{" +
                "respuesta=" + respuesta +
                '}';
    }
}
