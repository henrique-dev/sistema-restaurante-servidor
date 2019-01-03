/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.teste;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class Teste {
    
    public static void main(String[] args) {
        String time = new SimpleDateFormat("dd/MM/YYYY").format(new Date());        
        System.out.println(time);
    }
    
}
