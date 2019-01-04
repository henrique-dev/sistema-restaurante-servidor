/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.teste;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
//@EnableScheduling
@Controller
public class GreetingController {
    
    @Autowired
    private SimpMessagingTemplate template;
    
    @MessageMapping("/chat")
    @SendTo("paulo")
    public OutputMessage greeting(Message message) throws Exception {
        System.out.println(message.getText());
        Thread.sleep(1000);
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }
    
    //@Scheduled(fixedRate = 5000)
    public void ola() throws Exception {        
        //System.out.println("HERE");
        String time = new SimpleDateFormat("HH:mm").format(new Date());        
        OutputMessage out = new OutputMessage("Servidor", "Mensagem agendada", time);
        ObjectMapper mapeador = new ObjectMapper();
        String json = mapeador.writeValueAsString(out);
        //this.template.convertAndSend("/topic/messages", json);
        if (!WebSocketConfig.sessoes.isEmpty()) {
            System.out.println("Enviando msg para: " + WebSocketConfig.sessoes.get(0));
            this.template.convertAndSendToUser(WebSocketConfig.sessoes.get(0), 
                    "/queue/reply", json);
        }
    }
    
}
