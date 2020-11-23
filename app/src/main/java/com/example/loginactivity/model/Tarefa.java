package com.example.loginactivity.model;

import java.io.Serializable;

public class Tarefa implements Serializable, Comparable<Tarefa> {
    private String titulo;
    private String data;
    private String descricao;
    private String categoria;
    private int dias;

    private String id;

    public Tarefa(String titulo, String data, String categoria, String descricao, int dias) {
        this.titulo = titulo;
        this.data = data;
        this.categoria = categoria;
        this.descricao = descricao;
        this.dias = dias;
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

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    @Override
    public int compareTo(Tarefa outraTarefa) {
        if (this.dias > outraTarefa.getDias()) {
            return -1;
        } if (this.dias < outraTarefa.getDias()) {
            return 1;
        }
        return 0;
    }
}
