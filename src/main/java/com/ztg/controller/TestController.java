package com.ztg.controller;

import com.ztg.common.RespDTO;
import com.ztg.mapper.KlZtgMapper;
import com.ztg.rabbit.MySender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zhoutg
 * @time: 2021/3/31 11:21
 */
@RestController
@RequestMapping("/test")
@Api(tags = { "test相关接口" })
public class TestController {

    @Autowired
    MySender mySender;
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @ApiOperation(value = "发送消息API[zhoutg]",
            notes = "发送消息API")
    @PostMapping("/sendMessage")
    public String sendMessage(String msg) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.getMapper(KlZtgMapper.class);
        mySender.sendMessage("你好");
        return "ok";
    }

    @ApiOperation(value = "测试异常[zhoutg]",
            notes = "测试异常API")
    @RequestMapping("/exception")
    public RespDTO<String> testException() {
        int i = 1;
        // System.out.println(i / 0);
        System.out.println(111);
        return RespDTO.onSuc("ok");
    }
}
