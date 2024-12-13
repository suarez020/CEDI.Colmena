package com.crystal.colmenacedi.retrofit.response.configuracion;

import com.crystal.colmenacedi.models.Errors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseConfiguracion {
        @SerializedName("configuracion")
        @Expose
        private Configuracion configuracion;
        @SerializedName("errors")
        @Expose
        private Errors errors;

    public ResponseConfiguracion(Configuracion configuracion, Errors errors) {
        this.configuracion = configuracion;
        this.errors = errors;
    }

    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ResponseConfiguracion{" +
                "configuracion=" + configuracion +
                ", errors=" + errors +
                '}';
    }
}
