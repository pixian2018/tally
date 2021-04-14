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
     * 远程调度错误处理
     *
     * @param respDTO
     * @param errorMsg
     * @param <T>
     */
    private static <T> void rpcErrorMsg(RespDTO<T> respDTO, String errorMsg) {
        if (!rpcIsSuccess(respDTO)) {
            if (StringUtil.isBlank(errorMsg) && respDTO != null && StringUtil.isNotBlank(respDTO.msg)) {
                throw new CommonException(CommonErrorCode.RPC_ERROR, respDTO.msg);
            }
        }
        throw new CommonException(CommonErrorCode.RPC_ERROR, errorMsg);
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
