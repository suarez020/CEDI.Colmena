package com.crystal.colmenacedi.retrofit.response.logout;

import com.crystal.colmenacedi.models.Errors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaLogout{
    @SerializedName("logout")
    @Expose
    private Logout logout;

    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    @SerializedName("Voz")
    @Expose
    private String voz;

    @SerializedName("Error")
    @Expose
    private Errors error;

    public RespuestaLogout(Logout logout, String mensaje, String voz, Errors error) {
        this.logout = logout;
        this.mensaje = mensaje;
        this.voz = voz;
        this.error = error;
    }

    public Logout getLogout() {
        return logout;
    }

    public void setLogout(Logout logout) {
        this.logout = logout;
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
        return "RespuestaLogout{" +
                "logout=" + logout +
                ", mensaje='" + mensaje + '\'' +
                ", voz='" + voz + '\'' +
                ", error=" + error +
                '}';
    }
}
