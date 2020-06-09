package com.rpradhan.sqsdemo.application.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author rpradhan
 */
@Slf4j
@Component
public class MessageListener {

    /**
     * This {@link SqsListener will listen to message described by the queue name}
     * @param message
     */
    @SqsListener(value = "oce_test.fifo", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void getMessage(String message, @Headers Map<String, String> headers ) {
        log.info(headers.toString());
        log.info("Received message :: {}", message);
    }
}
