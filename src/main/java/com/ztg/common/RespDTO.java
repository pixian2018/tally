package com.ztg.common;

import com.ztg.common.exception.CommonErrorCode;
import com.ztg.common.exception.CommonException;
import com.ztg.util.StringUtil;
import java.io.Serializable;

/**
 * @Description: 通用返回格式
 * @author: zhoutg
 * @time: 2018/8/1 14:55
 */
public class RespDTO<T> implements Serializable {

    public String code = "0";
    public String msg = "";
    public T data;

    public static RespDTO onSuc(Object data) {
        RespDTO resp = new RespDTO();
        resp.data = data;
        return resp;
    }

    public static RespDTO onError(String errMsg) {
        RespDTO resp = new RespDTO();
        resp.code = "-1";
        resp.msg = errMsg;
        return resp;
    }

    /**
     * 远程调度是否成功
     *
     * @param respDTO 返回结果
     * @param <T>     泛型
     * @return 是否成功
     */
    public static <T> boolean rpcIsSuccess(RespDTO<T> respDTO) {
        if (null == respDTO
                || null == respDTO.data
                || !CommonErrorCode.OK.getCode().equals(respDTO.code)) {
            return false;
        }
        return true;
    }

    /**
     * 校验远程调度是否出错，不覆盖错误提示语
     *
     * @param respDTO 返回结果
     * @param errmsg  错误信息
     * @param <T>     泛型
     */
    public static <T> void verifyRpc(RespDTO<T> respDTO, String errmsg) {
        respNGDeal(respDTO, false, errmsg);
    }

    /**
     * 校验远程调度是否出错，覆盖错误提示语
     *
     * @param respDTO 返回结果
     * @param errmsg  错误信息
     * @param <T>     泛型
     */
    public static <T> void verifyRpcCover(RespDTO<T> respDTO, String errmsg) {
        respNGDeal(respDTO, true, errmsg);
    }

    /**
     * 校验远程调度是否出错内部方法
     *
     * @param respDTO
     * @param isCover
     * @param errmsg
     * @param <T>
     */
    private static <T> void respNGDeal(RespDTO<T> respDTO, Boolean isCover, String errmsg) {
        boolean flag = rpcIsSuccess(respDTO);
        if (flag) {
            if (!isCover) {
                if (respDTO != null && StringUtil.isNotBlank(respDTO.msg)) {
                    throw new CommonException(CommonErrorCode.RPC_ERROR, respDTO.msg);
                }
            }
            throw new CommonException(CommonErrorCode.RPC_ERROR, errmsg);
        }
    }

    @Override
    public String toString() {
        return "RespDTO{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
