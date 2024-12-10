package com.crystal.colmenacedi.retrofit.response.cerradoRFID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GenericosCerradoRFID implements Serializable {

    public GenericosCerradoRFID(List<List<String>> array) {
        this.array = array;
    }

    @SerializedName("Array")
    @Expose
    private List<List<String>> array = null;

    public List<List<String>> getArray() {
        return array;
    }

    public void setArray(List<List<String>> array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return "GenericosCerradoRFID{" +
                "array=" + array +
                '}';
    }
}
