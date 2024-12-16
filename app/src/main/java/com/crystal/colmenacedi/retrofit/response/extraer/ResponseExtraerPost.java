package com.crystal.colmenacedi.retrofit.response.extraer;

import com.crystal.colmenacedi.models.Errors;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseExtraerPost {
    @SerializedName("data")
    @Expose
    private DataPost data;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    public ResponseExtraerPost(DataPost data, Errors errors) {
        this.data = data;
        this.errors = errors;
    }

    public DataPost getData() {
        return data;
    }

    public void setData(DataPost data) {
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
        return "ResponseExtraerPost{" +
                "data=" + data +
                ", errors=" + errors +
                '}';
    }
}
