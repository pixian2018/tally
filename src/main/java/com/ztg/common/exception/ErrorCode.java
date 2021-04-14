package com.ztg.common.exception;

import java.io.Serializable;

/**
 * @Description: 错误基础接口
 * @author: zhoutg
 * @time: 2018/9/10 11:01
 */
public interface ErrorCode extends Serializable {

    String getCode();

    String getMsg();
}
