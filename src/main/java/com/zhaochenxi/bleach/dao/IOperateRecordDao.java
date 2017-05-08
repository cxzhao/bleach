package com.zhaochenxi.bleach.dao;

import com.zhaochenxi.bleach.model.OperateRecord;

public interface IOperateRecordDao {
	public boolean save(OperateRecord record);
	public OperateRecord queryByUserIdAndType(String userId,int type);
	public OperateRecord queryByUserIdDataIdAndType(String userId,int type,String dataId);
}
