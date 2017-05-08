package com.zhaochenxi.bleach.utils;

public enum CodeEnum {
	SUCCESS("000000", "操作成功"), 
	FAILURE("000001", "操作失败"),
	SQLERROR("000002","数据库层操作出错"),
	USERIDORPASSWDERROR("000003","用户名或密码错误"),
	USERNOTLOGIN("000004","您的账号尚未登录,请先登录。"),
	PARAMETERSNULL("000005","部分参数不可为空，请检查。"),
	FILETOOBIG("000006","上传文件过大。"),
	FILEUPLOADFAIL("000006","上传文件失败。"),
	FILECANNOTEMPTY("000007","上传文件不可为空。"),
	USER_EXITS("000008","用户已存在"),
	USER_NOT_EXITS("000009","用户不存在"),
	USER_PASSWORD_ERROR("000010","用户名或者密码错误"),
	USER_NOT_AGREE("000011","不同意用户协议"),
	USER_NOT_ADMIN("000012","没有管理员权限"),
	PARAM_ERROR("000013","参数错误"),
	CARTOON_NOT_EXITS("000014","参数错误"),
	RECORD_NOT_EXITS("000015","记录不存在"),
	OPERATOR_EXITS("000016","操作已经执行过"),
	CAN_NOT_DELETE("000017","不能删除该数据"),
	COMMENT_NOT_NULL("000018","评论内容不能为空"),
	CODE_ERROR("000019","验证码错误"),
	UNAUTHOR("000020","没有访问权限");
	
	private String code;
	private String message;

	// 构造方法
	private CodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
