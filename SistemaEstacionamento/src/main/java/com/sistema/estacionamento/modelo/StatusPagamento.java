package com.sistema.estacionamento.modelo;

public enum StatusPagamento {
    ABERTO("Aberto"),
    PAGO("Pago");

    private String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {return descricao;}
}
