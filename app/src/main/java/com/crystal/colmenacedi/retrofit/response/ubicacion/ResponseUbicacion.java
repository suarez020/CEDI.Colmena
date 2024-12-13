package com.crystal.colmenacedi.retrofit.response.ubicacion;

import com.crystal.colmenacedi.models.Errors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUbicacion {
    @SerializedName("ubicacion")
    @Expose
    private Ubicacion ubicacion;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    public ResponseUbicacion(Ubicacion ubicacion, Errors errors) {
        this.ubicacion = ubicacion;
        this.errors = errors;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ResponseUbicacion{" +
                "ubicacion=" + ubicacion +
                ", errors=" + errors +
                '}';
    }
}
