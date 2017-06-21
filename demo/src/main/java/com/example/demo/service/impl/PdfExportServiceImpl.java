package com.example.demo.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.PdfExportService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfExportServiceImpl implements PdfExportService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {

	    // 告诉浏览器用什么软件可以打开此文件
	    response.setHeader("content-Type", "application/pdf");
	    // 下载文件的默认名称
	    response.setHeader("Content-Disposition", "attachment;filename=emp.pdf");

	    //页面大小  
	    Rectangle rect = new Rectangle(PageSize.B5.rotate());  
	    //页面背景色  
	    rect.setBackgroundColor(BaseColor.WHITE);  
	    
	    //页面大小 默认A4 PageSize.A4
	    Document document = new Document(rect);
	    PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
	    //版本
	    writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
	    
	    //设置密码
	    writer.setEncryption("admin".getBytes(), "123456".getBytes(), PdfWriter.ALLOW_SCREENREADERS, PdfWriter.ENCRYPTION_AES_128);
	    //文档属性  
	    document.addTitle("Title");  
	    document.addAuthor("Author");  
	    document.addSubject("Subject");  
	    document.addKeywords("Keywords");  
	    document.addCreator("Creator");  
	      
	    //页边空白  
	    document.setMargins(10, 20, 30, 40);
	    
	    document.open();
	    
	    //document.add(new Paragraph("Hello World"));  

	    
	    //处理数据
	    List<Employee> list = employeeRepository.findAll();
	    PdfPCell cell = new PdfPCell();
	    PdfPTable table = new PdfPTable(3);

	      //cell = new PdfPCell();
	      cell.setPhrase(new Paragraph("123"));
	      table.addCell(cell);
	      document.add(table);

	      cell = new PdfPCell();
	      cell.setPhrase(new Paragraph("name"));
	      table.addCell(cell);
	      document.add(table);

	      cell = new PdfPCell();
	      cell.setPhrase(new Paragraph(new String("序号".getBytes(),"utf-8")));//iso-8859-1
	      table.addCell(cell);
	      document.add(table);
	      
	    for (Employee emp : list) {
	      
		  cell = new PdfPCell();
		  table = new PdfPTable(3);
	      
	      cell = new PdfPCell();
	      cell.setPhrase(new Paragraph(emp.getId().toString()));
	      table.addCell(cell);
	      document.add(table);

	      cell = new PdfPCell();
	      cell.setPhrase(new Paragraph(emp.getName().toString()));
	      table.addCell(cell);
	      document.add(table);

	      cell = new PdfPCell();
	      cell.setPhrase(new Paragraph(emp.getAge().toString()));
	      table.addCell(cell);
	      document.add(table);
	    }
	    
	    //二维码 和 条形码
	    PdfContentByte cb = writer.getDirectContent(); 
	    String myString = "http://www.baidu.com";  
	    
	    Barcode128 code128 = new Barcode128();  
	    code128.setCode(myString.trim());  
	    code128.setCodeType(Barcode128.CODE128);  
	    Image code128Image = code128.createImageWithBarcode(cb, null, null);  
	    code128Image.setAbsolutePosition(10,700);  
	    code128Image.scalePercent(125);  
	    document.add(code128Image);  
	      
	    BarcodeQRCode qrcode = new BarcodeQRCode(myString.trim(), 1, 1, null);  
	    Image qrcodeImage = qrcode.getImage();  
	    qrcodeImage.setAbsolutePosition(10,600);  
	    qrcodeImage.scalePercent(200);  
	    document.add(qrcodeImage);  
	    
	    document.close();
	  
	}

}
