package com.example.demo.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.FileStorage;
import com.example.demo.repository.FileStorageRepository;
import com.example.demo.service.FileUploadService;

@Service
@Transactional
public class FileUploadServiceImpl implements FileUploadService{
	private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

	
	@Value("${local.storage.root}")
	private String rootPath;
	@Autowired
	private FileStorageRepository fileStorageRepository;

	@Override
	public void upload(MultipartFile file) {
//		if (!file.isEmpty()) {} else {
//			//return "上传失败，因为文件是空的.";
//			throw new RuntimeException("上传失败，因为文件是空的.");
//		}
		try {
			/*
			 * 这段代码执行完毕之后，图片上传到了工程的跟路径； 大家自己扩散下思维，如果我们想把图片上传到 d:/files大家是否能实现呢？ 等等;
			 * 这里只是简单一个例子,请自行参考，融入到实际中可能需要大家自己做一些思考，比如： 1、文件路径； 2、文件名； 3、文件格式; 4、文件大小的限制;
			 */
			//File fileSourcePath = new File(rootPath);
			final String no = UUID.randomUUID().toString();
			String fileSourcePath = rootPath;
			
			// 获取文件名
			String fileName = file.getOriginalFilename();
			logger.info("上传的文件名为：" + fileName);
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			logger.info("上传的后缀名为：" + suffixName);
			logger.info("上传的size为：" + file.getSize());
			
//			File fileSource = new File(fileSourcePath, file.getOriginalFilename());
			File fileSource = new File(fileSourcePath, no+suffixName);

			
			if (!fileSource.getParentFile().exists()) {
				fileSource.getParentFile().mkdirs();
			}
			file.transferTo(fileSource);
			
			// BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new
			// File(file.getOriginalFilename())));
			// out.write(file.getBytes());
			// out.flush();
			// out.close();
			
			// 存储文件信息
			FileStorage storage = new FileStorage();
			storage.setFileName(fileName.toString());
			storage.setNo(no);
			storage.setFileSize(file.getSize());
			storage.setSuffixName(suffixName);
			this.fileStorageRepository.save(storage);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//return "上传失败," + e.getMessage();
			throw new RuntimeException("上传失败," + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			//return "上传失败," + e.getMessage();
			throw new RuntimeException("上传失败," + e.getMessage());
		}
		//return "上传成功";
	
	}

	@Override
	public void download(Long id, HttpServletResponse response){
//	    String fileName = "FileUploadTests.java";
//	    if (fileName != null) {}

	      //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
	      //String realPath = request.getServletContext().getRealPath(
	      //    "//WEB-INF//");
	      //String realPath = rootPath;
	      FileStorage fileStorage = fileStorageRepository.findOne(id);
	      if(fileStorage==null) {
	    	  throw new RuntimeException("文件不存在");
	      }
	      File file = new File(rootPath, fileStorage.getNo()+fileStorage.getSuffixName());
	      if (file.exists()) {
	        response.setContentType("application/force-download");// 设置强制下载不打开
	        //response.addHeader("content-type","application/x-msdownload");
	        try {
				response.addHeader("Content-Disposition",
				    "attachment;fileName=" + new String(fileStorage.getFileName().getBytes(),"iso-8859-1"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
				throw new RuntimeException("字符集异常");
			}// 设置文件名
	        byte[] buffer = new byte[1024];
	        FileInputStream fis = null;
	        BufferedInputStream bis = null;
	        try {
	          fis = new FileInputStream(file);	
	          bis = new BufferedInputStream(fis);
	          OutputStream os = response.getOutputStream();
	          //PrintWriter pWriter = response.getWriter();
	          int i = bis.read(buffer);
	          while (i != -1) {
	            os.write(buffer, 0, i);
	            i = bis.read(buffer);
	          }
	          //os.flush();//解决 getOutputStream() has already been called for this response 问题
	          os.close();
	          System.out.println("success");
	        } catch (Exception e) {
	          e.printStackTrace();
	        } finally {
	          if (bis != null) {
	            try {
	              bis.close();
	            } catch (IOException e) {
	              e.printStackTrace();
	            }
	          }
	          if (fis != null) {
	            try {
	              fis.close();
	            } catch (IOException e) {
	              e.printStackTrace();
	            }
	          }
	        }
	      }
	  }
}
