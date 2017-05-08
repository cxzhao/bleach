package com.zhaochenxi.bleach.shiro.session;

import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @ClassName: ShiroSession 
 * @Description: 创建和获取保存在Shiro中的session
 * @author zhaochenxi
 * @date 2017年1月15日 上午1:01:58
 */
public class ShiroSession {

	public static String createUserSession(Session session,String userId,String email){
		 String uuid = UUID.randomUUID().toString();
		 String tokenId = uuid.replaceAll("-","");
	     UserSession userSession = new UserSession();
	     userSession.setUserId(userId);
	     userSession.setToken(tokenId);
	     userSession.setEmail(email);
	     session.setAttribute(tokenId, userSession);
	     return tokenId;
	}
	
	/**
	 * 
	 * @Title: createVisitorToken 
	 * @Description: 创建一个临时身份令牌 
	 * @param 
	 * @return String 
	 * @throws 
	 * @author zhaochenxi
	 */
	public static String createVisitorToken(){
		 String uuid = UUID.randomUUID().toString();
		 String tokenId = uuid.replaceAll("-","");
		 return tokenId;
	}
	
	public static UserSession getSession(String token){
		Subject subject = SecurityUtils.getSubject(); 
		Session session = subject.getSession();
		if(session!=null){
			return (UserSession) session.getAttribute(token);
		}else{
			return null;
		}
	}
}
