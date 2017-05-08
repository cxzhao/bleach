package com.zhaochenxi.bleach.controller;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zhaochenxi.bleach.enums.CommentTypeEnum;
import com.zhaochenxi.bleach.model.EtComment;
import com.zhaochenxi.bleach.model.EtCommentReply;
import com.zhaochenxi.bleach.service.IEtCommentService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.IdCreater;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.Utils;

@RestController
@RequestMapping(value = "/etcom")
public class ETCommentController {
	Logger logger = LoggerFactory.getLogger(ETCommentController.class);
	
	private volatile int queryCount=0;
	
	@Autowired
	private IEtCommentService etCommentService;
	
	@RequestMapping(value = "/save",method=RequestMethod.POST)
	public Result<EtComment> save(@ModelAttribute EtComment etcomment){
		if(etcomment!=null){
			etcomment.setId(IdCreater.createId17());
			etcomment.setCreateTime(System.currentTimeMillis());
			etcomment.setAgree(0);
			etcomment.setReply(0);
			if(etcomment.getType()==CommentTypeEnum.cartoon.getValue()&&Utils.isEmptyOrNull(etcomment.getContext())){
				etcomment.setContext(null);
			}
			return etCommentService.save(etcomment);
		}else{
			return new Result<EtComment>(CodeEnum.PARAMETERSNULL);
		}	
	}
	
	@RequestMapping(value = "/reply",method=RequestMethod.POST)
	public Result<EtCommentReply> reply(@ModelAttribute EtCommentReply reply){
		if(reply!=null){
			reply.setId(IdCreater.createId17());
			reply.setCreateTime(System.currentTimeMillis());
			reply.setAgree(0);
			return etCommentService.reply(reply);
		}else{
			return new Result<EtCommentReply>(CodeEnum.PARAMETERSNULL);
		}	
	}
	 
	@RequestMapping(value = "/query",method=RequestMethod.GET)
	public Result<Page<EtComment>> query(
			@RequestParam(value="pageNumber",defaultValue="1")int pageNumber,
			@RequestParam(value="pageSize",defaultValue="20")int pageSize,
			@RequestParam(value="objectId")String objectId,
			@RequestParam(value="type",defaultValue="-1")int type
			){
		queryCount++;
		//logger.info("该接口被访问了"+queryCount+"次");
		System.out.println("该接口被访问了"+queryCount+"次");
		
		if(Utils.isEmptyOrNull(objectId)||pageNumber<1||pageSize<1){
			return  new Result<Page<EtComment>>(CodeEnum.PARAM_ERROR);
		}
		return etCommentService.paginationQuery(pageNumber, pageSize, objectId,type);
	}
	
	@RequestMapping(value = "/delete",method=RequestMethod.POST)
	public Result<Object> deleteReply(String id,String userId,String objectId,int type){
		if(type==0){
			return etCommentService.deleteReply(id,objectId,userId);
		}else{
			return etCommentService.deleteComment(id, userId);
		}		
	}
	
	
	
	@RequestMapping(value = "/love",method=RequestMethod.POST)
	public Result<Map<String, Integer>> love(String id,String userId){
		if(Utils.isEmptyOrNull(id)||Utils.isEmptyOrNull(userId)){
			return new Result<Map<String, Integer>>(CodeEnum.PARAMETERSNULL);
		}
		return etCommentService.love(id, userId);		
	}
}
