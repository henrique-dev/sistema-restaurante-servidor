/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.utils;

import com.br.phdev.srs.exceptions.PaymentException;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class ServicoPagamento {   

    private final String clientId = "ASzGEc_oF__CRXHexloOprXZUaIAQkpZx4t7KSs9Eqx14RLQR8rhUNUkq2OOob1HdcEQ0Pay_rP_Dz92";
    private final String secret = "ECq12WJhFwanQJTN6jELYm2WGf2rLfZ89aVBIRz_Mbexzu-2HzI6TplLbZbDbfpGXoW5FPbXDdHM-GGl";

    public Payment criarPagamento(String valorTotal, int metodoPagamento) throws PaymentException {

        String method = "";
        switch (metodoPagamento) {
            case 1:
                method = "paypal";
                break;
            case 2:
                method = "credit_card";
                break;
            case 3:
                method = "pay_upon_invoice";
                break;
            case 4:
                method = "carrier";
                break;
            case 5:
                method = "alternate_payment";
                break;
            case 6:
                method = "bank";
                break;

        }

        Amount amount = new Amount();
        amount.setCurrency("BRL");
        amount.setTotal(valorTotal);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        //Payer payer = new Payer();
        //payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent("sale");
        //payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://35.202.51.59/mrfood/pagamentos/cancelar-pagamento");
        redirectUrls.setReturnUrl("http://35.202.51.59/mrfood/pagamentos/executar-pagamento");
        payment.setRedirectUrls(redirectUrls);

        try {
            APIContext apiContext = new APIContext(clientId, secret, "sandbox");
            Payment createdPayment = payment.create(apiContext);
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
            System.out.println("Id do comprador: " + payerID);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            throw new PaymentException(e);
        }
    }

}
