package com.yijiajiao.root.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class TokenUtil {
	private static final String WEBCLIENT="E-web";
	private static final Logger  log  = LoggerFactory.getLogger(TokenUtil.class.getName());
	public static final int webexpire = Config.getInt("redis.webexpire");
	public static final int appexpire = Config.getInt("redis.appexpire");

	/**
	 * 验证token
	 * @param token
	 * @param openId
	 * @return
	 */
   	public  static boolean  verifyToken(String token,String openId) {
	   	log.info("\n __[验证登录信息：token="+token+" && openId="+openId+"]");
	   	if(token==null || openId == null || "".equals(openId) || "".equals(token)){
		   	return false;
	   	}
		boolean b = false;
		String key0 = openId+0;
		String key1 = openId+1;
		boolean flag0 = RedisUtil.exist(key0);
		boolean flag1 = RedisUtil.exist(key1);
		log.info("\n __[webToken exist:"+flag0+"]\n __[appToken exist:"+flag1+"]");
		if(flag0 && flag1){
			String value0 = RedisUtil.getValue(key0);
			String value1 = RedisUtil.getValue(key1);
			if(token.equals(value0)){
				RedisUtil.expire(key0+"r", webexpire);
				b= true;
			}else if(token.equals(value1)){
				RedisUtil.expire(key1+"r", appexpire);
				b= true;
			}
		}else if(flag0){
			String value = RedisUtil.getValue(key0);
			if(token.equals(value)){
				RedisUtil.expire(key0+"r", webexpire);
				b= true;
			}
		}else if(flag1){
			String value = RedisUtil.getValue(key1);
			if(token.equals(value)){
				RedisUtil.expire(key1+"r", appexpire);
				b= true;
			}
		}
	   	return b;
   	}

	/**
	 * 缓存token和refresh_token
	 * @param openId
	 * @param token
	 * @param clientId  E-web/E-ios/E-android
	 * @param refreshToken
	 */
	public static void putToken(String openId,String token,String clientId,String refreshToken){
		String tokenKey;
		String refreshTokenKey;
		if (WEBCLIENT.equals(clientId)){
			tokenKey = openId+0;
			refreshTokenKey = openId+"0r";
			RedisUtil.setEx(tokenKey,webexpire,token);
			RedisUtil.setEx(refreshTokenKey,webexpire,refreshToken);
		}else {
			tokenKey = openId+1;
			refreshTokenKey = openId+"1r";
			RedisUtil.setEx(tokenKey,appexpire,token);
			RedisUtil.setEx(refreshTokenKey,appexpire,refreshToken);
		}

	}
	/**
	 *	刷新token
	 * @param openId
	 * @param clientId
	 * @param refreshToken
	 * @return
	 */
	public static String[] refreshToken(String openId,String clientId,String refreshToken){
		String[] tokens = new String[2];
		String tokenKey;
		String refreshTokenKey;
		if (WEBCLIENT.equals(clientId)){
			tokenKey = openId+0;
			refreshTokenKey = openId+"0r";
			String refresh = RedisUtil.getValue(refreshTokenKey);
			if (refreshToken.equals(refresh)){
				String token = "TK"+StringUtil.getMD5(openId+refreshToken,2);
				refreshToken = UUID.randomUUID().toString().trim().replaceAll("-", "");
				RedisUtil.setEx(tokenKey,webexpire,token);
				RedisUtil.setEx(refreshTokenKey,webexpire,refreshToken);
				tokens[0] = token;
				tokens[1] = refreshToken;
			}
		}else {
			tokenKey = openId+1;
			refreshTokenKey = openId+"1r";
			String refresh = RedisUtil.getValue(refreshTokenKey);
			if (refreshToken.equals(refresh)){
				String token = "TK"+StringUtil.getMD5(openId+refreshToken,2);
				refreshToken = UUID.randomUUID().toString().trim().replaceAll("-", "");
				RedisUtil.setEx(tokenKey,appexpire,token);
				RedisUtil.setEx(refreshTokenKey,appexpire,refreshToken);
				tokens[0] = token;
				tokens[1] = refreshToken;
			}
		}
		if(StringUtil.isEmpty(tokens[0])){
			tokens = null;
		}
		return tokens;
	}
}
