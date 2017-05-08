package com.zhaochenxi.bleach.service;

import java.util.Map;

import com.zhaochenxi.bleach.model.EtComment;
import com.zhaochenxi.bleach.model.EtCommentReply;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;

public interface IEtCommentService {
	public Result<EtComment> save(EtComment etComment);
	public Result<EtCommentReply> reply(EtCommentReply etCommentReply);
	public Result<Page<EtComment>> paginationQuery(int pageNumber,int pageSize,String objectId,int type);
	public Result<Object> deleteReply(String id,String objectId,String userId);
	public Result<Object> deleteComment(String id, String userId);
	public Result<Map<String, Integer>> love(String id,String userId);
}
