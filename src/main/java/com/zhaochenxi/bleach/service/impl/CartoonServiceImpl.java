package com.zhaochenxi.bleach.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhaochenxi.bleach.dao.ICartoonAkiraDao;
import com.zhaochenxi.bleach.dao.ICartoonDao;
import com.zhaochenxi.bleach.dao.ICartoonDirectorDao;
import com.zhaochenxi.bleach.dao.ICartoonRoleDao;
import com.zhaochenxi.bleach.dao.ICartoonTypeRelationDao;
import com.zhaochenxi.bleach.dto.CartoonDto;
import com.zhaochenxi.bleach.enums.OperatorEnum;
import com.zhaochenxi.bleach.model.Cartoon;
import com.zhaochenxi.bleach.model.CartoonAkira;
import com.zhaochenxi.bleach.model.CartoonDirector;
import com.zhaochenxi.bleach.model.CartoonRole;
import com.zhaochenxi.bleach.model.CartoonTypeRelation;
import com.zhaochenxi.bleach.model.OperateRecord;
import com.zhaochenxi.bleach.service.ICartoonService;
import com.zhaochenxi.bleach.service.IOperateRecordService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.IdCreater;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.Utils;
@Service
public class CartoonServiceImpl implements ICartoonService{

	@Autowired
	private ICartoonDao cartoonDao;
	
	@Autowired
	private ICartoonTypeRelationDao cartoonTypeRelationDao;
	
	@Autowired
	private ICartoonAkiraDao cartoonAkiraDao;
	
	@Autowired
	private ICartoonRoleDao cartoonRoleDao;
	
	@Autowired
	private ICartoonDirectorDao cartoonDirectorDao;
	
	@Autowired
	private IOperateRecordService opService;
	
	@Override
	public boolean save(Cartoon cartoon) {
		return cartoonDao.save(cartoon);
	}

	@Override
	public boolean delete(Cartoon cartoon) {		
		return cartoonDao.delete(cartoon);
	}

	@Override
	public boolean update(Cartoon cartoon) {
		return cartoonDao.update(cartoon);
	}

	@Override
	public List<Cartoon> queryAll() {
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@Transactional
	public Result deleteCartoon(String id){
		Cartoon cartoon = new Cartoon();
		cartoon.setId(id);
		if(delete(cartoon)){
			CartoonTypeRelation cartoonTypeRelation = new CartoonTypeRelation();
			cartoonTypeRelation.setCartoonId(id);
			cartoonTypeRelationDao.delete(id);
			//1.查询关联的Type,然后删除掉，2.删除关联的声优，角色，导演
			cartoonAkiraDao.delete(id);
			cartoonRoleDao.delete(id);
			cartoonDirectorDao.delete(id);			
		}else{
			return new Result(CodeEnum.SQLERROR);
		}
	    return new Result(CodeEnum.SUCCESS);
	}
	
	@SuppressWarnings("rawtypes")
	@Transactional
	public Result add(Cartoon cartoon,String type,String role,String director,String akira) {
		cartoon.setId(IdCreater.createId());
		if(!cartoonDao.save(cartoon)){
			return new Result(CodeEnum.SQLERROR);
		}
		if(!Utils.isEmptyOrNull(role)){
			String []  array = role.split("\\,");
			List<CartoonRole> list = new ArrayList<CartoonRole>();
			for(String s:array){
				CartoonRole cartoonRole = new CartoonRole();
				cartoonRole.setCartoonId(cartoon.getId());
				cartoonRole.setRoleName(s);
				list.add(cartoonRole);
			}
			
			if(!cartoonRoleDao.saveBatch(list)){
				return new Result(CodeEnum.SQLERROR);
			}
		}
		if(!Utils.isEmptyOrNull(type)){
			String []  array = type.split("\\,");
			List<CartoonTypeRelation> list = new ArrayList<CartoonTypeRelation>();
			for(String s:array){
				CartoonTypeRelation cartoonType = new CartoonTypeRelation();
				cartoonType.setCartoonId(cartoon.getId());
				cartoonType.setTypeId(s);
				list.add(cartoonType);
			}
			if(!cartoonTypeRelationDao.saveBatch(list)){
				return new Result(CodeEnum.SQLERROR);
			}
		}
		
		if(!Utils.isEmptyOrNull(director)){
			String []  array = director.split("\\,");
			List<CartoonDirector> list = new ArrayList<CartoonDirector>();
			for(String s:array){
				CartoonDirector c = new CartoonDirector();
				c.setCartoonId(cartoon.getId());
				c.setDirectorName(s);
				list.add(c);
			}
			if(!cartoonDirectorDao.saveBatch(list)){
				return new Result(CodeEnum.SQLERROR);
			}
		}

		if(!Utils.isEmptyOrNull(akira)){
			String []  array = akira.split("\\,");
			List<CartoonAkira> list = new ArrayList<CartoonAkira>();
			for(String s:array){
				CartoonAkira c = new CartoonAkira();
				c.setCartoonId(cartoon.getId());
				c.setAkiraName(s);
				list.add(c);
			}
			if(!cartoonAkiraDao.saveBatch(list)){
				return new Result(CodeEnum.SQLERROR);
			}
		}
		return new Result(CodeEnum.SUCCESS);
	}
	
	@SuppressWarnings("rawtypes")
	@Transactional
	public Result updateCartoon(Cartoon cartoon,String type,String role,String director,String akira){		
		if(cartoonDao.update(cartoon)){
			if(!Utils.isEmptyOrNull(role)){
				String []  array = role.split("\\,");
				List<CartoonRole> list = new ArrayList<CartoonRole>();
				for(String s:array){
					CartoonRole cartoonRole = new CartoonRole();
					cartoonRole.setCartoonId(cartoon.getId());
					cartoonRole.setRoleName(s);
					list.add(cartoonRole);
				}
				cartoonRoleDao.delete(cartoon.getId());
				if(!cartoonRoleDao.saveBatch(list)){
					return new Result(CodeEnum.SQLERROR);
				}
				
			}
			if(!Utils.isEmptyOrNull(type)){
				String []  array = type.split("\\,");
				List<CartoonTypeRelation> list = new ArrayList<CartoonTypeRelation>();
				for(String s:array){
					CartoonTypeRelation cartoonType = new CartoonTypeRelation();
					cartoonType.setCartoonId(cartoon.getId());
					cartoonType.setTypeId(s);
					list.add(cartoonType);
				}
				cartoonTypeRelationDao.delete(cartoon.getId());
				if(!cartoonTypeRelationDao.saveBatch(list)){
					return new Result(CodeEnum.SQLERROR);
				}
				
			}
			
			if(!Utils.isEmptyOrNull(director)){
				String []  array = director.split("\\,");
				List<CartoonDirector> list = new ArrayList<CartoonDirector>();
				for(String s:array){
					CartoonDirector c = new CartoonDirector();
					c.setCartoonId(cartoon.getId());
					c.setDirectorName(s);
					list.add(c);
				}
				cartoonDirectorDao.delete(cartoon.getId());
				if(!cartoonDirectorDao.saveBatch(list)){
					return new Result(CodeEnum.SQLERROR);
				}
				
			}

			if(!Utils.isEmptyOrNull(akira)){
				String []  array = akira.split("\\,");
				List<CartoonAkira> list = new ArrayList<CartoonAkira>();
				for(String s:array){
					CartoonAkira c = new CartoonAkira();
					c.setCartoonId(cartoon.getId());
					c.setAkiraName(s);
					list.add(c);
				}
				cartoonAkiraDao.delete(cartoon.getId());
				if(!cartoonAkiraDao.saveBatch(list)){
					return new Result(CodeEnum.SQLERROR);
				}			
			}
		}else{
			return new Result(CodeEnum.SQLERROR);
		}
		return new Result(CodeEnum.SUCCESS);
	}
	/**
	 * 查询动漫详情
	 */
	public Result<Cartoon> queryCartoonDeatail(String id){
		Cartoon cartoon=cartoonDao.queryDetailById(id);
		return new Result<Cartoon>(CodeEnum.SUCCESS,cartoon);
	}
	
	/**
	 * @Title: paginationQuery 
	 * @Description: 分页查询 
	 * @param 
	 * @return Result<Page<Cartoon>> 
	 * @throws 
	 * @author zhaochenxi
	 */
	@Override
	public Result<Page<CartoonDto>>  paginationQuery(int pageNumber,int pageSize,String type,String keyword,int isEnd,String area){
		
		int start = (pageNumber-1)*pageSize;
		List<Cartoon> list = null;
		//根据type和更新状态查询
		if(!Utils.isEmptyOrNull(type) && isEnd!=-1 && Utils.isEmptyOrNull(area)){
			list = cartoonDao.queryByTypeAndIsEnd(type, isEnd, start, pageSize);
		}
		//根据类型，地区查询
		if(!Utils.isEmptyOrNull(type) && isEnd==-1 && !Utils.isEmptyOrNull(area)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("start", start);
			map.put("size", pageSize);
			map.put("typeId", type);
			String [] array = area.split("\\,");
			List<String> areaList = new ArrayList<String>();
			for(String s:array){
				areaList.add(s);
			}
			map.put("arealist", areaList);
			list = cartoonDao.queryByTypeAndArea(map);
		}
		//根据类型，地区，更新状态查询
		if(!Utils.isEmptyOrNull(type) && isEnd!=-1 && !Utils.isEmptyOrNull(area)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("isEnd", isEnd);
			map.put("start", start);
			map.put("size", pageSize);
			map.put("typeId", type);
			String [] array = area.split("\\,");
			List<String> areaList = new ArrayList<String>();
			for(String s:array){
				areaList.add(s);
			}
			map.put("arealist", areaList);
			list = cartoonDao.queryByTypeAndIsEndAndArea(map);
		}

		if(!Utils.isEmptyOrNull(type) && isEnd==-1 && Utils.isEmptyOrNull(area)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("start", start);
			map.put("size", pageSize);
			
			String [] array = type.split("\\,");
			List<String> typeList = new ArrayList<String>();
			for(String s:array){
				typeList.add(s);
			}
			map.put("list", typeList);
			list = cartoonDao.queryByType(map);
		}
		//暂时没有keyword查询	
//		if(Utils.isEmptyOrNull(type)&&!Utils.isEmptyOrNull(keyword)){
//			list=cartoonDao.queryByKeyword(keyword.trim(), start, pageSize);
//		}
		
		//根据地区查询查询
		if(Utils.isEmptyOrNull(type) && isEnd==-1 && !Utils.isEmptyOrNull(area)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("start", start);
			map.put("size", pageSize);
			String [] array = area.split("\\,");
			List<String> areaList = new ArrayList<String>();
			for(String s:array){
				areaList.add(s);
			}
			map.put("arealist", areaList);
			list = cartoonDao.queryByArea(map);
		}
		//根据更新状态查询
		if(Utils.isEmptyOrNull(type) && isEnd!=-1 && Utils.isEmptyOrNull(area)){
			list = cartoonDao.queryByIsEnd(isEnd, start, pageSize);
		}
		//根据更新状态和地区查询
		if(Utils.isEmptyOrNull(type) && isEnd!=-1 && !Utils.isEmptyOrNull(area)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("start", start);
			map.put("size", pageSize);
			map.put("isEnd", isEnd);
			String [] array = area.split("\\,");
			List<String> areaList = new ArrayList<String>();
			for(String s:array){
				areaList.add(s);
			}
			map.put("arealist", areaList);
			list = cartoonDao.queryByAreaAndIsEnd(map);
		}
		
		//默认查询
		if(Utils.isEmptyOrNull(type)&&isEnd==-1&&Utils.isEmptyOrNull(area)&&Utils.isEmptyOrNull(keyword)){
			list = cartoonDao.queryPageList(start, pageSize);
		}
		
		List<CartoonDto> cartoonList = new ArrayList<CartoonDto>();
		
		Page<CartoonDto> page = new Page<CartoonDto>();
		if(list!=null){
			for(Cartoon cartoon:list){
				CartoonDto c = new CartoonDto();
				c.setId(cartoon.getId());
				c.setCommentCount(cartoon.getCommentCount());
				c.setLoveCount(cartoon.getLoveCount());
				c.setName(cartoon.getName());
				c.setShortIntro(cartoon.getShortIntro());
				c.setScore(cartoon.getScore());
				c.setThemeImage(cartoon.getThemeImage());
				cartoonList.add(c);
			}
			page.setList(cartoonList);
			page.setPageNumber(pageNumber);
			page.setPageSize(pageSize);
			if(cartoonList.size()<pageSize){
				page.setLast(true);
			}else{
				page.setLast(false);
			}
			return new Result<Page<CartoonDto>>(CodeEnum.SUCCESS,page);
		}else{
			page.setLast(true);
			return new Result<Page<CartoonDto>>(CodeEnum.SUCCESS,page);
		}				
	}

	@Override
	@Transactional
	public Result<Object> updateLove(String cartoonId,String userId) {
		boolean isOp=opService.checkOperate(userId, cartoonId, OperatorEnum.cartoonLove.getValue());
		if(isOp==false){
			return new Result<Object>(CodeEnum.OPERATOR_EXITS,null);
		}
		
		Cartoon cartoon = cartoonDao.queryLoveById(cartoonId);
		if(cartoon!=null){
			int loveCount = cartoon.getLoveCount()+1;
			if(cartoonDao.updateLove(loveCount, cartoonId)){
				Map<String,Integer> map = new HashMap<String,Integer>();
				map.put("love", loveCount);
				OperateRecord record = new OperateRecord();
				record.setUserId(userId);
				record.setId(IdCreater.createId17());
				record.setDataId(cartoonId);
				record.setType(OperatorEnum.cartoonLove.getValue());
				record.setCreateTime(System.currentTimeMillis());
				opService.save(record);
				return new Result<Object> (CodeEnum.SUCCESS,map);				
			}else{
				return new Result<Object> (CodeEnum.SQLERROR);
			}
		}else{
			return new Result<Object> (CodeEnum.CARTOON_NOT_EXITS);
		}
	}

	@Override
	public Result<Object> adminUpdateLove(int loveCount, String cartoonId) {
		Cartoon cartoon = cartoonDao.queryLoveById(cartoonId);
		if(cartoon!=null){
			if(cartoonDao.updateLove(loveCount, cartoonId)){
				Map<String,Integer> map = new HashMap<String,Integer>();
				map.put("love", loveCount);
				return new Result<Object> (CodeEnum.SUCCESS,map);				
			}else{
				return new Result<Object> (CodeEnum.SQLERROR);
			}
		}else{
			return new Result<Object> (CodeEnum.CARTOON_NOT_EXITS);
		}
	}

	@Override
	public Result<Object> adminUpdateScore(float score, String cartoonId) {
		Cartoon cartoon = cartoonDao.queryScoreById(cartoonId);
		if(cartoon!=null){		
			if(cartoonDao.updateScore(score, cartoonId)){
				return new Result<Object> (CodeEnum.SUCCESS);				
			}else{
				return new Result<Object> (CodeEnum.SQLERROR);
			}
		}else{
			return new Result<Object> (CodeEnum.CARTOON_NOT_EXITS);
		}
	}

	@Override
	public boolean updateThemeImage(String path, String id) {
		return cartoonDao.updateThemeImage(path, id);
	}
	
	
}
