package com.crystal.colmenacedi.retrofit.response.auditoria;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAuditoria {
    @SerializedName("Respuesta")
    @Expose
    private RespuestaAuditoria respuesta;

    public ResponseAuditoria(RespuestaAuditoria respuesta) {
        this.respuesta = respuesta;
    }

    public RespuestaAuditoria getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaAuditoria respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "ResponseAuditoria{" +
                "respuesta=" + respuesta +
                '}';
    }
}
