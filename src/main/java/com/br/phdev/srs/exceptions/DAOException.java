/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.exceptions;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class DAOException extends Exception{
    
    public final int codigo;
    
    public DAOException(Exception e, int codigo) {
        super(e);
        this.codigo = codigo;
    }
    
    public DAOException(String message, int codigo) {
        super(message);
        this.codigo = codigo;
    }
    
    public DAOException(String message, Throwable throwable, int codigo) {
        super(message, throwable);        
        this.codigo = codigo;
    }
    
}
