/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.phdev.srs.interceptors;

import com.br.phdev.srs.utils.HttpUtils;
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
        System.out.println(uri);
        new HttpUtils().showHeaders(request);
        if (request.getSession().getAttribute("usuario") != null) {
            return true;
        } else {            
            if (uri.endsWith("autenticar") || uri.endsWith("cliente/sem-autorizacao") || uri.contains("cliente/teste")
                    || uri.contains("imagens") || uri.contains("validar-cadastro") || uri.endsWith("cliente/cadastrar")
                    || uri.endsWith("cliente/sair") || uri.endsWith("cliente/verificar-numero") || uri.endsWith("cliente/validar-numero")
                    || uri.endsWith("pagamentos/criar-pagamento") || uri.endsWith("pagamentos/executar-pagamento") || uri.contains("pagamento-efetuado")
                    || uri.contains("chat") || uri.contains("notificar") || uri.contains("teste") || uri.contains("resources")
                    || uri.contains("cliente/verificar-sessao") || uri.contains("pagamentos") 
                    || uri.contains("entrar")) {
                return true;
            } else {
                if (uri.contains("cliente"))
                    response.sendRedirect("sem-autorizacao");
                else
                    response.sendRedirect("entrar");
                return false;
            }
        }
    }

}
