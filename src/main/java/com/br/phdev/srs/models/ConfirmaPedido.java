/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.models;

import java.util.List;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class ConfirmaPedido {
        
    private List<ItemPedido> itens;
    private List<FormaPagamento> formaPagamentos;
    private List<Endereco> enderecos;
    private double precoTotal;

    public ConfirmaPedido() {
    }

    public ConfirmaPedido(List<ItemPedido> itens, List<FormaPagamento> formaPagamentos, List<Endereco> enderecos, double precoTotal) {
        this.itens = itens;
        this.formaPagamentos = formaPagamentos;
        this.enderecos = enderecos;
        this.precoTotal = precoTotal;
    }      

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }    

    public List<FormaPagamento> getFormaPagamentos() {
        return formaPagamentos;
    }

    public void setFormaPagamentos(List<FormaPagamento> formaPagamentos) {
        this.formaPagamentos = formaPagamentos;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
    
    public void calcularPrecoTotal(double frete) {
        this.precoTotal += frete;        
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }        

    public double getPrecoTotal() {
        return precoTotal;
    }      

    @Override
    public String toString() {
        return "ConfirmaPedido{" + "itens=" + itens + ", formaPagamentos=" + formaPagamentos + ", enderecos=" + enderecos + '}';
    }   
        
}
