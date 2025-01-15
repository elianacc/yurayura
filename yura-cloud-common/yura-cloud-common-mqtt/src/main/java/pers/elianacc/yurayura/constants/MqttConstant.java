package pers.elianacc.yurayura.constants;

/**
 * mqtt常量
 *
 * @author ELiaNaCc
 * @since 2024-12-16
 */
public class MqttConstant {

    /**
     * 客户端id消费者后缀
     */
    public static final String CLIENT_SUFFIX_CONSUMERS = "_consumers";

    /**
     * 客户端id生产者后缀
     */
    public static final String CLIENT_SUFFIX_PRODUCERS = "_producers";

    /**
     * 后台前端通知+1 主题
     */
    public static final String YURA_BUSINESS_VUE_NOTICE_PLUS = "yura-business-vue/notice-plus";

    /**
     * 添加系统通知 主题
     */
    public static final String YURA_CLOUD_SYS_INSERT_NOTICE = "yura-cloud-sys/insertNotice";

    /**
     * 发送密码密钥到前端 主题
     */
    public static final String YURA_CLOUD_SYS_SEND_FRONT_PASSKEY = "yura-cloud-sys/sendFrontPassKey";

    /**
     * 发送密码密钥和盐到前端 主题
     */
    public static final String YURA_CLOUD_SYS_SEND_FRONT_PASSKEY_SALT = "yura-cloud-sys/sendFrontPassKeySalt";

    /**
     * 后台前端获取密码密钥 主题
     */
    public static final String YURA_BUSINESS_VUE_GET_PASSKEY = "yura-business-vue/getPassKey";

    /**
     * 后台前端获取密码密钥和盐 主题
     */
    public static final String YURA_BUSINESS_VUE_GET_PASSKEY_SALT = "yura-business-vue/getPassKeySalt";

}
