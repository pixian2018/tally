package com.ztg.demo1.common.exception;

/**
 * @Description: 通用错误码
 * 系统码(3位) + 等级码(1位) + 4位顺序号
 * 系统码 通用码 000；用户中心 100； 管理中心 200；
 * @author: gaodm
 * @time: 2018/8/1 14:56
 */
public enum CommonErrorCode implements ErrorCode {

    OK("0", "操作成功"),
    FAIL("00000001", "操作失败"),
    RPC_ERROR("00000002", "远程调度失败"),
    CHECK_CONNECTION("00000005","是否解除绑定"),
    SAVE_SUCCESSFUL("00000007","添加成功"),
    ALTER_SUCCESSFUL("00000009","修改成功"),
    PARAM_ERROR("00029999", "%s"), //参数错误
    NOT_EXISTS("00020001", "该数据不存在!"),
    INSERT_DATA_FAILED("00020002", "数据库写入失败!"),
    UPDATE_INFO_FAIL("00020003", "更新数据失败!"),
    PARAM_IS_NULL("00020004", "传入的参数为空!"),
    PARAM_IS_ERROR("00020005", "传入的参数为错误!"),
    STATUS_IS_ERROR("00020006", "参数状态错误!"),
    SERVER_IS_ERROR("00020007", "各自业务错误!"),
    NO_PERMISSION("00020008", "无权访问!"),
    IS_EXISTS("00020009", "已存在!"),
    ANALYZER_TOKEN_FAIL("10020010", "解析token失败"),
    TOKEN_PAST("10020011", "token已失效，请重新登录"),
    SECURITYCODE_ERROR("20020001", "防伪码错误"),
    APPKEY_ERROR("20020002", "appkey或secret错误"),
    PRODUCT_STOP_ERROR("20020003", "该产品已停用"),
    OVERDUE_ERROR("20020004", "访问权限已过期"),
    NOPERMISSION_ERROR("20020005", "无权限访问"),
    SERVICE_STOP_ERROR("20020006", "当前服务已停用"),
    NOTVALID_ERROR("20020007", "该产品未在有效服务期内，无法使用"),
    EXPIRE_ERROR("20020008", "该产品已超出有效服务期，无法使用");

    private String code;
    private String msg;

    CommonErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static CommonErrorCode codeOf(String code) {
        for (CommonErrorCode state : values()) {
            if (state.getCode() == code) {
                return state;
            }
        }
        return null;
    }
}
