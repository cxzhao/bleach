package com.zhaochenxi.bleach.service;

import com.zhaochenxi.bleach.utils.Result;

public interface IVerificationCodeService {

	public Result<Object> sendForgetPassCode(String email);
	public boolean checkForgetPassCode(String email,int code);
}
