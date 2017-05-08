package com.zhaochenxi.bleach.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhaochenxi.bleach.model.Message;
import com.zhaochenxi.bleach.service.IMessageService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.IdCreater;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.Utils;

@RestController
@RequestMapping("messageAdmin")
public class MessageAdminController {
	
	@Autowired
	private IMessageService messageService;
	
	
	@RequestMapping(value="delete",method=RequestMethod.POST) 
	public Result<Object> delete(String message){
		if(Utils.isEmptyOrNull(message)){
			return new Result<Object>(CodeEnum.PARAMETERSNULL);
		}
		Message msg = new Message();
		msg.setId(IdCreater.createId17());
		msg.setContext(message);
		return messageService.save(msg);
	}
	
	@RequestMapping(value="query",method=RequestMethod.GET)
	public Result<Page<Message>> query(@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,@RequestParam(value="pageSize",defaultValue="20")int pageSize){
		return messageService.query(pageNumber, pageSize);		
	}
}
