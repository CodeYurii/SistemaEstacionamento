package com.sistema.estacionamento;

import com.sistema.estacionamento.modelo.VeiculoHistorico;

public class VeiculoHistoricoDao extends VeiculoDao<VeiculoHistorico> {
    @Override
    protected Class<VeiculoHistorico> getEntityClass() {return VeiculoHistorico.class;}
}
