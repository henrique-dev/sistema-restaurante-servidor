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
import com.br.phdev.srs.models.IPNMessage;
import com.br.phdev.srs.models.Mensagem;
import com.br.phdev.srs.utils.ServicoPagamento;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.api.payments.Payment;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
@Controller
public class PagamentoController {

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("pagamentos/executar-pagamento")
    public String executarPagamento(HttpServletRequest req) {
        try (Connection conexao = new FabricaConexao().conectar()) {
            String paymentId = req.getParameter("paymentId");
            String payerId = req.getParameter("PayerID");
            ServicoPagamento servicoPagamento = new ServicoPagamento();
            servicoPagamento.executarPagamento(paymentId, payerId);
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            if (clienteDAO.atualizarTokenPrePedido(paymentId, payerId)) {
                return "pagamento-efetuado";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PaymentException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return "processando-pagamento";
    }

    @GetMapping("pagamentos/cancelar-pagamento")
    public String cancelarPagamento(HttpServletRequest req) {
        System.out.println("Pagamento cancelado");
        String paymentId = req.getParameter("paymentId");
        String payerId = req.getParameter("PayerID");
        System.out.println("Id de pagamento: " + paymentId);        
        System.out.println("Id do comprador: " + payerId);
        return "processando-pagamento";
    }    

    @PostMapping("pagamentos/notificar2")
    public ResponseEntity<String> notificar2(HttpServletRequest req) {
        System.out.println("Chegou notificação de pagamento");
        try (Connection conexao = new FabricaConexao().conectar()) {
            Map<String, String> configMap = new HashMap<String, String>();
            configMap.put("mode", "sandbox");
            IPNMessage ipnListener = new IPNMessage(req, configMap);
            if (ipnListener.validate()) {
                Map<String, String> m = ipnListener.getIpnMap();
                String idComprador = m.get("payer_id");
                Mensagem mensagem = new Mensagem();
                ClienteDAO clienteDAO = new ClienteDAO(conexao);
                String sessaoUsuario = clienteDAO.recuperarSessaoClienteParaConfirmarCompra(idComprador);
                if (m.get("payment_status").equals("Completed")) {
                    mensagem.setCodigo(100);
                    mensagem.setDescricao("O pagamento foi confirmado");
                    clienteDAO.inserirPedidoDePrePedido(idComprador);
                } else {
                    mensagem.setCodigo(101);
                    mensagem.setDescricao("O pagamento foi recusado");
                }
                ObjectMapper mapeador = new ObjectMapper();
                String msg = mapeador.writeValueAsString(mensagem);
                this.template.convertAndSendToUser(sessaoUsuario, "/queue/reply", msg);
            }
        } catch (DAOException | SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>("", httpHeaders, HttpStatus.OK);
    }

}
