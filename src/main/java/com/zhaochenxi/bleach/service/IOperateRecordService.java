package com.zhaochenxi.bleach.service;

import com.zhaochenxi.bleach.model.OperateRecord;

public interface IOperateRecordService {
	public boolean save(OperateRecord operate);
	public boolean checkOperate(String userId,String dataId,int type);
}
