package com.crystal.colmenacedi.retrofit.response.auditoria;

import com.crystal.colmenacedi.models.Errors;
import com.crystal.colmenacedi.retrofit.response.empezarAuditoria.EmpezarAuditoria;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaAuditoria {
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    @SerializedName("Voz")
    @Expose
    private String voz;

    @SerializedName("empezarauditoria")
    @Expose
    private EmpezarAuditoria empezarauditoria;

    @SerializedName("Error")
    @Expose
    private Errors error;

    @SerializedName("Faltantes")
    @Expose
    private String faltantes;

    @SerializedName("Sobrantes")
    @Expose
    private String sobrantes;

    public RespuestaAuditoria(String mensaje, String voz, EmpezarAuditoria empezarauditoria, Errors error, String faltantes, String sobrantes) {
        this.mensaje = mensaje;
        this.voz = voz;
        this.empezarauditoria = empezarauditoria;
        this.error = error;
        this.faltantes = faltantes;
        this.sobrantes = sobrantes;
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

    public EmpezarAuditoria getEmpezarauditoria() {
        return empezarauditoria;
    }

    public void setEmpezarauditoria(EmpezarAuditoria empezarauditoria) {
        this.empezarauditoria = empezarauditoria;
    }

    public Errors getError() {
        return error;
    }

    public void setError(Errors error) {
        this.error = error;
    }

    public String getFaltantes() {
        return faltantes;
    }

    public void setFaltantes(String faltantes) {
        this.faltantes = faltantes;
    }

    public String getSobrantes() {
        return sobrantes;
    }

    public void setSobrantes(String sobrantes) {
        this.sobrantes = sobrantes;
    }

    @Override
    public String toString() {
        return "RespuestaAuditoria{" +
                "mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", empezarauditoria=" + empezarauditoria +
                ", error=" + error +
                ", faltantes='" + faltantes + '\'' +
                ", sobrantes='" + sobrantes + '\'' +
                '}';
    }
}
