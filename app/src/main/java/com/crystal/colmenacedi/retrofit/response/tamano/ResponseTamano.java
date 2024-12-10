package com.crystal.colmenacedi.retrofit.response.tamano;

import com.google.gson.annotations.SerializedName;

public class ResponseTamano {
    @SerializedName("Respuesta")
    private RespuestaTamano respuesta;

    public ResponseTamano(RespuestaTamano respuesta) {this.respuesta = respuesta;}

    public RespuestaTamano getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaTamano respuesta) {

        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseTamano{" +
                "respuesta=" + respuesta +
                '}';
    }
}
