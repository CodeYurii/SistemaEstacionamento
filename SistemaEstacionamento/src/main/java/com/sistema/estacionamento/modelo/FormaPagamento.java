package com.sistema.estacionamento.modelo;

public enum FormaPagamento {

    CARTAO("Cartão"),
    DINHEIRO("Dinheiro"),
    PIX("Pix");

    private String formaPagamento;

    FormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }
}
