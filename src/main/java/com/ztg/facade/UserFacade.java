package com.ztg.facade;

import com.alibaba.fastjson.JSONObject;
import com.ztg.util.WxUtils;
import com.ztg.vo.UserDecodeVO;
import org.springframework.stereotype.Component;


/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhoutg
 * @since 2021-07-05
 */
@Component
public class UserFacade {

    /**
     * 解密
     *
     * @param recordSaveVO
     */
    public JSONObject decodeUser(UserDecodeVO userDecodeVO) {
        JSONObject jsonObject = WxUtils.getUserInfo(userDecodeVO.getEncryptedData(), userDecodeVO.getSessionKey(), userDecodeVO.getIv());
        return jsonObject;
    }

}
