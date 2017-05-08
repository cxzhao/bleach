package com.zhaochenxi.bleach.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.zhaochenxi.bleach.service.ICartoonService;
import com.zhaochenxi.bleach.service.INewsService;
import com.zhaochenxi.bleach.service.IUserService;
import com.zhaochenxi.bleach.utils.CodeEnum;
import com.zhaochenxi.bleach.utils.ConstUtils;
import com.zhaochenxi.bleach.utils.Result;
import com.zhaochenxi.bleach.utils.Utils;
@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {
	
	private long maxSize = 10000000;
	
	@Autowired
	private ICartoonService cartoonService;
	@Autowired
	private INewsService newsService;
	@Autowired
	private IUserService userService;
	/**
	 * @Title: uploadThemeImageFile 
	 * @Description: 上传动漫主题图片 
	 * @param 
	 * @return Result<String> 
	 * @throws 
	 * @author zhaochenxi
	 */
	@RequestMapping(value = "/uploadThemeImage",method=RequestMethod.POST)	
	public Result<String> uploadThemeImageFile(@RequestParam(value="uploadfile",required = true) MultipartFile file,HttpServletRequest request){
		if(!file.isEmpty()){
			long size = file.getSize();
			if(size > maxSize){
				return new Result<String>(CodeEnum.FILETOOBIG);
			}
			String fileName = file.getOriginalFilename(); 	
			String [] array = fileName.split("\\.");
			
			String filePath = ConstUtils.themeImagePath+fileName;
			File destFile = new File(filePath);
			String newFileName = System.currentTimeMillis()+"."+array[1];
			String newFilePath = ConstUtils.themeImagePath+newFileName;
			File newFile = new File(newFilePath);
			try{
				FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
				destFile.renameTo(newFile);
			}catch(Exception e){
				e.printStackTrace();
				return new Result<String>(CodeEnum.FILEUPLOADFAIL);
			}
			return new Result<String>(CodeEnum.SUCCESS,ConstUtils.themeImageHost+newFileName);
		}else{
			return new Result<String>(CodeEnum.FILECANNOTEMPTY);
		}
	}
	
	/**
	 * 修改上传图片
	 * @Title: updateThemeImageFile 
	 * @Description: TODO 
	 * @param 
	 * @return Result<String> 
	 * @throws 
	 * @author zhaochenxi
	 */
	@RequestMapping(value = "/updateThemeImage",method=RequestMethod.POST)	
	public Result<String> uppdateThemeImageFile(@RequestParam(value="uploadfile",required = true) MultipartFile file,String id,HttpServletRequest request){
		if(!file.isEmpty()){
			long size = file.getSize();
			if(size > maxSize){
				return new Result<String>(CodeEnum.FILETOOBIG);
			}
			String fileName = file.getOriginalFilename(); 	
			String [] array = fileName.split("\\.");
			
			String filePath = ConstUtils.themeImagePath+fileName;
			File destFile = new File(filePath);
			String newFileName = System.currentTimeMillis()+"."+array[1];
			String newFilePath = ConstUtils.themeImagePath+newFileName;
			File newFile = new File(newFilePath);
			try{
				FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
				//时间戳重命名
				destFile.renameTo(newFile);
				//更新数据库
				cartoonService.updateThemeImage(ConstUtils.themeImageHost+newFileName, id);
			}catch(Exception e){
				e.printStackTrace();
				return new Result<String>(CodeEnum.FILEUPLOADFAIL);
			}
			return new Result<String>(CodeEnum.SUCCESS,ConstUtils.themeImageHost+newFileName);
		}else{
			return new Result<String>(CodeEnum.FILECANNOTEMPTY);
		}
	}
	
	@RequestMapping(value = "/uploadNewsThemeImage",method=RequestMethod.POST)	
	public Result<String> uploadNewsThemeImageFile(@RequestParam(value="uploadfile",required = true) MultipartFile file,String id,HttpServletRequest request){
		if(!file.isEmpty()){
			long size = file.getSize();
			if(size > maxSize){
				return new Result<String>(CodeEnum.FILETOOBIG);
			}
			String fileName = file.getOriginalFilename(); 	
			String [] array = fileName.split("\\.");
			
			String filePath = ConstUtils.newsThemeImage+fileName;
			File destFile = new File(filePath);
			String newFileName = System.currentTimeMillis()+"."+array[1];
			String newFilePath = ConstUtils.newsThemeImage+newFileName;
			File newFile = new File(newFilePath);
			try{
				FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
				//时间戳重命名
				destFile.renameTo(newFile);
				if(!Utils.isEmptyOrNull(id)){
					//更新数据库
					newsService.updateImages(id,ConstUtils.newsThemeImageHost+newFileName);
				}			
			}catch(Exception e){
				e.printStackTrace();
				return new Result<String>(CodeEnum.FILEUPLOADFAIL);
			}
			return new Result<String>(CodeEnum.SUCCESS,ConstUtils.newsThemeImageHost+newFileName);
		}else{
			return new Result<String>(CodeEnum.FILECANNOTEMPTY);
		}
	}
	
	@RequestMapping(value = "/uploadHeadImage",method=RequestMethod.POST)	
	public Result<String> uploadHeadImage(@RequestParam(value="uploadfile",required = true) MultipartFile file,String id,HttpServletRequest request){
		if(!file.isEmpty()){
			long size = file.getSize();
			if(size > maxSize){
				return new Result<String>(CodeEnum.FILETOOBIG);
			}
			String fileName = file.getOriginalFilename(); 	
			String [] array = fileName.split("\\.");		
			String filePath = ConstUtils.headImage+fileName;
			File destFile = new File(filePath);
			String newFileName = System.currentTimeMillis()+"."+array[1];
			String newFilePath = ConstUtils.headImage+newFileName;
			File newFile = new File(newFilePath);
			try{
				FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
				//时间戳重命名
				destFile.renameTo(newFile);
				if(!Utils.isEmptyOrNull(id)){
					//更新数据库
					userService.updateHeadImage(id, ConstUtils.headImageHost+newFileName);
				}			
			}catch(Exception e){
				e.printStackTrace();
				return new Result<String>(CodeEnum.FILEUPLOADFAIL);
			}
			return new Result<String>(CodeEnum.SUCCESS,ConstUtils.headImageHost+newFileName);
		}else{
			return new Result<String>(CodeEnum.FILECANNOTEMPTY);
		}
	}
	
	@RequestMapping(value = "/uploadEditorImage",method=RequestMethod.POST)	
	public String uploadNewsImageFile(@RequestParam(value="wangEditorH5File",required = true) MultipartFile file,HttpServletRequest request){
		if(!file.isEmpty()){
			long size = file.getSize();
			if(size > maxSize){
				return CodeEnum.FAILURE.getCode();
			}

			String fileName = file.getOriginalFilename(); 	
			String [] array = fileName.split("\\.");
			
			String filePath = ConstUtils.editorImage+fileName;
			File destFile = new File(filePath);
			String newFileName = System.currentTimeMillis()+"."+array[1];
			String newFilePath = ConstUtils.editorImage+newFileName;
			File newFile = new File(newFilePath);
			try{
				FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
				//时间戳重命名
				destFile.renameTo(newFile);						
			}catch(Exception e){
				e.printStackTrace();
				return CodeEnum.FAILURE.getCode();
			}
			return ConstUtils.editorImageHost+newFileName;
		}else{
			return CodeEnum.FAILURE.getCode();
		}
	}
	
}
