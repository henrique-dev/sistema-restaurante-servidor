/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.daos;

import com.br.phdev.srs.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class BasicDAO {
    
    public enum Tabela {
        arquivo,
        tipo,
        restaurante,
        genero,
        item,
        complemento,
        usuario,
        cliente,
        pedido,
        entrega,
        endereco,
        formapagamento
    };
    
    protected Connection conexao;
    
    BasicDAO(Connection conexao) {
        this.conexao = conexao;
    }
    
    @Deprecated
    synchronized static public long[] getProximoIndexArray(Connection conexao, Tabela nomeTabela, int quantidade) throws DAOException {
        long[] idsAlocados = new long[quantidade];
        for (int i=0; i<quantidade; i++) {
            idsAlocados[i] = getProximoIndex(conexao, nomeTabela);
        }
        return idsAlocados;
    }
    
    synchronized static public long getProximoIndex(Connection conexao, Tabela nomeTabela) throws DAOException {
        String sql = "CALL get_proximo_index(?,true)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)){            
            stmt.setString(1, nomeTabela.toString());
            ResultSet rs = stmt.executeQuery();            
            if (rs.next())
                return rs.getLong("proximo_index");
            else          
                return -1;
        } catch (SQLException e) {
            throw new DAOException("Falha ao requisitar um novo id", e);
        }
    }
    
    @Deprecated
    synchronized static public void desalocarIndexArray(Connection conexao, Tabela nomeTabela, long[] ids) throws DAOException {
        for (int i=0; i<ids.length; i++) {            
            desalocarIndex(conexao, nomeTabela, ids[i]);
        }
    }
    
    synchronized static public void desalocarIndex(Connection conexao, Tabela nomeTabela, long id) throws DAOException {
        String sql = "CALL desalocar_index(?,?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)){
            stmt.setString(1, nomeTabela.toString());
            stmt.setLong(2, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException("Falha ao liberar o id", e);
        }
    }    
    
}
