package com.crystal.colmenacedi.retrofit.response.finalizarUbicacionInfo;

import com.crystal.colmenacedi.models.Errors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaFinalizarUbicacionInfo{
    @SerializedName("finalizarubicacioninfo")
    private FinalizarUbicacionInfo finalizarUbicacionInfo;

    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    @SerializedName("Voz")
    @Expose
    private String voz;

    @SerializedName("Error")
    @Expose
    private Errors error;

    public RespuestaFinalizarUbicacionInfo(FinalizarUbicacionInfo finalizarUbicacionInfo, String mensaje, String voz, Errors error) {
        this.finalizarUbicacionInfo = finalizarUbicacionInfo;
        this.mensaje = mensaje;
        this.voz = voz;
        this.error = error;
    }

    public FinalizarUbicacionInfo getFinalizarUbicacionInfo() {
        return finalizarUbicacionInfo;
    }

    public void setFinalizarUbicacionInfo(FinalizarUbicacionInfo finalizarUbicacionInfo) {
        this.finalizarUbicacionInfo = finalizarUbicacionInfo;
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

    public Errors getError() {
        return error;
    }

    public void setError(Errors error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RespuestaFinalizarUbicacionInfo{" +
                "finalizarUbicacionInfo=" + finalizarUbicacionInfo +
                ", mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", error=" + error +
                '}';
    }
}
