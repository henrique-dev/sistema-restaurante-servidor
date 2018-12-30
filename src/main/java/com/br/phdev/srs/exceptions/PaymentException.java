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
public class PaymentException extends Exception {

    public PaymentException() {
    }

    public PaymentException(String string) {
        super(string);
    }

    public PaymentException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public PaymentException(Throwable thrwbl) {
        super(thrwbl);
    }        
    
}
