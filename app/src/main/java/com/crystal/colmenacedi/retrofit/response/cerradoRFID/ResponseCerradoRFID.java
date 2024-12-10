package com.crystal.colmenacedi.retrofit.response.cerradoRFID;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCerradoRFID {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaCerradoRFID respuesta;

    public ResponseCerradoRFID(RespuestaCerradoRFID respuesta) {
        this.respuesta = respuesta;
    }

    public RespuestaCerradoRFID getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaCerradoRFID respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseCerradoRFID{" +
                "respuesta=" + respuesta +
                '}';
    }
}
