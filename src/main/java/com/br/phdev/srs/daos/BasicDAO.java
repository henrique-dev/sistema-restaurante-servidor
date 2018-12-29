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
    
}
