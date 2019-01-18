/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.phdev.srs.daos;

import com.br.phdev.srs.exceptions.DAOException;
import com.br.phdev.srs.exceptions.DAOIncorrectData;
import com.br.phdev.srs.jdbc.FabricaConexao;
import com.br.phdev.srs.models.Complemento;
import com.br.phdev.srs.models.Foto;
import com.br.phdev.srs.models.GrupoVariacao;
import com.br.phdev.srs.models.Item;
import com.br.phdev.srs.models.Variacao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class RepositorioProdutos {

    private static RepositorioProdutos instancia = new RepositorioProdutos();

    private final HashMap<Long, Item> itens;
    private final HashMap<Long, Complemento> complementos;
    private final HashMap<Long, Variacao> variacoes;
    private Timestamp dataUltimoModificacao;

    public double frete;

    private RepositorioProdutos() {
        this.itens = new HashMap<>();
        this.complementos = new HashMap<>();
        this.variacoes = new HashMap<>();
        this.frete = 3;
    }

    public static RepositorioProdutos getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioProdutos();
        }
        return instancia;
    }

    public void preencherItem(Item item) {
        Item item2 = this.itens.get(item.getId());
        item.setPreco(item2.getPreco());
        item.setNome(item2.getNome());
        item.setFotos(item2.getFotos());
        item.setModificavel(item2.isModificavel());
        item.setModificavelIngrediente(item2.isModificavelIngrediente());
    }

    public void preencherComplemento(Complemento complemento) {
        Complemento complemento2 = this.complementos.get(complemento.getId());
        complemento.setPreco(complemento2.getPreco());
        complemento.setNome(complemento2.getNome());
    }

    public void preecherVariacao(Variacao variacao) {
        Variacao variacao2 = this.variacoes.get(variacao.getId());
        variacao.setNome(variacao2.getNome());
        variacao.setPreco(variacao2.getPreco());
    }

    public void checarVariacoes(List<GrupoVariacao> gvListCliente, Item itemReferencia) throws DAOIncorrectData {
        List<GrupoVariacao> gvListSistema = this.itens.get(itemReferencia.getId()).getVariacoes();        
        if (gvListSistema == null || gvListSistema.isEmpty()) {
            if (gvListCliente != null) {
                if (!gvListCliente.isEmpty()) {
                    throw new DAOIncorrectData(300);
                }
            }
        } else if (gvListCliente != null && !gvListCliente.isEmpty()) {
            if (gvListSistema.size() != gvListCliente.size()) {
                throw new DAOIncorrectData(300);
            }
            for (int i = 0; i < gvListSistema.size(); i++) {
                if (i > gvListCliente.size() - 1) {
                    throw new DAOIncorrectData(300);
                }
                GrupoVariacao gvCliente = gvListCliente.get(i);
                GrupoVariacao gvSistema = gvListSistema.get(i);
                if (gvSistema.getVariacoes() == null || gvCliente.getVariacoes() == null
                        || gvSistema.getVariacoes().isEmpty() || gvCliente.getVariacoes().isEmpty()) {
                    throw new DAOIncorrectData(300);
                }
                if (gvCliente.getVariacoes().size() > gvSistema.getMax()) {
                    throw new DAOIncorrectData(300);
                }
            }
        }
    }

    public void carregar(Connection conexao) throws DAOException {
        try (PreparedStatement stmt = conexao.prepareStatement("CALL get_data_ultima_alteracao_base(?)")) {
            stmt.setLong(1, 1);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Timestamp timestamp = (Timestamp) rs.getObject("ultima_modificacao_itens");
                if (this.dataUltimoModificacao == null) {
                    this.dataUltimoModificacao = timestamp;
                    this.carregarDados(conexao);
                } else {
                    if (this.dataUltimoModificacao.equals(timestamp)) {

                    } else {
                        this.dataUltimoModificacao = timestamp;
                        this.carregarDados(conexao);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
    }

    private void carregarDados(Connection conexao) throws SQLException {
        System.out.println("Atualizando dados dos produtos");
        this.itens.clear();
        this.complementos.clear();
        this.variacoes.clear();
        try (PreparedStatement stmt = conexao.prepareStatement("CALL get_lista_itens_basico")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getLong("id_item"));
                item.setNome(rs.getString("nome"));
                item.setPreco(rs.getDouble("preco"));
                item.setModificavel(rs.getBoolean("modificavel"));
                item.setModificavel(rs.getBoolean("modificavel_ingrediente"));
                HashSet<Foto> fotos = new HashSet<>();
                fotos.add(new Foto(rs.getLong("id_arquivo"), null, 0));
                item.setFotos(fotos);
                this.itens.put(rs.getLong("id_item"), item);
            }
        }
        try (PreparedStatement stmt = conexao.prepareStatement("CALL get_lista_complementos_basico")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Complemento complemento = new Complemento();
                complemento.setId(rs.getLong("id_complemento"));
                complemento.setNome(rs.getString("nome"));
                complemento.setPreco(rs.getDouble("preco"));
                this.complementos.put(rs.getLong("id_complemento"), complemento);
            }
        }

        try (PreparedStatement stmt = conexao.prepareStatement("CALL get_lista_variacoes")) {
            ResultSet rs = stmt.executeQuery();
            List<GrupoVariacao> variacoesList = new ArrayList<>();
            long idItemAtual = -1;
            while (rs.next()) {
                long idItemLido = rs.getLong("id_item");
                if (idItemAtual == -1) {
                    idItemAtual = idItemLido;
                }
                if (idItemAtual != idItemLido) {
                    this.itens.get(idItemAtual).setVariacoes(variacoesList);
                    idItemAtual = idItemLido;
                    variacoesList = new ArrayList<>();
                }
                if (variacoesList.size() > rs.getInt("grupo")) {
                    GrupoVariacao gv = variacoesList.get(rs.getInt("grupo"));
                    Variacao variacao = new Variacao(rs.getLong("id_variacao"), rs.getString("nome"), rs.getDouble("preco"), rs.getInt("ordem"));
                    gv.getVariacoes().add(variacao);
                    gv.setMax(rs.getInt("max"));
                    this.variacoes.put(rs.getLong("id_variacao"), variacao);
                } else {
                    GrupoVariacao gv = new GrupoVariacao();
                    Set<Variacao> v = new HashSet<>();
                    Variacao variacao = new Variacao(rs.getLong("id_variacao"), rs.getString("nome"), rs.getDouble("preco"), rs.getInt("ordem"));
                    v.add(variacao);
                    gv.setMax(rs.getInt("max"));
                    gv.setVariacoes(v);
                    variacoesList.add(gv);
                    this.variacoes.put(rs.getLong("id_variacao"), variacao);
                }
            }
            if (idItemAtual != -1) {
                this.itens.get(idItemAtual).setVariacoes(variacoesList);                
            }

        }
    }

}
