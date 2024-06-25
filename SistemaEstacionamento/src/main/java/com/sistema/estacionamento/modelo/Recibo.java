package com.sistema.estacionamento.modelo;

import java.awt.*;
import java.awt.print.*;

public class Recibo implements Printable {
    private String entrada;
    private String saida;
    private String valorPago;
    private String placa;
    private String tempoEstadia;
    private String formaPagamento;

    public Recibo(String placa, String entrada, String saida, String tempoEstadia, String valorPago, String formaPagamento) {
        this.entrada = entrada;
        this.saida = saida;
        this.valorPago = valorPago;
        this.placa = placa;
        this.tempoEstadia = tempoEstadia;
        this.formaPagamento = formaPagamento;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        if (page > 0) { // Apenas uma página
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        // Definindo o conteúdo do recibo
        g.drawString("********** Estacionamento **********", 10, 30);
        g.drawString("Placa: " + placa, 10, 50);
        g.drawString("Entrada: " + entrada, 10, 70);
        g.drawString("Saída: " + saida, 10, 90);
        g.drawString("Tempo Estadia: " + tempoEstadia + "h", 10, 110);
        g.drawString("Valor Pago: R$" + valorPago, 10, 130);
        g.drawString("Pago via: " + formaPagamento, 10, 150);
        g.drawString("***********************************", 10, 170);
        g.drawString("Obrigado por usar nosso serviço!", 10, 190);

        return PAGE_EXISTS;
    }
}
