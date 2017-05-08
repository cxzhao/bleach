package com.zhaochenxi.bleach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhaochenxi.bleach.model.Tag;
import com.zhaochenxi.bleach.service.ITagService;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;
@CrossOrigin
@RestController
@RequestMapping(value = "/tag")
public class TagController {

	@Autowired
	private ITagService tagService;
	
	@Cacheable(value="tagCache")
	@RequestMapping(value = "hotTag", method = RequestMethod.GET)
	public Result<Page<Tag>> queryHotTag(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
			@RequestParam(value = "type", defaultValue = "0") int type) {
		return tagService.queryHotTag(type, pageNumber, pageSize);
	}
}
