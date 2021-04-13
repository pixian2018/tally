package com.ztg.demo1;

import com.ztg.demo1.rabbit.MySender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo1ApplicationTests {

    @Autowired
    MySender mySender;

    @Test
    void contextLoads() {
        mySender.sendMessage("你好");
    }

}
