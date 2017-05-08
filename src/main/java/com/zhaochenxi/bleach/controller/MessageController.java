package com.zhaochenxi.bleach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zhaochenxi.bleach.model.Message;
import com.zhaochenxi.bleach.service.IMessageService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.IdCreater;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.Utils;
@CrossOrigin
@RestController
@RequestMapping("message")
public class MessageController {
	
	@Autowired
	private IMessageService messageService;
	
	@RequestMapping(value="send",method=RequestMethod.POST) 
	public Result<Object> sendMessage(String message){
		if(Utils.isEmptyOrNull(message)){
			return new Result<Object>(CodeEnum.PARAMETERSNULL);
		}
		Message msg = new Message();
		msg.setId(IdCreater.createId17());
		msg.setContext(message);
		return messageService.save(msg);
	}
	
	
}
