package com.zhaochenxi.bleach.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * @ClassName: 跨域配置适配器
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhaochenxi
 * @date 2017年1月8日 下午11:23:45
 */
public class CorsConfigurerAdapter extends WebMvcConfigurerAdapter{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
        .allowedOrigins("http://localhost:8080","http://localhost:3000","http://www.etkitty.com","http://api.etkitty.com")
        .allowedMethods("PUT","DELETE","GET","POST")
        .allowCredentials(CrossOrigin.DEFAULT_ALLOW_CREDENTIALS)
        .maxAge(3600)
        .exposedHeaders(HttpHeaders.SET_COOKIE,HttpHeaders.ACCEPT_LANGUAGE,HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,HttpHeaders.CONTENT_TYPE,HttpHeaders.COOKIE)
        .allowedHeaders(CrossOrigin.DEFAULT_ALLOWED_HEADERS);
	}

}
