package com.ztg.controller;


import com.google.common.collect.Lists;
import com.ztg.common.RespDTO;
import com.ztg.entity.KlZtg;
import com.ztg.service.KlZtgService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 诊断依据信息表 前端控制器
 * </p>
 *
 * @author zhoutg
 * @since 2021-04-15
 */
@RestController
@RequestMapping("/klZtg")
public class KlZtgController {

    @Autowired
    @Qualifier("klZtgServiceImpl")
    KlZtgService klZtgService;

    @ApiOperation(value = "插入测试[zhoutg]",
            notes = "插入测试API")
    @PostMapping("/testInsert")
    public RespDTO<String> testInsert() {
        List<KlZtg> klZtgList = Lists.newArrayList();
        for (int i = 0; i < 1; i++) {
            KlZtg klZtg = new KlZtg();
            klZtg.setConceptId(Long.valueOf(i));
            klZtgList.add(klZtg);
        }
        String msg = "saveBatch方法耗时：";
        // long t1 = System.currentTimeMillis();
        // klZtgService.saveBatch(klZtgList);
        // long t2 = System.currentTimeMillis();
        // msg += (t2 - t1) / 1000.0;

        msg += "，自写方法耗时：";
        long t3 = System.currentTimeMillis();
        klZtgService.insertBatch(klZtgList);
        long t4 = System.currentTimeMillis();
        msg += (t4 - t3) / 1000.0;
        return RespDTO.onSuc(msg);
    }
}