package com.crystal.colmenacedi.retrofit.response.logout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogout {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaLogout respuesta;

    public ResponseLogout(RespuestaLogout respuesta) {
        this.respuesta = respuesta;
    }

    public RespuestaLogout getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaLogout respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseLogout{" +
                "respuesta=" + respuesta +
                '}';
    }
}
