package com.zhaochenxi.bleach.dao;

import java.util.List;

import com.zhaochenxi.bleach.model.Message;

public interface IMessageDao {

	public boolean save(Message message);
	public boolean delete(String id);
	public List<Message> query(int start,int size);
	
}
