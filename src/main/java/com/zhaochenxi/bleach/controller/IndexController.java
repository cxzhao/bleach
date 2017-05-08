package com.zhaochenxi.bleach.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("view/index");
		return mv;
	}
	
	@RequestMapping("/welcome")
	public ModelAndView welcome() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("view/welcome");
		return mv;
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
