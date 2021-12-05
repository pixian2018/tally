package com.ztg.web;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ztg.common.RespDTO;
import com.ztg.dto.RecordDetailDTO;
import com.ztg.dto.RecordDetailGroupDTO;
import com.ztg.dto.RecordSettleDTO;
import com.ztg.facade.RecordDetailFacade;
import com.ztg.vo.RecordDetailDelVO;
import com.ztg.vo.RecordDetailGetVO;
import com.ztg.vo.RecordDetailPageVO;
import com.ztg.vo.RecordDetailSaveVO;
import com.ztg.vo.RecordSettleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 场次明细表 前端控制器
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
@RestController
@RequestMapping("/record-detail")
@Api(value = "场次记录API", tags = { "场次记录API" })
public class RecordDetailController {

    @Autowired
    RecordDetailFacade recordDetailFacade;

    @ApiOperation(value = "分页API[zhoutg]",
            notes = "分页API")
    @PostMapping("/page")
    public RespDTO<IPage<RecordDetailGroupDTO>> page(@RequestBody RecordDetailPageVO recordDetailPageVO) {
        IPage<RecordDetailGroupDTO> data = recordDetailFacade.getPageFac(recordDetailPageVO);
        return RespDTO.onSuc(data);
    }

    @ApiOperation(value = "获取API[zhoutg]",
            notes = "")
    @PostMapping("/getByRecordAndGroup")
    public RespDTO<List<RecordDetailDTO>> getByRecordAndGroup(@RequestBody RecordDetailGetVO recordDetailGetVO) {
        List<RecordDetailDTO> data = recordDetailFacade.getByRecordAndGroupFac(recordDetailGetVO);
        return RespDTO.onSuc(data);
    }

    @ApiOperation(value = "保存API[zhoutg]",
            notes = "保存API")
    @PostMapping("/saveOrUpdate")
    public RespDTO<Boolean> saveOrUpdate(@RequestBody RecordDetailSaveVO recordDetailSaveVO) {
        recordDetailFacade.saveOrUpdate(recordDetailSaveVO);
        return RespDTO.onSuc(true);
    }

    @ApiOperation(value = "删除API[zhoutg]",
            notes = "删除API")
    @PostMapping("/delete")
    public RespDTO<Boolean> delete(@RequestBody RecordDetailDelVO recordDetailDelVO) {
        Boolean data = recordDetailFacade.delete(recordDetailDelVO);
        return RespDTO.onSuc(data);
    }

    @ApiOperation(value = "结账API[zhoutg]",
            notes = "API")
    @PostMapping("/settle")
    public RespDTO<List<RecordSettleDTO>> settle(@RequestBody RecordSettleVO recordSettleVO) {
        List<RecordSettleDTO> data = recordDetailFacade.settle(recordSettleVO);
        return RespDTO.onSuc(data);
    }
}
