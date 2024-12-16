package com.crystal.colmenacedi.retrofit.response.terminar;

import com.crystal.colmenacedi.models.Errors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseTerminar {
    @SerializedName("terminar")
    @Expose
    private Terminar terminar;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    public ResponseTerminar(Terminar terminar, Errors errors) {
        this.terminar = terminar;
        this.errors = errors;
    }

    public Terminar getTerminar() {
        return terminar;
    }

    public void setTerminar(Terminar terminar) {
        this.terminar = terminar;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ResponseTerminar{" +
                "terminar=" + terminar +
                ", errors=" + errors +
                '}';
    }
}
