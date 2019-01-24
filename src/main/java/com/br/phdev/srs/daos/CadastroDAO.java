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
import com.br.phdev.srs.models.Codigo;
import com.br.phdev.srs.models.Mensagem;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class CadastroDAO extends BasicDAO {

    private final String chave = "ZXDas7966mby@";

    public CadastroDAO(Connection conexao) {
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
            if (!(((int) c > 64 && (int) c < 91) || ((int) c > 96 && (int) c < 123)
                    || ((int) c > 191 && (int) c < 198) || ((int) c > 198 && (int) c < 215)
                    || ((int) c > 215 && (int) c < 222) || ((int) c > 223 && (int) c < 230)
                    || ((int) c > 230 && (int) c < 247) || ((int) c > 248 && (int) c < 257)) && (int) c != 32) {
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

    synchronized public String preCadastrar(String usuario) throws DAOException, NoSuchAlgorithmException, UnsupportedEncodingException {
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL inserir_pre_cadastro_usuario(?,?)")) {
            stmt.setString(1, usuario);

            StringBuilder token = new StringBuilder();
            String textoParaHash = usuario
                    + Calendar.getInstance().getTime().toString() + this.chave;
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

    public Mensagem reenviarCodigo(String usuario) throws DAOException {
        Mensagem mensagem = new Mensagem();
        try (PreparedStatement stmt = super.conexao.prepareStatement("CALL utils_verificar_data_token_cadastro(?)")) {
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getObject("erro") != null) {
                    int segundos = rs.getInt("tempo_atual");
                    if (segundos > 300) {
                        try (PreparedStatement stmt2 = super.conexao.prepareStatement("CALL utils_reenviar_token_cadastro(?,?)")) {
                            stmt2.setString(1, usuario);

                            StringBuilder token = new StringBuilder();
                            String textoParaHash = usuario
                                    + Calendar.getInstance().getTime().toString() + this.chave;
                            MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
                            byte textoDigerido[] = algoritmo.digest(textoParaHash.getBytes("UTF-8"));
                            for (int i = 0; i < textoDigerido.length; i = i + 14) {
                                token.append(String.format("%02X", 0xFF & textoDigerido[i]));
                            }
                            stmt2.setString(2, token.toString());
                            stmt2.execute();
                            mensagem.setCodigo(100);
                            mensagem.setDescricao(token.toString());
                        }
                    } else {
                        mensagem.setCodigo(101);
                        mensagem.setDescricao(String.valueOf(segundos));
                    }
                } else {
                    mensagem.setCodigo(200);
                    mensagem.setDescricao(rs.getString("erro"));
                }
            }
        } catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new DAOException(e, 200);
        }
        return mensagem;
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

}
