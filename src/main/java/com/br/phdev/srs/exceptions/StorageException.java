/* Copyright (C) HeadRed, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gona�alves Bacelar <henrique.phgb@gmail.com>, Agosto 2018
 */
package com.br.phdev.srs.exceptions;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar
 */
public class StorageException extends Exception{
    
    public StorageException(Exception e) {
        super(e);
    }
    
    public StorageException(String message) {
        super(message);
    }
    
    public StorageException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
