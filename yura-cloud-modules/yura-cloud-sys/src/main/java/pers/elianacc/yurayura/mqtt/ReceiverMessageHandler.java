package pers.elianacc.yurayura.mqtt;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import pers.elianacc.yurayura.constants.MqttConstant;
import pers.elianacc.yurayura.entity.SysNotice;
import pers.elianacc.yurayura.service.ISysNoticeService;
import pers.elianacc.yurayura.component.MqttMessageSender;

/**
 * mqtt接收消息处理 component
 *
 * @author ELiaNaCc
 * @since 2024-12-16
 */
@Component
public class ReceiverMessageHandler implements MessageHandler {

    @Autowired
    private MqttMessageSender mqttMessageSender ;

    @Autowired
    private ISysNoticeService iSysNoticeService;

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

    }
}
