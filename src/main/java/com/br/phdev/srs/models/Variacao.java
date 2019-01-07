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
public class Variacao {
    
    private long id;
    private String nome;
    private double preco;    

    public Variacao() {
    }

    public Variacao(long id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }            

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }        

    @Override
    public String toString() {
        return "Variacao{" + "id=" + id + ", nome=" + nome + ", preco=" + preco + '}';
    }      
            
}
