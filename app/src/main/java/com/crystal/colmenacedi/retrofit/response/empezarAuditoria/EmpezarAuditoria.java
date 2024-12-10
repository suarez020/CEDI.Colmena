package com.crystal.colmenacedi.retrofit.response.empezarAuditoria;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmpezarAuditoria {
    @SerializedName("TFaltantes")
    @Expose
    private List<List<String>> tFaltantes;

    public EmpezarAuditoria(List<List<String>> tFaltantes) {
        this.tFaltantes = tFaltantes;
    }

    public List<List<String>> gettFaltantes() {
        return tFaltantes;
    }

    public void settFaltantes(List<List<String>> tFaltantes) {
        this.tFaltantes = tFaltantes;
    }

    @Override
    public String toString() {
        return "EmpezarAuditoria{" +
                "tFaltantes=" + tFaltantes +
                '}';
    }
}
