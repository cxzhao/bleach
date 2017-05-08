package com.zhaochenxi.bleach.test.controller;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:applicationContext.xml","classpath*:mvc-servlet.xml" })
public class CartoonControllerTest {

	@Autowired  
    private WebApplicationContext wac; 
    
	private MockMvc mockMvc;
    
    @Before
    public void init(){
    	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
    public void cartoonTypeAdd(){
    	try {
    		ResultActions resultActions=this.mockMvc.perform(MockMvcRequestBuilders.post("/add").accept(MediaType.APPLICATION_JSON).param("typeName","后宫"));
			MvcResult mvcResult = resultActions.andReturn();
			ModelAndView view=mvcResult.getModelAndView();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }

	
    
}
