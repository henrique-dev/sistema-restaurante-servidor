/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.phdev.srs.controladores;

import com.br.phdev.srs.utils.ServicoPagamento;
import com.google.gson.JsonObject;
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
    /*
    @PostMapping("cliente/criar-pagamento")
    public ResponseEntity<String> criarPagamento() {
        ServicoPagamento servicoPagamento = new ServicoPagamento();
        String id = servicoPagamento.criarPagamento();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        System.out.println(jsonObject.toString());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(jsonObject.toString(), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("cliente/executar-pagamento")
    public ResponseEntity<Object> executarPagamento(String paymentID, String payerID) {
        ServicoPagamento servicoPagamento = new ServicoPagamento();
        servicoPagamento.executarPagamento(paymentID, payerID);
        return null;
    }

    @PostMapping("cliente/retorno-pagamento")
    public ResponseEntity<Object> retornoPagamento(String paymentID, String payerID) {
        ServicoPagamento servicoPagamento = new ServicoPagamento();
        servicoPagamento.executarPagamento(paymentID, payerID);
        return null;
    }

    @PostMapping("cliente/cancelar-pagamento")
    public ResponseEntity<Object> cancelarPagamento(String paymentID, String payerID) {
        ServicoPagamento servicoPagamento = new ServicoPagamento();
        servicoPagamento.executarPagamento(paymentID, payerID);
        return null;
    }
    
    @PostMapping("pagamentos/notificar")
    public ResponseEntity<String> notificar() {
        System.out.println("NOTIFICAÇÃO DE PAGAMENTO");
        return null;
    }*/
    
}
