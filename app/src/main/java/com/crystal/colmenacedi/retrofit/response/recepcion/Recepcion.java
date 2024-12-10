package com.crystal.colmenacedi.retrofit.response.recepcion;

import com.google.gson.annotations.SerializedName;

public class Recepcion {
    @SerializedName("Pedido")
    private String Pedido;

    @SerializedName("Tienda")
    private String Tienda;

    @SerializedName("Ubicacion")
    private String Ubicacion;

    @SerializedName("Cantidad")
    private String Cantidad;

    public Recepcion(String pedido, String tienda, String ubicacion, String cantidad) {
        this.Pedido = pedido;
        this.Tienda = tienda;
        this.Ubicacion = ubicacion;
        this.Cantidad = cantidad;
    }

    public String getPedido() {
        return Pedido;
    }

    public void setPedido(String pedido) {
        this.Pedido = pedido;
    }

    public String getTienda() {
        return Tienda;
    }

    public void setTienda(String tienda) {
        this.Tienda = tienda;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.Ubicacion = ubicacion;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        this.Cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Recepcion{" +
                "Pedido='" + Pedido + '\'' +
                ", Tienda='" + Tienda + '\'' +
                ", Ubicacion='" + Ubicacion + '\'' +
                ", Cantidad='" + Cantidad + '\'' +
                '}';
    }
}
