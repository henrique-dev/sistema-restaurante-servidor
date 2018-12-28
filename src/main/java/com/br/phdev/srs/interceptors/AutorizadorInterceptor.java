/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.phdev.srs.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author henrique
 */
public class AutorizadorInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        System.out.println(uri);        
        //response.setHeader("Access-Control-Allow-Origin", "*");
        //response.setHeader("Access-Control-Allow-Headers", "*"); 
        if (request.getSession().getAttribute("usuario") != null) {
            
            return true;
        } else {
            if (uri.contains("cliente/autenticar") || uri.contains("cliente/sem-autorizacao") || uri.contains("cliente/teste")
                    || uri.contains("cliente/imagens") || uri.contains("validar-cadastro") || uri.contains("cadastrar") || uri.contains("sair")) {
                return true;
            } else {                                
                response.sendRedirect("sem-autorizacao");                
                return false;
            }
        }        
    }

}
