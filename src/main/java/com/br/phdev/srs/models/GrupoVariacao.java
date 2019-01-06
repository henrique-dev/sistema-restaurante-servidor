/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.models;

import java.util.HashSet;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class GrupoVariacao {
        
    private int max;
    private HashSet<Variacao> variacoes;        

    public GrupoVariacao() {
    }

    public GrupoVariacao(int max, HashSet<Variacao> variacoes) {        
        this.max = max;
        this.variacoes = variacoes;
    }            

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public HashSet<Variacao> getVariacoes() {
        return variacoes;
    }

    public void setVariacoes(HashSet<Variacao> variacoes) {
        this.variacoes = variacoes;
    }        
    
}
