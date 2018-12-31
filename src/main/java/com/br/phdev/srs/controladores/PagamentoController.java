/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.controladores;

import com.br.phdev.srs.daos.ClienteDAO;
import com.br.phdev.srs.exceptions.DAOException;
import com.br.phdev.srs.exceptions.PaymentException;
import com.br.phdev.srs.jdbc.FabricaConexao;
import com.br.phdev.srs.utils.ServicoPagamento;
import com.google.gson.JsonObject;
import com.paypal.api.payments.Payment;
import java.sql.Connection;
import java.sql.SQLException;
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

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
@Controller
public class PagamentoController {    

    @GetMapping("pagamentos/executar-pagamento")
    public String executarPagamento(HttpServletRequest req, HttpServletResponse res) {
        try (Connection conexao = new FabricaConexao().conectar()){
            String paymentId = req.getParameter("paymentId");
            String payerId = req.getParameter("PayerID");            
            System.out.println("ID do pagador: " + paymentId);
            System.out.println("ID do comprador: " + payerId);
            ServicoPagamento servicoPagamento = new ServicoPagamento();
            servicoPagamento.executarPagamento(paymentId, payerId);            
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            clienteDAO.inserirPedido(paymentId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (PaymentException e) {
            e.printStackTrace();
        }
        return "pagamento-efetuado";
    }    

    @PostMapping("pagamentos/cancelar-pagamento")
    public ResponseEntity<Object> cancelarPagamento(String paymentID, String payerID) {
        System.out.println("CANCELAR PAGAMENTO");
        return null;
    }

    @PostMapping("pagamentos/notificar")
    public ResponseEntity<String> notificar() {
        System.out.println("Notificação de pagamento");        
        return null;
    }

}
