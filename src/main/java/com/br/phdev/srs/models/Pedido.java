/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class Pedido {
    
    private long id;
    private double precoTotal;
    private Timestamp data;    
    private List<ItemFacil> itens;
    private FormaPagamento formaPagamento;    
    private Endereco endereco;
    private String status;

    public Pedido() {
    }

    public Pedido(long id, double precoTotal, Timestamp data, List<ItemFacil> itens, FormaPagamento formaPagamento, Endereco endereco, String status) {
        this.id = id;
        this.precoTotal = precoTotal;
        this.data = data;
        this.itens = itens;
        this.formaPagamento = formaPagamento;
        this.endereco = endereco;
        this.status = status;
    }     

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public List<ItemFacil> getItens() {
        return itens;
    }

    public void setItens(List<ItemFacil> itens) {
        this.itens = itens;
    }        

    public void convertItemParaItemFacil(List<Item> itens) {
        this.itens = new ArrayList<>();
        for (Item item : itens) {
            this.itens.add(new ItemFacil(item));
        }
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }        

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }      

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }        
    
}
