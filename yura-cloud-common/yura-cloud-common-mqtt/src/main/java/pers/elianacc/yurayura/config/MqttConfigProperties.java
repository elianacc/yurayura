package pers.elianacc.yurayura.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mqtt配置信息
 *
 * @author ELiaNaCc
 * @since 2024-12-16
 */
@Data
@ConfigurationProperties(prefix = "yurayura.mqtt")
public class MqttConfigProperties {

    /**
     * mqtt服务端地址
     */
    private String hostUrl;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 是否清空session(如果设置为false表示服务器会保留客户端的连接记录，
     * true表示每次连接到服务器都以新的身份连接)
     */
    private Boolean cleanSession;

    /**
     * 是否断线重连
     */
    private Boolean reconnect;

    /**
     * 超时时间
     */
    private Integer connectTimeout;

    /**
     * 会话心跳时间(单位为秒 服务器会每隔1.5*20秒的时间向客户端
     * 发送消息判断客户端是否在线，但这个方法并没有重连的机制)
     */
    private Integer keepAlive;

    /**
     * 消息等级
     */
    private Integer qos;

    /**
     * 订阅主题(多个,隔开)
     */
    private String subTopic;

}
