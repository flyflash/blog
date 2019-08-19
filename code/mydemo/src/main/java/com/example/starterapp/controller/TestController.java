package com.example.starterapp.controller;

import com.example.starterapp.commons.ServerResponse;
import com.example.starterapp.enums.ResponseCode;
import com.example.starterapp.exception.CustomException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/test")
	public ServerResponse<String> test(){
		log.info("请求成功");
		return ServerResponse.createBySuccessMessage("请求成功");
	}

	@PostMapping("/importSql")
	public ServerResponse<String> importSql(@RequestParam("file")MultipartFile file){

		if (file.isEmpty()){
			log.error("上传文件为空");
			throw new CustomException(ResponseCode.FILE_IS_NULL);
		}

		long size = file.getSize() / 1024; //单位kb
		if (size > 1024 || size < 10){
			throw new CustomException(ResponseCode.FILE_SIZE_NOT_SUITABLE);
		}
		try{
			BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
			if (bufferedImage == null){
				log.error("上传文件格式错误");
				throw new CustomException(ResponseCode.FILE_FORMAT_ERROR);
			}
			log.info("图片大小:[{}]高:[{}]宽:[{}]", size, bufferedImage.getHeight(), bufferedImage.getWidth());
		}catch (IOException e){}

		String filename = file.getOriginalFilename();
//		if (!filename.endsWith(".sql")){
//			log.error("上传文件格式错误");
//			return ServerResponse.createByErrorCodeMessage(ResponseCode.FILE_FORMAT_ERROR.getCode(),
//															ResponseCode.FILE_FORMAT_ERROR.getMsg());
//		}
		return ServerResponse.createBySuccessMessage(filename);
	}
}
