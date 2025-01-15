package pers.elianacc.yurayura.mqtt;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import pers.elianacc.yurayura.component.MqttMessageSender;
import pers.elianacc.yurayura.constants.MqttConstant;
import pers.elianacc.yurayura.entity.SysManager;
import pers.elianacc.yurayura.entity.SysNotice;
import pers.elianacc.yurayura.service.ISysManagerService;
import pers.elianacc.yurayura.service.ISysNoticeService;

/**
 * mqtt接收消息处理 component
 *
 * @author ELiaNaCc
 * @since 2024-12-16
 */
@Component
public class MqttReceiveMsgHandler implements MessageHandler {

    @Autowired
    private MqttMessageSender mqttMessageSender ;

    @Autowired
    private ISysNoticeService iSysNoticeService;

    @Autowired
    private ISysManagerService iSysManagerService;

    @Value("${yurayura.pass-secret-key}")
    private String passSecretKey;

    @Bean
    @ServiceActivator(inputChannel = "messageInboundChannel")
    public MessageHandler messageHandler() {
        return this;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String payload = message.getPayload().toString();
        MessageHeaders headers = message.getHeaders();
        String topicName = headers.get(MqttHeaders.RECEIVED_TOPIC).toString();

        if (MqttConstant.YURA_CLOUD_SYS_INSERT_NOTICE.equals(topicName)) {
            SysNotice notice = JSONUtil.toBean(payload, SysNotice.class);
            iSysNoticeService.save(notice);
            mqttMessageSender.sendMsg(MqttConstant.YURA_BUSINESS_VUE_NOTICE_PLUS, JSONUtil.toJsonStr(notice));
        }

        if (MqttConstant.YURA_CLOUD_SYS_SEND_FRONT_PASSKEY.equals(topicName)) {
            JSONObject jsonObject = JSONUtil.createObj()
                    .set("passSecretKey", passSecretKey);
            mqttMessageSender.sendMsg(MqttConstant.YURA_BUSINESS_VUE_GET_PASSKEY, JSONUtil
                    .toJsonStr(jsonObject));
        }

        if (MqttConstant.YURA_CLOUD_SYS_SEND_FRONT_PASSKEY_SALT.equals(topicName)) {
            SysManager sysManager = iSysManagerService.getEnableManagerByName(payload);
            if (!ObjectUtils.isEmpty(sysManager)) {
                JSONObject jsonObject = JSONUtil.createObj()
                        .set("passSecretKey", passSecretKey)
                        .set("passSalt", sysManager.getManagerPassSalt())
                        .set("result", "success");
                mqttMessageSender.sendMsg(MqttConstant.YURA_BUSINESS_VUE_GET_PASSKEY_SALT, JSONUtil
                        .toJsonStr(jsonObject));
            } else {
                JSONObject jsonObject = JSONUtil.createObj()
                        .set("passSecretKey", "")
                        .set("passSalt", "")
                        .set("result", "管理员不存在");
                mqttMessageSender.sendMsg(MqttConstant.YURA_BUSINESS_VUE_GET_PASSKEY_SALT, JSONUtil
                        .toJsonStr(jsonObject));
            }
        }

    }

}
