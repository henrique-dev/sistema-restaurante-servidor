/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.phdev.srs.controladores;

import com.br.phdev.srs.exceptions.PaymentException;
import com.br.phdev.srs.utils.ServicoPagamento;
import com.google.gson.JsonObject;
import com.paypal.api.payments.Payment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
@Controller
public class PagamentoController {

    @PostMapping("pagamentos/criar-pagamento")
    public ResponseEntity<String> criarPagamento() {        
        Payment pagamentoCriado = new Payment();
        try {
            ServicoPagamento servicoPagamento = new ServicoPagamento();
            pagamentoCriado = servicoPagamento.criarPagamento("50");                                    
        } catch (PaymentException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(pagamentoCriado.toJSON(), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("pagamentos/executar-pagamento")
    public ResponseEntity<Object> executarPagamento(String paymentID, String payerID) {
        try {
            ServicoPagamento servicoPagamento = new ServicoPagamento();
            servicoPagamento.executarPagamento(paymentID, payerID);
        } catch (PaymentException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("pagamentos/retorno-pagamento")
    public ResponseEntity<Object> retornoPagamento(String paymentID, String payerID) {
        System.out.println("RETORNO PAGAMENTO");
        return null;
    }

    @PostMapping("pagamentos/cancelar-pagamento")
    public ResponseEntity<Object> cancelarPagamento(String paymentID, String payerID) {
        System.out.println("CANCELAR PAGAMENTO");
        return null;
    }

    @PostMapping("pagamentos/notificar")
    public ResponseEntity<String> notificar() {
        System.out.println("NOTIFICAÇÃO DE PAGAMENTO");
        return null;
    }

}
