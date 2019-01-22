/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.daos;

import com.br.phdev.srs.exceptions.DAOException;
import com.br.phdev.srs.exceptions.DAOIncorrectData;
import com.br.phdev.srs.models.Cliente;
import com.br.phdev.srs.models.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class AutenticaDAO extends BasicDAO {
    
    public AutenticaDAO(Connection conexao) {
        super(conexao);
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
    
    public boolean verificarSessao(String sessaoId) throws DAOException {
        if (sessaoId == null) {
            return false;
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
            throw new DAOException(e, 300);
        }
        return false;
    }
    
}
