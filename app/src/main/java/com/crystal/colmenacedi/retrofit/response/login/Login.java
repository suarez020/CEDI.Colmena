package com.crystal.colmenacedi.retrofit.response.login;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("Matriculado")
    private String matriculado;

    public Login(String matriculado) {
        this.matriculado = matriculado;
    }

    public String getMatriculado() {
        return matriculado;
    }

    public void setMatriculado(String matriculado) {
        this.matriculado = matriculado;
    }

    @Override
    public String toString() {
        return "Login{" +
                "matriculado='" + matriculado + '\'' +
                '}';
    }
}
