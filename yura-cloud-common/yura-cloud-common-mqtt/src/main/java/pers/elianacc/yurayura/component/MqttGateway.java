package pers.elianacc.yurayura.component;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * mqtt网关 component
 *
 * @author ELiaNaCc
 * @since 2024-12-16
 */
@Component
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {

    void sendMsgToMqtt(@Header(value = MqttHeaders.TOPIC) String topic, String payload);

    void sendMsgToMqtt(@Header(value = MqttHeaders.TOPIC) String topic
            , @Header(value = MqttHeaders.QOS) int qos, String payload);

}
