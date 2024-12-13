package com.crystal.colmenacedi.retrofit.response.inicio;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Inicio {
    @SerializedName("login")
    @Expose
    private Boolean login;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("id")
    @Expose
    private String id;

    public Inicio(Boolean login, String nombre, String id) {
        this.login = login;
        this.nombre = nombre;
        this.id = id;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Inicio{" +
                "login=" + login +
                ", nombre='" + nombre + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
