package com.zhaochenxi.bleach.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	public static <T> String toJsonString(T t){
		 ObjectMapper mapper = new ObjectMapper();
		 try {
			String json =  mapper.writeValueAsString(t);
			return json;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T stringToJson(String str,Class<T> obj){
		ObjectMapper mapper = new ObjectMapper();
		try {
			return (T) mapper.readValue(str, obj);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	 public static void main(String[] args){
		 @SuppressWarnings({ "rawtypes", "unchecked" })
		Result<String> result1 = new Result(CodeEnum.SUCCESS,null);
		String str = toJsonString(result1);
		System.out.println(str);
		@SuppressWarnings("unchecked")
		Result<Object> result2 = stringToJson(str,Result.class);
		System.out.println(result2.getMessage());
	 }
	 
	 
}
