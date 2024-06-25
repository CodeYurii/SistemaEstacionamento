package com.sistema.estacionamento.controller;

import com.sistema.estacionamento.VeiculoHistoricoDao;
import com.sistema.estacionamento.VeiculoPatioDao;
import com.sistema.estacionamento.modelo.*;
import com.sistema.estacionamento.visao.Principal;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.List;

public class VeiculoController {
    private Principal view;
    private EntityManagerFactory emf;
    private List<VeiculoPatio> veiculoPatios;
    private VeiculoPatioDao veiculoDaoPatio;
    private VeiculoHistoricoDao veiculoDaoHistorico;

    public VeiculoController(Principal view) {
        this.view = view;
        this.emf = Persistence.createEntityManagerFactory("sistemaEstacionamento");
        veiculoDaoPatio = new VeiculoPatioDao();
        veiculoDaoHistorico = new VeiculoHistoricoDao();
    }

    // Métodos que registra uma nova entrada de veículo
    public void novaEntrada(VeiculoPatio veiculoPatio){
        veiculoDaoPatio.salvarVeiculo(veiculoPatio);
    }

    public boolean isPlacaExiste(String placa){
        if (veiculoDaoPatio.consultarVeiculoPorPlaca(placa) == null){
            return true;
        }
        return false;

    }
    public VeiculoPatio criarVeiculo(String placa, String marca, String modelo, String cor, String observacao){
        return new VeiculoPatio(placa, marca, modelo, cor, observacao);
    }

    // Métodos que registra uma saída de veículo
    public VeiculoPatio registrarSaida(String placa){
        VeiculoPatio veiculoPatio = veiculoDaoPatio.consultarVeiculoPorPlaca(placa);
        if (veiculoPatio != null) {
            veiculoPatio.setHoraSaida();
            veiculoPatio.setTempoEstadia();
            veiculoPatio.setValorPagamento();
            try {
                veiculoDaoPatio.atualizarVeiculo(veiculoPatio);
                view.setTelaEstadia(String.valueOf(veiculoPatio.getTempoEstadia()));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao atualizar veículo", e);
            }
        } else {
            throw new RuntimeException("Veículo não encontrado para a placa: " + placa);
        }
        return veiculoPatio;
    }

    // Método para atualizar a tela com os veículos presentes no pátio
    public void atualizarPatio(){
        veiculoPatios = veiculoDaoPatio.consultarVeiculos();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Placa", "Hora Entrada", "Marca", "Modelo", "Cor", "Observação", "Status Pagamento" }, 0);
        model.setRowCount(0);
        for (VeiculoPatio veiculoPatio : veiculoPatios) {
            model.addRow(new Object[]{veiculoPatio.getPlaca(), veiculoPatio.getHoraEntradaFormatada(), veiculoPatio.getMarca(), veiculoPatio.getModelo(),
                    veiculoPatio.getCor(), veiculoPatio.getObservacao(), veiculoPatio.getStatusPagamento()});
        }
        view.telaAtualizar(model);
    }
    // Método para atualizar a tela com o histórico de veículos
    public void atualizarHistorico() {
        List<VeiculoHistorico> veiculoHistoricos = veiculoDaoHistorico.consultarVeiculos();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Placa", "Hora Entrada", "Hora Saida", "Marca", "Modelo", "Cor",
                "Observação", "Tempo Estadia", "Status Pagamento", "Valor Pagamento", "Forma Pagamento"}, 0);
        model.setRowCount(0);
        for (VeiculoHistorico veiculoHistorico : veiculoHistoricos) {
            model.addRow(new Object[]{veiculoHistorico.getPlaca(), veiculoHistorico.getHoraEntradaFormatada(), veiculoHistorico.getHoraSaidaFormatada(), veiculoHistorico.getMarca(), veiculoHistorico.getModelo(),
                    veiculoHistorico.getCor(), veiculoHistorico.getObservacao(), veiculoHistorico.getTempoEstadia(), veiculoHistorico.getStatusPagamento(), veiculoHistorico.getValorPagamentoFormatado(), veiculoHistorico.getFormaPagamento()});
        }
        view.telaAtualizar(model);
    }

    // Método para atualizar um veículo do pátio
    public void atualizarVeiculoPatio(VeiculoPatio veiculoPatioAtualizado){
        veiculoDaoPatio.atualizarVeiculo(veiculoPatioAtualizado);
    }

    public VeiculoPatio consultarVeiculo(String placa){
        return veiculoDaoPatio.consultarVeiculoPorPlaca(placa);
    }

    // Métodos para Pagamento
    public void pagamentoDinheiro(String placa){
        VeiculoPatio veiculoPatio = veiculoDaoPatio.consultarVeiculoPorPlaca(placa);
        if (veiculoPatio != null) {
            veiculoPatio.setStatusPagamento("Pago");
            veiculoPatio.setFormaPagamento(FormaPagamento.DINHEIRO);
            veiculoDaoPatio.atualizarVeiculo(veiculoPatio);
            VeiculoHistorico veiculoHistorico = criarVeiculoHistorico(veiculoPatio);
            veiculoDaoHistorico.salvarVeiculo(veiculoHistorico);
            veiculoDaoPatio.excluirVeiculo(placa);
        } else {
            view.mensagemErroValidacao("Erro no pagamento");
        }
    }

    public void pagamentoPix(String placa){
        VeiculoPatio veiculoPatio = veiculoDaoPatio.consultarVeiculoPorPlaca(placa);
        if (veiculoPatio != null) {
            veiculoPatio.setStatusPagamento("Pago");
            veiculoPatio.setFormaPagamento(FormaPagamento.PIX);
            veiculoDaoPatio.atualizarVeiculo(veiculoPatio);
            VeiculoHistorico veiculoHistorico = criarVeiculoHistorico(veiculoPatio);
            veiculoDaoHistorico.salvarVeiculo(veiculoHistorico);
            veiculoDaoPatio.excluirVeiculo(placa);
        }
    }

    public void pagamentoCartao(String placa){
        VeiculoPatio veiculoPatio = veiculoDaoPatio.consultarVeiculoPorPlaca(placa);
        if (veiculoPatio != null) {
            veiculoPatio.setStatusPagamento("Pago");
            veiculoPatio.setFormaPagamento(FormaPagamento.CARTAO);
            veiculoDaoPatio.atualizarVeiculo(veiculoPatio);
            VeiculoHistorico veiculoHistorico = criarVeiculoHistorico(veiculoPatio);
            veiculoDaoHistorico.salvarVeiculo(veiculoHistorico);
            veiculoDaoPatio.excluirVeiculo(placa);
        }
    }

    // Validadores de Input
    public boolean isPlacaValida(String placa){
        if (placa.trim().isEmpty()) {
            return false;
        } else if (!placa.matches("[A-Za-z0-9]{7}")) {
            return false;
        }
        return true;
    }

    public boolean isMarcaValida(String marca){
        if (marca.trim().isEmpty()) {
            return false;
        } else if (!marca.matches("[a-zA-Z ]+")) {
            return false;
        }
        return true;
    }

    public boolean isModeloValido(String modelo){
        if (modelo.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean isCorValida(String cor){
        if (cor.trim().isEmpty()) {
            return false;
        } else if (!cor.matches("[a-zA-Z ]+")){
            return false;
        }
        return true;
    }

    public void registrarHistorico(VeiculoHistorico veiculo){
        veiculoDaoHistorico.salvarVeiculo(veiculo);
    }

    public VeiculoHistorico criarVeiculoHistorico(VeiculoPatio veiculoPatio){
        return new VeiculoHistorico(veiculoPatio);
    }

    public VeiculoHistorico consultarVeiculoRecibo(String placa){
        VeiculoHistorico veiculoHistorico = veiculoDaoHistorico.consultarVeiculoImpressao(placa);
        return veiculoHistorico;
    }

    public void imprimirRecibo(Veiculo veiculo) {
        String placa = veiculo.getPlaca();
        String entrada = veiculo.getHoraEntradaFormatada();
        String saida = (veiculo instanceof VeiculoHistorico) ? ((VeiculoHistorico) veiculo).getHoraSaidaFormatada() : "N/A";
        String tempoEstadia = veiculo.getTempoEstadia().toString(); // Supondo que toString() de Long seja adequado
        String valor = veiculo.getValorPagamentoFormatado();
        String formaPagamento = (veiculo instanceof VeiculoHistorico) ? ((VeiculoHistorico) veiculo).getFormaPagamento().toString() : "N/A";

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new Recibo(placa, entrada, saida, tempoEstadia, valor, formaPagamento));

        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(null, "Erro na impressão!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}