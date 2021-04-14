package com.ztg.rabbit;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @description: 自定义Stream发布和消费对象
 * @author: zhoutg
 * @time: 2021/4/1 15:13
 */
public interface MyMessage {
    // 需要注意的是INPUT和OUTPUT的值会在配置文件中做配置,
    // 不然你会发现项目没报错,消息也发出去了,但是收不到,这里非常重要
    String INPUTMESSAGE = "inputMessage";
    String OUTPUTMESSAGE = "outputMessage";

    @Input(INPUTMESSAGE)
    SubscribableChannel inputMessage();

    @Output(OUTPUTMESSAGE)
    MessageChannel outputMessage();
}
