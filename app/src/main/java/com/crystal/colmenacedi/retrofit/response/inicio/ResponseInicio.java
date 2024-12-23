package com.crystal.colmenacedi.retrofit.response.inicio;

import com.crystal.colmenacedi.models.Errors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseInicio {
    @SerializedName("inicio")
    @Expose
    private Inicio inicio;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    public ResponseInicio(Inicio inicio, Errors errors) {
        this.inicio = inicio;
        this.errors = errors;
    }

    public Inicio getInicio() {
        return inicio;
    }

    public void setInicio(Inicio inicio) {
        this.inicio = inicio;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ResponseInicio{" +
                "inicio=" + inicio +
                ", errors=" + errors +
                '}';
    }
}
