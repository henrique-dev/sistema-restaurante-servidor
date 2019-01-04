/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.teste;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public static List<String> sessoes = new ArrayList<>();

    @Override
    public void registerStompEndpoints(StompEndpointRegistry ser) {
        //ser.addEndpoint("/chat");
        //ser.addEndpoint("/chat").setAllowedOrigins("*").withSockJS();

        ser.addEndpoint("/chat").addInterceptors(new HttpSessionHandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest req,
                    ServerHttpResponse res, WebSocketHandler handler,
                    Map attributes) {
                if (req instanceof ServletServerHttpRequest) {
                    ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) req;
                    HttpSession sessao = serverHttpRequest.getServletRequest().getSession();
                    attributes.put("sessionId", sessao.getId());
                    sessoes.add(0, sessao.getId());
                    System.out.println(sessao.getId());
                }
                return true;
            }

        }).setHandshakeHandler(new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest req,
                    WebSocketHandler wsHandler,
                    Map<String, Object> attributes) {
                HttpHeaders headers = req.getHeaders();                
                System.out.println(headers);
                String user = "";
                if (req instanceof ServletServerHttpRequest) {
                    ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) req;
                    HttpSession sessao = serverHttpRequest.getServletRequest().getSession();
                    user = sessao.getId();
                }
                System.out.println("usuario GERADO: " + user);
                return new StompPrincipal(user);
            }
        }).setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration wstr) {
            
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration cr) {

    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration cr) {

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> list) {
        return true;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry mbr) {
        mbr.enableSimpleBroker("/queue", "/user");
        mbr.setApplicationDestinationPrefixes("/app");        
        //mbr.setUserDestinationPrefix("/user");
    }
    
    public class StompPrincipal implements Principal {
        
        private String name;

        public StompPrincipal(String name) {
            this.name = name;
        }                

        @Override
        public String getName() {
            return this.name;
        }
        
    }

}
