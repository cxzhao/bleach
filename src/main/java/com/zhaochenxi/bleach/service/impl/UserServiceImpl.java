package com.zhaochenxi.bleach.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.zhaochenxi.bleach.dao.IEtCommentReplyDao;
import com.zhaochenxi.bleach.dao.IUserDao;
import com.zhaochenxi.bleach.dto.UserDto;
import com.zhaochenxi.bleach.enums.DataStatusEnum;
import com.zhaochenxi.bleach.enums.UserRoleEnum;
import com.zhaochenxi.bleach.enums.UserTypeEnum;
import com.zhaochenxi.bleach.model.User;
import com.zhaochenxi.bleach.service.IUserService;
import com.zhaochenxi.bleach.service.IVerificationCodeService;
import com.zhaochenxi.bleach.shiro.session.ShiroSession;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.ConstUtils;
import com.zhaochenxi.bleach.utils.IdCreater;
import com.zhaochenxi.bleach.utils.MD5Utils;
import com.zhaochenxi.bleach.utils.Result;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IEtCommentReplyDao replyDao;
	
	@Autowired
	private IVerificationCodeService codeService;
	@Override
	public boolean save(User user) {
		return userDao.save(user);
	}

	@Override
	public boolean delete(User t) {
		return false;
	}

	@Override
	public boolean update(User t) {
		return false;
	}

	/**
	 * @Title: register
	 * @Description: 用户注册
	 * @param
	 * @return Result<?>
	 * @author zhaochenxi
	 */
	@SuppressWarnings("rawtypes")
	public Result register(String email, String name, String password) {
		// 判断用户是否已存在
		User u = userDao.queryByEmail(email);
		if (u != null) {
			return new Result(CodeEnum.USER_EXITS);
		}
		password = MD5Utils.string2MD5(password);
		User user = new User();
		user.setCreateTime(new Date());
		user.setEmail(email);
		user.setName(name);
		user.setId(IdCreater.createId17());
		user.setPassword(password);
		user.setRole(UserRoleEnum.register.getValue());
		user.setStatus(DataStatusEnum.active.getValue());
		user.setType(UserTypeEnum.et.getValue());
		user.setHeadImage(createHeadImage());
		if (save(user)) {
			return new Result(CodeEnum.SUCCESS);
		} else {
			return new Result(CodeEnum.FAILURE);
		}

	}

	private Result<UserDto> loginFail(String tokenId){
		UserDto userDto = new UserDto();
    	userDto.setEmail("XXXXX@qq.com");
    	userDto.setHeadImage(ConstUtils.headImage);
    	userDto.setName(ConstUtils.DEFAULT_USERNAME);	
    	userDto.setToken(tokenId);
    	userDto.setUserId(tokenId);
    	return new Result<UserDto>(CodeEnum.USER_PASSWORD_ERROR,userDto);
	} 
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result login(String email, String password, String code,HttpSession httpSession) {
		
		UsernamePasswordToken token = new UsernamePasswordToken(email, password);
	    token.setRememberMe(true); 
	    Subject subject = SecurityUtils.getSubject(); 
	    String sessionId = null;
	    try {  
	    	Session session = subject.getSession();
	        subject.login(token); 	        
	        if (subject.isAuthenticated()) {  	        	
	        	User user = queryByEmail(email);
	        	String tokenId=ShiroSession.createUserSession(session,user.getId(),user.getEmail());
	        	UserDto userDto = new UserDto();
	        	userDto.setEmail(user.getEmail());
	        	userDto.setHeadImage(user.getHeadImage());
	        	userDto.setName(user.getName());	
	        	userDto.setToken(tokenId);
	        	return new Result<UserDto>(CodeEnum.SUCCESS,userDto);
	        } else {  
	        	return loginFail(ShiroSession.createVisitorToken());
	        }  
	    } catch (IncorrectCredentialsException e) {  
	        //msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";  
	    	e.printStackTrace();
	    	return loginFail(ShiroSession.createVisitorToken());
	    } catch (ExcessiveAttemptsException e) {  
	        //msg = "登录失败次数过多";  
	    	e.printStackTrace();
	    	return loginFail(ShiroSession.createVisitorToken());
	    } catch (LockedAccountException e) {  
	       // msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";  
	    	e.printStackTrace();
	    	return loginFail(ShiroSession.createVisitorToken());
	    } catch (DisabledAccountException e) {  
	        //msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";  
	    	e.printStackTrace();
	    	return loginFail(sessionId);
	    } catch (ExpiredCredentialsException e) {  
	       // msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";  
	    	e.printStackTrace();
	    	return loginFail(ShiroSession.createVisitorToken());
	    } catch (UnknownAccountException e) {  
	       // msg = "帐号不存在. There is no user with username of " + token.getPrincipal();  
	    	e.printStackTrace();
	    	return loginFail(ShiroSession.createVisitorToken());
	    } catch (UnauthorizedException e) {  
	       // msg = "您没有得到相应的授权！" + e.getMessage();  
	    	e.printStackTrace();
	    	return loginFail(ShiroSession.createVisitorToken());
	    }   
	}

	@Override
	public List<User> queryAll() {
		return null;
	}

	private String createHeadImage(){
		Random random=new Random();// 定义随机类
		int index=random.nextInt(21);// 返回[0,10)集合中的整数，注意不包括10
		return ConstUtils.headImageMap.get(index);
	}

	@Override
	public Result<Object> updateName(String userId, String name) {
		User user = userDao.queryById(userId);
		if(user!=null){
			if(userDao.updateName(userId, name)){
				replyDao.updateObjName(name, userId);
				replyDao.updateUserName(name, userId);
				return new Result<Object>(CodeEnum.SUCCESS);
			}else{
				return new Result<Object>(CodeEnum.FAILURE);
			}
		}else{
			return new Result<Object>(CodeEnum.USER_NOT_EXITS);
		}
	}

	@Override
	public Result<Object> updateHeadImage(String userId, String headImage) {
		User user = userDao.queryById(userId);
		if(user!=null){
			if(userDao.updateHeadImage(userId, headImage)){
				return new Result<Object>(CodeEnum.SUCCESS);
			}else{
				return new Result<Object>(CodeEnum.FAILURE);
			}
		}else{
			return new Result<Object>(CodeEnum.USER_NOT_EXITS);
		}
	}

	@Override
	public Result<Object> updatePassword(String userId, String password) {
		User user = userDao.queryById(userId);
		if(user!=null){
			if(userDao.updatePassword(userId, MD5Utils.string2MD5(password))){
				return new Result<Object>(CodeEnum.SUCCESS);
			}else{
				return new Result<Object>(CodeEnum.FAILURE);
			}
		}else{
			return new Result<Object>(CodeEnum.USER_NOT_EXITS);
		}
	}

	@Override
	public Result<Object> resetPassword(String email, int code, String password) {
		if(code<100000){
			return new Result<Object>(CodeEnum.CODE_ERROR);
		}
		User user = userDao.queryByEmail(email);
		if(user==null){
			return new Result<Object>(CodeEnum.USER_NOT_EXITS);
		}
		if(codeService.checkForgetPassCode(email, code)){
			if(userDao.updatePassword(user.getId(), MD5Utils.string2MD5(password))){
				return new Result<Object>(CodeEnum.SUCCESS);
			}else{
				return new Result<Object>(CodeEnum.FAILURE);
			}
		}else{
			return new Result<Object>(CodeEnum.CODE_ERROR);
		}
		
	}

	@Cacheable("UserCache")
	@Override
	public User queryByEmail(String email){
		return userDao.queryByEmail(email);
	}
	
	@Cacheable("UserCache")
	@Override
	public User queryById(String id) {
		return userDao.queryById(id);
	}
}
