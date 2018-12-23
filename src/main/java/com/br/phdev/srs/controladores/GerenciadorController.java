/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.controladores;

import com.br.phdev.srs.daos.ClienteDAO;
import com.br.phdev.srs.daos.GerenciadorDAO;
import com.br.phdev.srs.exceptions.DAOException;
import com.br.phdev.srs.jdbc.FabricaConexao;
import com.br.phdev.srs.models.Genero;
import com.br.phdev.srs.models.Tipo;
import com.br.phdev.srs.models.Usuario;
import com.br.phdev.srs.utils.Mensagem;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
@Controller
public class GerenciadorController {
    
    @PostMapping("Gerenciador/ListarGeneros")
    public ResponseEntity<List<Genero>> listarGeneros(HttpSession sessao) {
        List<Genero> generos = null;
        try (Connection conexao = new FabricaConexao().conectar()){
            GerenciadorDAO gerenciadorDAO = new GerenciadorDAO(conexao);
            generos = gerenciadorDAO.getGeneros();
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);        
        return new ResponseEntity<>(generos, httpHeaders, HttpStatus.OK);        
    }
    
    @PostMapping("Gerenciador/CadastrarGeneros")
    public ResponseEntity<Mensagem> cadastrarGeneros(@RequestBody List<Genero> generos) {
        Mensagem mensagem = new Mensagem();
        try (Connection conexao = new FabricaConexao().conectar()){
            GerenciadorDAO gerenciadorDAO = new GerenciadorDAO(conexao);
            gerenciadorDAO.adicionarGeneros(generos);
            mensagem.setCodigo(0);
            mensagem.setDescricao("Generos inseridos com sucesso");
        } catch (DAOException e) {
            mensagem.setCodigo(-1);
            mensagem.setDescricao("Erro na inserção dos generos");
            e.printStackTrace();
        } catch (SQLException e) {
            mensagem.setCodigo(-1);
            mensagem.setDescricao("Erro ao abrir conexão");
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);        
        return new ResponseEntity<>(mensagem, httpHeaders, HttpStatus.OK);        
    }
    
    @PostMapping("Gerenciador/RemoverGeneros")
    public ResponseEntity<Mensagem> removerGeneros(@RequestBody List<Genero> generos) {
        Mensagem mensagem = new Mensagem();
        try (Connection conexao = new FabricaConexao().conectar()){
            GerenciadorDAO gerenciadorDAO = new GerenciadorDAO(conexao);
            gerenciadorDAO.removerGeneros(generos);
            mensagem.setCodigo(0);
            mensagem.setDescricao("Generos removidos com sucesso");
        } catch (DAOException e) {
            mensagem.setCodigo(-1);
            mensagem.setDescricao("Erro na remoção dos generos");
            e.printStackTrace();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                mensagem.setCodigo(-1);
                mensagem.setDescricao("Alguns gêneros estão sendo utilizados e não podem ser excluídos");
            } else {
                mensagem.setCodigo(-1);
                mensagem.setDescricao("Erro ao abrir conexão");
                e.printStackTrace();
            }            
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);        
        return new ResponseEntity<>(mensagem, httpHeaders, HttpStatus.OK);        
    }
    
    @PostMapping("Gerenciador/ListarTipos")
    public ResponseEntity<List<Tipo>> listarTipos(HttpSession sessao) {
        List<Tipo> tipos = null;
        try (Connection conexao = new FabricaConexao().conectar()){
            GerenciadorDAO gerenciadorDAO = new GerenciadorDAO(conexao);
            tipos = gerenciadorDAO.getTipos();
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);        
        return new ResponseEntity<>(tipos, httpHeaders, HttpStatus.OK);        
    }
    
    @PostMapping("Gerenciador/CadastrarTipos")
    public ResponseEntity<Mensagem> cadastrarTipos(@RequestBody List<Tipo> tipos) {
        Mensagem mensagem = new Mensagem();
        try (Connection conexao = new FabricaConexao().conectar()){
            GerenciadorDAO gerenciadorDAO = new GerenciadorDAO(conexao);
            gerenciadorDAO.adicionarTipos(tipos);
            mensagem.setCodigo(0);
            mensagem.setDescricao("Tipos inseridos com sucesso");
        } catch (DAOException e) {
            mensagem.setCodigo(-1);
            mensagem.setDescricao("Erro na inserção dos tipos");
            e.printStackTrace();
        } catch (SQLException e) {
            mensagem.setCodigo(-1);
            mensagem.setDescricao("Erro ao abrir conexão");
            e.printStackTrace();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);        
        return new ResponseEntity<>(mensagem, httpHeaders, HttpStatus.OK);        
    }
    
    @PostMapping("Gerenciador/RemoverTipos")
    public ResponseEntity<Mensagem> removerTipos(@RequestBody List<Tipo> tipos) {
        Mensagem mensagem = new Mensagem();
        try (Connection conexao = new FabricaConexao().conectar()){
            GerenciadorDAO gerenciadorDAO = new GerenciadorDAO(conexao);
            gerenciadorDAO.removerTipos(tipos);
            mensagem.setCodigo(0);
            mensagem.setDescricao("Tipos removidos com sucesso");
        } catch (DAOException e) {
            mensagem.setCodigo(-1);
            mensagem.setDescricao("Erro na remoção dos tipos");
            e.printStackTrace();
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                mensagem.setCodigo(-1);
                mensagem.setDescricao("Alguns tipos estão sendo utilizados e não podem ser excluídos");
            } else {
                mensagem.setCodigo(-1);
                mensagem.setDescricao("Erro ao abrir conexão");
                e.printStackTrace();
            }            
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);        
        return new ResponseEntity<>(mensagem, httpHeaders, HttpStatus.OK);        
    }
    
}
