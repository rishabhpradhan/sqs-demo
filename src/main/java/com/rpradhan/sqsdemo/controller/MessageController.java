package com.rpradhan.sqsdemo.controller;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rpradhan
 */

@Slf4j
@RestController
@RequestMapping("/sqs")
public class MessageController {

    private final AmazonSQSAsync amazonSQSAsync;
    private final String sqsUrl;

    public MessageController(@Value("${cloud.aws.sqs.url}") String sqsUrl, AmazonSQSAsync amazonSQSAsync) {
        this.sqsUrl = sqsUrl;
        this.amazonSQSAsync = amazonSQSAsync;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody String message) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest(sqsUrl, message);
        sendMessageRequest.setMessageGroupId("IAUR");
        log.debug("Sending message {}", sendMessageRequest);
        SendMessageResult result = amazonSQSAsync.sendMessage(sendMessageRequest);
        log.info("Message sent to queue :: messageId :: " + result.getMessageId());
    }

}
