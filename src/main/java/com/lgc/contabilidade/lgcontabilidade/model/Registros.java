package com.lgc.contabilidade.lgcontabilidade.model;

import com.lgc.contabilidade.lgcontabilidade.controller.MainController;

import java.time.Duration;

public class Registros {

    private static int id = 0;
    private String data;
    private String horaEntrada;
    private String horaSaidaAlmoco;
    private String horaVoltaAlmoco;
    private String horaSaidaCasa;
    private String totalExtras;
    private String totalHoras;

    public Registros(String data, String horaEntrada, String horaSaidaAlmoco, String horaVoltaAlmoco, String horaSaidaCasa, String totalHoras, String totalExtras) {
        this.data = data;
        this.horaEntrada = horaEntrada;
        this.horaSaidaAlmoco = horaSaidaAlmoco;
        this.horaVoltaAlmoco = horaVoltaAlmoco;
        this.horaSaidaCasa = horaSaidaCasa;
        this.totalExtras = totalExtras;
        this.totalHoras = totalHoras;
        id++;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSaidaAlmoco() {
        return horaSaidaAlmoco;
    }

    public void setHoraSaidaAlmoco(String horaSaidaAlmoco) {
        this.horaSaidaAlmoco = horaSaidaAlmoco;
    }

    public String getHoraVoltaAlmoco() {
        return horaVoltaAlmoco;
    }

    public void setHoraVoltaAlmoco(String horaVoltaAlmoco) {
        this.horaVoltaAlmoco = horaVoltaAlmoco;
    }

    public String getHoraSaidaCasa() {
        return horaSaidaCasa;
    }

    public void setHoraSaidaCasa(String horaSaidaCasa) {
        this.horaSaidaCasa = horaSaidaCasa;
    }

    public String getTotalExtras() {
        return totalExtras;
    }

    public void setTotalExtras(String totalExtras) {
        this.totalExtras = totalExtras;
    }

    public String getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(String totalHoras) {
        this.totalHoras = totalHoras;
    }
}