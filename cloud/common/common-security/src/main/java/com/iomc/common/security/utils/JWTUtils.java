package com.iomc.common.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.iomc.common.core.exception.BizException;
import com.iomc.common.core.exception.ExceptionEnum;
import com.iomc.common.redis.utils.RedisUtil;
import com.iomc.common.security.config.AuthProperties;
import com.iomc.common.security.vo.LoginUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
@Slf4j
public class JWTUtils {


    /**
     * jwtToken开头
     */
    public static final String JWT_TOKEN_KEY = "jwt_token:";

    /**
     * 用户信息存储到redis
     */
    public static final String USER_INFO_KEY = "user:info:";

    /** 在线用户 key */
    public static final String ONLINE_USERS_KEY = "user:online:";


    /**
     * 生成随机token
     *
     * @return
     */
    public static String createRandomToken() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-", "")
                .toLowerCase();
    }

    /**
     *
     * 生成JwtToken
     *
     * @param map 传入payload
     *
     * @return 返回token
     */
    public static String createJwtToken(Map<String,String> map){

        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, AuthProperties.EXPIRE);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(AuthProperties.SIGNATURE));
    }

    /**
     * 验证token
     * @param jwtToken
     */
    public static boolean verify(String jwtToken){

        // 在redis中获取token信息
        String userId = getUserId(jwtToken);
        Object o = RedisUtil.get(getJwtTokenKey(userId, jwtToken));
        if(o == null){
            throw new BizException(ExceptionEnum.TOKEN_EXPIRED);
        }

        // 验证token签名
        JWT.require(Algorithm.HMAC256(AuthProperties.SIGNATURE))
                .build()
                .verify(jwtToken);

        // 验证有效期
        if (!isTokenExpired(jwtToken)) {
            removeToken(jwtToken);
            throw new BizException(ExceptionEnum.TOKEN_EXPIRED);
        }

        return true;
    }

    /**
     * 获取token中payload
     * @param jwtToken
     *
     * @return
     */
    public static String getPayloadToken(String jwtToken){
        return getPayload(jwtToken, "token");
    }

    /**
     * 获取id
     * @param jwtToken
     *
     * @return
     */
    public static String getUserId(String jwtToken){
        return getPayload(jwtToken, "id");
    }

    /**
     * 获取token中payload
     * @param jwtToken
     * @param name 参数名称
     *
     * @return
     */
    public static String getPayload(String jwtToken, String name){
       try {
           DecodedJWT jwt = JWT.decode(jwtToken);
           return jwt.getClaim(name).asString();
       } catch (JWTDecodeException e) {
           e.printStackTrace();
           return null;
       }
    }


    /**
     * 验证token是否有效期
     *
     * @param token
     * @return
     */
    public static boolean isTokenExpired(String token){

        String userId = getUserId(token);
        Object o = RedisUtil.get(getJwtTokenKey(userId, token));
        if (o == null) {
            return false;
        }
        Date expiresAt = JWT.decode(token).getExpiresAt();
        Date now = new Date();

        long time = expiresAt.getTime();
        long nowTime = now.getTime();

        return nowTime < time;
    }


    /**
     * 登录
     *
     * 保存用户登陆信息
     * 使用jwtToken返回
     * jwt中的payload为随机token
     * 根据随机token可以获取用户信息
     * @param loginUser
     * @return
     */
    public static Map<String, String> login(LoginUserVO loginUser){

        String token = "token";
        String userId = loginUser.getId() + "";

        // 1.生成随机token 用于保存用户信息
        String randomToken = JWTUtils.createRandomToken();
        // 2.存入redis设置登录失效时间
        RedisUtil.set(getUserInfoKey(userId, randomToken), loginUser, AuthProperties.EXPIRE);
        RedisUtil.set(getOnlineUsersKey(userId), userId, AuthProperties.EXPIRE);

        // 3.生成jwtToken 随机token作为jwtToken的payload
        Map<String, String> payload = new HashMap<>();
        payload.put(token, randomToken);
        payload.put("id", String.valueOf(loginUser.getId()));
        String jwtToken = JWTUtils.createJwtToken(payload);
        RedisUtil.set(getJwtTokenKey(userId, jwtToken), loginUser.getDevice(), AuthProperties.EXPIRE);
        // 返回jwtToken
        payload.put(token, jwtToken);
        return payload;
    }

    /**
     * 退出登录
     *
     * 删除redis中的用户信息、登陆信息、在线状态
     * @param request
     */
    public static void logout(HttpServletRequest request) {
        String jwtToken = getJwtToken(request);
        removeToken(jwtToken);
    }


    /**
     * 获取用户信息
     * @return 用户信息
     */
    public static LoginUserVO getUserInfo(String jwtToken) {

        // 1.获取jwtToken对应的payload
        String payloadToken = getPayloadToken(jwtToken);
        String userId = getUserId(jwtToken);

        // 2.获取用户信息
        Object o = RedisUtil.get(getUserInfoKey(userId, payloadToken));
        if(o == null){
            throw new BizException(ExceptionEnum.TOKEN_EXPIRED);
        }

        // 3.转成对象
        return (LoginUserVO) o;
    }


    /**
     * 获取当前请求的jwtToken
     */
    public static String getJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken == null) {
            return null;
        }
        // 去除"Bearer "
        return jwtToken.substring(7);
    }

    /**
     * 移除token
     * 删除redis中的用户信息、登陆信息、在线状态
     * @param jwtToken
     * @return
     */
    public static void removeToken(String jwtToken){
        String payloadToken = getPayloadToken(jwtToken);
        String userId = getUserId(jwtToken);

        RedisUtil.delete(getJwtTokenKey(userId, jwtToken));
        RedisUtil.delete(getUserInfoKey(userId, payloadToken));
        RedisUtil.delete(getOnlineUsersKey(userId));
    }

    /**
     * 踢人下线
     *
     * @param userId
     */
    public static void kickUser(String userId){

        // 获取该用户所有的jwtToken
        String jwtKey = getJwtTokenKey(userId, "*");
        removeKeyByLike(jwtKey);

        // 获取该用户存储的用户信息
        String userInfoKey = getUserInfoKey(userId, "*");
        removeKeyByLike(userInfoKey);

        String onlineUsersKey = getOnlineUsersKey(userId);
        removeKeyByLike(onlineUsersKey);
    }


    /**
     * 删除redis中所有以likeKey开头的key
     *
     * @param likeKey
     */
    private static void removeKeyByLike(String likeKey) {
        Set scan = RedisUtil.scan(likeKey);
        for (Object o : scan) {
            String s = (String) o;
            RedisUtil.delete(s);
        }
    }


    /**
     * 获取用户信息key
     *
     * @param userId
     * @param randomToken
     * @return
     */
    private static String getUserInfoKey(String userId, String randomToken) {
        return USER_INFO_KEY + userId + ":" + randomToken;
    }

    /**
     * 获取jwtToken
     *
     * @param userId
     * @param jwtToken
     */
    private static String getJwtTokenKey(String userId, String jwtToken) {
        return JWT_TOKEN_KEY + userId + ":" + jwtToken;
    }

    /**
     * 获取在线用户key
     *
     * @param userId
     */
    public static String getOnlineUsersKey(String userId) {
        return ONLINE_USERS_KEY + userId;
    }
}
