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
import com.br.phdev.srs.models.Cliente;
import com.br.phdev.srs.models.Complemento;
import com.br.phdev.srs.models.ConfirmaPedido;
import com.br.phdev.srs.models.Endereco;
import com.br.phdev.srs.models.FormaPagamento;
import com.br.phdev.srs.models.Foto;
import com.br.phdev.srs.models.Genero;
import com.br.phdev.srs.models.ListaItens;
import com.br.phdev.srs.models.Item;
import com.br.phdev.srs.models.Pedido;
import com.br.phdev.srs.models.Tipo;
import com.br.phdev.srs.models.Usuario;
import com.br.phdev.srs.models.ValidaCadastro;
import com.br.phdev.srs.utils.Arquivo;
import com.br.phdev.srs.utils.ServicoArmazenamento;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public void cadastrar(Cliente cliente, boolean opcao, String token) throws DAOException, DAOExpectedException, DAOIncorrectData {
        if (cliente.getNome() == null || cliente.getCpf() == null || cliente.getEmail() == null
                || cliente.getTelefone() == null || cliente.getSenhaUsuario() == null) {
            throw new DAOIncorrectData("Alguns dados estão incorretos");
        }
        if (cliente.getNome().trim().isEmpty() || cliente.getCpf().trim().isEmpty() || cliente.getEmail().trim().isEmpty()
                || cliente.getTelefone().trim().isEmpty() || cliente.getSenhaUsuario().trim().isEmpty()) {
            throw new DAOIncorrectData("Alguns dados estão incorretos");
        }
        for (char c : cliente.getNome().toCharArray()) {
            if (!(((int) c > 64 && (int) c < 91) || ((int) c > 96 && (int) c < 122)) && (int)c != 32) {
                throw new DAOIncorrectData("Alguns dados estão incorretos");
            }
        }
        if (!cliente.getEmail().contains("@")) {
            throw new DAOIncorrectData("Alguns dados estão incorretos");
        }
        for (char c : cliente.getCpf().toCharArray()) {
            if (!((int) c > 47 && (int) c < 58)) {
                throw new DAOIncorrectData("Alguns dados estão incorretos");
            }
        }
        for (char c : cliente.getTelefone().toCharArray()) {
            if (!((int) c > 47 && (int) c < 58)) {
                throw new DAOIncorrectData("Alguns dados estão incorretos");
            }
        }
        
        StringBuilder ultimosDigitos = new StringBuilder();
        int soma = 0;
        int fator = 10;
        for (int i=0; i<cliente.getCpf().length()-2; i++) {
            soma += Integer.parseInt(String.valueOf(cliente.getCpf().charAt(i))) * fator--;
        }
        int resto = soma % 11;
        if (resto == 0 || resto == 1)
            ultimosDigitos.append(0);
        else
            ultimosDigitos.append((resto - 11));
        soma = 0;
        fator = 11;
        for (int i=0; i<cliente.getCpf().length()-2; i++) {
            soma += Integer.parseInt(String.valueOf(cliente.getCpf().charAt(i))) * fator--;
        }
        soma += Integer.parseInt(String.valueOf(ultimosDigitos.toString())) * 2;
        resto = soma % 11;
        if (resto == 0 || resto == 1)
            ultimosDigitos.append(0);
        else
            ultimosDigitos.append((resto - 11));
        
        if (!cliente.getCpf().endsWith(ultimosDigitos.toString()))
            throw new DAOIncorrectData("Alguns dados estão incorretos");

        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL cadastrar_cliente(?,?,?,?,?,?,?)")) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getSenhaUsuario());
            stmt.setBoolean(6, opcao);
            stmt.setString(7, token);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("erro") != null) {
                    throw new DAOExpectedException(rs.getString("erro"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public boolean validarCadastro(ValidaCadastro validaCadastro) throws DAOException, DAOIncorrectData {
        if (validaCadastro.getUsuario() == null || validaCadastro.getToken() == null) {
            throw new DAOIncorrectData("Alguns dados estão incorretos");
        }
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL validar_cadastro(?,?)")) {
            stmt.setString(1, validaCadastro.getUsuario());
            stmt.setString(2, validaCadastro.getToken());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("erro") == null) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return false;
    }

    public Cliente autenticar(Usuario usuario) throws DAOException, DAOIncorrectData {
        if (usuario.getNomeUsuario() == null || usuario.getSenhaUsuario() == null) {
            throw new DAOIncorrectData("Alguns dados estão incorretos");
        }
        if (usuario.getNomeUsuario().isEmpty() || usuario.getSenhaUsuario().isEmpty()) {
            throw new DAOIncorrectData("Alguns dados estão incorretos");
        }
        Cliente cliente = null;
        try {
            String sql = "call autenticar(?,?)";
            PreparedStatement stmt = super.conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getSenhaUsuario());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("erro") == null) {
                    cliente = new Cliente(
                            rs.getLong("id_cliente"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            null,
                            rs.getLong("id_usuario"),
                            usuario.getNomeUsuario(),
                            null
                    );
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao recuperar informações", e);
        }
        return cliente;
    }

    public void gerarSessao(Usuario usuario, String token) throws DAOException {
        String sql = "CALL sessao_validar(?,?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setLong(1, usuario.getIdUsuario());
            stmt.setString(2, token);
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void sairSessao(Usuario usuario, String token) throws DAOException {
        String sql = "CALL sessao_desvalidar(?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setLong(1, usuario.getIdUsuario());
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public ListaItens getItens() throws DAOException {
        ListaItens listaItens = null;
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL listar_itens")) {
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

                        try (PreparedStatement stmt2 = super.conexao.prepareStatement("CALL get_arquivos(?)")) {
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

                try (PreparedStatement stmt2 = super.conexao.prepareStatement("CALL get_arquivos(?)")) {
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
            throw new DAOException("Erro ao recuperar informações", e);
        }
        return listaItens;
    }

    public Item getItem(long idItem) throws DAOException {
        Item item = null;
        String sql = "call info_item(?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setLong(1, idItem);
            ResultSet rs = stmt.executeQuery();
            Set<Tipo> tipos = new HashSet<>();
            Set<Foto> fotos = new HashSet<>();
            Set<Complemento> complementos = new HashSet<>();
            item = new Item();
            long itemAtual = -1;
            while (rs.next()) {
                long idItemRecuperado = rs.getLong("id_item");
                if (idItemRecuperado != itemAtual) {
                    itemAtual = idItemRecuperado;
                    tipos = new HashSet<>();
                    fotos = new HashSet<>();
                    item.setId(rs.getLong("id_item"));
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
                try (PreparedStatement stmt2 = super.conexao.prepareStatement("CALL get_arquivos(?)")) {
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
                try (PreparedStatement stmt2 = super.conexao.prepareStatement("CALL get_complementos_item(?)")) {
                    stmt2.setLong(1, item.getId());
                    ResultSet rs2 = stmt2.executeQuery();
                    complementos = new HashSet<>();
                    while (rs2.next()) {
                        complementos.add(new Complemento(
                                rs2.getLong("id_complemento"),
                                rs2.getString("nome"),
                                0,
                                new Foto(rs2.getLong("id_arquivo"), null, 0)));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                item.setTipos(tipos);
                item.setComplementos(complementos);
                item.setFotos(fotos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Erro ao recuperar informações", e);
        }
        return item;
    }

    public List<Endereco> getEnderecos(Cliente cliente) throws DAOException {
        List<Endereco> enderecos = null;
        String sql = "call listar_enderecos(?)";
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
                enderecos.add(endereco);
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao recuperar informações", e);
        }
        return enderecos;
    }

    public List<FormaPagamento> getFormasPagamento(Cliente cliente) throws DAOException {
        List<FormaPagamento> formaPagamentos = null;
        String sql = "call listar_formaspagamento(?)";
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
            throw new DAOException("Erro ao recuperar informações", e);
        }
        return formaPagamentos;
    }

    public Foto getPublicFile(int idArquivo) throws DAOException {
        Foto foto = null;
        String sql = "call get_foto(?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setInt(1, idArquivo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                foto = new Foto();
                foto.setId(idArquivo);
            }
        } catch (SQLException e) {
            throw new DAOException("Falha ao adquirir informações do arquivo", e);
        }
        return foto;
    }

    public ConfirmaPedido inserirPrecos(ConfirmaPedido confirmaPedido) throws DAOException {
        String sql = "call get_preco_item(?)";
        try {
            PreparedStatement stmt = super.conexao.prepareStatement(sql);
            Set<Item> setItem = new HashSet<>();
            for (Item item : confirmaPedido.getItens()) {
                setItem.add(item);
            }
            StringBuilder itensList = new StringBuilder();
            for (Item item : setItem) {
                itensList.append(item.getId());
                itensList.append(',');
            }
            itensList.deleteCharAt((setItem.size() * 2) - 1);
            stmt.setString(1, itensList.toString());
            ResultSet rs = stmt.executeQuery();
            Map<Long, Double> precoItens = new HashMap<>();
            while (rs.next()) {
                precoItens.put(rs.getLong("id_item"), rs.getDouble("preco"));
            }
            for (Item item : confirmaPedido.getItens()) {
                item.setPreco(precoItens.get(item.getId()));
            }
            stmt.close();
            sql = "call get_preco_complemento(?)";
            stmt = super.conexao.prepareStatement(sql);
            Set<Complemento> setComplemento = new HashSet<>();
            for (Item item : confirmaPedido.getItens()) {
                if (item.getComplementos() != null) {
                    for (Complemento complemento : item.getComplementos()) {
                        setComplemento.add(complemento);
                    }
                }
            }
            StringBuilder listaComplementos = new StringBuilder();
            for (Complemento complemento : setComplemento) {
                listaComplementos.append(complemento.getId());
                listaComplementos.append(',');
            }
            listaComplementos.deleteCharAt((setComplemento.size() * 2) - 1);
            stmt.setString(1, listaComplementos.toString());
            rs = stmt.executeQuery();
            Map<Long, Double> precoComplementos = new HashMap<>();
            while (rs.next()) {
                precoComplementos.put(rs.getLong("id_complemento"), rs.getDouble("preco"));
            }
            for (Item item : confirmaPedido.getItens()) {
                if (item.getComplementos() != null) {
                    for (Complemento complemento : item.getComplementos()) {
                        complemento.setPreco(precoComplementos.get(complemento.getId()));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Falha ao adquirir informações do arquivo", e);
        }
        return confirmaPedido;
    }

    public void inserirPedido(Pedido pedido, Cliente cliente) throws DAOException {
        String sql = "call inserir_pedido(?,?,?,?,?,?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setObject(1, pedido.getData());
            stmt.setDouble(1, pedido.getPrecoTotal());
            stmt.setLong(2, pedido.getFormaPagamento().getId());
            stmt.setLong(3, cliente.getId());
            stmt.setLong(4, pedido.getEndereco().getId());
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(pedido.getItens());
            stmt.setString(5, json);
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException("Falha ao adquirir informações do arquivo", e);
        } catch (JsonProcessingException e) {
            throw new DAOException("Falha ao adquirir informações do arquivo", e);
        }
    }

}
