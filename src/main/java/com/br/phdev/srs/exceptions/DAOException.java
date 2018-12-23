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
    
    public DAOException(Exception e) {
        super(e);
    }
    
    public DAOException(String message) {
        super(message);
    }
    
    public DAOException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
