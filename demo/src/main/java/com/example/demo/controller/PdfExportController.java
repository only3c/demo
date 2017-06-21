package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.PdfExportService;

/**
 * 
 * iText 导出pdf
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/pdf")
public class PdfExportController {
	
	@Autowired
	private PdfExportService pdfExportService;
	

      // 生成并下载pdf文档
	  @RequestMapping("/download")
	  public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		  pdfExportService.download(request, response);
	  }
	  
	  //压缩PDF到Zip
	  @RequestMapping("/zip")
	  public void zip(HttpServletRequest request, HttpServletResponse response) {
		  
	  }
}
