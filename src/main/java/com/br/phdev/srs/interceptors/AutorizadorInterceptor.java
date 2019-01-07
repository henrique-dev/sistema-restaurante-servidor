/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.phdev.srs.interceptors;

import com.br.phdev.srs.daos.ClienteDAO;
import com.br.phdev.srs.jdbc.FabricaConexao;
import com.br.phdev.srs.utils.HttpUtils;
import java.io.IOException;
import java.sql.Connection;
import java.util.Enumeration;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author henrique
 */
public class AutorizadorInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();        
        HttpSession sessao = request.getSession();
        System.out.println(sessao.getId() + " > " + uri);
        new HttpUtils().showHeaders(request);
        if (request.getSession().isNew())
            System.out.println("A sessão é nova");
                
        if (request.getSession().getAttribute("usuario") != null) {
            return true;
        } else {            
            if (uri.endsWith("cliente/autenticar") || uri.endsWith("cliente/sem-autorizacao") || uri.contains("cliente/teste")
                    || uri.contains("imagens") || uri.contains("validar-cadastro") || uri.endsWith("cliente/cadastrar")
                    || uri.endsWith("cliente/sair") || uri.endsWith("cliente/verificar-numero") || uri.endsWith("cliente/validar-numero")
                    || uri.endsWith("pagamentos/criar-pagamento") || uri.endsWith("pagamentos/executar-pagamento") || uri.contains("pagamento-efetuado")
                    || uri.contains("chat") || uri.contains("notificar") || uri.contains("teste") || uri.contains("resources") ||
                    uri.contains("cliente/verificar-sessao")) {
                return true;
            } else {
                response.sendRedirect("sem-autorizacao");
                return false;
            }
        }        
    }        

}
