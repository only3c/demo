package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.Barcode128;

@Controller
@RequestMapping("/bar")
public class BarcodeController {
	
	public static final String URL = "http://www.baidu.com";

	@RequestMapping("/qr")
	public void createQRCode() {
		
		//Document document = new Document();
		Barcode128 barcode128 = new Barcode128();
		barcode128.setCode(URL);
		barcode128.setCodeType(Barcode128.CODE128);
		
	}
	
	@RequestMapping("/128")
	public void create128() {
		
	}
}
