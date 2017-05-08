package com.zhaochenxi.bleach.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping(value="/type",method=RequestMethod.GET)
	public String type(){
		return "admin/type";
	}
	
	@RequestMapping(value="/admin",method=RequestMethod.GET)
	public String admin(){
		return "admin/admin";
	}
	
	@RequestMapping(value="/cartoon",method=RequestMethod.GET)
	public String cartoon(){
		return "admin/cartoon";
	}
	
	@RequestMapping(value="/addCartoon",method=RequestMethod.GET)
	public String addCartoon(){
		return "admin/add_cartoon";
	}
	
	@RequestMapping(value="/editCartoon",method=RequestMethod.GET)
	public String editCartoon(){
		return "admin/edit_cartoon";
	}
	
	@RequestMapping(value="/recommend",method=RequestMethod.GET)
	public String recommend(){
		return "admin/recommend";
	}
	
	@RequestMapping(value="/news",method=RequestMethod.GET)
	public String news(){
		return "admin/news";
	}
	
	@RequestMapping(value="/addNews",method=RequestMethod.GET)
	public String addNews(){
		return "admin/add_news";
	}
	
	
	@RequestMapping(value="/editNews",method=RequestMethod.GET)
	public String editNews(){
		return "admin/edit_news";
	}
	
}
