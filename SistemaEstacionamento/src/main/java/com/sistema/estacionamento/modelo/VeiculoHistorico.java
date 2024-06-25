package com.sistema.estacionamento.modelo;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class VeiculoHistorico extends Veiculo {
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


    public VeiculoHistorico() {}

    public VeiculoHistorico(VeiculoPatio veiculoPatio) {
        this.placa = veiculoPatio.getPlaca();
        this.marca = veiculoPatio.getMarca();
        this.modelo = veiculoPatio.getModelo();
        this.cor = veiculoPatio.getCor();
        this.observacao = veiculoPatio.getObservacao();
        this.horaEntrada = veiculoPatio.getHoraEntrada();
        this.horaSaida = veiculoPatio.getHoraSaida();
        this.valorPagamento = veiculoPatio.getValorPagamento();
        this.tempoEstadia = veiculoPatio.getTempoEstadia();
        this.statusPagamento = veiculoPatio.getStatusPagamento();
        this.formaPagamento = veiculoPatio.getFormaPagamento();
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
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

    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(LocalDateTime horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Double getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento(Double valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    public String getValorPagamentoFormatado() {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(valorPagamento);
    }

    public Long getTempoEstadia() {
        return tempoEstadia;
    }

    public void setTempoEstadia(Long tempoEstadia) {
        this.tempoEstadia = tempoEstadia;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public String getHoraEntradaFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return horaEntrada.format(formatter);
    }

    public String getHoraSaidaFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return horaSaida.format(formatter);
    }
}
