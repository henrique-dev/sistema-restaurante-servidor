/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.controladores;

import com.br.phdev.srs.daos.ClienteDAO;
import com.br.phdev.srs.exceptions.DAOException;
import com.br.phdev.srs.exceptions.DAOExpectedException;
import com.br.phdev.srs.exceptions.DAOIncorrectData;
import com.br.phdev.srs.jdbc.FabricaConexao;
import com.br.phdev.srs.models.Cadastro;
import com.br.phdev.srs.models.Cliente;
import com.br.phdev.srs.models.Codigo;
import com.br.phdev.srs.models.ConfirmaPedido;
import com.br.phdev.srs.models.Endereco;
import com.br.phdev.srs.models.FormaPagamento;
import com.br.phdev.srs.models.Foto;
import com.br.phdev.srs.models.ListaItens;
import com.br.phdev.srs.models.Item;
import com.br.phdev.srs.models.Pedido;
import com.br.phdev.srs.models.Teste;
import com.br.phdev.srs.models.Usuario;
import com.br.phdev.srs.models.ValidaCadastro;
import com.br.phdev.srs.utils.Arquivo;
import com.br.phdev.srs.utils.Mensagem;
import com.br.phdev.srs.utils.ServicoArmazenamento;
import com.br.phdev.srs.utils.ServicoPagamento;
import com.br.phdev.srs.utils.ServicoValidacaoCliente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.exception.ApiException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private final String chave = "ZXDas7966mby@";

    @PostMapping("cliente/autenticar")
    public ResponseEntity<Mensagem> autenticar(@RequestBody Usuario usuario, HttpSession sessao) {
        Mensagem mensagem = new Mensagem();
        try (Connection conexao = new FabricaConexao().conectar()) {
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            Cliente cliente = clienteDAO.autenticar(usuario);
            if (cliente != null) {
                String textoParaHash = usuario.getNomeUsuario() + usuario.getSenhaUsuario()
                        + Calendar.getInstance().getTime().toString();
                MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
                byte textoDigerido[] = algoritmo.digest(textoParaHash.getBytes("UTF-8"));
                StringBuilder tokenHex = new StringBuilder();
                for (byte b : textoDigerido) {
                    tokenHex.append(String.format("%02X", 0xFF & b));
                }
                clienteDAO.gerarSessao(usuario, tokenHex.toString());
                mensagem.setCodigo(100);
                mensagem.setDescricao(tokenHex.toString());
                sessao.setAttribute("usuario", usuario);
                sessao.setAttribute("cliente", cliente);
                sessao.setAttribute("token", tokenHex.toString());
            } else {
                mensagem.setCodigo(101);
                mensagem.setDescricao("Usuário ou senha inválidos");
            }
        } catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            mensagem.setCodigo(200);
            mensagem.setDescricao(e.getMessage());
        } catch (DAOException e) {
            e.printStackTrace();
            mensagem.setCodigo(e.codigo);
            mensagem.setDescricao(e.getMessage());
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(mensagem, httpHeaders, HttpStatus.OK);
    }

    /*@PostMapping("cliente/teste-requisicao")
    public ResponseEntity<HttpHeaders> testeRequisicao(HttpSession sessao, HttpServletRequest request) {
        Mensagem mensagem = new Mensagem();

        HttpHeaders httpHeadersTmp = new HttpHeaders();
        Enumeration headersEnum = request.getHeaderNames();
        while (headersEnum.hasMoreElements()) {
            String header = (String) headersEnum.nextElement();
            Enumeration valuesEnum = request.getHeaders(header);
            while (valuesEnum.hasMoreElements()) {
                httpHeadersTmp.add(header, valuesEnum.nextElement().toString());
            }
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(httpHeadersTmp, httpHeaders, HttpStatus.OK);
    }*/
    
    @PostMapping("cliente/sair")
    public ResponseEntity<Mensagem> sair(HttpSession sessao, HttpServletRequest request) {
        Mensagem mensagem = new Mensagem();
        try (Connection conexao = new FabricaConexao().conectar()) {
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            clienteDAO.sairSessao((Usuario) sessao.getAttribute("usuario"), null);
            mensagem.setCodigo(100);
            mensagem.setDescricao("Desconectado do sistema");
            sessao.removeAttribute("usuario");
            sessao.removeAttribute("cliente");
            sessao.invalidate();
        } catch (SQLException e) {
            e.printStackTrace();
            mensagem.setCodigo(200);
            mensagem.setDescricao(e.getMessage());
        } catch (DAOException e) {
            e.printStackTrace();
            mensagem.setCodigo(e.codigo);
            mensagem.setDescricao(e.getMessage());
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(mensagem, httpHeaders, HttpStatus.OK);
    }        

    @RequestMapping("cliente/verificar-numero")
    public ResponseEntity<Mensagem> verificarNumero(@RequestBody Cadastro cadastro) {
        Mensagem mensagem = new Mensagem();
        try (Connection conexao = new FabricaConexao().conectar()) {
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            String token = clienteDAO.preCadastrar(cadastro.getTelefone(), this.chave);
            try {
                new ServicoValidacaoCliente().enviarMensagem(cadastro.getTelefone(), token);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            mensagem.setDescricao("Um código foi enviado para seu número. Insira esse código no aplicativo");
            mensagem.setCodigo(100);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | SQLException e) {
            e.printStackTrace();
            mensagem.setDescricao(e.getMessage());
            mensagem.setCodigo(200);
        } catch (DAOException e) {
            e.printStackTrace();
            mensagem.setCodigo(e.codigo);
            mensagem.setDescricao(e.getMessage());
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(mensagem, httpHeaders, HttpStatus.OK);
    }
    
    @RequestMapping("cliente/validar-numero")
    public ResponseEntity<Mensagem> validarNumero(@RequestBody Codigo codigo) {
        Mensagem mensagem = new Mensagem();
        try (Connection conexao = new FabricaConexao().conectar()) {
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            if (clienteDAO.validarNumero(codigo)) {
                mensagem.setDescricao("Numero verificado com sucesso");
                mensagem.setCodigo(100);
            } else {
                mensagem.setDescricao("Codigo inválido");
                mensagem.setCodigo(101);
            }
        } catch (SQLException e) {            
            e.printStackTrace();
            mensagem.setCodigo(200);
            mensagem.setDescricao(e.getMessage());
        } catch (DAOException e) {
            e.printStackTrace();
            mensagem.setCodigo(200);
            mensagem.setDescricao(e.getMessage());
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(mensagem, httpHeaders, HttpStatus.OK);
    }
    
    @RequestMapping("cliente/cadastrar")
    public ResponseEntity<Mensagem> cadastrar(@RequestBody Cadastro cadastro) {
        Mensagem mensagem = new Mensagem();
        try (Connection conexao = new FabricaConexao().conectar()) {
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            clienteDAO.cadastrar(cadastro);            
            mensagem.setDescricao("Cadastro realizado com sucesso");
            mensagem.setCodigo(100);
        } catch (SQLException e) {
            e.printStackTrace();
            mensagem.setDescricao(e.getMessage());
            mensagem.setCodigo(200);
        } catch (DAOException e) {
            e.printStackTrace();
            mensagem.setCodigo(e.codigo);
            mensagem.setDescricao(e.getMessage());
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(mensagem, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping("cliente/validar-cadastro-aaaaaaaaaa")
    public ResponseEntity<Mensagem> validarCadastro(@RequestBody ValidaCadastro validaCadastro) {
        Mensagem mensagem = new Mensagem();
        try (Connection conexao = new FabricaConexao().conectar()) {
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            if (clienteDAO.validarCadastro(validaCadastro)) {
                mensagem.setCodigo(100);
                mensagem.setDescricao("Sua conta foi ativada");
            } else {
                mensagem.setCodigo(101);
                mensagem.setDescricao("Houve um problema ao ativar a conta");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mensagem.setDescricao(e.getMessage());
            mensagem.setCodigo(200);
        } catch (DAOException e) {
            e.printStackTrace();
            mensagem.setCodigo(e.codigo);
            mensagem.setDescricao(e.getMessage());
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(mensagem, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping("cliente/sem-autorizacao")
    public ResponseEntity<Mensagem> semAutorizacao() {
        Mensagem mensagem = new Mensagem();
        mensagem.setCodigo(401);
        mensagem.setDescricao("Sem autorização para acessar este recurso");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(mensagem, httpHeaders, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("cliente/meu-perfil")
    public ResponseEntity<Cliente> meuPerfil(HttpSession sessao) {
        Cliente cliente = (Cliente) sessao.getAttribute("cliente");
        try (Connection conexao = new FabricaConexao().conectar()) {
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            clienteDAO.getCliente(cliente);            
        } catch (SQLException e) {
            e.printStackTrace();
            cliente = null;
        } catch (DAOException e) {
            e.printStackTrace();
            cliente = null;
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(cliente, httpHeaders, HttpStatus.OK);
    }

    @PostMapping(value = "cliente/listar-itens")
    public ResponseEntity<ListaItens> getPratos() {
        ListaItens listaItens = null;
        try (Connection conexao = new FabricaConexao().conectar()) {
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

    @PostMapping(value = "cliente/info-item")
    public ResponseEntity<Item> infoPrato(@RequestBody Item item) {
        try (Connection conexao = new FabricaConexao().conectar()) {
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            clienteDAO.getItem(item);
        } catch (DAOException e) {
            e.printStackTrace();
            item = null;
        } catch (SQLException e) {
            e.printStackTrace();
            item = null;
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(item, httpHeaders, HttpStatus.OK);
    }

    @PostMapping(value = "cliente/pre-confirmar-pedido")
    public ResponseEntity<ConfirmaPedido> preConfirmaPedido(@RequestBody ConfirmaPedido confirmaPedido, HttpSession sessao) {
        try (Connection conexao = new FabricaConexao().conectar()) {
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            Cliente cliente = (Cliente) sessao.getAttribute("cliente");
            clienteDAO.inserirPrecos(confirmaPedido);
            List<Endereco> enderecos = clienteDAO.getEnderecos(cliente);
            List<FormaPagamento> formaPagamentos = clienteDAO.getFormasPagamento(cliente);
            confirmaPedido.setFormaPagamentos(formaPagamentos);
            confirmaPedido.setEnderecos(enderecos);
            confirmaPedido.calcularPrecoTotal(0);
            sessao.setAttribute("pre-pedido-itens", confirmaPedido.getItens());
            sessao.setAttribute("pre-pedido-preco", confirmaPedido.getPrecoTotal());
        } catch (DAOException e) {
            e.printStackTrace();
            confirmaPedido = null;
        } catch (SQLException e) {
            e.printStackTrace();
            confirmaPedido = null;
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(confirmaPedido, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("cliente/confirmar-pedido")
    public ResponseEntity<Pedido> confirmarPedido(@RequestBody ConfirmaPedido confirmaPedido, HttpSession sessao) {
        Pedido pedido = null;
        try (Connection conexao = new FabricaConexao().conectar()) {
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            Cliente cliente = (Cliente) sessao.getAttribute("cliente");
            ServicoPagamento servicoPagamento = new ServicoPagamento();
            if (!servicoPagamento.efetuarPagamento()) {
                pedido = new Pedido();
                pedido.setData(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                pedido.setEndereco(confirmaPedido.getEnderecos().get(0));
                pedido.setFormaPagamento(confirmaPedido.getFormaPagamentos().get(0));
                pedido.convertItemParaItemFacil((List<Item>) sessao.getAttribute("pre-pedido-itens"));
                pedido.setPrecoTotal((Double) sessao.getAttribute("pre-pedido-preco"));
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

    @PostMapping("cliente/listar-enderecos")
    public ResponseEntity<List<Endereco>> infoEndereco(HttpSession sessao) {
        List<Endereco> enderecos = null;
        try (Connection conexao = new FabricaConexao().conectar()) {
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            Cliente cliente = (Cliente) sessao.getAttribute("cliente");
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

    @PostMapping(value = "cliente/listar-formas-pagamento")
    public ResponseEntity<List<FormaPagamento>> listarFormasPagamento(HttpSession sessao) {
        List<FormaPagamento> formaPagamentos = null;
        try (Connection conexao = new FabricaConexao().conectar()) {
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            Cliente cliente = (Cliente) sessao.getAttribute("cliente");
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

    @GetMapping("cliente/imagens/{idArquivo}")
    @ResponseBody
    public ResponseEntity<byte[]> image(@PathVariable int idArquivo) {
        byte[] bytes = null;
        Foto foto = new Foto();
        foto.setId(idArquivo);
        bytes = new ServicoArmazenamento().carregar(foto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

}
