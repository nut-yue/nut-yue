//package com.mall.clinicapi.jwt;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTDecodeException;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.Claim;
//import com.auth0.jwt.interfaces.DecodedJWT;
//
//import javax.xml.crypto.Data;
//import java.util.Date;
//
//public class JwtUtil {
//    // 设置token的过期时间为3分钟
//    private static final long EXPIRE_TIME = 3 * 60 * 1000;
//
//    /**
//     * 校验密钥是否有效
//     * @param token 密钥
//     * @param username 用户名
//     * @param password 密码
//     * @return 返回true则token有效，反之无效
//     */
//    public static boolean verify(String token, String username, String password) {
//        try {
//            // 使用HMAC256算法进行加密
//            Algorithm algorithm = Algorithm.HMAC256(password);
//            // 生产校验器用于token校验
//            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
//            // 校验token
//            DecodedJWT jwt = verifier.verify(token);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
//    /**
//     * 从token中解析出用户名，无需密码也可以获得
//     * @param token 密钥
//     * @return 密钥中包含的用户名
//     */
//    public static String getUsername(String token){
//        try {
//            // 调用decode方法返回DecodedJWT接口对象
//            DecodedJWT jwt = JWT.decode(token);
//            // DecodedJWT接口继承Payload, Header两个接口,调用payload接口的getClaim()方法取得用户名
//            return jwt.getClaim("username").asString();
//        } catch (JWTDecodeException e){
//            return null;
//        }
//    }
//
//    /**
//     * 生成签名，签名有效期为5分钟
//     * @param username 用户名
//     * @param password 密码
//     * @return 加密之后的token
//     */
//    public static String sign (String username,String password) {
//        try {
//            // 指定时效时间为当前时间之后的5分钟
//            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
//            // 使用HMAC256对密码加密
//            Algorithm algorithm = Algorithm.HMAC256(password);
//            // 创建token
//            return JWT.create()
//                    .withClaim("username", username)
//                    .withExpiresAt(date)
//                    .sign(algorithm);
//        } catch (Exception e) {
//            return null;
//        }
//
//    }
//
//}
