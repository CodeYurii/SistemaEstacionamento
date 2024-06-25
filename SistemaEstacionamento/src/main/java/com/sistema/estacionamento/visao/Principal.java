package com.sistema.estacionamento.visao;

import com.sistema.estacionamento.LeitorPlaca.LeitorPlaca;
import com.sistema.estacionamento.controller.VeiculoController;
import com.sistema.estacionamento.modelo.VeiculoHistorico;
import com.sistema.estacionamento.modelo.VeiculoPatio;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import static com.sistema.estacionamento.LeitorPlaca.LeitorPlaca.lerPlaca;


public class Principal {
    private JPanel Principal;
    private JPanel totalPagar;
    private JPanel tempoEstadia;
    private JScrollPane painelAtivos;
    private JButton novaEntradaButton;
    private JButton registrarSaidaButton;
    private JButton alterarEntradaButton;
    private JButton historicoButton;
    private JButton pagamentoEmDinheiroButton;
    private JButton pagamentoViaPixButton;
    private JButton pagamentoViaCartaoButton;
    private JButton atualizarPatio;
    private JTextField txtPlaca;
    private JTextField txtMarca;
    private JTextField txtModelo;
    private JTextField txtCor;
    private JTextField txtObservacao;
    private JTable table1;
    private JLabel totalPagamento;
    private JLabel totalHora;
    private JButton imprimirButton;
    private JLabel horario;
    private JPanel horarioPainel;
    private JLabel telaHoraEntrada;
    private JLabel telaHoraSaida;
    private JPanel imagem;
    private JLabel icone;
    private JButton capturaAutomatica;
    private VeiculoController controller;
    private VeiculoPatio novoVeiculoPatio;
    private VeiculoHistorico veiculoHistorico;
    private DefaultTableModel model;
    List<VeiculoPatio>lista;


    public Principal() {

        //Inicia o relógio
        atualizarRelogio();
        iniciarTimerRelogio();
        setImagem();
        try {
            Font digitalFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src\\main\\resources\\Font\\digital-7.ttf")).deriveFont( 90f);
            horario.setFont(digitalFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        //instancia um controller para manipulação
        controller = new VeiculoController(this);

        //ActionListener Botão Entrada
        novaEntradaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Obtendo os textos das textfields
                String placa = txtPlaca.getText();
                String marca = txtMarca.getText();
                String modelo = txtModelo.getText();
                String cor = txtCor.getText();
                String observacao = txtObservacao.getText();

                //Validação se Textfields estão vazias
                if(placa.trim().isEmpty()){
                    mensagemErroValidacao("Placa Obrigatório!");
                    return;
                }
                if(marca.trim().isEmpty()){
                    mensagemErroValidacao("Marca Obrigatório!");
                    return;
                }
                if(modelo.trim().isEmpty()){
                    mensagemErroValidacao("Modelo Obrigatório!");
                    return;
                }
                if(cor.trim().isEmpty()){
                    mensagemErroValidacao("Cor Obrigatório!");
                    return;
                }

                //Validação das regras de negócio das Textfields
                if (!controller.isPlacaValida(placa)) {
                    mensagemErroValidacao("Placa inválida - Preencha neste formato: ABC1234");
                    return;
                }
                if (!controller.isMarcaValida(marca)) {
                    mensagemErroValidacao("Marca não deve usar  número caracteres especiais");
                    return;
                }
                if (!controller.isModeloValido(modelo)) {
                    mensagemErroValidacao("Modelo não deve ser vazio");
                    return;
                }
                if (!controller.isCorValida(cor)) {
                    mensagemErroValidacao("Cor não deve possuir números ou caracteres especiais");
                    return;
                }

                //Metodos para Criar o Veículo e Inserir no BD
                novoVeiculoPatio = controller.criarVeiculo(placa, marca, modelo, cor, observacao);
                if (controller.isPlacaExiste(novoVeiculoPatio.getPlaca())){
                    controller.novaEntrada(novoVeiculoPatio);
                } else {
                    mensagemErroValidacao("Placa já existente no pátio");
                    return;
                }
                if (novoVeiculoPatio != null) {
                    JOptionPane.showMessageDialog(null, "Veículo registrado com sucesso!",
                            "Sucesso na operação", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //ActionListener Botão Saída
        registrarSaidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = txtPlaca.getText();

                //Validação se a Textfield está vazia
                if(placa.trim().isEmpty()){
                    mensagemErroValidacao("Placa Obrigatório!");
                    return;
                }

                //Validação da regra de negócio da Placa
                if (!controller.isPlacaValida(placa)) {
                    mensagemErroValidacao("Placa inválida - Preencha neste formato: ABC1234");
                    return;
                }

                //Registrando a saída
                novoVeiculoPatio = controller.registrarSaida(placa);

                //Carregando informações na tela
                if (novoVeiculoPatio != null) {
                    String valorPagamento = String.valueOf(novoVeiculoPatio.getValorPagamentoFormatado());
                    String horaEstadia = String.valueOf(novoVeiculoPatio.getTempoEstadia());
                    String horaEntrada = novoVeiculoPatio.getHoraEntradaFormatada();
                    String horaSaida = novoVeiculoPatio.getHoraSaidaFormatada();
                    totalPagamento.setText("$" + valorPagamento);
                    totalHora.setText(horaEstadia + "h");
                    telaHoraEntrada.setText(horaEntrada + "h");
                    telaHoraSaida.setText(horaSaida + "h");
                    JOptionPane.showMessageDialog(null, "Selecione a forma de pagamento",
                            "Forma de Pagamento", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhum veículo encontrado com a placa especificada "
                            + "ou já foi registrado à saída do veículo .", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //ActionListener Botão Atualizar Pátio
        atualizarPatio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.atualizarPatio();
            }
        });

        //ActionListener Botão Alterar Entrada
        alterarEntradaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = txtPlaca.getText();
                String marca = txtMarca.getText();
                String modelo = txtModelo.getText();
                String cor = txtCor.getText();
                String observacao = txtObservacao.getText();

                if (placa.trim().isEmpty()) {
                    mensagemErroValidacao("Placa não deve ser vazia");
                    return;
                } else if (!placa.matches("[A-Za-z0-9]{7}")) {
                    mensagemErroValidacao("Placa inválida - Preencha neste formato: abc1234");
                    return;
                }

                novoVeiculoPatio = controller.consultarVeiculo(placa);

                if (novoVeiculoPatio == null){
                    mensagemErroValidacao("Erro nos dados do veículo");
                    return;
                }
                if (!marca.trim().isEmpty() && marca.matches("[A-Za-z0-9]")) {
                    novoVeiculoPatio.setMarca(marca);
                } else if(!marca.matches("[A-Za-z0-9]")){
                    mensagemErroValidacao("Preencha Marca corretamente!");
                    return;
                }
                if(!modelo.trim().isEmpty()) {
                    novoVeiculoPatio.setModelo(modelo);
                }
                if(!cor.trim().isEmpty() && cor.matches("[A-Za-z0-9]")) {
                    novoVeiculoPatio.setCor(cor);
                } else if(!cor.matches("[A-Za-z0-9]")){
                    mensagemErroValidacao("Preencha Cor corretamente!");
                    return;
                }
                controller.atualizarVeiculoPatio(novoVeiculoPatio);
                JOptionPane.showMessageDialog(null,"Entrada alterada com Sucesso!",
                        "Alterar Entrada", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //ActionListener Botão Histórico
        historicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.atualizarHistorico();
            }
        });

        //ActionListener Botão Pagamento em Dinheiro
        pagamentoEmDinheiroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = txtPlaca.getText();
                if (placa.trim().isEmpty()) {
                    mensagemErroValidacao("Placa não deve ser vazia");
                    return;
                } else if (!placa.matches("[A-Za-z0-9]{7}")) {
                    mensagemErroValidacao("Placa inválida - Preencha neste formato: abc1234");
                    return;
                }
                novoVeiculoPatio = controller.consultarVeiculo(placa);
                if (novoVeiculoPatio == null){
                    mensagemErroValidacao("Veículo não encontrado, digite uma placa válida");
                    return;
                } else if ( novoVeiculoPatio.getHoraSaida() == null){
                    mensagemErroValidacao("Veículo não registrou a saída");
                    return;
                }
                controller.pagamentoDinheiro(placa);
                JOptionPane.showMessageDialog(null,"Pagamento em Dinheiro recebido com sucesso!",
                        "Pagamento em Dinherio", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        painelAtivos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        ////ActionListener Botão Pagamento via Pix
        pagamentoViaPixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = txtPlaca.getText();
                if (placa.trim().isEmpty()) {
                    mensagemErroValidacao("Placa não deve ser vazia");
                    return;
                } else if (!placa.matches("[A-Za-z0-9]{7}")) {
                    mensagemErroValidacao("Placa inválida - Preencha neste formato: abc1234");
                    return;
                }
                if (novoVeiculoPatio == null){
                    return;
                }  else if ( novoVeiculoPatio.getHoraSaida() == null){
                    mensagemErroValidacao("Veículo não registrou a saída");
                    return;
                }
                else {
                    controller.pagamentoPix(placa);
                    JOptionPane.showMessageDialog(null,"Pagamento via Pix recebido com sucesso!",
                            "Pagamento via Pix", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //ActionListener Botão Pagamento via Cartão
        pagamentoViaCartaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = txtPlaca.getText();
                if (placa.trim().isEmpty()) {
                    mensagemErroValidacao("Placa não deve ser vazia");
                    return;
                } else if (!placa.matches("[A-Za-z0-9]{7}")) {
                    mensagemErroValidacao("Placa inválida - Preencha neste formato: abc1234");
                    return;
                }
                if (novoVeiculoPatio == null){
                    return;
                } else if ( novoVeiculoPatio.getHoraSaida() == null){
                    mensagemErroValidacao("Veículo não registrou a saída");
                    return;
                } else {
                    controller.pagamentoCartao(placa);
                    JOptionPane.showMessageDialog(null,"Pagamento via Cartão recebido com sucesso!",
                            "Pagamento via Cartão", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //ActionListener Botão Imprimir
        imprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String placa = txtPlaca.getText();
                if (placa.trim().isEmpty()) {
                    mensagemErroValidacao("Placa não deve ser vazia");
                    return;
                } else if (!placa.matches("[A-Za-z0-9]{7}")) {
                    mensagemErroValidacao("Placa inválida - Preencha neste formato: abc1234");
                    return;
                }
                veiculoHistorico = controller.consultarVeiculoRecibo(placa);
                if (veiculoHistorico != null) {
                    String entrada = veiculoHistorico.getHoraEntradaFormatada();
                    String saida = veiculoHistorico.getHoraSaidaFormatada();
                    String valor = veiculoHistorico.getValorPagamentoFormatado();
                    String tempoEstadia = String.valueOf(veiculoHistorico.getTempoEstadia());
                    String formaPagamento = String.valueOf(veiculoHistorico.getFormaPagamento());
                    controller.imprimirRecibo(veiculoHistorico);
                } else {
                    mensagemErroValidacao("Veiculo não encontrado!");
                    return;
                }
            }
        });

        //ActionListener Botão Captura Automática
        capturaAutomatica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LeitorPlaca leitura = new LeitorPlaca();
                String caminhoImagem = "src\\main\\resources\\Placas\\1.png";
                txtPlaca.setText(lerPlaca(caminhoImagem));
                setImagemCaptura();
            }
        });
    }

    public void setTelaEstadia(String tempoEstadia){
        totalHora.setText(tempoEstadia);
    }

    public void setImagem(){
        ImageIcon icone1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Imagens/3.png")));
        icone.setIcon(icone1);
    }

    public void setImagemCaptura(){
        String caminhoImagem = "src\\main\\resources\\Placas\\1.png";
        try {
            BufferedImage imagemPlaca = ImageIO.read(new File(caminhoImagem));
            int novaLargura = painelAtivos.getWidth();
            int novaAltura = painelAtivos.getHeight();
            Image imagemRedimensionada = imagemPlaca.getScaledInstance(novaLargura, novaAltura, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(imagemRedimensionada);
            JLabel label = new JLabel(icon);
            painelAtivos.setViewportView(label);
            painelAtivos.revalidate();
            painelAtivos.repaint();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void atualizarRelogio() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        horario.setText(sdf.format(new Date()));
    }

    private void iniciarTimerRelogio() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarRelogio();
            }
        });
        timer.start();
    }

    public void telaAtualizar(DefaultTableModel model){
        this.table1.setModel(model);
        painelAtivos.setViewportView(this.table1);
    }

    public void mensagemErroValidacao(String mensagem){
        JOptionPane.showMessageDialog(null, mensagem,
                "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    }

    public VeiculoPatio criarVeiculo(){
        String placa = txtPlaca.getText();
        if (placa.trim().isEmpty()) {
           mensagemErroValidacao("Placa não deve ser vazia");
            return null;
        } else if (!placa.matches("[A-Za-z0-9]{7}")) {
            mensagemErroValidacao("Placa inválida - Preencha neste formato: abc1234");
            return null;
        }
        String marca = txtMarca.getText();
        if(marca.trim().isEmpty()) {
            mensagemErroValidacao("Marca não deve ser vazio ou usar caracteres especiais");
            return null;

        } else if (!marca.matches("[a-zA-Z ]+")) {
            mensagemErroValidacao("Marca não deve usar  número caracteres especiais");
            return null;
        }

        String modelo = txtModelo.getText();
        if(modelo.trim().isEmpty()){
            mensagemErroValidacao("Modelo não deve ser vazio");
            return null;
        }
        String cor = txtCor.getText();
        if(cor.trim().isEmpty()) {
            mensagemErroValidacao("Cor não deve ser vazio");
            return null;
        } else if (!cor.matches("[a-zA-Z ]+")){
            mensagemErroValidacao("Cor não deve possuir números ou caracteres especiais");
            return null;
        }
        String observacao = txtObservacao.getText();
        return new VeiculoPatio(placa, marca, modelo, cor, observacao);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Principal");
        frame.setContentPane(new Principal().Principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
