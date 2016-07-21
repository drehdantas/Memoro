package com.project.andredantas.memoro.model;

import java.io.File;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andre Dantas on 7/14/16.
 */
public class Lembrete extends RealmObject {
    @PrimaryKey
    private long id;
    private String titulo;
    private String descricao;
    private String cor;
    private String type;
    private String diaAlarme;
    private String mesAlarme;
    private String tempo;
    private int hora;
    private int minutos;
    private long horarioRelacionado;
    private boolean active = true;

    private String imagem;
    private String audio;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }


    public long getHorarioRelacionado() {
        return horarioRelacionado;
    }

    public void setHorarioRelacionado(long horarioRelacionado) {
        this.horarioRelacionado = horarioRelacionado;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiaAlarme() {
        return diaAlarme;
    }

    public void setDiaAlarme(String diaAlarme) {
        this.diaAlarme = diaAlarme;
    }

    public String getMesAlarme() {
        return mesAlarme;
    }

    public void setMesAlarme(String mesAlarme) {
        this.mesAlarme = mesAlarme;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }
}
