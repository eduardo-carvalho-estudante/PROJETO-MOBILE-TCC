package com.example.eduardo.frokyvendas;

import com.google.gson.annotations.SerializedName;

public class ItemCompra {

    @SerializedName("ID_ITEM_COMPRA")
    private int idItem;
    @SerializedName("FK_PRODUTO")
    private Produto prod;
    @SerializedName("QUANTIDADE")
    private int qtd;
    @SerializedName("VALOR_UNIT")
    private double valorUnit;
    @SerializedName("VALOR_TOTAL")
    private double valorTotalItem;
    @SerializedName("FK_PEDIDO")
    private int idCompra;

    public ItemCompra( int idItem,Produto prod, int qtd, double valorUnit, double valorTotalItem, int idCompra) {
        this.idItem = idItem;
        this.prod = prod;
        this.qtd = qtd;
        this.valorUnit = valorUnit;
        this.valorTotalItem = valorTotalItem;
        this.idCompra = idCompra;
    }
     public ItemCompra(){

     }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public Produto getProd() {
        return prod;
    }

    public void setProd(Produto prod) {
        this.prod = prod;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public double getValorUnit() {
        return valorUnit;
    }

    public void setValorUnit(double valorUnit) {
        this.valorUnit = valorUnit;
    }

    public double getValorTotalItem() {
        return valorTotalItem;
    }

    public void setValorTotalItem(double valorTotalItem) {
        this.valorTotalItem = valorTotalItem;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }
}
