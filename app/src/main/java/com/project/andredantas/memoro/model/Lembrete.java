package com.project.andredantas.memoro.model;

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
    private long alarmeDia;
    private long alarmeHora;
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

    public long getAlarmeDia() {
        return alarmeDia;
    }

    public void setAlarmeDia(long alarmeDia) {
        this.alarmeDia = alarmeDia;
    }

    public long getAlarmeHora() {
        return alarmeHora;
    }

    public void setAlarmeHora(long alarmeHora) {
        this.alarmeHora = alarmeHora;
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
}
