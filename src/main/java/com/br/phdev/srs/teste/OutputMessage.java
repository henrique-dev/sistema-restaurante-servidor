/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.teste;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class OutputMessage {

    private String from;
    private String text;
    private String time;

    public OutputMessage() {
    }

    public OutputMessage(String from, String text, String time) {
        this.from = from;
        this.text = text;
        this.time = time;
    }        

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }        

}
