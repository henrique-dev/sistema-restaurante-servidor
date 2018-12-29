/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.phdev.srs.daos;

import com.br.phdev.srs.exceptions.DAOException;
import com.br.phdev.srs.jdbc.FabricaConexao;
import com.br.phdev.srs.models.Complemento;
import com.br.phdev.srs.models.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class RepositorioPrecos {

    private static RepositorioPrecos instancia = new RepositorioPrecos();

    private final HashMap<Long, Double> itens;
    private final HashMap<Long, Double> complementos;
    private Timestamp dataUltimoModificacao;
    
    public double frete;

    private RepositorioPrecos() {
        System.out.println("REPOSITORIO DE PREÇOS CRIADO");
        this.itens = new HashMap<>();
        this.complementos = new HashMap<>();
        this.frete = 3;
    }

    public static RepositorioPrecos getInstancia() {
        System.out.println("HERE");
        if (instancia == null) {
            instancia = new RepositorioPrecos();
        }
        return instancia;
    }
    
    public void inserirPrecoNoItem(Item item) {
        item.setPreco(this.itens.get(item.getId()));
    }
    
    public void inserirPrecoNoComplemento(Complemento complemento) {
        complemento.setPreco(this.complementos.get(complemento.getId()));
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
        this.itens.clear();
        this.complementos.clear();
        try (PreparedStatement stmt2 = conexao.prepareStatement("CALL get_lista_itens_basico")) {
            ResultSet rs = stmt2.executeQuery();
            while (rs.next()) {
                this.itens.put(rs.getLong("id_item"), rs.getDouble("preco"));
            }
        }
        try (PreparedStatement stmt2 = conexao.prepareStatement("CALL get_lista_complementos_basic")) {
            ResultSet rs = stmt2.executeQuery();
            while (rs.next()) {
                this.complementos.put(rs.getLong("id_complemento"), rs.getDouble("preco"));
            }
        }
    }

}
