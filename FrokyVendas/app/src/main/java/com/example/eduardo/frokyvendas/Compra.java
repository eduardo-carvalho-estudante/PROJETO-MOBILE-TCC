package com.example.eduardo.frokyvendas;

import com.google.gson.annotations.SerializedName;

public class Compra {
    private int idCompra;
    private String dataCompra;
    @SerializedName("VALOR_TOTAL")
    private double valorTotal;
    private Usuario user;

    public Compra(){

    }

    public Compra(int idCompra, String dataCompra, double valorTotal, Usuario user) {
        this.idCompra = idCompra;
        this.dataCompra = dataCompra;
        this.valorTotal = valorTotal;
        this.user = user;
    }

    public int getIdCompra() {

        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
