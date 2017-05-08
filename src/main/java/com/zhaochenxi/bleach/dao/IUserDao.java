package com.zhaochenxi.bleach.dao;

import com.zhaochenxi.bleach.model.User;

public interface IUserDao {
	public boolean save(User user);	
	public boolean updateName(String id,String name);	
	public boolean updatePassword(String id,String password);	
	public boolean updateHeadImage(String id,String headImage);	
	public User queryByEmail(String email);
	public User queryById(String id);
}
