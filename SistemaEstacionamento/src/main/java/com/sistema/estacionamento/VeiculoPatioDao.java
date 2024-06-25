package com.sistema.estacionamento;

import com.sistema.estacionamento.modelo.VeiculoPatio;

public class VeiculoPatioDao extends VeiculoDao<VeiculoPatio> {
    @Override
    protected Class<VeiculoPatio> getEntityClass() {return VeiculoPatio.class;}
}
