package com.ztg.facade;

import com.ztg.dto.OpenidDTO;
import com.ztg.util.FastJsonUtil;
import com.ztg.vo.UserDecodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


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
    @Autowired
    RestTemplate restTemplate;

    /**
     * 解密
     *
     * @param userDecodeVO
     */
    public OpenidDTO getOpenId(UserDecodeVO userDecodeVO) {
        String code = userDecodeVO.getCode();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" +code + "&grant_type=authorization_code";
        Object obj =  restTemplate.getForObject(url, String.class);
        OpenidDTO openidDTO = FastJsonUtil.getJsonToBean(obj.toString(), OpenidDTO.class);
        return openidDTO;
    }

}
