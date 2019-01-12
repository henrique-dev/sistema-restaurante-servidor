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
import com.br.phdev.srs.models.ExecutarPagamento;
import com.br.phdev.srs.models.IPNMessage;
import com.br.phdev.srs.models.Mensagem;
import com.br.phdev.srs.utils.HttpUtils;
import com.br.phdev.srs.utils.ServicoPagamentoPagSeguro;
import com.br.phdev.srs.utils.ServicoPagamentoPayPal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
            ServicoPagamentoPayPal servicoPagamento = new ServicoPagamentoPayPal();
            servicoPagamento.executarPagamento(paymentId, payerId);
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            if (clienteDAO.atualizarTokenPrePedido(paymentId, payerId)) {
                return "processando-pagamento";
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
    
    @PostMapping("pagamentos/executar-pagamento4")
    public ResponseEntity<Mensagem> executarPagamento4() {
        new ServicoPagamentoPagSeguro().teste();
        HttpHeaders httpHeaders = new HttpHeaders();        
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
    }
    

    @PostMapping("pagamentos/executar-pagamento2")
    public ResponseEntity<Mensagem> executarPagamento2(@RequestBody ExecutarPagamento ep, HttpSession sessao) {
        System.out.println("Executando pagamento pag-seguro");        
        Mensagem mensagem = new Mensagem();
        try (Connection conexao = new FabricaConexao().conectar()) {
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            ExecutarPagamento pagamentoRecuperado = (ExecutarPagamento) sessao.getAttribute("executar-pagamento");            
            ServicoPagamentoPagSeguro servicoPagamento = new ServicoPagamentoPagSeguro();
            pagamentoRecuperado.getConfirmaPedido().getEnderecos().set(0, clienteDAO.getEndereco(
                    pagamentoRecuperado.getConfirmaPedido().getEnderecos().get(0),
                    pagamentoRecuperado.getCliente()));            
            pagamentoRecuperado.setCpf(ep.getCpf());
            pagamentoRecuperado.setNome(ep.getNome());
            pagamentoRecuperado.setData(ep.getData());
            pagamentoRecuperado.setTelefone(ep.getTelefone());
            pagamentoRecuperado.setTokenSessao(ep.getTokenSessao());
            pagamentoRecuperado.setTokenCartao(ep.getTokenCartao());
            pagamentoRecuperado.setHashCliente(ep.getHashCliente());
            String codigoPagamento = servicoPagamento.executarPagamento(pagamentoRecuperado);
            System.out.println("Dados capturados, preparando para executar pagamento");
            if (codigoPagamento != null) {
                if (clienteDAO.atualizarTokenPrePedido(ep.getTokenSessao(), codigoPagamento)) {
                    mensagem.setCodigo(100);
                    mensagem.setDescricao("Processando pagamento");
                } else {
                    mensagem.setCodigo(300);
                    mensagem.setDescricao("Houve algum erro ao processar o pagamento");
                }
            } else {
                mensagem.setCodigo(101);
                mensagem.setDescricao("Não foi possível processar o pagamento");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mensagem.setCodigo(300);
            mensagem.setDescricao(e.getMessage());
        } catch (PaymentException e) {
            e.printStackTrace();
            mensagem.setCodigo(300);
            mensagem.setDescricao(e.getMessage());
        } catch (DAOException e) {
            e.printStackTrace();
            mensagem.setCodigo(300);
            mensagem.setDescricao(e.getMessage());
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(mensagem, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("pagamentos/cancelar-pagamento")
    public String cancelarPagamento(HttpServletRequest req) {
        System.out.println("");
        System.out.println("Cancelar pagamento");
        HttpUtils hu = new HttpUtils();
        hu.showHeaders(req);
        hu.showParams(req);
        System.out.println("Pagamento cancelado");
        String paymentId = req.getParameter("paymentId");
        String payerId = req.getParameter("PayerID");
        System.out.println("Id de pagamento: " + paymentId);
        System.out.println("Id do comprador: " + payerId);
        return "processando-pagamento";
    }

    @PostMapping("pagamentos/notificar3")
    public ResponseEntity<String> notificar3(HttpServletRequest req) {
        System.out.println("Notificação de pagamento do pagseguro");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>("", httpHeaders, HttpStatus.OK);
    }

    @PostMapping("pagamentos/notificar2")
    public ResponseEntity<String> notificar2(HttpServletRequest req) {
        System.out.println("Notificação de pagamento do paypal");
        try (Connection conexao = new FabricaConexao().conectar()) {
            Map<String, String> configMap = new HashMap<>();
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
