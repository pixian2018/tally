package com.ztg.controller;


import com.ztg.common.RespDTO;
import com.ztg.facade.RecordFacade;
import com.ztg.vo.RecordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 场次名称表 前端控制器
 * </p>
 *
 * @author zhoutg
 * @since 2021-07-05
 */

@RestController
@RequestMapping("/record")
@Api(value = "场次API", tags = { "场次API" })
public class RecordController {

    @Autowired
    RecordFacade recordFacade;

    @ApiOperation(value = "保存记录API[zhoutg]",
            notes = "保存记录API")
    @PostMapping("/save")
    public RespDTO<Boolean> save(RecordVO recordVO) {
        recordFacade.save(recordVO);
        return RespDTO.onSuc(true);
    }

}
