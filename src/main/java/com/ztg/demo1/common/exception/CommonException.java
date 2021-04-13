package com.ztg.demo1.common.exception;


/**
 * @Description: 通用错误封装
 * @author: gaodm
 * @time: 2018/8/1 14:56
 */
public class CommonException extends RuntimeException {

    private ErrorCode errorCode;

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public CommonException(ErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }


    public String getCode() {
        return errorCode.getCode();
    }

    public String getMsg() {
        return errorCode.getMsg();
    }

}
