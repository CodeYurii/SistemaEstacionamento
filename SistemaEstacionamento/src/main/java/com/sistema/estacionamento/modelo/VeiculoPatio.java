package com.sistema.estacionamento.modelo;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class VeiculoPatio extends Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placa")
    private String placa;

    @Column(name = "marca")
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "cor")
    private String cor;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "hora_entrada")
    private LocalDateTime horaEntrada;

    @Column(name = "hora_saida")
    private LocalDateTime horaSaida;

    @Column(name = "valor_pagamento")
    private Double valorPagamento;

    @Column(name = "tempo_estadia")
    private Long tempoEstadia;

    @Column(name = "status_pagamento")
    private String statusPagamento;

    @Column(name = "forma_pagamento")
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    private static final double TARIFA = 5.00;

    public VeiculoPatio(){}

    public VeiculoPatio(String placa, String marca, String modelo, String cor, String observacao) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.observacao = observacao;
        this.horaEntrada = LocalDateTime.now();
        setStatusPagamento("Aberto");
    }

    public VeiculoPatio(String valorPagamento, String tempoEstadia) {
        this.valorPagamento = Double.valueOf(valorPagamento);
        this.tempoEstadia = Long.valueOf(tempoEstadia);
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public String getValorPagamentoFormatado() {
        DecimalFormat df = new DecimalFormat("#.00");
        return (df.format(valorPagamento));
    }

    public Double getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento() {
        this.valorPagamento = (this.tempoEstadia.doubleValue()) * TARIFA;
        if(this.valorPagamento == 0){
            this.valorPagamento += 5;
        }
    }

    public Long getTempoEstadia() {
        return tempoEstadia;
    }

    public void setTempoEstadia() {
        Duration duracao = Duration.between(horaEntrada, horaSaida);
        long minutos = duracao.toMinutes();
        this.tempoEstadia = (minutos + 59) / 60;
        if(this.tempoEstadia == 0){
            this.tempoEstadia += 1;
        }
    }



    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public String getHoraEntradaFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return horaEntrada.format(formatter);
    }

    public String getHoraSaidaFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return horaSaida.format(formatter);
    }

    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida() {
        this.horaSaida = LocalDateTime.now();
    }

    public String getMarca() {
        return marca;
    }


}
