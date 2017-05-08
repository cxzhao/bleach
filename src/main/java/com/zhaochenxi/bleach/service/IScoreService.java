package com.zhaochenxi.bleach.service;

public interface IScoreService {

	/**
	 * @Title: caculateScore 
	 * @Description: 计算动漫的评分 
	 * @param 
	 * @return float 
	 * @throws 
	 * @author zhaochenxi
	 */
	public float caculateScore(String cartoonId,float score);
	
}
