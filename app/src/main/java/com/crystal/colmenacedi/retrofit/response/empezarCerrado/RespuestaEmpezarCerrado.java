package com.crystal.colmenacedi.retrofit.response.empezarCerrado;

import com.crystal.colmenacedi.models.Errors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaEmpezarCerrado{
    @SerializedName("empezarcerrado")
    private EmpezarCerrado empezarCerrado;

    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    @SerializedName("Voz")
    @Expose
    private String voz;

    @SerializedName("QR")
    @Expose
    private boolean qr;

    @SerializedName("Error")
    @Expose
    private Errors error;

    public RespuestaEmpezarCerrado(EmpezarCerrado empezarCerrado, String mensaje, String voz, boolean qr, Errors error) {
        this.empezarCerrado = empezarCerrado;
        this.mensaje = mensaje;
        this.voz = voz;
        this.qr = qr;
        this.error = error;
    }

    public EmpezarCerrado getEmpezarCerrado() {
        return empezarCerrado;
    }

    public void setEmpezarCerrado(EmpezarCerrado empezarCerrado) {
        this.empezarCerrado = empezarCerrado;
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
        return "RespuestaEmpezarCerrado{" +
                "empezarCerrado=" + empezarCerrado +
                ", mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", qr=" + qr +
                ", error=" + error +
                '}';
    }
}
