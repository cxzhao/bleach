package com.zhaochenxi.bleach.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Cartoon implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4258223203307130068L;
	private String id;
	private String name;
	private String englishName;
	private String introduction;
	private float score;
	private int status;
	private String country;
	private String language;
	private int commentCount;
	private String themeImage;
	private String years;
	private Date createTime;
	private String shortIntro;
	private int loveCount;
	private String keyword;//搜索关键字
	private int isEnd;
	private String author;
	
	private List<CartoonRole> roleList;
	private List<CartoonAkira> akiraList;
	private List<CartoonDirector> directorList;
//	private List<CartoonType> typeList;
	
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
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getThemeImage() {
		return themeImage;
	}
	public void setThemeImage(String themeImage) {
		this.themeImage = themeImage;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getShortIntro() {
		return shortIntro;
	}
	public void setShortIntro(String shortIntro) {
		this.shortIntro = shortIntro;
	}
	public int getLoveCount() {
		return loveCount;
	}
	public void setLoveCount(int loveCount) {
		this.loveCount = loveCount;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<CartoonRole> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<CartoonRole> roleList) {
		this.roleList = roleList;
	}
	public List<CartoonAkira> getAkiraList() {
		return akiraList;
	}
	public void setAkiraList(List<CartoonAkira> akiraList) {
		this.akiraList = akiraList;
	}
	public List<CartoonDirector> getDirectorList() {
		return directorList;
	}
	public void setDirectorList(List<CartoonDirector> directorList) {
		this.directorList = directorList;
	}
//	public List<CartoonType> getTypeList() {
//		return typeList;
//	}
//	public void setTypeList(List<CartoonType> typeList) {
//		this.typeList = typeList;
//	}
	public int getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(int isEnd) {
		this.isEnd = isEnd;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	

}
