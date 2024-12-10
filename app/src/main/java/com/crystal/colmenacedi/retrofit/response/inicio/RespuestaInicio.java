package com.crystal.colmenacedi.retrofit.response.inicio;

import com.crystal.colmenacedi.models.Error;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespuestaInicio{
    @SerializedName("Matriculado")
    @Expose
    private Matriculado matriculado;

    @SerializedName("Mensaje")
    @Expose
    private String mensaje;

    @SerializedName("Voz")
    @Expose
    private String voz;

    @SerializedName("Error")
    @Expose
    private Error error;

    public RespuestaInicio(Matriculado matriculado, String mensaje, String voz, Error error) {
        this.matriculado = matriculado;
        this.mensaje = mensaje;
        this.voz = voz;
        this.error = error;
    }

    public Matriculado getMatriculado() {
        return matriculado;
    }

    public void setMatriculado(Matriculado matriculado) {
        this.matriculado = matriculado;
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
        return "\n{ " +
                "\n" + matriculado +
                ",\nmensaje='" + mensaje + '\'' +
                ",\nvoz='" + voz + '\'' +
                ",\n" + error +
                "\n"+'}';
    }
}
