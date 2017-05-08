package com.zhaochenxi.bleach.dao;

import java.util.List;

import com.zhaochenxi.bleach.dto.CommentScoreDto;
import com.zhaochenxi.bleach.model.EtComment;

public interface IEtCommentDao {
	public boolean save(EtComment comment);
	public boolean delete(String id,String userId);
	public boolean updateReply(String id);
	public boolean decreaseReply(String id);
	public boolean updateAgree(String id);
	public EtComment queryCommentById(String id);
	public CommentScoreDto queryScore();
	public List<EtComment> queryComment(int start,int size,String objectId);
	public List<EtComment> queryCartoonComment(int start,int size,String objectId);
}
