package pers.elianacc.yurayura.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

/**
 * mqtt消息处理 config
 *
 * @author ELiaNaCc
 * @since 2024-12-16
 */
@Configuration
public class MessageHandlerConfig {

    @Autowired
    private ReceiverMessageHandler receiverMessageHandler;

    @Bean
    @ServiceActivator(inputChannel = "messageInboundChannel")
    public MessageHandler messageHandler() {
        return receiverMessageHandler;
    }

}
