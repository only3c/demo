package com.example.demo.controller;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.repository.FileStorageRepository;
import com.example.demo.service.FileUploadService;

@Controller
public class FileUploadController {

	//private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	@Value("${local.storage.root}")
	private String rootPath;
	// 通过Spring的autowired注解获取spring默认配置的request
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private FileStorageRepository fileStorageRepository;

	// 访问路径为：http://127.0.0.1:8080/file
	@RequestMapping("/file")
	public String file() {
		return "/file";
	}
	
	@RequestMapping("/mutifile")
	public String batchFile() {
		return "/mutifile";
	}

	/**
	 * 文件上传具体实现方法;
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {
		this.fileUploadService.upload(file);
		return "上传成功";
	}
	/**
	 * 批量文件上传具体实现方法;
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/batch/upload")
	public @ResponseBody String batchFileUpload(HttpServletRequest request) {
		//(MultipartHttpServletRequest)request
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
	    //MultipartFile file = null;
	    //BufferedOutputStream stream = null;
	    for (MultipartFile file :files) {
	        //file = files.get(i);
//	        if (!file.isEmpty()) {} else {
//                return "You failed to upload " + file.getOriginalFilename() + " because the file was empty.";
//	        }

            try {
//                byte[] bytes = file.getBytes();
//                stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
//                stream.write(bytes);
//                stream.close();
            this.fileUploadService.upload(file);
            } catch (Exception e) {
                //stream = null;
                return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
            }
	    }
	        return "上传成功";//upload successful
	}

	  //文件下载相关代码
	  @RequestMapping("/download/{id}")
	  public String downloadFile(@PathVariable("id") Long id, HttpServletResponse response) throws UnsupportedEncodingException{  
		  this.fileUploadService.download(id, response);
		  return "下载成功";
	  }
	  
	/***
	 * 读取上传文件中得所有文件并返回
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list() {
		//String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
		String filePath = rootPath;
		//final ModelMap modelMap = new ModelMap();
		ModelAndView mav = new ModelAndView("list");//src/main/resources/excel/
		//ModelAndView mav = new ModelAndView("defaultListDataExcelView",modelMap);
		File uploadDest = new File(filePath);
		String[] fileNames = uploadDest.list();
		for (int i = 0; i < fileNames.length; i++) {
			// 打印出文件名
			System.out.println(fileNames[i]);
		}
		
//		
//		// 指定下载文件名
//		modelMap.addAttribute("filename", "file.xls");
//
//		// 模板文件名
//		modelMap.addAttribute("templateUrl", "/excel/list.xlsx");
//
//		// 数据对象
//		modelMap.addAttribute("datas", fileNames);
		return mav;
	}
}