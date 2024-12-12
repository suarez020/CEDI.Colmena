package com.crystal.colmenacedi.retrofit.response.finUbicacion;

import com.crystal.colmenacedi.retrofit.response.empezarCerrado.EmpezarCerrado;
import com.crystal.colmenacedi.models.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaFinUbicacion{
    @SerializedName("finalizarubicacion")
    private EmpezarCerrado finUbicacion;

    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    @SerializedName("Voz")
    @Expose
    private String voz;

    @SerializedName("Error")
    @Expose
    private Error error;

    public RespuestaFinUbicacion(EmpezarCerrado finUbicacion, String mensaje, String voz, Error error) {
        this.finUbicacion = finUbicacion;
        this.mensaje = mensaje;
        this.voz = voz;
        this.error = error;
    }

    public EmpezarCerrado getFinUbicacion() {
        return finUbicacion;
    }

    public void setFinUbicacion(EmpezarCerrado finUbicacion) {
        this.finUbicacion = finUbicacion;
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
        return "RespuestaFinUbicacion{" +
                "finUbicacion=" + finUbicacion +
                ", mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", error=" + error +
                '}';
    }
}