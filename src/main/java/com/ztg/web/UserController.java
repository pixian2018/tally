package com.ztg.web;

import com.ztg.common.RespDTO;
import com.ztg.dto.OpenidDTO;
import com.ztg.facade.UserFacade;
import com.ztg.vo.UserDecodeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zhoutg
 * @since 2021-07-05
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户API", tags = { "用户API" })
public class UserController {

    @Autowired
    UserFacade userFacade;

    @ApiOperation(value = "获取用户唯一标识openid[zhoutg]",
            notes = "code: 登录凭证")
    @PostMapping("/getOpenId")
    public RespDTO<OpenidDTO> getOpenId(@Valid @RequestBody UserDecodeVO userDecodeVO) {
        OpenidDTO data = userFacade.getOpenId(userDecodeVO);
        return RespDTO.onSuc(data);
    }

    @ApiOperation(value = "测试API[zhoutg]",
            notes = "测试API")
    @RequestMapping("/test")
    @ApiIgnore
    public RespDTO<Object> test() {
        return RespDTO.onSuc("ok");
    }
}
