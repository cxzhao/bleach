package com.zhaochenxi.bleach.dao;

import com.zhaochenxi.bleach.model.VerificationCode;

public interface IVerificationCodeDao {

	public boolean save(VerificationCode code);
	public VerificationCode selectByEmail(String email,int type);
	public boolean delete(String id);
}
