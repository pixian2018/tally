package com.ztg.web;


import com.ztg.common.RespDTO;
import com.ztg.entity.RecordPlayer;
import com.ztg.facade.RecordPlayerFacade;
import com.ztg.vo.RecordPlayerDeleteVO;
import com.ztg.vo.RecordPlayerGetVO;
import com.ztg.vo.RecordPlayerSaveVO;
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
 * 场次玩家表 前端控制器
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
@RestController
@RequestMapping("/record-player")
@Api(value = "场次玩家API", tags = { "场次玩家API" })
public class RecordPlayerController {

    @Autowired
    RecordPlayerFacade recordPlayerFacade;

    @ApiOperation(value = "保存API[zhoutg]",
            notes = "user：用户\n" +
                    "recordId：场次id\n" +
                    "recordPlayerVOList：玩家列表")
    @PostMapping("/saveOrUpdate")
    public RespDTO<Boolean> saveOrUpdate(@RequestBody RecordPlayerSaveVO recordPlayerSaveVO) {
        Boolean data = recordPlayerFacade.saveOrUpdate(recordPlayerSaveVO);
        return RespDTO.onSuc(data);
    }

    @ApiOperation(value = "获取列表API[zhoutg]",
            notes = "recordId：场次id")
    @PostMapping("/getList")
    public RespDTO<List<RecordPlayer>> getList(@RequestBody RecordPlayerGetVO recordPlayerGetVO) {
        List<RecordPlayer> data = recordPlayerFacade.getList(recordPlayerGetVO);
        return RespDTO.onSuc(data);
    }

    @ApiOperation(value = "删除API[zhoutg]",
            notes = "删除API")
    @PostMapping("/delete")
    public RespDTO<Boolean> delete(@RequestBody RecordPlayerDeleteVO recordPlayerDeleteVO) {
        Boolean data = recordPlayerFacade.delete(recordPlayerDeleteVO);
        return RespDTO.onSuc(data);
    }

}
