package com.zhaochenxi.bleach.shiro.session;

import java.io.Serializable;

import com.zhaochenxi.bleach.model.User;
/**
 * @ClassName: UserSessionBean 
 * @Description: 用户登陆会话类，token是用户的身份令牌，通过该令牌去session中查找用户的userId,
 * 找不到的时候表示用户身份失效，需要重新登陆，客户端需要保存一份该token,每一个请求都需要带上这个token
 * @author zhaochenxi
 * @date 2017年1月14日 下午11:53:09
 */
public class UserSession implements Serializable{

	private static final long serialVersionUID = -3173950243007916747L;
	
	//临时生成的一个UUID
	private String token;
	private String userId;
	private String email;
	
	public UserSession(){
		
	}

	public UserSession(User user){
		this.setUserId(user.getId());
	}	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
		
}
