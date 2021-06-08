package com.ztg.controller;

import com.ztg.mapper.KlZtgMapper;
import com.ztg.rabbit.MySender;
import com.ztg.util.ExcelUtils;
import com.ztg.dto.ExportTestDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @ApiOperation(value = "测试导出API[zhoutg]",
            notes = "测试导出API")
    @PostMapping("/testExport")
    public void testExport(HttpServletResponse response) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, String> map = new LinkedMap<>();
        map.put(ExcelUtils.DATA_HEIGHT, "6");
        List<ExportTestDTO> exportTestDTOList1 = new ArrayList<>();
        ExportTestDTO exportTestDTO = new ExportTestDTO();
        exportTestDTO.setName("aaa");
        exportTestDTOList1.add(exportTestDTO);

        ExportTestDTO exportTestDTO1 = new ExportTestDTO();
        exportTestDTO1.setName("afafa");
        exportTestDTOList1.add(exportTestDTO1);
        list.add(ExcelUtils.createOneSheet("s1", null, ExportTestDTO.class, exportTestDTOList1, map));

        List<ExportTestDTO> exportTestDTOList2 = new ArrayList<>();
        ExportTestDTO exportTestDTO2 = new ExportTestDTO();
        exportTestDTO2.setName("bbb");
        exportTestDTOList2.add(exportTestDTO2);
        list.add(ExcelUtils.createOneSheet("s2", null, ExportTestDTO.class, exportTestDTOList2, map));

        ExcelUtils.exportExcel(list, "aa.xls", response);
    }
}
