package com.ztg.demo1.rabbit;

import com.ztg.demo1.util.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

/**
 * @description: 发布者
 * @author: zhoutg
 * @time: 2021/4/1 15:30
 */
@EnableBinding(MyMessage.class)
public class MySender {

    @Autowired
    @Output("outputMessage")
    private MessageChannel channel;

    public void sendMessage(Object object) {
        channel.send(MessageBuilder.withPayload(GsonUtil.toJson(object)).build());
    }
}
