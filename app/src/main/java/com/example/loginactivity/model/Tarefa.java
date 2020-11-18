package com.example.loginactivity.model;

import java.io.Serializable;

public class Tarefa implements Serializable {
    private String titulo;
    private String data;
    private String descricao;
    private String categoria;
    private String id;

    public Tarefa(String titulo, String data, String descricao, String categoria) {
        this.titulo = titulo;
        this.data = data;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public Tarefa() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
