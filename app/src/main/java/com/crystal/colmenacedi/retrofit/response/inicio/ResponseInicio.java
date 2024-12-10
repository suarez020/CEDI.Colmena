package com.crystal.colmenacedi.retrofit.response.inicio;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseInicio {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaInicio respuesta;

    public RespuestaInicio getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaInicio respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "" + respuesta;
    }
}
