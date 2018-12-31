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
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("pagamentos/executar-pagamento")
    public ResponseEntity<String> executarPagamento(HttpServletRequest req, HttpServletResponse res) {
        try {
            
            Enumeration<String> headersEnum = req.getHeaderNames();
            while (headersEnum.hasMoreElements()) {
                String headerName = headersEnum.nextElement();
                System.out.println(headerName + ": ");
                Enumeration<String> valuesEnum = req.getHeaders(headerName);
                while (valuesEnum.hasMoreElements()) {
                    String value = valuesEnum.nextElement();
                    System.out.print(value + "; ");
                }
                System.out.println("");
            }
            
            
            Enumeration<String> attributesEnum = req.getAttributeNames();
            while (attributesEnum.hasMoreElements()) {
                String attributeName = attributesEnum.nextElement();
                System.out.println(attributeName + ": ");
                System.out.print(req.getAttribute(attributeName));                
                System.out.println("");
            }
            
            Enumeration<String> paramsName = req.getParameterNames();
            while (paramsName.hasMoreElements()) {
                String paramName = paramsName.nextElement();
                System.out.println(paramName + ": ");
                System.out.print(req.getParameter(paramName));                
                System.out.println("");
            }
            
            /*
            System.out.println("ID do pagador: " + paymentid);
            System.out.println("ID do comprador: " + payerID);
            ServicoPagamento servicoPagamento = new ServicoPagamento();
            servicoPagamento.executarPagamento(paymentid, payerID);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    @GetMapping("pagamentos/executar-pagamento")
    public ResponseEntity<Object> retornoPagamento(String paymentID, String payerID) {
        System.out.println("RETORNO PAGAMENTO");
        return null;
    }*/

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
