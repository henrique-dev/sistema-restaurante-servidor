/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.models;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class GrupoVariacao {
        
    private String nome;
    private int max;
    private Set<Variacao> variacoes;        

    public GrupoVariacao() {
    }

    public GrupoVariacao(String nome, int max, Set<Variacao> variacoes) {
        this.nome = nome;
        this.max = max;
        this.variacoes = variacoes;
    }             

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }        

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Set<Variacao> getVariacoes() {
        return variacoes;
    }

    public void setVariacoes(Set<Variacao> variacoes) {
        this.variacoes = variacoes;
    }       
    
}
