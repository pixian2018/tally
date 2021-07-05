// package com.ztg.tally.util;
//
// import com.auth0.jwt.JWT;
// import com.auth0.jwt.exceptions.JWTDecodeException;
// import com.auth0.jwt.interfaces.Claim;
// import com.auth0.jwt.interfaces.DecodedJWT;
// import com.diagbot.exception.CommonErrorCode;
// import com.diagbot.exception.CommonException;
//
// import java.util.Date;
// import java.util.Map;
//
// /**
//  * @Description: Jwt Token解析工具类
//  * @author: zhoutg
//  * @time: 2018/10/29 9:36
//  */
// public final class JwtUtil {
//
//     static final String TOKEN_PREFIX = "Bearer";
//
//     /**
//      * 解析token获取用户ID
//      *
//      * @param token 输入token
//      * @return 用户ID
//      */
//     public static String getUserId(String token) {
//         DecodedJWT jwt = decodedJWT(token);
//         Map<String, Claim> claims = jwt.getClaims();
//         Claim claim = claims.get("user_id");
//         return claim.asInt().toString();
//     }
//
//     /**
//      * 解析token获取用户ID
//      *
//      * @param token 输入token
//      * @return 用户ID
//      */
//     public static Date getExp(String token) {
//         DecodedJWT jwt = decodedJWT(token);
//         Map<String, Claim> claims = jwt.getClaims();
//         Claim claim = claims.get("exp");
//         return claim.asDate();
//     }
//
//     /**
//      * 解析token
//      *
//      * @param token 输入token
//      * @return 解析结果
//      */
//     public static DecodedJWT decodedJWT(String token) {
//         try {
//             DecodedJWT jwt = JWT.decode(token);
//             return jwt;
//         } catch (JWTDecodeException e) {
//             e.printStackTrace();
//             throw new CommonException(CommonErrorCode.ANALYZER_TOKEN_FAIL);
//         }
//     }
//
//     public static void main(String[] args) {
//         String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxOSwidXNlcl9uYW1lIjoic3EiLCJzY29wZSI6WyJzZXJ2aWNlIl0sImV4cCI6MTU0MDg3MDE4MCwiYXV0aG9yaXRpZXMiOlsiL3VzZXJSZW5ld2Fscy9kZWxldGVSZW5ld2Fsc0luZm9zO0FMTCIsIi9wcm9kdWN0T3JkZXIvZ2V0SW5mb3JtYXRpb25BdmFpbGFibGVBbGw7QUxMIiwiL3VzZXIvdXBkYXRlUGVyc29uSW5mbztBTEwiLCIvdXNlckluZm8vdXBkYXRlT3JnYW5pemF0aW9ucztBTEwiLCIvcHJvZHVjdE9yZGVyL2dldEluZm9ybWF0aW9uQXZhaWxhYmxlQnlVc2VySWQ7QUxMIiwiL2RpYWdMYW50b25lUHJvZHVjdC91cGRhdGVTZXJ2aWNlU3RhdHVzO0FMTCIsIi91c2VySW5mby9nZXRVc2VySW5mb1BhZztBTEwiLCIvdXNlckluZm8vdXBkYXRlVXNlckluZm9BbGw7QUxMIiwiL3VzZXJJbmZvL2FkZFVzZXJJbmZvO0FMTCIsIi9wcm9kdWN0T3JkZXIvd2FpdEV4YW1PcmRlckNvdTtBTEwiLCIvZGlhZ09yZGVyRGV0YWlscy9nZXRBbGxPcmRlckRldGlhbHM7QUxMIiwiL3Byb2R1Y3RPcmRlci9nZXRBbGxQcm9kdWN0T3JkZXI7QUxMIiwiL3VzZXIvZ2V0VXNlck9yZ01lbnU7QUxMIiwiL3JlcG9ydC9leHBvcnRLZW1hO0FMTCIsIi9yZXBvcnQvZXhwb3J0VmVyaWZpZWQ7QUxMIiwiL3Byb2R1Y3RPcmRlci9tb2RpZnlPcGVuaW5nVGltZTtBTEwiLCIvdXNlckluZm8vdXBkYXRlRGVsZXRlZDtBTEwiLCIvcmVwb3J0L2V4cG9ydFVzZXJJbmZvQWxsO0FMTCIsIi9yZXBvcnQvZXhwb3J0T3JnYW5pemF0aW9uO0FMTCIsIi91c2VySW5mby9hdWRpdFVzZXJJbmZvQWxsTm9wYXNzO0FMTCIsIi91c2VySW5mby9xdWVyeVVzZXJJbmZvcm1hdGlvbjtBTEwiLCIvdXNlckluZm8vZmluZE9yZ2FuaXphdGlvbjtBTEwiLCIvdXNlckluZm8vYWRkT3JnYW5pemF0aW9uO0FMTCIsIi9kaWFnT3JkZXJEZXRhaWxzL2F1ZGl0U3RhdHVzO0FMTCIsIi91c2VyL21pZGlmeVBhc3N3b3JkO0FMTCIsIi91c2VySW5mby9nZXRVc2VyT3JnYW5Qcm9kdWN0QWxsO0FMTCIsIi91c2VySW5mby91cGRhdGVVc2VySW5mbztBTEwiLCIvZGlhZ0xhbnRvbmVQcm9kdWN0L3VwZGF0ZVByb2R1Y3Q7QUxMIiwiL2RpYWdMYW50b25lUHJvZHVjdC9vcGVuZGVkUHJvZHVjdDtBTEwiLCIvZGlhZ0xhbnRvbmVQcm9kdWN0L3NlbGVjdFByb2R1Y3Q7QUxMIiwiL3VzZXJJbmZvL2F1ZGl0VXNlckluZm9BbGxQYXNzO0FMTCIsIi9yZXBvckV4Y2VsL2V4cG9ydFByb2R1Y3RPcmRlckluZm87QUxMIiwiL3VzZXIvZ2V0UGVyc29uSW5mbztBTEwiLCIvdXNlclJlbmV3YWxzL2NhbmNlbFJlbmV3YWxzSW5mb3M7QUxMIiwiL2RpYWdMYW50b25lUHJvZHVjdC9hZGRQcm9kdWN0cztBTEwiLCIvdXNlckluZm8vcXVlcnlWZXJpZmllZFVzZXJPcmdhbml6YXRpb25Qcm9kdWN0O0FMTCIsIi9yZXBvcnQvZXhwb3J0VXNlckluZm87QUxMIiwiL3Byb2R1Y3RPcmRlci9kZWxJbmZvcm1hdGlvbkF2YWlsYWJsZTtBTEwiLCIvdXNlclJlbmV3YWxzL3JlbmV3YWxzSW5mb3M7QUxMIiwiL3VzZXJJbmZvL3F1ZXJ5QXV0aGVudGljYXRpb247QUxMIiwiL3Byb2R1Y3RPcmRlci9zdGFydEFuZGVuZEJ5dXNlcklkO0FMTCIsIi9kaWFnTGFudG9uZVByb2R1Y3QvZGVsZXRlUHJvZHVjdDtBTEwiLCIvdXNlckF1dGhlbnRpY2F0aW9uL3dhaXRBdXRoZW47QUxMIiwiL3VzZXJJbmZvL3F1ZXJ5TWVjaGFuaXNtSW5mb3JtYXRpb247QUxMIl0sImp0aSI6Ijc4MTFkZGNjLWJhZTEtNDI4OS1iNzdiLWFkMjc5MDkzYmI5NiIsImNsaWVudF9pZCI6InVhYS1zZXJ2aWNlIn0.QFRuSM8x73SP--yaaxtmjtOmKNnE0xJ4EjX_tzl9iOY-kLBul8QCHOEd1ERYy-tVEdDP6njuralANqneEI3TbtRdmLAeUOYV1K5cmeBpHkTZmcS6nk2p30REH6DI8HWnl59qAv0-MwPiguqYpcFJ1dqnMDh8ZIdv5Xj4LUHh3lLLjRxo6_jr9FVUnF_R87BFQ91w4NYdYJy0uJYOBvk4dL16ZgGgc_cpYcncmp0KzCTk-r7_-UFlrNbW8HV-05uVPQY7CCnvLDwG7iQQR2xNZA80wS6EcvUBJijMe8zKw_bp3BTiBbVeTnyQMfAgHFCMAZqpIyuulpmHFGrLplLmJg";
//         //        System.out.println(GsonUtil.toJson(decodedJWT(token)));
//         System.out.println(getUserId(token));
//         System.out.println(getExp(token));
//     }
// }
