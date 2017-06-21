package com.example.demo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * pdf操作Service
 * @author Administrator
 *
 */
public interface PdfExportService {

	/**
	 * 直接生成pdf
	 * //Step 1—Create a Document.  
     * Document document = new Document();  
     * //Step 2—Get a PdfWriter instance.  
     * PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "createSamplePDF.pdf"));  
     * //Step 3—Open the Document.  
     * document.open();  
     * //Step 4—Add content.  
     * document.add(new Paragraph("Hello World"));  
     * //Step 5—Close the Document.  
     * document.close();  
	 * 
	 */
	void download(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
