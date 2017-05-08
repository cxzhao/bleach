package com.zhaochenxi.bleach.shiro.realm;

import java.util.HashSet;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zhaochenxi.bleach.model.User;
import com.zhaochenxi.bleach.service.IUserService;
import com.zhaochenxi.bleach.utils.MD5Utils;
/**
 * @ClassName: UserRealm 
 * @Description: 登陆权限验证实现类
 * @author zhaochenxi
 * @date 2017年1月14日 下午10:03:00
 */
@Service
public class UserRealm extends AuthorizingRealm{

	@Autowired
	private IUserService userService;
	
	/**
	 * 用户权限验证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		 String username = (String)principals.getPrimaryPrincipal();
	     SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
	     User user=userService.queryByEmail(username);
	     Set<String> role = new HashSet<>();
	     role.add(user.getRole()+"");
	     Set<String> permissions = new HashSet<>();
	     permissions.add(user.getEmail());	     
	     authorizationInfo.setRoles(role);
	     authorizationInfo.setStringPermissions(permissions);
	     return authorizationInfo;
	}

	/**
	 * 用户身份认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;          
		User user=userService.queryByEmail(token.getUsername());
		if(user!=null){
			char [] password = token.getPassword();
			StringBuffer passwordStr = new StringBuffer();
			for(char c:password){
				passwordStr.append(c);
			}		
			String pass = MD5Utils.string2MD5(passwordStr.toString());
			if(user.getPassword().equals(pass)){
				return new SimpleAuthenticationInfo(user.getEmail(),password,getName());
			}else{
				return null;
			}
		}else{
			return null;
		}

	}

}
