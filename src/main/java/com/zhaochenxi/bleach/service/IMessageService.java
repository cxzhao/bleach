package com.zhaochenxi.bleach.service;

import com.zhaochenxi.bleach.model.Message;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;

public interface IMessageService {
	public Result<Object> save(Message message);
	public Result<Object> delete(String id);
	public Result<Page<Message>> query(int pageNumber,int pageSize);	
}
