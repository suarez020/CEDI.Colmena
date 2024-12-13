package com.crystal.colmenacedi.retrofit.response.ubicacionGet;

import com.crystal.colmenacedi.models.Errors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUbicacionGet {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    public ResponseUbicacionGet(Data data, Errors errors) {
        this.data = data;
        this.errors = errors;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ResponseUbicacionGet{" +
                "data=" + data +
                ", errors=" + errors +
                '}';
    }
}
