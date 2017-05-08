package com.zhaochenxi.bleach.service;

import com.zhaochenxi.bleach.dto.CartoonDto;
import com.zhaochenxi.bleach.model.Cartoon;
import com.zhaochenxi.bleach.utils.Page;
import com.zhaochenxi.bleach.utils.Result;

public interface ICartoonService extends IBaseService<Cartoon>{
	@SuppressWarnings("rawtypes")
	public Result add(Cartoon cartoon,String type,String role,String director,String akira);
	@SuppressWarnings("rawtypes")
	public Result updateCartoon(Cartoon cartoon,String type,String role,String director,String akira);	
	@SuppressWarnings("rawtypes")
	public Result deleteCartoon(String id);
	public Result<Cartoon> queryCartoonDeatail(String id);
	public Result<Page<CartoonDto>> paginationQuery(int pageNumber,int pageSize,String type,String keyword,int isEnd,String area);
	public Result<Object> updateLove(String cartoonId,String userId);
	public Result<Object> adminUpdateLove(int loveCount,String cartoonId);
	public Result<Object> adminUpdateScore(float score,String cartoonId);
	public boolean updateThemeImage(String path,String id);
}
