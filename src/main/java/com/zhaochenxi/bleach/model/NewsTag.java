package com.zhaochenxi.bleach.model;

import java.io.Serializable;

public class NewsTag implements Serializable{

	private static final long serialVersionUID = 2855504513475955365L;

	private String newsId;
	private String tagId;
	public String getNewsId() {
		return newsId;
	}
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	
}
