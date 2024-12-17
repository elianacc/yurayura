package pers.elianacc.yurayura.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import pers.elianacc.yurayura.constants.MqttConstant;

/**
 * mqtt出站 config
 *
 * @author ELiaNaCc
 * @since 2024-12-16
 */
@Configuration
public class MqttOutboundConfig {

    @Autowired
    private MqttConfigProperties mqttConfigProperties;

    @Autowired
    private MqttPahoClientFactory mqttPahoClientFactory;

    // 消息通道
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutboundMessageHandler() {
        MqttPahoMessageHandler mqttPahoMessageHandler = new MqttPahoMessageHandler(
                mqttConfigProperties.getHostUrl()
                , mqttConfigProperties.getClientId() + MqttConstant.CLIENT_SUFFIX_PRODUCERS
                , mqttPahoClientFactory);
        mqttPahoMessageHandler.setDefaultQos(mqttConfigProperties.getQos());
        mqttPahoMessageHandler.setDefaultTopic("default");
        mqttPahoMessageHandler.setAsync(true);
        return mqttPahoMessageHandler;
    }

}
