/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.daos;

import com.br.phdev.srs.exceptions.DAOException;
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
import com.br.phdev.srs.utils.Arquivo;
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

    public Cliente autenticar(Usuario usuario) throws DAOException {
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

    public ListaItens getItens() throws DAOException {
        ListaItens listaItens = null;
        String sql = "call listar_itens";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            List<Item> itens = new ArrayList<>();
            Set<Tipo> tipos = new HashSet<>();
            Set<Genero> generos = new HashSet<>();
            Set<Foto> fotos = new HashSet<>();
            Item item = new Item();
            long pratoAtual = -1;
            while (rs.next()) {
                long idPrato = rs.getLong("id_item");
                if (idPrato != pratoAtual) {
                    if (pratoAtual != -1) {
                        item.setTipos(tipos);
                        item.setFotos(fotos);
                        itens.add(item);
                    }
                    pratoAtual = idPrato;
                    item = new Item();
                    tipos = new HashSet<>();
                    fotos = new HashSet<>();
                    item.setId(rs.getLong("id_item"));
                    item.setNome(rs.getString("nome"));
                    item.setPreco(rs.getDouble("preco"));
                    item.setModificavel(rs.getBoolean("modificavel"));
                    fotos.add(new Foto(rs.getLong("id_arquivo"), null));
                    Genero genero = new Genero(rs.getLong("id_genero"), rs.getString("genero"));
                    item.setGenero(genero);
                    generos.add(genero);
                }
                tipos.add(new Tipo(rs.getLong("id_tipo"), rs.getString("tipo_nome")));
            }
            if (pratoAtual != -1) {
                item.setTipos(tipos);
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
        Item prato = null;
        String sql = "call info_item(?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setLong(1, idItem);
            ResultSet rs = stmt.executeQuery();
            Set<Tipo> tipos = new HashSet<>();
            Set<Foto> fotos = new HashSet<>();
            Set<Complemento> complementos = new HashSet<>();
            prato = new Item();
            long pratoAtual = -1;
            while (rs.next()) {
                if (idItem != pratoAtual) {
                    pratoAtual = idItem;
                    tipos = new HashSet<>();
                    fotos = new HashSet<>();
                    prato.setId(rs.getLong("id_item"));
                    prato.setNome(rs.getString("nome"));
                    prato.setPreco(rs.getDouble("preco"));
                    prato.setDescricao(rs.getString("descricao"));
                    prato.setModificavel(rs.getBoolean("modificavel"));
                    prato.setGenero(new Genero(rs.getLong("id_genero"), rs.getString("genero")));
                }
                tipos.add(new Tipo(rs.getLong("id_tipo"), rs.getString("tipo_nome")));
                fotos.add(new Foto(rs.getLong("id_arquivo"), null));
                complementos.add(new Complemento(
                        rs.getLong("id_complemento"),
                        rs.getString("complemento_nome"),
                        rs.getDouble("complemento_preco"),
                        new Foto(rs.getLong("complemento_id_arquivo"), null)));
            }
            if (pratoAtual != -1) {
                prato.setTipos(tipos);
                prato.setFotos(fotos);
                prato.setComplementos(complementos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Erro ao recuperar informações", e);
        }
        return prato;
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

    public Arquivo getPublicFile(int idArquivo) throws DAOException {
        Arquivo arquivo = null;
        String sql = "call get_foto(?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setInt(1, idArquivo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                arquivo = new Arquivo();
                arquivo.setId(idArquivo);
                arquivo.setCaminho(rs.getString("caminho"));
            }
        } catch (SQLException e) {
            throw new DAOException("Falha ao adquirir informações do arquivo", e);
        }
        return arquivo;
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
