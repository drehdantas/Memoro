package com.project.andredantas.memoro.model;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andre Dantas on 7/14/16.
 */
public class HorarioNormal{
    public static String AZUL = "azul";
    public static String VERMELHO = "vermelho";
    public static String AMARELO = "amarelo";
    public static String VERDE = "verde";

    private long id;
    private String dia;
    private String titulo;
    private String descricao;
    private String tempo;
    private String cor;
    private int hora;
    private int minutos;
    private boolean active = true;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public static List<HorarioNormal> convertFromRealm(List<Horario> listRealm){
        List<HorarioNormal> horariosNormais = new ArrayList<>();
        for (Horario horario: listRealm) {
            horariosNormais.add(HorarioNormal.copyFromRealm(horario));
        }
        return horariosNormais;
    }

    private static HorarioNormal copyFromRealm(Horario horario){
        HorarioNormal horarioNormal = new HorarioNormal();
        horarioNormal.setActive(horario.isActive());
        horarioNormal.setMinutos(horario.getMinutos());
        horarioNormal.setHora(horario.getHora());
        horarioNormal.setDia(horario.getDia());
        horarioNormal.setCor(horario.getCor());
        horarioNormal.setDescricao(horario.getDescricao());
        horarioNormal.setId(horario.getId());
        horarioNormal.setTempo(horario.getTempo());
        horarioNormal.setTitulo(horario.getTitulo());
        return horarioNormal;
    }

    public static List<Horario> convertFromNormal(List<HorarioNormal> listNormal){
        List<Horario> horariosRealm = new ArrayList<>();
        for (HorarioNormal horarioNormal: listNormal) {
            horariosRealm.add(HorarioNormal.copyFromNormal(horarioNormal));
        }
        return horariosRealm;
    }

    private static Horario copyFromNormal(HorarioNormal horarioNormal){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Horario horario = new Horario();
        horarioNormal.setActive(horario.isActive());
        horarioNormal.setMinutos(horario.getMinutos());
        horarioNormal.setHora(horario.getHora());
        horarioNormal.setDia(horario.getDia());
        horarioNormal.setCor(horario.getCor());
        horarioNormal.setDescricao(horario.getDescricao());
        horarioNormal.setId(horario.getId());
        horarioNormal.setTempo(horario.getTempo());
        horarioNormal.setTitulo(horario.getTitulo());
        realm.commitTransaction();
        return horario;
    }
}
