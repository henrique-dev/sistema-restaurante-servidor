/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.phdev.srs.exceptions;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class DAOExpectedException extends DAOException {        
    
    public DAOExpectedException(String message, int codigo) {
        super(message, codigo);
    }        
    
}
