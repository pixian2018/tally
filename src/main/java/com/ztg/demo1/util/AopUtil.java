// package com.ztg.demo1.util;
//
// import com.diagbot.annotation.BiLogger;
// import com.diagbot.annotation.BiLoggerResult;
// import com.diagbot.annotation.SysLogger;
// import com.diagbot.annotation.SysLoggerExport;
// import com.diagbot.biz.log.entity.BiRecord;
// import com.diagbot.biz.log.entity.SysLog;
// import com.diagbot.dto.RespDTO;
// import com.diagbot.enums.BiSourceEnum;
// import com.diagbot.exception.CommonErrorCode;
// import com.diagbot.exception.CommonException;
// import com.diagbot.vo.BaseBiVO;
// import org.aspectj.lang.JoinPoint;
// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.reflect.MethodSignature;
// import org.springframework.validation.BindException;
// import org.springframework.validation.FieldError;
// import org.springframework.web.bind.MethodArgumentNotValidException;
// import org.springframework.web.bind.MissingServletRequestParameterException;
//
// import java.lang.reflect.Method;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;
//
// /**
//  * @Description:
//  * @author: zhoutg
//  * @time: 2019/11/12 18:17
//  */
// public class AopUtil {
//     /**
//      * SysLoggerAspect入参设置
//      *
//      * @param joinPoint
//      * @param sysType
//      */
//     public static SysLog sysLoggerAspect(JoinPoint joinPoint, Integer sysType) {
//         MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//         Method method = signature.getMethod();
//
//         SysLog sysLog = new SysLog();
//         SysLogger sysLogger = method.getAnnotation(SysLogger.class);
//         if (sysLogger != null) {
//             //注解上的描述
//             sysLog.setOperation(sysLogger.value());
//         }
//         //请求的方法名
//         String className = joinPoint.getTarget().getClass().getName();
//         String methodName = signature.getName();
//         sysLog.setMethod(className + "." + methodName + "()");
//         //请求的参数
//         Object[] args = joinPoint.getArgs();
//         String params = "";
//         for (Object o : args) {
//             params += FastJsonUtil.getBeanToJson(o);
//         }
//         if (!StringUtil.isEmpty(params)) {
//             sysLog.setParams(params);
//         }
//         //设置IP地址
//         sysLog.setIp(HttpUtils.getIpAddress());
//         //用户名
//         String username = UserUtils.getCurrentPrinciple();
//         if (!StringUtil.isEmpty(username)) {
//             sysLog.setUsername(username);
//         }
//         sysLog.setGmtCreate(new Date());
//         sysLog.setSysType(sysType);
//         return sysLog;
//     }
//
//     /**
//      * SysLoggerExprotAspect入参设置
//      *
//      * @param joinPoint
//      * @param sysType
//      */
//     public static SysLog sysLoggerExprotAspect(JoinPoint joinPoint, Integer sysType) {
//         MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//         Method method = signature.getMethod();
//
//         SysLog sysLog = new SysLog();
//         SysLoggerExport sysLogger = method.getAnnotation(SysLoggerExport.class);
//         if (sysLogger != null) {
//             //注解上的描述
//             sysLog.setOperation(sysLogger.value());
//         }
//         //请求的方法名
//         String className = joinPoint.getTarget().getClass().getName();
//         String methodName = signature.getName();
//         sysLog.setMethod(className + "." + methodName + "()");
//         //请求的参数
//         Object[] args = joinPoint.getArgs();
//         String params = "";
//         for (Object o : args) {
//             params += FastJsonUtil.getBeanToJson(o);
//             break;
//         }
//         if (!StringUtil.isEmpty(params)) {
//             sysLog.setParams(params);
//         }
//         //设置IP地址
//         sysLog.setIp(HttpUtils.getIpAddress());
//         //用户名
//         String username = UserUtils.getCurrentPrinciple();
//         if (!StringUtil.isEmpty(username)) {
//             sysLog.setUsername(username);
//         }
//         sysLog.setGmtCreate(new Date());
//         sysLog.setSysType(sysType);
//         return sysLog;
//     }
//
//     /**
//      * Bi日志有返回值处理（普通）
//      *
//      * @param biRecord
//      * @param joinPoint
//      * @param productType
//      * @return
//      * @throws Throwable
//      */
//     public static Object biLoggerAspect(BiRecord biRecord, ProceedingJoinPoint joinPoint,
//                                         Integer productType) throws Throwable {
//         long start = System.currentTimeMillis();
//         Object object = joinPoint.proceed();
//         if (!biRecordSet(biRecord, joinPoint, productType, BiSourceEnum.BI_NORMAL.getKey())) {
//             biRecord = null;
//             return object;
//         }
//         //出参设置
//         String result = "";
//         result = FastJsonUtil.getBeanToJson(object);
//         if (!StringUtil.isEmpty(result)) {
//             biRecord.setResult(result);
//         }
//         biRecord.setSuccessFlag(1);
//         long execTime = System.currentTimeMillis() - start;
//         biRecord.setExecTime(String.valueOf(execTime));
//         return object;
//     }
//
//     /**
//      * Bi日志异常统一处理（普通）
//      *
//      * @param joinPoint
//      * @param ex
//      * @param productType
//      * @return
//      */
//     public static BiRecord biLoggerAspectThrow(JoinPoint joinPoint, Throwable ex, Integer productType) {
//         BiRecord biRecord = new BiRecord();
//         if (!biRecordSet(biRecord, joinPoint, productType, BiSourceEnum.BI_NORMAL.getKey())) {
//             return null;
//         }
//         //出参设置
//         String result = "";
//         result = FastJsonUtil.getBeanToJson(handleException((Exception) ex));
//         biRecord.setResult(result);
//         biRecord.setSuccessFlag(0);
//         return biRecord;
//     }
//
//     /**
//      * Bi日志有返回值处理（特殊）
//      *
//      * @param biRecord
//      * @param joinPoint
//      * @param productType
//      * @return
//      * @throws Throwable
//      */
//     public static Object biLoggerResultAspect(BiRecord biRecord, ProceedingJoinPoint joinPoint,
//                                               Integer productType) throws Throwable {
//         long start = System.currentTimeMillis();
//         Object object = joinPoint.proceed();
//         if (!biRecordSet(biRecord, joinPoint, productType, BiSourceEnum.BI_RESULT.getKey())) {
//             biRecord = null;
//             return object;
//         }
//         //出参设置
//         String result = "";
//         if (object instanceof RespDTO) {
//             RespDTO respDTO = (RespDTO) object;
//             RespDTO rs = new RespDTO();
//             rs.code = respDTO.code;
//             rs.msg = respDTO.msg;
//             rs.data = new Object();
//             result = FastJsonUtil.getBeanToJson(rs);
//         } else {
//             result = FastJsonUtil.getBeanToJson(object);
//         }
//         if (!StringUtil.isEmpty(result)) {
//             biRecord.setResult(result);
//         }
//         biRecord.setSuccessFlag(1);
//         long execTime = System.currentTimeMillis() - start;
//         biRecord.setExecTime(String.valueOf(execTime));
//         return object;
//     }
//
//     /**
//      * Bi日志异常统一处理（特殊）
//      *
//      * @param joinPoint
//      * @param ex
//      * @param productType
//      * @return
//      */
//     public static BiRecord biLoggerResultAspectThrow(JoinPoint joinPoint, Throwable ex, Integer productType) {
//         BiRecord biRecord = new BiRecord();
//         if (!biRecordSet(biRecord, joinPoint, productType, BiSourceEnum.BI_RESULT.getKey())) {
//             return null;
//         }
//         //出参设置
//         String result = "";
//         result = FastJsonUtil.getBeanToJson(handleException((Exception) ex));
//         biRecord.setResult(result);
//         biRecord.setSuccessFlag(0);
//         return biRecord;
//     }
//
//     /**
//      * 消息设定
//      *
//      * @param biRecord
//      * @param joinPoint
//      * @return 是否可以继续下去
//      */
//     private static Boolean biRecordSet(BiRecord biRecord, JoinPoint joinPoint,
//                                        Integer productType, Integer biSourceType) {
//         MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//         Method method = signature.getMethod();
//         //请求的参数
//         Object[] args = joinPoint.getArgs();
//         String params = "";
//         for (Object o : args) {
//             if (o instanceof BaseBiVO) {
//                 BaseBiVO baseBiVO = (BaseBiVO) o;
//                 if (StringUtil.isBlank(baseBiVO.getHospitalCode())) {
//                     if (StringUtil.isNotBlank(baseBiVO.getHosCode())) {
//                         BeanUtil.copyProperties(baseBiVO, biRecord);
//                         biRecord.setHospitalCode(baseBiVO.getHosCode());
//                     } else {
//                         return false;
//                     }
//                 } else {
//                     BeanUtil.copyProperties(baseBiVO, biRecord);
//                 }
//             }
//             params += FastJsonUtil.getBeanToJson(o);
//         }
//         if (!StringUtil.isEmpty(params)) {
//             biRecord.setParams(params);
//         }
//
//         //设置功能编码
//         if (biSourceType.equals(BiSourceEnum.BI_NORMAL.getKey())) {
//             BiLogger biLogger = method.getAnnotation(BiLogger.class);
//             if (biLogger != null) {
//                 //注解上的描述
//                 biRecord.setCode(biLogger.value());
//             }
//         } else if (biSourceType.equals(BiSourceEnum.BI_RESULT.getKey())) {
//             BiLoggerResult biLoggerResult = method.getAnnotation(BiLoggerResult.class);
//             if (biLoggerResult != null) {
//                 //注解上的描述
//                 biRecord.setCode(biLoggerResult.value());
//             }
//         } else {
//             return false;
//         }
//         biRecord.setGmtCreate(new Date());
//         biRecord.setProductType(productType);
//         biRecord.setSource(biSourceType);
//         return true;
//     }
//
//     /**
//      * 抛错信息处理
//      *
//      * @param e
//      * @return 结果参数
//      */
//     private static RespDTO handleException(Exception e) {
//         RespDTO resp = new RespDTO();
//         if (e instanceof BindException) {
//             BindException ex = (BindException) e;
//             Map<String, String> stringMap = new HashMap<>();
//             for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
//                 stringMap.put(fieldError.getField(), fieldError.getDefaultMessage());
//             }
//             String msg = FastJsonUtil.getBeanToJson(stringMap);
//             resp.code = CommonErrorCode.PARAM_ERROR.getCode();
//             resp.msg = msg;
//             return resp;
//         }
//         if (e instanceof MethodArgumentNotValidException) {
//             MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
//             Map<String, String> stringMap = new HashMap<>();
//             for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
//                 stringMap.put(fieldError.getField(), fieldError.getDefaultMessage());
//             }
//             String msg = FastJsonUtil.getBeanToJson(stringMap);
//             resp.code = CommonErrorCode.PARAM_ERROR.getCode();
//             resp.msg = msg;
//             return resp;
//         }
//         if (e instanceof MissingServletRequestParameterException) {
//             MissingServletRequestParameterException ex = (MissingServletRequestParameterException) e;
//             Map<String, String> stringMap = new HashMap<>();
//             stringMap.put(ex.getParameterName(), "不能为null");
//             String msg = FastJsonUtil.getBeanToJson(stringMap);
//             resp.code = CommonErrorCode.PARAM_ERROR.getCode();
//             resp.msg = msg;
//             return resp;
//         }
//         if (e instanceof CommonException) {
//             CommonException taiChiException = (CommonException) e;
//             resp.code = taiChiException.getCode();
//             resp.msg = e.getMessage();
//             return resp;
//         }
//         resp.code = CommonErrorCode.FAIL.getCode();
//         resp.msg = e.getMessage();
//         return resp;
//     }
// }
