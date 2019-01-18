/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.models;

import java.util.HashSet;
import java.util.List;
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
    private Set<Ingrediente> ingredientes;
    private List<GrupoVariacao> variacoes;

    public ItemFacil() {
    }

    public ItemFacil(Item item) {
        this.id = item.getId();
        this.nome = item.getNome();
        this.preco = item.getPreco();
        this.variacoes = item.getVariacoes();
        this.ingredientes = item.getIngredientes();
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

    public List<GrupoVariacao> getVariacoes() {
        return variacoes;
    }

    public void setVariacoes(List<GrupoVariacao> variacoes) {
        this.variacoes = variacoes;
    }    

    public Set<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(Set<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }        
            
}
