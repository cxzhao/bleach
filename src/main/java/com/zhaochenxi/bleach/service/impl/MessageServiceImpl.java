package com.zhaochenxi.bleach.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhaochenxi.bleach.dao.IMessageDao;
import com.zhaochenxi.bleach.model.Message;
import com.zhaochenxi.bleach.service.IMessageService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;

@Service
public class MessageServiceImpl implements IMessageService {

	@Autowired
	private IMessageDao messageDao;
	
	@Override
	public Result<Object> save(Message message) {
		if(messageDao.save(message)){
			return new Result<Object>(CodeEnum.SUCCESS);
		}else{
			return new Result<Object>(CodeEnum.FAILURE);
		}
	}

	@Override
	public Result<Object> delete(String id) {
		if(messageDao.delete(id)){
			return new Result<Object>(CodeEnum.SUCCESS);
		}else{
			return new Result<Object>(CodeEnum.FAILURE);
		}
	}

	@Override
	public Result<Page<Message>> query(int pageNumber, int pageSize) {
		int start = (pageNumber-1)*pageSize;
		List<Message> list= messageDao.query(start,pageSize);
		Page<Message> page = new Page<Message>();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setList(list);		
		return new Result<Page<Message>>(CodeEnum.SUCCESS, page);
	}
	
}
