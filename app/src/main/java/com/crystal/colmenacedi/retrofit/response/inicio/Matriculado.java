package com.crystal.colmenacedi.retrofit.response.inicio;

import com.google.gson.annotations.SerializedName;

public class Matriculado {
    @SerializedName("Matriculado")
    private boolean isMatriculado;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("Cedula")
    private String cedula;

    public Matriculado(boolean isMatriculado, String nombre, String cedula) {
        this.isMatriculado = isMatriculado;
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public boolean isMatriculado() {
        return isMatriculado;
    }

    public void setMatriculado(boolean matriculado) {
        isMatriculado = matriculado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    @Override
    public String toString() {
        return "Matriculado{" +
                "\nisMatriculado=" + isMatriculado +
                ", \nnombre='" + nombre + '\'' +
                ", \ncedula='" + cedula + '\'' +
                "\n"+'}';
    }
}
