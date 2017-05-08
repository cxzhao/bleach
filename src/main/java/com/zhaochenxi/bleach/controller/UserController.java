package com.zhaochenxi.bleach.controller;

import javax.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zhaochenxi.bleach.dto.UserDto;
import com.zhaochenxi.bleach.service.IVerificationCodeService;
import com.zhaochenxi.bleach.service.impl.UserServiceImpl;
import com.zhaochenxi.bleach.shiro.session.ShiroSession;
import com.zhaochenxi.bleach.shiro.session.UserSession;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.ConstUtils;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.Utils;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private IVerificationCodeService codeService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/toLogin",method=RequestMethod.GET)
	public Result toLogin(){
		return new Result<Object>(CodeEnum.USERNOTLOGIN);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/unauthor",method=RequestMethod.GET)
	public Result unauthor(){
		return new Result<Object>(CodeEnum.UNAUTHOR);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Result login(String email,String password,String code,HttpSession session){
		if(Utils.isEmptyOrNull(email)||Utils.isEmptyOrNull(password)){
			return new Result(CodeEnum.PARAMETERSNULL);
		}		
		return userService.login(email, password,code,session);	
	}
	
	
	/**
	 * @Title: register 
	 * @Description: 用户注册 
	 * @param 
	 * @return Result 
	 * @throws 
	 * @author zhaochenxi
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public Result register(String email,String name,String password,@RequestParam(name="isAgree",defaultValue="false")boolean isAgree){
		if(Utils.isEmptyOrNull(email)||Utils.isEmptyOrNull(name)||Utils.isEmptyOrNull(password)){
			return new Result(CodeEnum.PARAMETERSNULL);
		}	
		if(isAgree==false){
			return new Result(CodeEnum.USER_NOT_AGREE);
		}
		return userService.register(email, name, password);
	}
	
	/**
	 * 需要修改，没有token传过来的时候临时的userId和token在用户的会话结束前不能改变
	 * @Title: getSession 
	 * @Description: TODO 
	 * @param 
	 * @return Result<Object> 
	 * @throws 
	 * @author zhaochenxi
	 */
	@RequestMapping(value="/me",method=RequestMethod.GET)
	public Result<Object> getSession(String token){
		if(!Utils.isEmptyOrNull(token)){
			//用户已经登录了，通过tokenId去获取用户权限
			Subject subject = SecurityUtils.getSubject(); 
			Session session = subject.getSession();
			UserSession userSession = (UserSession) session.getAttribute(token);
			if(userSession!=null){
				UserDto userDto = new UserDto();
	        	userDto.setEmail(userSession.getEmail());
	        	userDto.setHeadImage(userSession.getEmail());
	        	userDto.setName(userSession.getEmail());
	        	userDto.setToken(userSession.getToken());
	        	userDto.setUserId(userSession.getUserId());
				return new Result<Object>(CodeEnum.SUCCESS,userDto);
			}
		}
		UserDto userDto = new UserDto();
    	userDto.setEmail("还没有注册，去注册吧");
    	userDto.setHeadImage(ConstUtils.defaultheadImage);
    	userDto.setName("ET9527");	
    	userDto.setToken(ShiroSession.createVisitorToken());
    	userDto.setUserId(ShiroSession.createVisitorToken());
		return new Result<Object>(CodeEnum.USERNOTLOGIN,userDto);
	}
	
	@RequestMapping(value="/loginout",method=RequestMethod.GET)
	public Result<UserDto> loginOut(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		UserDto userDto = new UserDto();
    	userDto.setEmail("还没有注册，去注册吧");
    	userDto.setHeadImage(ConstUtils.defaultheadImage);
    	userDto.setName("ET9527");	
    	userDto.setToken(ShiroSession.createVisitorToken());
		return new Result<UserDto>(CodeEnum.USERNOTLOGIN,userDto);
	}
		
	@RequestMapping(value="/setName",method=RequestMethod.POST)
	public Result<Object> setName(String id,String name){
		if(!Utils.isEmptyOrNull(id)||!Utils.isEmptyOrNull(name)){
			return userService.updateName(id, name);
		}else{
			return new Result<Object>(CodeEnum.PARAMETERSNULL);
		}
	}
	
	@RequestMapping(value="/setPass",method=RequestMethod.POST)
	public Result<Object> setPass(String id,String pass){
		if(!Utils.isEmptyOrNull(id)||!Utils.isEmptyOrNull(pass)){
			return userService.updatePassword(id, pass);
		}else{
			return new Result<Object>(CodeEnum.PARAMETERSNULL);
		}
	}
	
	@RequestMapping(value="/sendPassCode",method=RequestMethod.POST)
	public Result<Object> sendPassCode(String email){
		if(Utils.isEmptyOrNull(email)){
			return new Result<Object>(CodeEnum.PARAMETERSNULL);
		}
		return codeService.sendForgetPassCode(email);
	}
	
	@RequestMapping(value="/resetPass",method=RequestMethod.POST)
	public Result<Object> setPass(String email,@RequestParam(name="code",defaultValue="1")int code,String pass){

		if(!Utils.isEmptyOrNull(email)||!Utils.isEmptyOrNull(pass)){
			return userService.resetPassword(email, code, pass);
		}else{
			return new Result<Object>(CodeEnum.PARAMETERSNULL);
		}
	}
	
}
