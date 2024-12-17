package pers.elianacc.yurayura.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import pers.elianacc.yurayura.constants.MqttConstant;

/**
 * mqtt入站 config
 *
 * @author ELiaNaCc
 * @since 2024-12-16
 */
@Configuration
public class MqttInboundConfig {

    @Autowired
    private MqttConfigProperties mqttConfigProperties;

    @Autowired
    private MqttPahoClientFactory mqttPahoClientFactory;

    // 消息通道
    @Bean
    public MessageChannel messageInboundChannel() {
        return new DirectChannel();
    }

    // 配置入站适配器，作用：设置订阅主题，以及指定消息的相关属性
    @Bean
    public MessageProducer messageProducer() {
        MqttPahoMessageDrivenChannelAdapter mqttPahoMessageDrivenChannelAdapter = new MqttPahoMessageDrivenChannelAdapter(
                mqttConfigProperties.getHostUrl(),
                mqttConfigProperties.getClientId() + MqttConstant.CLIENT_SUFFIX_CONSUMERS,
                mqttPahoClientFactory,
                mqttConfigProperties.getSubTopic().split(",")
        );

        mqttPahoMessageDrivenChannelAdapter.setQos(mqttConfigProperties.getQos());
        mqttPahoMessageDrivenChannelAdapter.setConverter(new DefaultPahoMessageConverter());
        mqttPahoMessageDrivenChannelAdapter.setOutputChannel(messageInboundChannel());
        return mqttPahoMessageDrivenChannelAdapter;
    }

}
