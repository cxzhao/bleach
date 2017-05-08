package com.zhaochenxi.bleach.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhaochenxi.bleach.dao.IArticleDao;
import com.zhaochenxi.bleach.dao.ICartoonDao;
import com.zhaochenxi.bleach.dao.IEtCommentDao;
import com.zhaochenxi.bleach.dao.IEtCommentReplyDao;
import com.zhaochenxi.bleach.dao.INewsDao;
import com.zhaochenxi.bleach.enums.CommentTypeEnum;
import com.zhaochenxi.bleach.enums.OperatorEnum;
import com.zhaochenxi.bleach.model.EtComment;
import com.zhaochenxi.bleach.model.EtCommentReply;
import com.zhaochenxi.bleach.model.OperateRecord;
import com.zhaochenxi.bleach.model.User;
import com.zhaochenxi.bleach.service.IEtCommentService;
import com.zhaochenxi.bleach.service.IOperateRecordService;
import com.zhaochenxi.bleach.service.IScoreService;
import com.zhaochenxi.bleach.service.IUserService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.IdCreater;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.Utils;

@Service
public class EtCommentServiceImpl implements IEtCommentService {

	@Autowired
	private IEtCommentDao etCommentDao;

	@Autowired
	private IEtCommentReplyDao etCommentReplyDao;

	@Autowired
	private IOperateRecordService opService;
	
	@Autowired
	private ICartoonDao cartoonDao;
	
	@Autowired
	private IArticleDao articleDao;
	
	@Autowired
	private INewsDao newsDao;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IScoreService scoreService;
	
	@Override
	public Result<EtComment> save(EtComment etComment) {
		
		if (etComment != null) {
			User user=userService.queryById(etComment.getUserId());
			if(user!=null){
				etComment.setName(user.getName());
			}else{
				return new Result<EtComment>(CodeEnum.USER_NOT_EXITS);
			}
			
			if(etComment.getType()==CommentTypeEnum.cartoon.getValue()){
				//计算动漫的评分
				float score=scoreService.caculateScore(etComment.getObjectId(), etComment.getScore());
				cartoonDao.updateScore(score, etComment.getObjectId());
			}
			if(etComment.getType()!=CommentTypeEnum.cartoon.getValue()){
				if(Utils.isEmptyOrNull(etComment.getContext())){
					return new Result<EtComment>(CodeEnum.SUCCESS, etComment);
				}
			}
			
			if (etCommentDao.save(etComment)) {				
				//评论数量加1
				updateCommentCount(etComment.getObjectId(),etComment.getType());
				return new Result<EtComment>(CodeEnum.SUCCESS, etComment);
			} else {
				return new Result<EtComment>(CodeEnum.FAILURE);
			}
		}else{
			return new Result<EtComment>(CodeEnum.FAILURE);
		}
		
	}
	
	/**
	 * @Title: updateCommentCount 
	 * @Description: 评论数+1 
	 * @param 
	 * @return void 
	 * @throws 
	 * @author zhaochenxi
	 */
	public void updateCommentCount(String objectId,int type){
		if(type==CommentTypeEnum.cartoon.getValue()){
			cartoonDao.updateComment(objectId);
		}else if(type==CommentTypeEnum.article.getValue()){
			articleDao.updateCommentCount(objectId);
		}else if(type==CommentTypeEnum.news.getValue()){
			newsDao.updateCommentCount(objectId);
		}
	}
	
	/**
	 * 
	 * @Title: decreaseCommentCount 
	 * @Description: 评论数减1 
	 * @param 
	 * @return void 
	 * @throws 
	 * @author zhaochenxi
	 */
	public void decreaseCommentCount(String objectId,int type){
		if(type==CommentTypeEnum.cartoon.getValue()){
			cartoonDao.decreaseComment(objectId);
		}else if(type==CommentTypeEnum.article.getValue()){
			articleDao.decreaseCommentCount(objectId);
		}else if(type==CommentTypeEnum.news.getValue()){
			newsDao.decreaseCommentCount(objectId);
		}
	}

	@Override
	public Result<EtCommentReply> reply(EtCommentReply etCommentReply) {
		
		if (etCommentReply != null) {
			//TODO 查询用户，待优化为in查询
			User user =userService.queryById(etCommentReply.getUserId());
			User objUser = userService.queryById(etCommentReply.getObjUserId());
			
			if(user==null||objUser==null){
				return new Result<EtCommentReply>(CodeEnum.FAILURE);
			}	
			
			etCommentReply.setUserName(user.getName());
			etCommentReply.setObjUserName(objUser.getName());
			
			if (etCommentReplyDao.save(etCommentReply)) {
				//更新回复数。
				etCommentDao.updateReply(etCommentReply.getObjId());
				//更新评论数
				EtComment comment = etCommentDao.queryCommentById(etCommentReply.getObjId());
				if(comment!=null){
					updateCommentCount(comment.getObjectId(),comment.getType());
				}
				return new Result<EtCommentReply>(CodeEnum.SUCCESS, etCommentReply);
			} else {
				return new Result<EtCommentReply>(CodeEnum.FAILURE);
			}
		}
		return new Result<EtCommentReply>(CodeEnum.FAILURE);
	}

	@Override
	public Result<Page<EtComment>> paginationQuery(int pageNumber, int pageSize, String objectId,int type) {		
		int start = (pageNumber - 1) * pageSize;
		List<EtComment> list = null;
		if(type==0){
			//查动漫评论不查空的
			list = etCommentDao.queryCartoonComment(start, pageSize, objectId);
		}else{
			list = etCommentDao.queryComment(start, pageSize, objectId);
		}
		

		Page<EtComment> page = new Page<EtComment>();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setList(list);
		if (list != null && list.size() < pageSize) {
			page.setLast(true);
		} else {
			page.setLast(false);
		}
		return new Result<Page<EtComment>>(CodeEnum.SUCCESS, page);
	}

	@Transactional
	@Override
	public Result<Object> deleteComment(String id, String userId) {
		if (Utils.isEmptyOrNull(id) || Utils.isEmptyOrNull(userId)) {
			return new Result<Object>(CodeEnum.PARAMETERSNULL);
		}
		EtComment comment = etCommentDao.queryCommentById(id);
		if(comment.getReply()!=0){
			return new Result<Object>(CodeEnum.CAN_NOT_DELETE);
		}
		//减掉对象的评论数
		decreaseCommentCount(comment.getObjectId(),comment.getType());
		
		if (etCommentDao.delete(id, userId)) {
			return new Result<Object>(CodeEnum.SUCCESS);
		} else {
			return new Result<Object>(CodeEnum.FAILURE);
		}
	}
	
	@Transactional
	@Override
	public Result<Object> deleteReply(String id,String objectId,String userId) {
		if (Utils.isEmptyOrNull(id) || Utils.isEmptyOrNull(userId)) {
			return new Result<Object>(CodeEnum.PARAMETERSNULL);
		}
		//减掉对象的回复数
		etCommentDao.decreaseReply(objectId);
		//减掉对象的评论数量
		EtComment comment = etCommentDao.queryCommentById(objectId);
		decreaseCommentCount(comment.getObjectId(),comment.getType());
		if (etCommentReplyDao.delete(id, userId)) {
			return new Result<Object>(CodeEnum.SUCCESS);
		} else {
			return new Result<Object>(CodeEnum.FAILURE);
		}
	}

	/**
	 * 不需要返回当前的点赞数
	 */
	@Override
	public Result<Map<String, Integer>> love(String id, String userId) {
		// 检查是否有操作权限
		boolean isOp = opService.checkOperate(userId, id, OperatorEnum.articleLove.getValue());
		if (isOp == false) {
			return new Result<Map<String, Integer>>(CodeEnum.OPERATOR_EXITS, null);
		}
		if(etCommentDao.updateAgree(id)){
			OperateRecord record = new OperateRecord();
			record.setUserId(userId);
			record.setId(IdCreater.createId17());
			record.setDataId(id);
			record.setType(OperatorEnum.articleLove.getValue());
			record.setCreateTime(System.currentTimeMillis());
			opService.save(record);
			return new Result<Map<String,Integer>>(CodeEnum.SUCCESS);
		}else{
			return new Result<Map<String,Integer>>(CodeEnum.SQLERROR);
		}
	}

}
