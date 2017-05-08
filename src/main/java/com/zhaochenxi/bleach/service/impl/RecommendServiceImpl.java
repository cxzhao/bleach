package com.zhaochenxi.bleach.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhaochenxi.bleach.dao.IRecommendDao;
import com.zhaochenxi.bleach.dao.IUserDao;
import com.zhaochenxi.bleach.enums.DataStatusEnum;
import com.zhaochenxi.bleach.enums.OperatorEnum;
import com.zhaochenxi.bleach.model.OperateRecord;
import com.zhaochenxi.bleach.model.Recommend;
import com.zhaochenxi.bleach.model.User;
import com.zhaochenxi.bleach.service.IOperateRecordService;
import com.zhaochenxi.bleach.service.IRecommendService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.ConstUtils;
import com.zhaochenxi.bleach.utils.IdCreater;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.TextCheckUtils;
import com.zhaochenxi.bleach.utils.Utils;

@Service
public class RecommendServiceImpl implements IRecommendService{

	public static final int ORDER_BY_TIME_DESC = 0;
	public static final int ORDER_BY_AGREE_DESC = 0;
	@Autowired
	private IRecommendDao recommendDao;
	
	@Autowired
	private IUserDao userDao;
	
	
	@Autowired
	private IOperateRecordService opService;
	
	@Override
	public Result<Object> recommend(String userId, String cartoonName, String reason) {
		User user = null;
		if(!Utils.isEmptyOrNull(userId)){
			user = userDao.queryById(userId);
		}
		String userName = null;
		if(user!=null){
			userName = user.getName();
		}else{
			userName = ConstUtils.DEFAULT_USERNAME;
		}
		DataStatusEnum status = TextCheckUtils.checkText(cartoonName);
		status = TextCheckUtils.checkText(reason);
		Recommend recommend = new Recommend();
		recommend.setId(IdCreater.createId17());
		recommend.setAgree(0);
		recommend.setCreateTime(new Date());
		recommend.setCartoonName(cartoonName);
		recommend.setReason(reason);
		recommend.setUserId(userId);
		recommend.setUserName(userName);
		recommend.setStatus(status.getValue());
		if(recommendDao.save(recommend)){
			return new Result<Object>(CodeEnum.SUCCESS);
		}else{
			return new Result<Object>(CodeEnum.SQLERROR);
		}
	}

	
	@Override
	@Transactional
	public Result<Map<String,Integer>> agree(String id,String userId) {
		//检查是否有操作权限
		boolean isOp=opService.checkOperate(userId, id, OperatorEnum.recommendAgree.getValue());
		if(isOp==false){
			return new Result<Map<String,Integer>>(CodeEnum.OPERATOR_EXITS,null);
		}		
		int agree=recommendDao.queryAgreeById(id)+1;
		Map<String,Integer> map = new HashMap<String,Integer>();			
		if(recommendDao.updateAgree(id, agree)){	
			map.put("agree", agree);
			OperateRecord record = new OperateRecord();
			record.setUserId(userId);
			record.setId(IdCreater.createId17());
			record.setDataId(id);
			record.setType(OperatorEnum.recommendAgree.getValue());
			record.setCreateTime(System.currentTimeMillis());
			opService.save(record);
			return new Result<Map<String,Integer>>(CodeEnum.SUCCESS,map);
		}else{
			map.put("agree", agree-1);
			return new Result<Map<String,Integer>>(CodeEnum.SQLERROR,map);
		}
		
		
	}


	@Override
	public Result<Page<Recommend>> paginationQuery(int pageNumber, int pageSize, int orderRule) {
		int rowCount = recommendDao.rowCountActive();
		int pageTotal = 0;
		if((rowCount%pageSize)==0){
			pageTotal=rowCount/pageSize;
		}else{
			pageTotal = rowCount/pageSize+1;
		}
		int start = (pageNumber-1)*pageSize;
		List<Recommend> list = null;
		if(orderRule==ORDER_BY_AGREE_DESC){
			list = recommendDao.queryOrderByAgree(start, pageSize);
		}else{
			list = recommendDao.queryOrderByTime(start, pageSize);
		}
		Page<Recommend> page = new Page<Recommend>();
		page.setList(list);
		page.setTotal(pageTotal);
		page.setPageSize(pageSize);
		page.setPageNumber(pageNumber);
		if(list!=null){
			if(list.size()<pageSize){
				page.setLast(true);
			}else{
				page.setLast(false);
			}
		}else{
			page.setLast(true);
		}
		return new Result<Page<Recommend>>(CodeEnum.SUCCESS,page);
	}


	@Override
	public Result<Object> check(String id, int status) {
		if(recommendDao.updateStatus(id, status)){
			return new Result<Object>(CodeEnum.SUCCESS);
		}else{
			return new Result<Object>(CodeEnum.FAILURE);
		}
	}


	@Override
	public Result<Recommend> detail(String id) {
		
		return null;
	}


	@Override
	public Result<Page<Recommend>> paginationAdminQuery(int pageNumber, int pageSize) {
		int rowCount = recommendDao.rowCount();
		int pageTotal = 0;
		if((rowCount%pageSize)==0){
			pageTotal=rowCount/pageSize;
		}else{
			pageTotal = rowCount/pageSize+1;
		}
		int start = (pageNumber-1)*pageSize;
		List<Recommend> list = recommendDao.queryAllOrderByTime(start, pageSize);
		Page<Recommend> page = new Page<Recommend>();
		page.setList(list);
		page.setTotal(pageTotal);
		page.setPageSize(pageSize);
		page.setPageNumber(pageNumber);
		if(list!=null){
			if(list.size()<pageSize){
				page.setLast(true);
			}else{
				page.setLast(false);
			}
		}else{
			page.setLast(true);
		}
		return new Result<Page<Recommend>>(CodeEnum.SUCCESS,page);
	}


	@Override
	public Result<Object> delete(String id) {
		if(recommendDao.delete(id)){
			return new Result<Object>(CodeEnum.SUCCESS);
		}else{
			return new Result<Object>(CodeEnum.FAILURE);
		}
	}

}
