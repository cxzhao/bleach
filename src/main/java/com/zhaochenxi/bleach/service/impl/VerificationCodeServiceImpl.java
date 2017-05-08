package com.zhaochenxi.bleach.service.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zhaochenxi.bleach.dao.IVerificationCodeDao;
import com.zhaochenxi.bleach.enums.VerificationCodeEnum;
import com.zhaochenxi.bleach.mail.Mail;
import com.zhaochenxi.bleach.mail.MailService;
import com.zhaochenxi.bleach.model.VerificationCode;
import com.zhaochenxi.bleach.service.IVerificationCodeService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.IdCreater;
import com.zhaochenxi.bleach.utils.Result;

@Service
public class VerificationCodeServiceImpl implements IVerificationCodeService{

	public static final int FORGET_PASS_CODE_TIME = 300000;
	@Autowired
	private IVerificationCodeDao codeDao;
	
	@Override
	public Result<Object> sendForgetPassCode(String email) {
		Random random = new Random();
		int code = random.nextInt(800000)+100000;
		VerificationCode verificationCode = new VerificationCode();
		verificationCode.setId(IdCreater.createId17());
		verificationCode.setEmail(email);
		verificationCode.setCode(code);
		verificationCode.setType(VerificationCodeEnum.forgetPass.getValue());
		verificationCode.setCreateTime(System.currentTimeMillis());
		if(codeDao.save(verificationCode)){
			MailService mailService = new MailService();
			Mail mail = new Mail();
			mail.setAddress(email);
			mail.setContext("您的验证码为"+code+"验证码有效时间为5分钟，请及时操作");
			mail.setTitle("外星猫验证码");
			mailService.sendTextMail(mail);
			return new Result<Object>(CodeEnum.SUCCESS);
		}else{
			return new Result<Object>(CodeEnum.FAILURE);
		}
		
	}


	@Override
	public boolean checkForgetPassCode(String email,int code) {
		VerificationCode vercode = codeDao.selectByEmail(email, VerificationCodeEnum.forgetPass.getValue());
		if(vercode==null){
			return false;
		}
		if((System.currentTimeMillis()-vercode.getCreateTime())>=FORGET_PASS_CODE_TIME){
			return false;
		}
		if(vercode.getCode()==code){
			codeDao.delete(vercode.getId());
			return true;
		}
		return false;
	}
	


}
