package com.zhaochenxi.bleach.service;

import javax.servlet.http.HttpSession;

import com.zhaochenxi.bleach.model.User;
import com.zhaochenxi.bleach.utils.Result;

public interface IUserService extends IBaseService<User>{
	@SuppressWarnings("rawtypes")
	Result register(String email,String name,String password);
	@SuppressWarnings("rawtypes")
	Result login(String email,String password,String code,HttpSession httpSession);
	User queryByEmail(String email);
	User queryById(String id);
	Result<Object> resetPassword(String email,int code,String password);
	Result<Object> updateName(String userId,String name);
	Result<Object> updateHeadImage(String userId,String headImage);
	Result<Object> updatePassword(String userId,String password);
}
