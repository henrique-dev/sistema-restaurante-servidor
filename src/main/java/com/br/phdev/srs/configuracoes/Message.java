/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.configuracoes;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class Message {
    
    private String from;
    private String to;   
    private String text;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }        

    @Override
    public String toString() {
        return "Message{" + "from=" + from + ", text=" + text + '}';
    }        
    
}
