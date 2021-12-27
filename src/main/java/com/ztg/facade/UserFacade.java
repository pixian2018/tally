package com.ztg.facade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ztg.dto.OpenidDTO;
import com.ztg.util.HttpRequestUtil;
import com.ztg.util.StringUtil;
import com.ztg.vo.UserDecodeVO;
import org.springframework.beans.factory.annotation.Value;
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

    //小程序唯一标识   (在微信小程序管理后台获取)
    @Value("${tally.appid}")
    private String appid;
    //小程序的 app secret (在微信小程序管理后台获取)
    @Value("${tally.secret}")
    private String secret;


    /**
     * 解密
     *
     * @param userDecodeVO
     */
    public OpenidDTO decodeUser(UserDecodeVO userDecodeVO) {
        OpenidDTO openidDTO = new OpenidDTO();
        // 登录凭证不能为空
        if (StringUtil.isBlank(userDecodeVO.getCode())) {
            return null;
        }
        String code = userDecodeVO.getCode();
        // 授权（必填）
        String grant_type = "authorization_code";
        //https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        //1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid
        //请求参数
        String params = "appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequestUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSON.parseObject(sr);
        //获取会话密钥（session_key）json.get("session_key").toString();
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        openidDTO.setSessionKey(session_key);
        openidDTO.setOpenid(openid);
        return openidDTO;
    }

}
