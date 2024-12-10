package com.crystal.colmenacedi.retrofit.response.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {

    @SerializedName("Respuesta")
    @Expose
    private RespuestaLogin respuesta;

    public ResponseLogin(RespuestaLogin resLogin) {
        this.respuesta = resLogin;
    }

    public RespuestaLogin getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaLogin resLogin) {
        this.respuesta = resLogin;
    }

    @Override
    public String toString() {
        return "ResponseLogin{" +
                "respuesta=" + respuesta +
                '}';
    }
}
