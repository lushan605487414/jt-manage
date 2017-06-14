package com.jt.manage.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.PropertyService;

@Controller
public class PicUploadController {
	
	@Autowired
	private PropertyService propertyService;
	
	//文件上传,uploadFile名称初始化时定义，必须相同
	//通用文件上传，方便集成到其它项目
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult upload(MultipartFile uploadFile){
		PicUploadResult result = new PicUploadResult();
		
		//1.获取文件名称和扩展名
		String fileName = uploadFile.getOriginalFilename();
		String extName = fileName.substring(fileName.lastIndexOf("."));
		
		//2.判断文件后缀类型，正则表达式 abc.jpg,abc.png
		if(!fileName.matches("^(?i).*?\\.(jpg|png|bmp|gif)$")){
			result.setError(1);
		}else{
			//3.判断是否为木马文件，从包装的上传的文件获取流，强制转换成图片对象
			try{
				BufferedImage image = ImageIO.read(uploadFile.getInputStream());
				result.setHeight(""+image.getHeight());
				result.setWidth(""+image.getWidth());
				
				//4.生成两个路径：
				//绝对路径，图片保存的路径		c:/jt-upload/images/2017/06/12/239847293874.jpg
				//新文件名称：当前毫秒数+3位随机数与
				String newFileName = ""+System.currentTimeMillis()+RandomUtils.
						nextInt(100, 999)+extName;
				String _dir = "/images/"+new SimpleDateFormat("yyyy/MM/dd").
						format(new Date())+"/";
				String path = propertyService.REPOSITORY_PATH+_dir;
				//相对路径，图片网上访问路径		http://image.jt.com/images/2017/06/12/239847293874.jpg
				String url = propertyService.IMAGE_BASE_URL+_dir+newFileName;
				result.setUrl(url);
				
				//5.如果目录不存在就生成
				File dir = new File(path);
				if(!dir.exists()){	//目录不存在
					dir.mkdirs();	//创建多级目录
				}
				
				//6.保存文件
				uploadFile.transferTo(new File(path+newFileName));
			}catch(IOException e){
				result.setError(1);	//不能抛异常，必须设置出错
				e.printStackTrace();
			}
		}
		return result;
	}
		
	
}
