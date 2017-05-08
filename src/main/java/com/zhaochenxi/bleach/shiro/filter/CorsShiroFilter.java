package com.zhaochenxi.bleach.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.AdviceFilter;

import com.zhaochenxi.bleach.utils.Utils;

/**
 * @ClassName: BleachShiroFilter 
 * @Description: 跨域配置过滤器
 * @author zhaochenxi
 * @date 2017年1月16日 下午11:50:25
 */
public class CorsShiroFilter extends AdviceFilter{

	private final static String ACCESS_CONTROL_ALLLOW_ORIGIN = "Access-Control-Allow-Origin";
	private final static String ACCESS_CONTROL_ALLLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
	private final static String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
	private final static String ACCESS_CONTROL_MAX_AGE_VALUE = "3600";
    private final static String ACCESS_CONTROL_ALLLOW_METHODS = "Access-Control-Allow-Methods"; 
    private final static String ACCESS_CONTROL_ALLLOW_HEADERS = "Access-Control-Allow-Headers";
    private final static String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {	
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest resq = (HttpServletRequest) request;	
		request.setCharacterEncoding("utf-8");
        String origin = resq.getHeader("Origin");
		if(corsAuth(request)){
			setCorsResponse(resq,res,origin);
		}
		return super.preHandle(request, res);
	}
	
	public boolean requestAuth(String origin){
		if(Utils.isEmptyOrNull(origin)){
			return false;
		}
		if(CorsProp.originSet.contains(origin)){
			return true;
		}
		return false;		
	}
	
	/**
	 * @Title: corsAuth 
	 * @Description:web端跨域请求权限验证
	 * @param 
	 * @return boolean 
	 * @throws 
	 * @author zhaochenxi
	 */
	private boolean corsAuth(ServletRequest request){
		HttpServletRequest resq = (HttpServletRequest) request;		
		String origin = resq.getHeader("Origin");
		if(requestAuth(origin)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * @Title: setCorsResponse 
	 * @Description: CORS跨域请求的响应 
	 * @param 
	 * @return void 
	 * @throws 
	 * @author zhaochenxi
	 */
	private void setCorsResponse(ServletRequest request, HttpServletResponse res,String origin){
		res.setHeader(ACCESS_CONTROL_ALLLOW_ORIGIN, origin);
		res.setHeader(ACCESS_CONTROL_ALLLOW_METHODS, "POST,GET,OPTIONS");
		res.setHeader(ACCESS_CONTROL_MAX_AGE, ACCESS_CONTROL_MAX_AGE_VALUE);
		HttpServletRequest resq = (HttpServletRequest) request;		
		String requestHeader = resq.getHeader(ACCESS_CONTROL_REQUEST_HEADERS);
		res.setHeader(ACCESS_CONTROL_REQUEST_HEADERS, requestHeader);
		res.setHeader(ACCESS_CONTROL_ALLLOW_HEADERS,requestHeader);
		res.setHeader(ACCESS_CONTROL_ALLLOW_CREDENTIALS,"true");
	}

}
