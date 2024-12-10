package com.crystal.colmenacedi.retrofit.response.configuracion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseConfiguracion {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaConfiguracion respuesta;

    public ResponseConfiguracion(RespuestaConfiguracion respuesta) {
        this.respuesta = respuesta;
    }

    public RespuestaConfiguracion getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaConfiguracion respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseConfiguracion{" +
                "respuesta=" + respuesta +
                '}';
    }
}
