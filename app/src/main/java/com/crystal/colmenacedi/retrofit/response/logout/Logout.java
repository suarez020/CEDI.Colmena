package com.crystal.colmenacedi.retrofit.response.logout;

import com.google.gson.annotations.SerializedName;

public class Logout {

    @SerializedName("Desmatriculado")
    private boolean isDesmatriculado;

    public Logout(boolean isDesmatriculado) {
        this.isDesmatriculado = isDesmatriculado;
    }

    public boolean isDesmatriculado() {
        return isDesmatriculado;
    }

    public void setDesmatriculado(boolean desmatriculado) {
        isDesmatriculado = desmatriculado;
    }

    @Override
    public String toString() {
        return "Logout{" +
                "isDesmatriculado=" + isDesmatriculado +
                '}';
    }
}
