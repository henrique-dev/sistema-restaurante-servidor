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
        //response.setHeader("Access-Control-Allow-Origin", "*");
        //response.setHeader("Access-Control-Allow-Headers", "*");        
        if (request.getSession().getAttribute("usuario") != null) {
            System.out.println(request.getSession().getId());
            return true;
        } else {
            if (uri.equals("/sr/cliente/autenticar") || uri.equals("/sr/cliente/sem-autorizacao")) {
                return true;
            } else {                                
                response.sendRedirect("cliente/sem-autorizacao");
                return false;
            }
        }        
    }

}
