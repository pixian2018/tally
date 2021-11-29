package com.ztg.web;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ztg.common.RespDTO;
import com.ztg.dto.RecordDetailDTO;
import com.ztg.entity.RecordDetail;
import com.ztg.facade.RecordDetailFacade;
import com.ztg.vo.RecordDetailDelVO;
import com.ztg.vo.RecordDetailGetVO;
import com.ztg.vo.RecordDetailPageVO;
import com.ztg.vo.RecordDetailSaveVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "获取API[zhoutg]",
            notes = "")
    @PostMapping("/getById")
    public RespDTO<RecordDetail> getById(@RequestBody RecordDetailGetVO recordDetailGetVO) {
        RecordDetail data = recordDetailFacade.getById(recordDetailGetVO);
        return RespDTO.onSuc(data);
    }

    @ApiOperation(value = "保存API[zhoutg]",
            notes = "保存API")
    @PostMapping("/saveOrUpdate")
    public RespDTO<Boolean> saveOrUpdate(@RequestBody RecordDetailSaveVO recordDetailSaveVO) {
        recordDetailFacade.saveOrUpdate(recordDetailSaveVO);
        return RespDTO.onSuc(true);
    }

    @ApiOperation(value = "分页API[zhoutg]",
            notes = "分页API")
    @PostMapping("/page")
    public RespDTO<IPage<RecordDetailDTO>> page(@RequestBody RecordDetailPageVO recordDetailPageVO) {
        IPage<RecordDetailDTO> data = recordDetailFacade.getPageFac(recordDetailPageVO);
        return RespDTO.onSuc(data);
    }

    @ApiOperation(value = "删除API[zhoutg]",
            notes = "删除API")
    @PostMapping("/delete")
    public RespDTO<Boolean> delete(@RequestBody RecordDetailDelVO recordDetailDelVO) {
        Boolean data = recordDetailFacade.delete(recordDetailDelVO);
        return RespDTO.onSuc(data);
    }
}
