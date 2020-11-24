package com.example.loginactivity.model;

import java.io.Serializable;

public class Tarefa implements Serializable, Comparable<Tarefa>  {
    private String titulo;
    private String descricao;
    private String categoria;

    private int dia;
    private int mes;
    private int ano;

    private String id;

    public Tarefa(String titulo, String categoria, String descricao, int dia, int mes, int ano) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.descricao = descricao;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public Tarefa() {
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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


    private long allTime(Tarefa tarefa) {
        long totalTime = tarefa.ano*365 + tarefa.getMes()*30 + tarefa.getDia();
        return totalTime;
    }

    public long allTimeThis() {
        long totalTime = this.ano*365 + this.getMes()*30 + this.getDia();
        return totalTime;
    }

    @Override
    public int compareTo(Tarefa outraTarefa) {
        if (allTime(this) > allTime(outraTarefa)) {
            return -1;
        }
        if (allTime(this) < allTime(outraTarefa)) {
            return 1;
        }
        return 0;
    }
}
