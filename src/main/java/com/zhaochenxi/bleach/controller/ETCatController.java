package com.zhaochenxi.bleach.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
@CrossOrigin
@Controller
@RequestMapping("/et")
public class ETCatController {
	
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String index() {
		return "view/index";
	}
	
	@RequestMapping(value="recommend",method=RequestMethod.GET)
	public String recommend() {
		return "view/index2";
	}
	
	
	@RequestMapping(value="main",method=RequestMethod.GET)
	public String main() {
		return "view/main";
	}
	
	@RequestMapping(value="detail",method=RequestMethod.GET)
	public String detail() {
		return "view/detail";
	}
	
	@RequestMapping(value="news",method=RequestMethod.GET)
	public String news(){
		return "view/news";
	}
	
	@RequestMapping(value="newsDetail",method=RequestMethod.GET)
	public String newsDetail(){
		return "view/news_detail";
	}
	
	
	@RequestMapping(value="register",method=RequestMethod.GET)
	public String register(){
		return "view/register";
	}
	
	@RequestMapping(value="resetpass",method=RequestMethod.GET)
	public String resetpass(){
		return "view/find_pass";
	}
	
	@RequestMapping(value="comment",method=RequestMethod.GET)
	public String comment(){
		return "view/comment";
	}
	
	@RequestMapping(value="editComment",method=RequestMethod.GET)
	public String editComment(){
		return "view/edit_comment";
	}
	
	@RequestMapping(value="commentDetail",method=RequestMethod.GET)
	public String commentDetail(){
		return "view/comment_detail";
	}
	
	@RequestMapping(value="mine",method=RequestMethod.GET)
	public String mine(){
		return "view/mine";
	}
	
	@RequestMapping(value="myComment",method=RequestMethod.GET)
	public String myComment(){
		return "view/my_comment";
	}
	
	@RequestMapping(value="editMyComment",method=RequestMethod.GET)
	public String editMyComment(){
		return "view/edit_my_comment";
	}
	
	@RequestMapping(value="myRecommend",method=RequestMethod.GET)
	public String myRecommend(){
		return "view/my_recommend";
	}
	
	@RequestMapping(value="setinfo",method=RequestMethod.GET)
	public String updatePassword(){
		return "view/set_info";
	}
	
	@RequestMapping(value="login",method=RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request){
		String sessionId = request.getSession().getId();
		String user = (String) request.getSession().getAttribute(sessionId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("view/login");
		mv.addObject("user", user);
		return mv;
	}
	
	@RequestMapping(value="adminLogin",method=RequestMethod.GET)
	public ModelAndView adminLogin(HttpServletRequest request){
		String sessionId = request.getSession().getId();
		String user = (String) request.getSession().getAttribute(sessionId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/login");
		mv.addObject("user", user);
		return mv;
	}
	
	@RequestMapping(value="editCartoon",method=RequestMethod.GET)
	public String editCartoon(){
		return "admin/edit_cartoon";
	}
	
	@RequestMapping(value="protocal",method=RequestMethod.GET)
	public String protocal(){
		return "view/protocal";
	}
	
	@RequestMapping(value="contact",method=RequestMethod.GET)
	public String header(){
		return "view/contact";
	}
	
	@RequestMapping(value="about",method=RequestMethod.GET)
	public String about(){
		return "view/aboutus";
	}
	
	@RequestMapping(value="work",method=RequestMethod.GET)
	public String work(){
		return "view/work";
	}
}





