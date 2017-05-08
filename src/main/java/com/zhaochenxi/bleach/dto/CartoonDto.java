package com.zhaochenxi.bleach.dto;

public class CartoonDto {
	private String id;
	private String name;
	private String themeImage;
	private String shortIntro;
	private float score;
	private int loveCount;
	private int commentCount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getThemeImage() {
		return themeImage;
	}
	public void setThemeImage(String themeImage) {
		this.themeImage = themeImage;
	}
	public String getShortIntro() {
		return shortIntro;
	}
	public void setShortIntro(String shortIntro) {
		this.shortIntro = shortIntro;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public float getLoveCount() {
		return loveCount;
	}
	public void setLoveCount(int loveCount) {
		this.loveCount = loveCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
		
}
