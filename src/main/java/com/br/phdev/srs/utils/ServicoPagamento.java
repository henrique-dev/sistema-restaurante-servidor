/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.utils;

import com.br.phdev.srs.exceptions.PaymentException;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Event;
import com.paypal.api.payments.EventType;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentCard;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.api.payments.Webhook;
import com.paypal.base.Constants;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class ServicoPagamento {

    private final String clientId = "AQRyK3MPSQHRz7VR0M0-TrsY67UFDltY90PogemFDlTFgMqNf21NlBLOCmiMxCLIOJIzQlK6FElahJa-";
    private final String secret = "ED_LIsYNSGmfrTCXegWKAqK0VJkJ5YF0BUX60d8hc2aJL6QCxoEFZXtZv4TXrIKuq5VRtzr_L_Sk2r5Q";

    public Payment criarPagamento(String valorTotal) throws PaymentException {

        Amount amount = new Amount();
        amount.setCurrency("BRL");
        amount.setTotal(valorTotal);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://35.202.51.59/mrfood/pagamentos/cancelar-pagamento");
        redirectUrls.setReturnUrl("http://35.202.51.59/mrfood/pagamentos/retorno-pagamento");        
        payment.setRedirectUrls(redirectUrls);

        try {
            APIContext apiContext = new APIContext(clientId, secret, "sandbox");
            Payment createdPayment = payment.create(apiContext);
            System.out.println(createdPayment.toString());
            return createdPayment;
        } catch (PayPalRESTException e) {            
            throw new PaymentException(e);
        }        
    }

    public void executarPagamento(String paymentID, String payerID) throws PaymentException {
        try {
            APIContext apiContext = new APIContext(clientId, secret, "sandbox");
            Payment payment = new Payment();
            payment.setId(paymentID);
            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(payerID);           
            payment.execute(apiContext, paymentExecution);                                                            
        } catch (PayPalRESTException e) {
            throw new PaymentException(e);
        }
    }
    
    public void aguadarConfirmacao() throws PayPalRESTException {
        
    }
    
    public static void main(String[] args) {
        try {
            new ServicoPagamento().aguadarConfirmacao();
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
    }

}
