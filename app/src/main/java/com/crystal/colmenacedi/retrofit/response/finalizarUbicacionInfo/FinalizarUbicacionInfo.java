package com.crystal.colmenacedi.retrofit.response.finalizarUbicacionInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FinalizarUbicacionInfo {

    @SerializedName("Array")
    @Expose
    private List<List<String>> array = null;

    public FinalizarUbicacionInfo(List<List<String>> array) {
        this.array = array;
    }

    public List<List<String>> getArray() {
        return array;
    }

    public void setArray(List<List<String>> array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return "FinalizarUbicacionInfo{" +
                "array=" + array +
                '}';
    }
}
