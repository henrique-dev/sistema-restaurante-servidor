/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.controladores;

import com.br.phdev.srs.daos.ClienteDAO;
import com.br.phdev.srs.exceptions.DAOException;
import com.br.phdev.srs.jdbc.FabricaConexao;
import com.br.phdev.srs.models.Cliente;
import com.br.phdev.srs.models.ConfirmaPedido;
import com.br.phdev.srs.models.Endereco;
import com.br.phdev.srs.models.FormaPagamento;
import com.br.phdev.srs.models.ListaItens;
import com.br.phdev.srs.models.Item;
import com.br.phdev.srs.models.Pedido;
import com.br.phdev.srs.models.Teste;
import com.br.phdev.srs.models.Usuario;
import com.br.phdev.srs.utils.Arquivo;
import com.br.phdev.srs.utils.ServicoArmazenamento;
import com.br.phdev.srs.utils.ServicoPagamento;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
@Controller
public class ClienteController {    

    @PostMapping("Autenticar")
    public ResponseEntity<String> autenticar(String usuario, String senha, HttpSession sessao) {
        System.out.println("Usuario: " + usuario);
        System.out.println("Senha: " + senha);
        sessao.setAttribute("usuario", new Usuario());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_HTML);        
        return new ResponseEntity<>("sucesso", httpHeaders, HttpStatus.OK);        
    }
        
    @PostMapping(value = "ListarItens")
    public ResponseEntity<ListaItens> getPratos() {                        
        ListaItens listaItens = null;
        try (Connection conexao = new FabricaConexao().conectar()){
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            listaItens = clienteDAO.getItens();            
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);        
        return new ResponseEntity<>(listaItens, httpHeaders, HttpStatus.OK);        
    }
    
    @PostMapping(value = "InfoItem")
    public ResponseEntity<Item> infoPrato(@RequestBody Item item) {                        
        Item prato = null;
        try (Connection conexao = new FabricaConexao().conectar()){            
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            prato = clienteDAO.getItem(item.getId());
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(prato, httpHeaders, HttpStatus.OK);        
    }
    
    @PostMapping(value = "PreConfirmarPedido")
    public ResponseEntity<ConfirmaPedido> preConfirmaPedido(@RequestBody ConfirmaPedido confirmaPedido, HttpSession sessao) {        
        try (Connection conexao = new FabricaConexao().conectar()){                        
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            //Cliente cliente = (Cliente)sessao.getAttribute("cliente");
            Cliente cliente = new Cliente(1);
            clienteDAO.inserirPrecos(confirmaPedido);
            List<Endereco> enderecos = clienteDAO.getEnderecos(cliente);
            List<FormaPagamento> formaPagamentos =  clienteDAO.getFormasPagamento(cliente);
            confirmaPedido.setFormaPagamentos(formaPagamentos);
            confirmaPedido.setEnderecos(enderecos);            
            confirmaPedido.calcularPrecoTotal(0);
            sessao.setAttribute("pre-pedido-itens", confirmaPedido.getItens());
            sessao.setAttribute("pre-pedido-preco", confirmaPedido.getPrecoTotal());
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(confirmaPedido, httpHeaders, HttpStatus.OK);        
    }
    
    @PostMapping("ConfirmarPedido")
    public ResponseEntity<Pedido> confirmarPedido(@RequestBody ConfirmaPedido confirmaPedido, HttpSession sessao) {
        Pedido pedido = new Pedido();
        try (Connection conexao = new FabricaConexao().conectar()){
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            //Cliente cliente = (Cliente)sessao.getAttribute("cliente");
            Cliente cliente = new Cliente(1);
            ServicoPagamento servicoPagamento = new ServicoPagamento();
            if (!servicoPagamento.efetuarPagamento()) {                
                pedido.setData(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                pedido.setEndereco(confirmaPedido.getEnderecos().get(0));
                pedido.setFormaPagamento(confirmaPedido.getFormaPagamentos().get(0));
                pedido.convertItemParaItemFacil((List<Item>)sessao.getAttribute("pre-pedido-itens"));
                pedido.setPrecoTotal((Double)sessao.getAttribute("pre-pedido-preco"));
                pedido.setStatus("Pagamento aprovado");
                clienteDAO.inserirPedido(pedido, cliente);
            } else {
                pedido = new Pedido();
                pedido.setStatus("Pagamento não aprovado");
            }
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(pedido, httpHeaders, HttpStatus.OK);
    }
    
    @PostMapping(value = "ListarEnderecos")
    public ResponseEntity<List<Endereco>> infoEndereco(HttpSession sessao) {
        List<Endereco> enderecos = null;
        try (Connection conexao = new FabricaConexao().conectar()){              
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            //Cliente cliente = (Cliente)sessao.getAttribute("cliente");
            Cliente cliente = new Cliente(1);
            enderecos = clienteDAO.getEnderecos(cliente);
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(enderecos, httpHeaders, HttpStatus.OK);        
    } 
    
    @PostMapping(value = "ListarFormasPagamento")
    public ResponseEntity<List<FormaPagamento>> listarFormasPagamento(HttpSession sessao) {
        List<FormaPagamento> formaPagamentos = null;
        try (Connection conexao = new FabricaConexao().conectar()){              
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            //Cliente cliente = (Cliente)sessao.getAttribute("cliente");
            Cliente cliente = new Cliente(1);
            formaPagamentos = clienteDAO.getFormasPagamento(cliente);
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(formaPagamentos, httpHeaders, HttpStatus.OK);        
    } 
    
    @PostMapping("Imagem/{idArquivo}")
    @ResponseBody
    public ResponseEntity<byte[]> image(@PathVariable int idArquivo) {
        byte[] bytes = null;
        try (Connection con = new FabricaConexao().conectar()) {
            Arquivo arquivo = new ClienteDAO(con).getPublicFile(idArquivo);
            bytes = new ServicoArmazenamento().carregar(arquivo);            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        }                        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);        
    }
    
}
