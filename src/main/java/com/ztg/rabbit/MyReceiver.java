package com.ztg.rabbit;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * @description: 接收者
 * @author: zhoutg
 * @time: 2021/4/1 15:36
 */
@EnableBinding(MyMessage.class)
public class MyReceiver {

    @StreamListener(MyMessage.INPUTMESSAGE)
    public void receive(String msg) {
        System.out.println("接收消息" + msg);
    }
}
