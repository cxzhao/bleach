package com.zhaochenxi.bleach.model;

import java.io.Serializable;

public class ArticleTag implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9085343606109198377L;
	
	private String tagId;
	private String articleId;
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	
	
}
