package com.crystal.colmenacedi.retrofit.response.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("nombre")
    @Expose
    private String nombre;

    public Login(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Login{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
