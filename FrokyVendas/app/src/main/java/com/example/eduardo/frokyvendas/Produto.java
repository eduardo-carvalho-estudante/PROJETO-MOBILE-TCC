package com.example.eduardo.frokyvendas;

import com.google.gson.annotations.SerializedName;

public class Produto {
    @SerializedName("NOME")
    private String Title;
    //ajustar
    private String Category;
    @SerializedName("DESCRICAO")
    private String Descricao;
    //Prox versao ponte
    @SerializedName("IMAGEM")
    private int Thumbnail;
    @SerializedName("PRECO")
    private String Price;
    @SerializedName("ID_PRODUTO")
    private int Id;

    @SerializedName("QUANTIDADE")
    private int quantidade;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Produto() {
    }

    public Produto(int id,String title, String category, String descricao,String price, int thumbnail) {
        Title = title;
        Category = category;
        Descricao = descricao;
        Thumbnail = thumbnail;
        Price = price;
        Id = id;
    }

    public String getPrice() { return Price; }

    public String getTitle() {
        return Title;
    }

    public String getCategory() {
        return Category;
    }

    public String getDescricao() {
        return Descricao;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public void setPrice(String price) { Price = price; }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
