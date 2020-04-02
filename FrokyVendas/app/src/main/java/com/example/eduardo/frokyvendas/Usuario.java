package com.example.eduardo.frokyvendas;

import com.google.gson.annotations.SerializedName;

public class Usuario {
    @SerializedName("ID_USUARIO")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @SerializedName("CPF")
    private String cpf;
    @SerializedName("NOME")
    private String nome;
    @SerializedName("TELEFONE")
    private String telefone;
    @SerializedName("E_MAIL")
    private String email;
    @SerializedName("DATA_DE_NASCIMENTO")
    private String data_de_nascimento;
    @SerializedName("SENHA")
    private String senha;
    @SerializedName("SEXO")
    private String sexo;

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Usuario(){

    }


    public Usuario(int id,String cpf, String nome,String sexo, String telefone, String email, String data_de_nascimento, String senha) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.sexo = sexo;
        this.telefone = telefone;
        this.email = email;
        this.data_de_nascimento = data_de_nascimento;
        this.senha = senha;
    }

    public String getCpf() {

        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData_de_nascimento() {
        return data_de_nascimento;
    }

    public void setData_de_nascimento(String data_de_nascimento) {
        this.data_de_nascimento = data_de_nascimento;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
