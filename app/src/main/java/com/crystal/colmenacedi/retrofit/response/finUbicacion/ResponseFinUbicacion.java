package com.crystal.colmenacedi.retrofit.response.finUbicacion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseFinUbicacion {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaFinUbicacion respuesta;

    public ResponseFinUbicacion(RespuestaFinUbicacion respuesta) {
        this.respuesta = respuesta;
    }

    public RespuestaFinUbicacion getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaFinUbicacion respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseFinUbicacion{" +
                "respuesta=" + respuesta +
                '}';
    }
}
