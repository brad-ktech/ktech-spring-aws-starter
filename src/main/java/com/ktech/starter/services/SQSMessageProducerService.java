package com.ktech.starter.services;


import com.ktech.starter.entities.SQSMessagable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class SQSMessageProducerService {


    @Autowired(required=false)
    private QueueMessagingTemplate queueMessagingTemplate;




    public <T> void send(String queue, SQSMessagable message, Map<String, Object> headers) {

        if (message == null) {
            log.warn("SQS Producer cant produce 'null' value");
            return;
        }

        send(queue, message.getSQSMessage(), headers);

    }

    public void send(String queue, String message, Map<String, Object> headers) {
        if (message == null) {
            log.warn("SQS Producer cant produce 'null' value");
            return;
        }

        log.debug(" Message {} " + message);
        log.debug(" Queue name {} " + queue);

        queueMessagingTemplate.convertAndSend(queue, MessageBuilder.withPayload(message).build(), headers);
    }

    public void send(String queue, String message){
        if (message == null) {
            log.warn("SQS Producer cant produce 'null' value");
            return;
        }
        log.debug(" Message {} " + message);
        log.debug(" Queue name {} " + queue);

        queueMessagingTemplate.send(queue, MessageBuilder.withPayload(message).build());

    }

    public <T> void send(String queue, SQSMessagable message){

        send(queue, message.getSQSMessage());

    }








}
