package com.ztg.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ztg.common.RespDTO;
import com.ztg.dto.RecordDTO;
import com.ztg.entity.Record;
import com.ztg.facade.RecordFacade;
import com.ztg.vo.RecordDeleteVO;
import com.ztg.vo.RecordGetVO;
import com.ztg.vo.RecordPageVO;
import com.ztg.vo.RecordSaveVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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


    @ApiOperation(value = "获取API[zhoutg]",
            notes = "")
    @PostMapping("/getById")
    public RespDTO<Record> getById(@RequestBody RecordGetVO recordGetVO) {
        Record data = recordFacade.getById(recordGetVO);
        return RespDTO.onSuc(data);
    }

    @ApiOperation(value = "保存API[zhoutg]",
            notes = "保存API")
    @PostMapping("/saveOrUpdate")
    public RespDTO<Boolean> saveOrUpdate(@RequestBody RecordSaveVO recordSaveVO) {
        Boolean data = recordFacade.saveOrUpdate(recordSaveVO);
        return RespDTO.onSuc(data);
    }

    @ApiOperation(value = "分页API[zhoutg]",
            notes = "分页API")
    @PostMapping("/page")
    public RespDTO<IPage<RecordDTO>> page(@RequestBody RecordPageVO recordPageVO) {
        IPage<RecordDTO> recordDTOIPage = recordFacade.getPageFac(recordPageVO);
        return RespDTO.onSuc(recordDTOIPage);
    }

    @ApiOperation(value = "删除API[zhoutg]",
            notes = "删除API")
    @PostMapping("/delete")
    public RespDTO<Boolean> delete(@RequestBody RecordDeleteVO recordDeleteVO) {
        recordFacade.delete(recordDeleteVO);
        return RespDTO.onSuc(true);
    }
}
