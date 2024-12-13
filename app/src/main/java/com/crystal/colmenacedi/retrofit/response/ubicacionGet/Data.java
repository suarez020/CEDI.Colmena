package com.crystal.colmenacedi.retrofit.response.ubicacionGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("items")
    @Expose
    private List<List<String>> items;

    public Data(List<List<String>> items) {
        this.items = items;
    }

    public List<List<String>> getItems() {
        return items;
    }

    public void setItems(List<List<String>> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Data{" +
                "items=" + items +
                '}';
    }
}
