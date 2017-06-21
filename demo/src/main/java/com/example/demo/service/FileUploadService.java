package com.example.demo.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

	void upload(MultipartFile file);
	
	void download(Long id,HttpServletResponse response);
}
