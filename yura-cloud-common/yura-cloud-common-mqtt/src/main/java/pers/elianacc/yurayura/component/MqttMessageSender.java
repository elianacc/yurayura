package pers.elianacc.yurayura.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * mqtt消息发送 component
 *
 * @author ELiaNaCc
 * @since 2024-12-16
 */
@Component
public class MqttMessageSender {

    @Autowired
    private MqttGateway mqttGateway;

    public void sendMsg(String topic, String msg) {
        mqttGateway.sendMsgToMqtt(topic, msg);
    }

    public void sendMsg(String topic, int qos, String msg) {
        mqttGateway.sendMsgToMqtt(topic, qos, msg);
    }

}
