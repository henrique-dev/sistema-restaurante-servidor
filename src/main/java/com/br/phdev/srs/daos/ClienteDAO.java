/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.daos;

import com.br.phdev.srs.exceptions.DAOException;
import com.br.phdev.srs.exceptions.DAOExpectedException;
import com.br.phdev.srs.exceptions.DAOIncorrectData;
import com.br.phdev.srs.models.Cadastro;
import com.br.phdev.srs.models.Cliente;
import com.br.phdev.srs.models.Codigo;
import com.br.phdev.srs.models.Complemento;
import com.br.phdev.srs.models.ComplementoFacil;
import com.br.phdev.srs.models.ConfirmaPedido;
import com.br.phdev.srs.models.Endereco;
import com.br.phdev.srs.models.FormaPagamento;
import com.br.phdev.srs.models.Foto;
import com.br.phdev.srs.models.Genero;
import com.br.phdev.srs.models.ListaItens;
import com.br.phdev.srs.models.Item;
import com.br.phdev.srs.models.ItemPedido;
import com.br.phdev.srs.models.ItemPedidoFacil;
import com.br.phdev.srs.models.Pedido;
import com.br.phdev.srs.models.Pedido2;
import com.br.phdev.srs.models.Tipo;
import com.br.phdev.srs.models.Usuario;
import com.br.phdev.srs.models.ValidaCadastro;
import com.br.phdev.srs.models.Arquivo;
import com.br.phdev.srs.models.GrupoVariacao;
import com.br.phdev.srs.models.Variacao;
import com.br.phdev.srs.utils.ServicoArmazenamento;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class ClienteDAO extends BasicDAO {

    public ClienteDAO(Connection conexao) {
        super(conexao);
    }

    synchronized public String cadastrarCliente(Cadastro cadastro) throws DAOException {
        if (cadastro == null) {
            throw new DAOIncorrectData(300);
        }
        if (cadastro.getNome() == null || cadastro.getCpf() == null || cadastro.getEmail() == null
                || cadastro.getTelefone() == null || cadastro.getSenhaUsuario() == null) {
            throw new DAOIncorrectData(300);
        }
        if (cadastro.getNome().trim().isEmpty() || cadastro.getCpf().trim().isEmpty() || cadastro.getEmail().trim().isEmpty()
                || cadastro.getTelefone().trim().isEmpty() || cadastro.getSenhaUsuario().trim().isEmpty()) {
            throw new DAOIncorrectData(301);
        }
        for (char c : cadastro.getNome().toCharArray()) {
            if (!(((int) c > 64 && (int) c < 91) || ((int) c > 96 && (int) c < 122)) && (int) c != 32) {
                throw new DAOIncorrectData(302);
            }
        }
        if (!cadastro.getEmail().contains("@")) {
            throw new DAOIncorrectData(303);
        }
        for (char c : cadastro.getCpf().toCharArray()) {
            if (!((int) c > 47 && (int) c < 58)) {
                throw new DAOIncorrectData(304);
            }
        }
        for (char c : cadastro.getTelefone().toCharArray()) {
            if (!((int) c > 47 && (int) c < 58)) {
                throw new DAOIncorrectData(305);
            }
        }

        StringBuilder ultimosDigitos = new StringBuilder();
        int soma = 0;
        int fator = 10;
        for (int i = 0; i < cadastro.getCpf().length() - 2; i++) {
            soma += Integer.parseInt(String.valueOf(cadastro.getCpf().charAt(i))) * fator--;
        }
        int resto = soma % 11;
        if (resto == 0 || resto == 1) {
            ultimosDigitos.append(0);
        } else {
            ultimosDigitos.append((11 - resto));
        }
        soma = 0;
        fator = 11;
        for (int i = 0; i < cadastro.getCpf().length() - 2; i++) {
            soma += Integer.parseInt(String.valueOf(cadastro.getCpf().charAt(i))) * fator--;
        }
        soma += Integer.parseInt(String.valueOf(ultimosDigitos.toString())) * 2;
        resto = soma % 11;
        if (resto == 0 || resto == 1) {
            ultimosDigitos.append(0);
        } else {
            ultimosDigitos.append((11 - resto));
        }

        if (!cadastro.getCpf().endsWith(ultimosDigitos.toString())) {
            throw new DAOIncorrectData(306);
        }

        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL inserir_cliente(?,?,?,?,?,?)")) {
            stmt.setString(1, cadastro.getNome());
            stmt.setString(2, cadastro.getCpf());
            stmt.setString(3, cadastro.getTelefone());
            stmt.setString(4, cadastro.getEmail());
            stmt.setString(5, cadastro.getSenhaUsuario());
            stmt.setString(6, cadastro.getCodigo());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("erro") != null) {
                    throw new DAOExpectedException(rs.getString("erro"), 400);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
        return null;
    }

    synchronized public String preCadastrar(String usuario, String chave) throws DAOException, NoSuchAlgorithmException, UnsupportedEncodingException {
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL inserir_pre_cadastro_usuario(?,?)")) {
            stmt.setString(1, usuario);

            StringBuilder token = new StringBuilder();
            String textoParaHash = usuario
                    + Calendar.getInstance().getTime().toString() + chave;
            MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
            byte textoDigerido[] = algoritmo.digest(textoParaHash.getBytes("UTF-8"));
            for (int i = 0; i < textoDigerido.length; i = i + 14) {
                token.append(String.format("%02X", 0xFF & textoDigerido[i]));
            }

            stmt.setString(2, token.toString());
            stmt.execute();
            return token.toString();
        } catch (SQLException e) {
            if (e.getSQLState().equals("45000")) {
                throw new DAOException(e, 500);
            } else if (e.getSQLState().equals("45001")) {
                throw new DAOException(e, 501);
            }
            throw new DAOException(e, 200);
        }
    }

    public boolean validarNumero(Codigo codigo) throws DAOException {
        if (codigo == null) {
            throw new DAOIncorrectData(300);
        }
        if (codigo.getCodigo() == null) {
            throw new DAOIncorrectData(300);
        }
        if (codigo.getCodigo().isEmpty()) {
            throw new DAOIncorrectData(301);
        }
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL utils_validar_numero(?,?)")) {
            stmt.setString(1, codigo.getTelefone());
            stmt.setString(2, codigo.getCodigo());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("erro") == null) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
        return false;
    }

    public Cliente autenticar(Usuario usuario) throws DAOException {
        if (usuario == null) {
            throw new DAOIncorrectData(300);
        }
        if (usuario.getNomeUsuario() == null || usuario.getSenhaUsuario() == null) {
            throw new DAOIncorrectData(300);
        }
        if (usuario.getNomeUsuario().isEmpty() || usuario.getSenhaUsuario().isEmpty()) {
            throw new DAOIncorrectData(301);
        }
        Cliente cliente = null;
        try {
            String sql = "call utils_autenticar(?,?)";
            PreparedStatement stmt = super.conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getSenhaUsuario());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("erro") == null) {
                    usuario.setIdUsuario(rs.getLong("id_usuario"));
                    usuario.setNomeUsuario(null);
                    usuario.setSenhaUsuario(null);
                    cliente = new Cliente();
                    cliente.setId(rs.getLong("id_cliente"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
        return cliente;
    }

    public void gerarSessao(Usuario usuario, String token1) throws DAOException {
        String sql = "CALL utils_validar_sessao(?,?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setLong(1, usuario.getIdUsuario());
            stmt.setString(2, token1);
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
    }

    public void sairSessao(Usuario usuario, String token) throws DAOException {
        if (usuario == null) {
            return;
        }
        if (usuario.getIdUsuario() == 0) {
            throw new DAOIncorrectData(301);
        }
        String sql = "CALL utils_invalidar_sessao(?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setLong(1, usuario.getIdUsuario());
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
    }

    public void cadastrarTokenAlerta(Cliente cliente, String token) throws DAOException {
        if (cliente == null) {
            throw new DAOException("Erro", 300);
        }
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL utils_inserir_token_web_socket(?,?)")) {
            stmt.setLong(1, cliente.getId());
            stmt.setString(2, token);
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
    }

    public boolean verificarSessao(String sessaoId) throws DAOException {
        if (sessaoId == null) {
            throw new DAOException("Erro", 300);
        }
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL utils_verificar_sessao(?)")) {
            stmt.setString(1, sessaoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getObject("id") != null) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
        return false;
    }

    public void getCliente(Cliente cliente) throws DAOException {
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL get_perfil_cliente(?)")) {
            stmt.setLong(1, cliente.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                cliente.setEnderecos(getEnderecos(cliente));
            } else {
                cliente = null;
            }
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
    }

    public ListaItens getItens() throws DAOException {
        ListaItens listaItens = null;
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL get_lista_itens")) {
            ResultSet rs = stmt.executeQuery();
            List<Item> itens = new ArrayList<>();
            Set<Tipo> tipos = new HashSet<>();
            Set<Genero> generos = new HashSet<>();
            Set<Foto> fotos = null;
            Item item = new Item();
            long pratoAtual = -1;
            while (rs.next()) {
                long idPrato = rs.getLong("id_item");
                if (idPrato != pratoAtual) {
                    if (pratoAtual != -1) {
                        item.setTipos(tipos);

                        try (PreparedStatement stmt2 = super.conexao.prepareStatement("CALL get_lista_arquivos(?)")) {
                            stmt2.setLong(1, pratoAtual);
                            ResultSet rs2 = stmt2.executeQuery();
                            fotos = new HashSet<>();
                            while (rs2.next()) {
                                Foto foto = new Foto();
                                foto.setId(rs2.getLong("id_arquivo"));
                                fotos.add(ServicoArmazenamento.setTamanho(foto));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        item.setFotos(fotos);
                        itens.add(item);
                    }
                    pratoAtual = idPrato;
                    item = new Item();
                    tipos = new HashSet<>();
                    item.setId(rs.getLong("id_item"));
                    item.setNome(rs.getString("nome"));
                    item.setPreco(rs.getDouble("preco"));
                    item.setModificavel(rs.getBoolean("modificavel"));
                    Genero genero = new Genero(rs.getLong("id_genero"), rs.getString("genero"));
                    item.setGenero(genero);
                    generos.add(genero);
                }
                tipos.add(new Tipo(rs.getLong("id_tipo"), rs.getString("tipo_nome")));
            }
            if (pratoAtual != -1) {
                item.setTipos(tipos);

                try (PreparedStatement stmt2 = super.conexao.prepareStatement("CALL get_lista_arquivos(?)")) {
                    stmt2.setLong(1, pratoAtual);
                    ResultSet rs2 = stmt2.executeQuery();
                    fotos = new HashSet<>();
                    while (rs2.next()) {
                        Foto foto = new Foto();
                        foto.setId(rs2.getLong("id_arquivo"));
                        fotos.add(ServicoArmazenamento.setTamanho(foto));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                item.setFotos(fotos);
                itens.add(item);
                listaItens = new ListaItens();
                listaItens.setGeneros(generos);
                listaItens.setItens(itens);
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao recuperar informações", e, 200);
        }
        return listaItens;
    }

    public Item getItem(Item item) throws DAOException {
        if (item == null) {
            throw new DAOIncorrectData(300);
        }
        if (item.getId() == 0) {
            throw new DAOIncorrectData(301);
        }
        String sql = "call get_item(?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setLong(1, item.getId());
            ResultSet rs = stmt.executeQuery();
            Set<Tipo> tipos = new HashSet<>();
            Set<Foto> fotos = new HashSet<>();
            Set<Complemento> complementos = new HashSet<>();
            List<GrupoVariacao> variacoes = new ArrayList<>();
            long itemAtual = -1;
            while (rs.next()) {
                long idItemRecuperado = rs.getLong("id_item");
                if (idItemRecuperado != itemAtual) {
                    itemAtual = idItemRecuperado;
                    tipos = new HashSet<>();
                    fotos = new HashSet<>();
                    item.setNome(rs.getString("nome"));
                    item.setPreco(rs.getDouble("preco"));
                    item.setDescricao(rs.getString("descricao"));
                    item.setModificavel(rs.getBoolean("modificavel"));
                    Genero genero = new Genero(rs.getLong("id_genero"), rs.getString("genero"));
                    item.setGenero(genero);
                }
                tipos.add(new Tipo(rs.getLong("id_tipo"), rs.getString("tipo_nome")));
            }
            if (itemAtual != -1) {
                try (PreparedStatement stmt2 = super.conexao.prepareStatement("CALL get_lista_arquivos(?)")) {
                    stmt2.setLong(1, item.getId());
                    ResultSet rs2 = stmt2.executeQuery();
                    fotos = new HashSet<>();
                    while (rs2.next()) {
                        Foto foto = new Foto();
                        foto.setId(rs2.getLong("id_arquivo"));
                        fotos.add(ServicoArmazenamento.setTamanho(foto));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try (PreparedStatement stmt2 = super.conexao.prepareStatement("CALL get_lista_complementos_item(?)")) {
                    stmt2.setLong(1, item.getId());
                    ResultSet rs2 = stmt2.executeQuery();
                    complementos = new HashSet<>();
                    while (rs2.next()) {
                        complementos.add(new Complemento(
                                rs2.getLong("id_complemento"),
                                rs2.getString("nome"),
                                rs2.getDouble("preco"),
                                new Foto(rs2.getLong("id_arquivo"), null, 0)));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try (PreparedStatement stmt2 = super.conexao.prepareStatement("CALL get_lista_variacoes_item(?)")) {
                    stmt2.setLong(1, item.getId());
                    ResultSet rs2 = stmt2.executeQuery();
                    variacoes = new ArrayList<>();
                    while (rs2.next()) {                                
                        if (variacoes.size() > rs2.getInt("grupo")) {
                            GrupoVariacao gv = variacoes.get(rs2.getInt("grupo"));                            
                            gv.getVariacoes().add(new Variacao(rs2.getLong("id_variacao"), rs2.getString("nome"), rs2.getDouble("preco"), rs2.getInt("ordem")));
                            gv.setMax(rs2.getInt("max"));
                        } else {
                            GrupoVariacao gv = new GrupoVariacao();
                            Set<Variacao> v = new HashSet<>();                            
                            v.add(new Variacao(rs2.getLong("id_variacao"), rs2.getString("nome"), rs2.getDouble("preco"), rs2.getInt("ordem")));
                            gv.setMax(rs2.getInt("max"));
                            gv.setVariacoes(v);
                            variacoes.add(gv);
                            
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                item.setTipos(tipos);
                item.setComplementos(complementos);
                item.setFotos(fotos);
                item.setVariacoes(variacoes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Erro ao recuperar informações", e, 200);
        }
        return item;
    }

    public List<Endereco> getEnderecos(Cliente cliente) throws DAOException {
        if (cliente == null) {
            throw new DAOIncorrectData(300);
        }
        if (cliente.getId() == 0) {
            throw new DAOIncorrectData(301);
        }
        List<Endereco> enderecos = null;
        String sql = "call get_lista_enderecos(?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setLong(1, cliente.getId());
            ResultSet rs = stmt.executeQuery();
            enderecos = new ArrayList<>();
            while (rs.next()) {
                Endereco endereco = new Endereco();
                endereco.setId(rs.getLong("id_endereco"));
                endereco.setLogradouro(rs.getString("logradouro"));
                endereco.setNumero(rs.getString("numero"));
                endereco.setBairro(rs.getString("bairro"));
                endereco.setCidade(rs.getString("cidade"));
                endereco.setComplemento(rs.getString("complemento"));
                endereco.setCep(rs.getString("cep"));
                endereco.setDescricao(rs.getString("descricao"));
                enderecos.add(endereco);
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao recuperar informações", e, 200);
        }
        return enderecos;
    }

    public List<FormaPagamento> getFormasPagamento(Cliente cliente) throws DAOException {
        if (cliente == null) {
            throw new DAOIncorrectData(300);
        }
        if (cliente.getId() == 0) {
            throw new DAOIncorrectData(301);
        }
        List<FormaPagamento> formaPagamentos = null;
        String sql = "call get_lista_formaspagamento(?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setLong(1, cliente.getId());
            ResultSet rs = stmt.executeQuery();
            formaPagamentos = new ArrayList<>();
            while (rs.next()) {
                FormaPagamento formaPagamento = new FormaPagamento();
                formaPagamento.setId(rs.getLong("id_formapagamento"));
                formaPagamento.setDescricao(rs.getString("descricao"));
                formaPagamentos.add(formaPagamento);
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao recuperar informações", e, 200);
        }
        return formaPagamentos;
    }

    public Foto getPublicFile(int idArquivo) throws DAOException {
        if (idArquivo == 0) {
            throw new DAOIncorrectData(301);
        }
        Foto foto = null;
        String sql = "call get_arquivo(?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setInt(1, idArquivo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                foto = new Foto();
                foto.setId(idArquivo);
            }
        } catch (SQLException e) {
            throw new DAOException("Falha ao adquirir informações do arquivo", e, 200);
        }
        return foto;
    }

    public ConfirmaPedido inserirPrecos(ConfirmaPedido confirmaPedido) throws DAOException, DAOIncorrectData {
        if (confirmaPedido.getItens() == null) {
            throw new DAOIncorrectData(300);
        }
        if (confirmaPedido.getItens().isEmpty()) {
            throw new DAOIncorrectData(301);
        }
        RepositorioProdutos repositorioPrecos = RepositorioProdutos.getInstancia();
        repositorioPrecos.carregar(super.conexao);
        BigDecimal valorTotal = new BigDecimal("0.00");
        for (ItemPedido ip : confirmaPedido.getItens()) {
            repositorioPrecos.preencherItem(ip);
            System.out.println("inserirPrecos: " + ip.getId());
            BigDecimal valorItem = new BigDecimal("0.00");
            if (ip.getComplementos() != null) {
                for (Complemento c : ip.getComplementos()) {
                    repositorioPrecos.preencherComplemento(c);
                    valorItem = valorItem.add(new BigDecimal(String.valueOf(c.getPreco())));
                }
            }
            if (ip.getVariacoes() != null) {
                List<GrupoVariacao> variacoes = ip.getVariacoes();
                repositorioPrecos.checarVariacoes(variacoes, ip);
                for (GrupoVariacao gv : variacoes) {
                    if (gv.getVariacoes() != null) {
                        for (Variacao v : gv.getVariacoes()) {
                            repositorioPrecos.preecherVariacao(v);
                            valorItem = valorItem.add(new BigDecimal(String.valueOf(v.getPreco())));
                        }
                    }
                }
            }
            valorItem = valorItem.add(new BigDecimal(String.valueOf(ip.getPreco())));
            valorTotal = valorTotal.add(valorItem.multiply(new BigDecimal(ip.getQuantidade())));
        }
        confirmaPedido.setPrecoTotal(valorTotal.doubleValue());
        return confirmaPedido;
    }

    public List<ItemPedido> recuperarPrePredido(Cliente cliente) throws DAOException {
        List<ItemPedido> itens = null;
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL recuperar_pre_pedido(?)")) {
            stmt.setLong(1, cliente.getId());
            ResultSet rs = stmt.executeQuery();
            List<ItemPedidoFacil> itemPedidos = new ArrayList<>();
            if (rs.next()) {
                ObjectMapper mapeador = new ObjectMapper();
                itemPedidos = mapeador.readValue(rs.getString("itens"),
                        new TypeReference<List<ItemPedidoFacil>>() {
                });
            }
            if (!itemPedidos.isEmpty()) {
                RepositorioProdutos.getInstancia().carregar(super.conexao);
                itens = new ArrayList<>();
                for (ItemPedidoFacil ipf : itemPedidos) {
                    ItemPedido ip = new ItemPedido();
                    ip.setId(ipf.getId());
                    ip.setQuantidade(ipf.getQuantidade());
                    ip.setPreco(ipf.getPreco());
                    Set<Complemento> complementos = new HashSet<>();
                    for (ComplementoFacil cf : ipf.getComplementos()) {
                        Complemento complemento = new Complemento();
                        complemento.setId(cf.getId());
                        complemento.setNome(cf.getNome());
                        complemento.setPreco(cf.getPreco());
                        complemento.setCheck(true);
                        complementos.add(complemento);
                    }
                    ip.setComplementos(complementos);
                    RepositorioProdutos.getInstancia().preencherItem(ip);
                    itens.add(ip);
                }
            }
        } catch (SQLException | IOException e) {
            throw new DAOException(e, 200);
        }
        return itens;
    }

    public boolean possuiPrePredido(Cliente cliente) throws DAOException {
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL existe_pre_pedido(?)")) {
            stmt.setLong(1, cliente.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
        return false;
    }

    public void removerPrepedido(Cliente cliente) throws DAOException {
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL invalidar_pre_pedido(?)")) {
            stmt.setLong(1, cliente.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
    }

    synchronized public boolean inserirPrePedido(Pedido pedido, Cliente cliente, String token) throws DAOException {
        if (pedido == null || cliente == null) {
            throw new DAOIncorrectData(300);
        }
        String sql = "call inserir_pre_pedido(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setObject(1, pedido.getData());
            stmt.setDouble(2, pedido.getPrecoTotal());
            stmt.setLong(3, pedido.getFormaPagamento().getId());
            stmt.setLong(4, cliente.getId());
            stmt.setLong(5, pedido.getEndereco().getId());
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(pedido.getItens());
            stmt.setString(6, json);
            stmt.setString(7, token);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getObject("erro") == null) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        } catch (JsonProcessingException e) {
            throw new DAOException(e, 307);
        }
        return false;
    }

    synchronized public void inserirPedido(Pedido pedido, Cliente cliente) throws DAOException {
        if (pedido == null || cliente == null) {
            throw new DAOIncorrectData(300);
        }
        String sql = "call inserir_pedido(?,?,?,?,?,?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setObject(1, pedido.getData());
            stmt.setDouble(2, pedido.getPrecoTotal());
            stmt.setLong(3, pedido.getFormaPagamento().getId());
            stmt.setLong(4, cliente.getId());
            stmt.setLong(5, pedido.getEndereco().getId());
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(pedido.getItens());
            stmt.setString(6, json);
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        } catch (JsonProcessingException e) {
            throw new DAOException(e, 307);
        }
    }

    synchronized public boolean atualizarTokenPrePedido(String idPagamento, String idComprador) throws DAOException {
        if (idPagamento == null) {
            throw new DAOIncorrectData(300);
        }
        try (PreparedStatement stmt = super.conexao.prepareStatement("call atualizar_tokem_pre_pedido(?,?)")) {
            stmt.setString(1, idPagamento);
            stmt.setString(2, idComprador);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getObject("erro") == null) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
        return false;
    }

    public String recuperarSessaoClienteParaConfirmarCompra(String idComprador) throws DAOException {
        try (PreparedStatement stmt = super.conexao.prepareStatement("call utils_recuperar_sessao_cliente_pra_pagamento(?)")) {
            stmt.setString(1, idComprador);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("token_websocket");
            }
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
        return null;
    }

    synchronized public void inserirPedidoDePrePedido(String idPagamento) throws DAOException {
        if (idPagamento == null) {
            throw new DAOIncorrectData(300);
        }
        String sql = "call inserir_pedido_de_pre_pedido(?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setString(1, idPagamento);
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
    }

    public List<Pedido2> getPedidos(Cliente cliente) throws DAOException {
        List<Pedido2> pedidos = null;
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL get_lista_pedidos(?)")) {
            stmt.setLong(1, cliente.getId());
            ResultSet rs = stmt.executeQuery();
            pedidos = new ArrayList<>();
            while (rs.next()) {
                Pedido2 pedido = new Pedido2();
                pedido.setId(rs.getLong("id_pedido"));
                String time = new SimpleDateFormat("dd/MM/YYYY - HH:mm").format(new Date(((Timestamp) rs.getObject("datapedido", Timestamp.class)).getTime()));
                pedido.setData(time);
                pedido.setPrecoTotal(rs.getDouble("precototal"));
                pedido.setFormaPagamento(new FormaPagamento(0, rs.getString("formapagamento_descricao")));
                Endereco endereco = new Endereco();
                endereco.setId(rs.getObject("id_endereco") != null ? rs.getLong("id_endereco") : -1);
                endereco.setDescricao(rs.getString("endereco_descricao"));
                pedido.setEndereco(endereco);
                pedido.setStatus(!rs.getBoolean("entregue") ? "Entrega em andamento" : "Entregue");
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
        return pedidos;
    }

    public void getPedido(Pedido pedido, Cliente cliente) throws DAOException, IOException {
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL get_pedido(?,?)")) {
            stmt.setLong(1, cliente.getId());
            stmt.setLong(2, pedido.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pedido.setData(rs.getObject("datapedido", Timestamp.class));
                pedido.setPrecoTotal(rs.getDouble("precototal"));
                pedido.setFormaPagamento(new FormaPagamento(0, rs.getString("formapagamento_descricao")));
                Endereco endereco = new Endereco();
                endereco.setId(-1);
                endereco.setDescricao(rs.getString("endereco_descricao"));
                pedido.setEndereco(endereco);

                ObjectMapper mapeador = new ObjectMapper();
                List<ItemPedidoFacil> itens = mapeador.readValue(rs.getString("itens"), new TypeReference<List<ItemPedidoFacil>>() {
                });
                pedido.setItens(itens);
            }
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
    }

    synchronized public void cadastrarEndereco(Cliente cliente, Endereco endereco) throws DAOException {
        if (cliente == null || endereco == null) {
            throw new DAOIncorrectData(300);
        }
        if (endereco.getLogradouro() == null || endereco.getBairro() == null || endereco.getCep() == null
                || endereco.getCidade() == null || endereco.getComplemento() == null || endereco.getDescricao() == null
                || endereco.getNumero() == null) {
            throw new DAOIncorrectData(300);
        }
        if (endereco.getLogradouro().isEmpty() || endereco.getBairro().isEmpty() || endereco.getDescricao().isEmpty()
                || endereco.getNumero().isEmpty() || endereco.getCep().isEmpty() || endereco.getCidade().isEmpty()) {
            throw new DAOIncorrectData(301);
        }
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL inserir_endereco(?,?,?,?,?,?,?,?)")) {
            stmt.setLong(1, cliente.getId());
            stmt.setString(2, endereco.getLogradouro());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getComplemento());
            stmt.setString(5, endereco.getNumero());
            stmt.setString(6, endereco.getCidade());
            stmt.setString(7, endereco.getCep());
            stmt.setString(8, endereco.getDescricao());
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
    }

    synchronized public void removerEndereco(Cliente cliente, Endereco endereco) throws DAOException {
        if (cliente == null || endereco == null) {
            throw new DAOIncorrectData(300);
        }
        if (endereco.getId() <= 0) {
            throw new DAOIncorrectData(300);
        }
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL remover_endereco(?,?)")) {
            stmt.setLong(1, cliente.getId());
            stmt.setLong(2, endereco.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
    }

}
