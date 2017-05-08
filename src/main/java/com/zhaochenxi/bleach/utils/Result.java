package com.zhaochenxi.bleach.utils;

public class Result<T> {
	private String code;
	private String message;
	private T data;
	
	public Result(CodeEnum code, T data){
		this.setCode(code.getCode());
		this.setMessage(code.getMessage());
		this.data=data;
	}
	
	public Result(CodeEnum code){
		this.setCode(code.getCode());
		this.setMessage(code.getMessage());
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
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
