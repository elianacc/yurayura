package pers.elianacc.yurayura.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

/**
 * mqtt config
 *
 * @author ELiaNaCc
 * @since 2024-12-16
 */
@Configuration
public class MqttConfig {

    @Autowired
    private MqttConfigProperties mqttConfigProperties;

    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory() {
        DefaultMqttPahoClientFactory mqttPahoClientFactory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{mqttConfigProperties.getHostUrl()});
        options.setUserName(mqttConfigProperties.getUsername());
        options.setPassword(mqttConfigProperties.getPassword().toCharArray());
        options.setCleanSession(mqttConfigProperties.getCleanSession());
        options.setAutomaticReconnect(mqttConfigProperties.getReconnect());
        options.setKeepAliveInterval(mqttConfigProperties.getKeepAlive());
        options.setConnectionTimeout(mqttConfigProperties.getConnectTimeout());

        mqttPahoClientFactory.setConnectionOptions(options);
        return mqttPahoClientFactory;
    }

}
