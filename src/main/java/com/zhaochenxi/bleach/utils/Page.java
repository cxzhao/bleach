package com.zhaochenxi.bleach.utils;

import java.util.List;
/**
 * @ClassName: Page 
 * @Description: 分页对象
 * @author zhaochenxi
 * @date 2016年11月1日 下午9:08:32
 */
public class Page<T> {
	private int pageNumber;
	private int pageSize;
	private int total;
	private boolean last;
	private List<T> list;
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean isLast) {
		this.last = isLast;
	}
	
}
