package com.project.andredantas.memoro.model;

import io.realm.RealmObject;

/**
 * Created by Andre Dantas on 7/14/16.
 */
public class Horario extends RealmObject{
    private String dia;
    private String titulo;
    private String descricao;
    private long hora;

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getHora() {
        return hora;
    }

    public void setHora(long hora) {
        this.hora = hora;
    }
}
