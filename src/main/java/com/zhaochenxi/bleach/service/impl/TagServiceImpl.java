package com.zhaochenxi.bleach.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhaochenxi.bleach.dao.ITagDao;
import com.zhaochenxi.bleach.model.Tag;
import com.zhaochenxi.bleach.service.ITagService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;
@Service
public class TagServiceImpl implements ITagService{

	@Autowired
	private ITagDao tagDao;
	
	@Override
	public List<Tag> queryTag(List<String> taglist) {
		return null;
	}

	/**
	 * 查询热门标签
	 */
	@Override
	public Result<Page<Tag>> queryHotTag(int type, int pageNumber, int pageSize) {
		if(pageNumber<1||pageSize<0){
			return new Result<Page<Tag>>(CodeEnum.PARAM_ERROR);
		}
		int start = (pageNumber-1)*pageSize;
		List<Tag> tagList=tagDao.queryOrderDescByType(type, start, pageSize);
		Page<Tag> page = new Page<Tag>();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setList(tagList);
		return new Result<Page<Tag>>(CodeEnum.SUCCESS, page);
	}
	
}
