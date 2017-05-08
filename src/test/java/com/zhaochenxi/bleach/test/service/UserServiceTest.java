package com.zhaochenxi.bleach.test.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.zhaochenxi.bleach.model.User;
import com.zhaochenxi.bleach.service.impl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml","classpath*:spring-mybatis.xml" })
public class UserServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private UserServiceImpl userService;
	
	@Test
	public void userSaveTest(){
		String id="1";
		String email = "3111946308@qq.com";
		String name="赵晨曦";
		String password="123456";
		
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setCreateTime(new Date());
		user.setName(name);
		user.setPassword(password);
		userService.save(user);
	}
	
	
}
