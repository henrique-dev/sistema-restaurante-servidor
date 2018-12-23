/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.daos;

import com.br.phdev.srs.exceptions.DAOException;
import com.br.phdev.srs.models.Genero;
import com.br.phdev.srs.models.Tipo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class GerenciadorDAO extends BasicDAO {
    
    public GerenciadorDAO(Connection conexao) {
        super(conexao);
    }
    
    public List<Genero> getGeneros() throws DAOException {
        List<Genero> generos = null;
        String sql = "call listar_generos";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            generos = new ArrayList<>();
            while (rs.next()) {
                Genero genero = new Genero();
                genero.setId(rs.getLong("id_genero"));
                genero.setNome(rs.getString("nome"));
                generos.add(genero);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return generos;
    }
    
    public void adicionarGeneros(List<Genero> generos) throws DAOException {
        StringBuilder sql = new StringBuilder("INSERT INTO genero (nome) VALUES (");
        for (int i = 0; i < generos.size(); i++) {
            sql.append('?');
            if (i < generos.size() - 1) {
                sql.append("),(");
            } else {
                sql.append(")");
            }
        }
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql.toString())) {
            for (int i = 0; i < generos.size(); i++) {
                stmt.setString(i + 1, generos.get(i).getNome());
            }
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public String removerGeneros(List<Genero> generos) throws DAOException, SQLIntegrityConstraintViolationException {
        StringBuilder sql = new StringBuilder("DELETE FROM genero WHERE genero.id_genero in (");
        for (int i = 0; i < generos.size(); i++) {
            sql.append('?');
            if (i < generos.size() - 1) {
                sql.append(",");
            } else {
                sql.append(")");
            }
        }
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql.toString())) {
            for (int i = 0; i < generos.size(); i++) {
                stmt.setLong(i + 1, generos.get(i).getId());
            }
            stmt.execute();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException("Algum genero está sendo utilizado e não pode ser excluido.");
            }
            throw new DAOException(e);
        }
        return "";
    }
    
    public List<Tipo> getTipos() throws DAOException {
        List<Tipo> tipos = null;
        String sql = "call listar_tipos";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            tipos = new ArrayList<>();
            while (rs.next()) {
                Tipo tipo = new Tipo();
                tipo.setId(rs.getLong("id_tipo"));
                tipo.setNome(rs.getString("nome"));
                tipos.add(tipo);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return tipos;
    }   
    
    public void adicionarTipos(List<Tipo> tipos) throws DAOException {
        StringBuilder sql = new StringBuilder("INSERT INTO tipo (nome) VALUES (");
        for (int i = 0; i < tipos.size(); i++) {
            sql.append('?');
            if (i < tipos.size() - 1) {
                sql.append("),(");
            } else {
                sql.append(")");
            }
        }
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql.toString())) {
            for (int i = 0; i < tipos.size(); i++) {
                stmt.setString(i + 1, tipos.get(i).getNome());
            }
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public String removerTipos(List<Tipo> tipos) throws DAOException, SQLIntegrityConstraintViolationException {
        StringBuilder sql = new StringBuilder("DELETE FROM tipo WHERE tipo.id_tipo in (");
        for (int i = 0; i < tipos.size(); i++) {
            sql.append('?');
            if (i < tipos.size() - 1) {
                sql.append(",");
            } else {
                sql.append(")");
            }
        }
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql.toString())) {
            for (int i = 0; i < tipos.size(); i++) {
                stmt.setLong(i + 1, tipos.get(i).getId());
            }
            stmt.execute();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException("Algum tipo está sendo utilizado e não pode ser excluido.");
            }
            throw new DAOException(e);
        }
        return "";
    }
    
}
