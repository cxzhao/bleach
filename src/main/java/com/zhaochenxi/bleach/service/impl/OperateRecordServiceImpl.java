package com.zhaochenxi.bleach.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhaochenxi.bleach.dao.IOperateRecordDao;
import com.zhaochenxi.bleach.model.OperateRecord;
import com.zhaochenxi.bleach.service.IOperateRecordService;
import com.zhaochenxi.bleach.utils.ConstUtils;
/**
 * @ClassName: OperateRecordServiceImpl 
 * @Description:检查某一个用户在一段时间内是否对某条数据进行过操作
 * @author zhaochenxi
 * @date 2016年11月12日 下午3:08:50
 */
@Service
public class OperateRecordServiceImpl implements IOperateRecordService {

	@Autowired
	private IOperateRecordDao opDao;
	@Override
	public boolean save(OperateRecord operate) {
		if(operate==null){
			return false;
		}else{
		    return opDao.save(operate);
		}
	}
	
	/**
	 * 检查是否对某条数据有操作权限
	 */
	@Override
	public boolean checkOperate(String userId, String dataId, int type) {
		OperateRecord operate = opDao.queryByUserIdDataIdAndType(userId, type, dataId);
		if(operate==null){
			return true;
		}
		long currentTime = System.currentTimeMillis();
		if((currentTime-operate.getCreateTime())>ConstUtils.OPERATOR_TIME_LIMIT){
			return true;
		}
		return false;
	}

}
