package com.crystal.colmenacedi.retrofit.response.lecturaEan;
import com.crystal.colmenacedi.retrofit.response.empezarCerrado.EmpezarCerrado;
import com.crystal.colmenacedi.models.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaLecturaEan {
    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    @SerializedName("Voz")
    @Expose
    private String voz;

    @SerializedName("EAN")
    private EmpezarCerrado ean;

    @SerializedName("Error")
    @Expose
    private Error error;

    public RespuestaLecturaEan(EmpezarCerrado ean, String mensaje, String voz, Error error) {
        this.ean = ean;
        this.mensaje = mensaje;
        this.voz = voz;
        this.error = error;
    }

    public EmpezarCerrado getEan() {
        return ean;
    }

    public void setEan(EmpezarCerrado ean) {
        this.ean = ean;
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

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RespuestaLecturaEan{" +
                "mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", ean=" + ean +
                ", error=" + error +
                '}';
    }
}
