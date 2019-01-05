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
        return "pagamento-erro";
    }

    @GetMapping("pagamentos/cancelar-pagamento")
    public ResponseEntity<Object> cancelarPagamento(String paymentID, String payerID) {
        System.out.println("CANCELAR PAGAMENTO");
        return null;
    }

    @RequestMapping("pagamentos/notificar")
    public ResponseEntity<String> notificar(@RequestBody Payment pagamento) {
        System.out.println("Notificação de pagamento");
        if (pagamento != null) {
            System.out.println(pagamento.toJSON());
        }
        return null;
    }

    @RequestMapping("pagamentos/notificar2")
    public ResponseEntity<String> notificar2(HttpServletRequest req) {
        try (Connection conexao = new FabricaConexao().conectar()) {
            System.out.println("notificar2");
            Map<String, String> configMap = new HashMap<String, String>();
            configMap.put("mode", "sandbox");
            IPNMessage ipnListener = new IPNMessage(req, configMap);
            ipnListener.validate();
            Map<String, String> m = ipnListener.getIpnMap();
            System.out.println(m);
            String idComprador = m.get("payer_id");
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            String sessaoUsuario = clienteDAO.recuperarSessaoClienteParaConfirmarCompra(idComprador);
            Mensagem mensagem = new Mensagem();
            mensagem.setCodigo(100);
            mensagem.setDescricao("O pagamento foi confirmado");
            ObjectMapper mapeador = new ObjectMapper();
            String msg = mapeador.writeValueAsString(mensagem);
            clienteDAO.inserirPedidoDePrePedido(idComprador);
            this.template.convertAndSendToUser(sessaoUsuario, "/queue/reply", msg);           
        } catch (DAOException | SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>("", httpHeaders, HttpStatus.OK);
    }

}
