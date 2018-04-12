package com.guohuai.basic.component.oss;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.guohuai.basic.component.exception.GHException;
import com.guohuai.basic.component.ext.web.BaseController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/common/file", produces = "application/json")
public class OSSUploadController extends BaseController {
	
	// 上传
	@RequestMapping(value = "yup", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<FileD>> yupload(HttpServletRequest request, 
            HttpServletResponse response, @RequestParam("file") List<MultipartFile> file) {
			
		super.getLoginUser();
		
		if (file.isEmpty()) {
			throw GHException.getException("文件为空，上传失败！");
		}
		List<FileD> ds = new ArrayList<FileD>();
		try {
			for (MultipartFile mfile : file) {
				FileD fileD = new FileD();

				String filename = mfile.getOriginalFilename();
				
				String fileExte = filename.substring(filename.lastIndexOf(".") + 1 ); // 文件扩展名
		       
				fileD = OSSUploadUtil.uploadFile(mfile.getInputStream(), filename, fileExte);
				ds.add(fileD);
			}
		} catch (Throwable e) {
			log.error("文件上传失败！", e);
			throw GHException.getException("文件上传失败！");
		}

		return new ResponseEntity<List<FileD>>(ds, HttpStatus.OK);
	}
	
	// 下载链接
	@RequestMapping(value = "yupload", method = {RequestMethod.POST,RequestMethod.GET}, produces="text/plain")
	@ResponseBody
	public String yupload(@RequestParam(required = true) String url) {
		String ossPath=OSSUploadUtil.getIpaURl(url);
		log.info("yupload: osspath={}",ossPath);
		return ossPath;
	}
	
}
