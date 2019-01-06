/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.models;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class ItemFacil {
    
    private long id;
    private String nome;
    private double preco;
    private Set<ComplementoFacil> complementos;
    private Map<Long, Variacao> variacoes;

    public ItemFacil() {
    }

    public ItemFacil(Item item) {
        this.id = item.getId();
        this.nome = item.getNome();
        this.preco = item.getPreco();
        this.variacoes = item.getVariacoes();
        this.complementos = new HashSet<>();        
        if (item.getComplementos() != null)
            for (Complemento c : item.getComplementos()) {
                this.complementos.add(new ComplementoFacil(c.getId(), c.getPreco(), c.getNome()));
            }
    }    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Set<ComplementoFacil> getComplementos() {
        return complementos;
    }

    public void setComplementos(Set<ComplementoFacil> complementos) {
        this.complementos = complementos;
    }            

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }        

    public Map<Long, Variacao> getVariacoes() {
        return variacoes;
    }

    public void setVariacoes(Map<Long, Variacao> variacoes) {
        this.variacoes = variacoes;
    }       
    
}
