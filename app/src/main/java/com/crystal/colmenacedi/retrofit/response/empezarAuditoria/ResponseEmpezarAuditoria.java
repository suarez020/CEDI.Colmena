package com.crystal.colmenacedi.retrofit.response.empezarAuditoria;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseEmpezarAuditoria {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaEmpezarAuditoria respuesta;

    public ResponseEmpezarAuditoria(RespuestaEmpezarAuditoria respuesta) {
        this.respuesta = respuesta;
    }

    public RespuestaEmpezarAuditoria getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaEmpezarAuditoria respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseEmpezarAuditoria{" +
                "respuesta=" + respuesta +
                '}';
    }
}
