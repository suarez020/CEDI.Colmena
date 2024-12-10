package com.crystal.colmenacedi.retrofit.response.cerrarCarton;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCerrarCarton {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaCerrarCarton respuesta;

    public ResponseCerrarCarton(RespuestaCerrarCarton respuesta) {
        this.respuesta = respuesta;
    }

    public RespuestaCerrarCarton getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaCerrarCarton respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseCerrarCarton{" +
                "respuesta=" + respuesta +
                '}';
    }
}
