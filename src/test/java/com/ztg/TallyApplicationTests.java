package com.ztg;

import com.ztg.rabbit.MySender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TallyApplicationTests {

    @Autowired
    MySender mySender;

    @Test
    void contextLoads() {
        mySender.sendMessage("你好");
    }

}
