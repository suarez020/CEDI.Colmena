package com.crystal.colmenacedi.retrofit.response.empezarCerrado;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseEmpezarCerrado {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaEmpezarCerrado respuesta;

    public ResponseEmpezarCerrado(RespuestaEmpezarCerrado respuesta) {
        this.respuesta = respuesta;
    }

    public RespuestaEmpezarCerrado getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaEmpezarCerrado respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseEmpezarCerrado{" +
                "respuesta=" + respuesta +
                '}';
    }
}
