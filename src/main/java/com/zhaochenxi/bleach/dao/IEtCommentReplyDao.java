package com.zhaochenxi.bleach.dao;

import com.zhaochenxi.bleach.model.EtCommentReply;

public interface IEtCommentReplyDao {
	public boolean save(EtCommentReply cr);
	public boolean delete(String id,String userId);
	public boolean updateObjName(String objName,String objUserId);
	public boolean updateUserName(String userName,String objUserId);
}
