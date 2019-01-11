/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.models;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class ExecutarPagamento {
        
    private String nome;
    private String cpf;
    private String data;
    private String telefone;
    private ConfirmaPedido confirmaPedido;
    private Cliente cliente;
    private String tokenSessao;
    private String tokenCartao;
    private String hashCliente;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public ConfirmaPedido getConfirmaPedido() {
        return confirmaPedido;
    }

    public void setConfirmaPedido(ConfirmaPedido confirmaPedido) {
        this.confirmaPedido = confirmaPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }        

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }        

    public String getTokenSessao() {
        return tokenSessao;
    }

    public void setTokenSessao(String tokenSessao) {
        this.tokenSessao = tokenSessao;
    }        

    public String getTokenCartao() {
        return tokenCartao;
    }

    public void setTokenCartao(String tokenCartao) {
        this.tokenCartao = tokenCartao;
    }

    public String getHashCliente() {
        return hashCliente;
    }

    public void setHashCliente(String hashCliente) {
        this.hashCliente = hashCliente;
    }        
    
}
