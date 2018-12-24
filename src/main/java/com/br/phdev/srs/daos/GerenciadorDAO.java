/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.daos;

import com.br.phdev.srs.exceptions.DAOException;
import com.br.phdev.srs.models.Complemento;
import com.br.phdev.srs.models.Foto;
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
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
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
        String sql = "CALL inserir_genero(?)";
        for (Genero genero : generos) {
            try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
                stmt.setString(1, genero.getNome());
                stmt.execute();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    public void removerGeneros(List<Genero> generos) throws DAOException, SQLIntegrityConstraintViolationException {
        String sql = "CALL remover_genero(?)";
        for (Genero genero : generos) {
            try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
                stmt.setLong(1, genero.getId());
                stmt.execute();
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    throw new SQLIntegrityConstraintViolationException("Algum genero está sendo utilizado e não pode ser excluido.");
                } else {
                    throw new DAOException(e);
                }
            }
        }
    }

    public List<Tipo> getTipos() throws DAOException {
        List<Tipo> tipos = null;
        String sql = "call listar_tipos";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
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
        String sql = "CALL inserir_tipo(?)";
        for (Tipo tipo : tipos) {
            try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
                stmt.setString(1, tipo.getNome());
                stmt.execute();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    public void removerTipos(List<Tipo> tipos) throws DAOException, SQLIntegrityConstraintViolationException {
        String sql = "CALL remover_tipo(?)";
        for (Tipo tipo : tipos) {
            try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
                stmt.setLong(1, tipo.getId());
                stmt.execute();
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    throw new SQLIntegrityConstraintViolationException("Algum tipo está sendo utilizado e não pode ser excluido.");
                } else {
                    throw new DAOException(e);
                }
            }
        }
    }

    public List<Complemento> getComplementos() throws DAOException {
        List<Complemento> complementos = null;
        String sql = "CALL listar_complementos";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            complementos = new ArrayList<>();
            while (rs.next()) {
                Complemento complemento = new Complemento();
                complemento.setId(rs.getLong("id_complemento"));
                complemento.setNome(rs.getString("nome"));
                complemento.setPreco(rs.getDouble("preco"));
                complemento.setFoto(new Foto(rs.getLong("id_arquivo"), null));
                complementos.add(complemento);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return complementos;
    }

    public void adicionarComplemento(Complemento complemento) throws DAOException {
        String sql = "call inserir_complemento(?,?,?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql.toString())) {
            stmt.setString(1, complemento.getNome());
            stmt.setDouble(2, complemento.getPreco());
            stmt.setDouble(3, complemento.getFoto().getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void removerComplementos(List<Complemento> complementos) throws DAOException, SQLIntegrityConstraintViolationException {        
        List<Complemento> complementosParaApagarFoto = new ArrayList<>();
        for (Complemento complemento : complementos) {
            try {
                String sql = "CALL remover_complemento(?)";
                PreparedStatement stmt = super.conexao.prepareStatement(sql);
                stmt.setLong(1, complemento.getFoto().getId());
                stmt.execute();
                stmt.close();                              
                sql = "CALL remover_arquivo(?)";
                stmt = super.conexao.prepareStatement(sql);
                stmt.setLong(1, complemento.getId());                
                stmt.execute();
                stmt.close();
                complementosParaApagarFoto.add(complemento);
            } catch (SQLException e) {
                if (e instanceof SQLIntegrityConstraintViolationException) {
                    //throw new SQLIntegrityConstraintViolationException("Algum complmento está sendo utilizado e não pode ser excluido.");
                }
                throw new DAOException(e);
            }
        }
        complementos.clear();
        complementos.addAll(complementosParaApagarFoto);
    }

    public long adicionarArquivo() throws DAOException {
        String sql = "call inserir_arquivo";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
            return -1;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void removerArquivo(Foto foto) throws DAOException {
        String sql = "call remover_arquivo(?)";
        try (PreparedStatement stmt = super.conexao.prepareStatement(sql)) {
            stmt.setLong(1, foto.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

}
