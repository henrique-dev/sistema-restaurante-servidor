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
    private Endereco endereco;
    private Pedido pedido;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    @Override
    public String toString() {
        return "ExecutarPagamento{" + "nome=" + nome + ", cpf=" + cpf + ", data=" + data + ", telefone=" + telefone + ", endereco=" + endereco + ", pedido=" + pedido + ", cliente=" + cliente + ", tokenSessao=" + tokenSessao + ", tokenCartao=" + tokenCartao + ", hashCliente=" + hashCliente + '}';
    }
                
}
