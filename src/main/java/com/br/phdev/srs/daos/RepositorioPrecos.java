/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.phdev.srs.daos;

import com.br.phdev.srs.exceptions.DAOException;
import com.br.phdev.srs.jdbc.FabricaConexao;
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

    private static RepositorioPrecos instancia;

    private HashMap<Long, Double> itens;
    private HashMap<Long, Double> complementos;
    private Timestamp dataUltimoModificacao;

    private RepositorioPrecos() {
        this.itens = new HashMap<>();
        this.complementos = new HashMap<>();
    }

    public static RepositorioPrecos getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioPrecos();
        }
        return instancia;
    }

    public String carregarPrecos(Connection conexao) throws DAOException {
        StringBuilder data = new StringBuilder();
        try (PreparedStatement stmt = conexao.prepareStatement("CALL get_data_ultima_alteracao(?)")) {
            stmt.setLong(1, 1);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Timestamp timestamp = (Timestamp) rs.getObject("ultima_modificacao_itens");
                if (this.dataUltimoModificacao == null) {
                    this.dataUltimoModificacao = timestamp;
                    data.append("Data extraida: " + timestamp + "\n");                    
                } else {
                    data.append("Data extraida: " + timestamp + "\n");
                    data.append("Data atual: " + dataUltimoModificacao + "\n");
                }
                //return timestamp.toString();
            }
        } catch (SQLException e) {
            throw new DAOException(e, 200);
        }
        return data.toString();
    }

}
