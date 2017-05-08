package com.zhaochenxi.bleach.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhaochenxi.bleach.dao.ICartoonDao;
import com.zhaochenxi.bleach.dao.IEtCommentDao;
import com.zhaochenxi.bleach.dto.CommentScoreDto;
import com.zhaochenxi.bleach.model.Cartoon;
import com.zhaochenxi.bleach.service.IScoreService;

/**
 * @ClassName: ScoreServiceImpl 
 * @Description: 计算评分
 * @author zhaochenxi
 * @date 2016年12月1日 下午10:39:24
 */
@Service
public class ScoreServiceImpl implements IScoreService{

	@Autowired
	private ICartoonDao cartoonDao; 
	
	@Autowired
 	private IEtCommentDao etCommentDao; 
	
	/**
	 * IMDB排名评分算法
	 *(v ÷ (v+m)) × R + (m ÷ (v+m)) × C 
	 *R:平均分
	 *v:投票人数
	 *m:计算的最小票数=1
	 *C:目前所有动漫的平均分
	 */
	@Override
	public float caculateScore(String cartoonId, float score) {
		Cartoon cartoon = cartoonDao.queryById(cartoonId);
		CommentScoreDto commentScore = etCommentDao.queryScore();
		if(commentScore==null){
			commentScore = new CommentScoreDto();
			commentScore.setCount(1);
			commentScore.setSumScore(cartoon.getScore());
		}else{
			commentScore.setCount(commentScore.getCount()+1);
		}
		int V = commentScore.getCount();
		float M = 100;
		float R = commentScore.getSumScore()/V;
		float C = 7f;
		
		return (V/(V+M))*R + (M/(V+M))*C;
	}

}
