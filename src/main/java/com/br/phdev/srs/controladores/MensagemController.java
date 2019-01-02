/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.controladores;

import com.br.phdev.srs.configuracoes.Message;
import com.br.phdev.srs.configuracoes.OutputMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
@EnableScheduling
@Controller
public class MensagemController {
    
    @Autowired
    private SimpMessagingTemplate template;
            
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(Message mensagem) throws Exception {
        System.out.println(mensagem);
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(mensagem.getFrom(), mensagem.getText(), time);
    }
    
    @Scheduled(fixedRate = 5000)
    public void ola() {
        System.out.println("HERE");
        this.template.convertAndSend("/topic/messages", "EAI MAN");
    }
    
}