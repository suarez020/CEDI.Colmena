package com.crystal.colmenacedi.retrofit.response.empezarAuditoria;

import com.crystal.colmenacedi.models.Errors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaEmpezarAuditoria {
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    @SerializedName("Voz")
    @Expose
    private String voz;

    @SerializedName("Sobrantes")
    @Expose
    private String sobrantes;

    @SerializedName("Faltantes")
    @Expose
    private String faltantes;

    @SerializedName("Estacion")
    @Expose
    private String estacion;

    @SerializedName("empezarauditoria")
    private EmpezarAuditoria empezarAuditoria;

    @SerializedName("QR")
    @Expose
    private boolean qr;

    @SerializedName("Error")
    @Expose
    private Errors error;

    public RespuestaEmpezarAuditoria(String mensaje, String voz, String sobrantes, String faltantes, EmpezarAuditoria empezarAuditoria, boolean qr, Errors error) {
        this.mensaje = mensaje;
        this.voz = voz;
        this.sobrantes = sobrantes;
        this.faltantes = faltantes;
        this.empezarAuditoria = empezarAuditoria;
        this.qr = qr;
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getVoz() {
        return voz;
    }

    public void setVoz(String voz) {
        this.voz = voz;
    }

    public String getSobrantes() {
        return sobrantes;
    }

    public void setSobrantes(String sobrantes) {
        this.sobrantes = sobrantes;
    }

    public String getFaltantes() {
        return faltantes;
    }

    public void setFaltantes(String faltantes) {
        this.faltantes = faltantes;
    }

    public EmpezarAuditoria getEmpezarAuditoria() {
        return empezarAuditoria;
    }

    public void setEmpezarAuditoria(EmpezarAuditoria empezarAuditoria) {
        this.empezarAuditoria = empezarAuditoria;
    }

    public boolean isQr() {
        return qr;
    }

    public void setQr(boolean qr) {
        this.qr = qr;
    }

    public Errors getError() {
        return error;
    }

    public void setError(Errors error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RespuestaEmpezarAuditoria{" +
                "mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", sobrantes='" + sobrantes + '\'' +
                ", faltantes='" + faltantes + '\'' +
                ", empezarAuditoria=" + empezarAuditoria +
                ", qr=" + qr +
                ", error=" + error +
                '}';
    }
}
