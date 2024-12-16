package com.crystal.colmenacedi.retrofit.response.extraer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("items")
    @Expose
    private List<List<String>> items;
    @SerializedName("items2")
    @Expose
    private List<List<String>> items2;

    public Data(List<List<String>> items, List<List<String>> items2) {
        this.items = items;
        this.items2 = items2;
    }

    public List<List<String>> getItems() {
        return items;
    }

    public void setItems(List<List<String>> items) {
        this.items = items;
    }

    public List<List<String>> getItems2() {
        return items2;
    }

    public void setItems2(List<List<String>> items2) {
        this.items2 = items2;
    }

    @Override
    public String toString() {
        return "Data{" +
                "items=" + items +
                ", items2=" + items2 +
                '}';
    }
}
