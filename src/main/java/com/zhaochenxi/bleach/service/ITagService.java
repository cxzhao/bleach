package com.zhaochenxi.bleach.service;

import java.util.List;

import com.zhaochenxi.bleach.model.Tag;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;

public interface ITagService {
	public List<Tag> queryTag(List<String> taglist);
	public Result<Page<Tag>> queryHotTag(int type,int pageNumber,int pageSize);
}
