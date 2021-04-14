package com.ztg.common.exception;

import com.ztg.common.RespDTO;
import com.ztg.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;


/**
 * @Description: 错误通用处理
 * @author: zhoutg
 * @time: 2018/8/2 14:22
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespDTO> handleException(Exception e) {
        RespDTO resp = new RespDTO();
        if (e instanceof BindException) {
            BindException ex = (BindException) e;
            Map<String, String> stringMap = new HashMap<>();
            for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
                stringMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            String msg = GsonUtil.toJson(stringMap);
            log.warn("【参数异常】:{}", msg);
            resp.code = CommonErrorCode.PARAM_ERROR.getCode();
            resp.msg = msg;
            return new ResponseEntity(resp, HttpStatus.OK);
        }
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            Map<String, String> stringMap = new HashMap<>();
            for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
                stringMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            String msg = GsonUtil.toJson(stringMap);
            log.warn("【参数异常】:{}", msg);
            resp.code = CommonErrorCode.PARAM_ERROR.getCode();
            resp.msg = msg;
            return new ResponseEntity(resp, HttpStatus.OK);
        }
        if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException ex = (MissingServletRequestParameterException) e;
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put(ex.getParameterName(), "不能为null");
            String msg = GsonUtil.toJson(stringMap);
            log.warn("【参数异常】:{}", msg);
            resp.code = CommonErrorCode.PARAM_ERROR.getCode();
            resp.msg = msg;
            return new ResponseEntity(resp, HttpStatus.OK);
        }
        if (e instanceof CommonException) {
            CommonException taiChiException = (CommonException) e;
            resp.code = taiChiException.getCode();
            resp.msg = e.getMessage();
            log.error("【业务异常】:{}", e.getMessage());
            return new ResponseEntity(resp, HttpStatus.OK);
        }
        resp.code = CommonErrorCode.FAIL.getCode();
        resp.msg = e.getMessage();
        log.error("【系统异常】:{}", e.getMessage());
        e.printStackTrace();
        return new ResponseEntity(resp, HttpStatus.OK);
    }

}
