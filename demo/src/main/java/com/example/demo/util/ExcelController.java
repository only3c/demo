//package com.example.demo.util;
//
//import java.io.ByteArrayInputStream;  
//import java.io.ByteArrayOutputStream;  
//import java.io.IOException;  
//import java.text.SimpleDateFormat;  
//import java.util.Date;  
//import java.util.HashMap;  
//import java.util.List;  
//import java.util.Map;  
//import java.util.UUID;  
//  
//import org.apache.poi.hssf.usermodel.HSSFCell;  
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;  
//import org.apache.poi.hssf.usermodel.HSSFDataFormat;  
//import org.apache.poi.hssf.usermodel.HSSFFont;  
//import org.apache.poi.hssf.usermodel.HSSFRow;  
//import org.apache.poi.hssf.usermodel.HSSFSheet;  
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
//import org.springframework.beans.factory.annotation.Autowired;  
//import org.springframework.web.bind.annotation.RequestMapping;  
//import org.springframework.web.bind.annotation.RestController;  
//  
////import com.microsoft.azure.storage.blob.CloudBlobContainer;  
////import com.microsoft.azure.storage.blob.CloudBlockBlob;  
////import com.xiaofangtech.sunt.bean.StatisticsInfo;  
////import com.xiaofangtech.sunt.repository.StatisticsRepository;  
////import com.xiaofangtech.sunt.storage.BlobHelper;  
////import com.xiaofangtech.sunt.storage.StorageConfig;  
////import com.xiaofangtech.sunt.utils.ResultMsg;  
////import com.xiaofangtech.sunt.utils.ResultStatusCode;  
//  
//  
//@RestController  
//@RequestMapping("excel")  
//public class ExcelController {  
//      
////    @Autowired  
////    private StatisticsRepository statisticsRepository;  
////      
////    @Autowired  
////    private StorageConfig storageConfig;  
//      
//    /*** 
//     * 创建表头 
//     * @param workbook 
//     * @param sheet 
//     */  
//    private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet)  
//    {  
//        HSSFRow row = sheet.createRow(0);  
//        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度  
//        sheet.setColumnWidth(2, 12*256);  
//        sheet.setColumnWidth(3, 17*256);  
//          
//        //设置为居中加粗  
//        HSSFCellStyle style = workbook.createCellStyle();  
//        HSSFFont font = workbook.createFont();  
//        font.setBold(true);  
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
//        style.setFont(font);  
//          
//        HSSFCell cell;  
//        cell = row.createCell(0);  
//        cell.setCellValue("序号");  
//        cell.setCellStyle(style);  
//          
//        cell = row.createCell(1);  
//        cell.setCellValue("金额");  
//        cell.setCellStyle(style);  
//          
//        cell = row.createCell(2);  
//        cell.setCellValue("描述");  
//        cell.setCellStyle(style);  
//          
//        cell = row.createCell(3);  
//        cell.setCellValue("日期");  
//        cell.setCellStyle(style);  
//    }  
//      
//    /*** 
//     * 获取excel数据 
//     * @return 返回文件名称及excel文件的URL 
//     * @throws IOException 
//     */  
//    @SuppressWarnings({ "unchecked", "rawtypes" })  
//    @RequestMapping("getExcel")  
//    public Object getExcel() throws IOException  
//    {  
//        HSSFWorkbook workbook = new HSSFWorkbook();  
//        HSSFSheet sheet = workbook.createSheet("统计表");  
//        createTitle(workbook, sheet);  
//        List<StatisticsInfo> entities = (List<StatisticsInfo>) statisticsRepository.findAll();  
//          
//        //设置日期格式  
//        HSSFCellStyle style=workbook.createCellStyle();  
//        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));  
//          
//        //新增数据行，并且设置单元格数据  
//        int rowNum = 1;  
//        for (StatisticsInfo statisticsInfo:entities) {  
//              
//            HSSFRow row = sheet.createRow(rowNum);  
//            row.createCell(0).setCellValue(statisticsInfo.getId());  
//            row.createCell(1).setCellValue(statisticsInfo.getMoney().toString());  
//            row.createCell(2).setCellValue(statisticsInfo.getDescription());  
//            HSSFCell cell = row.createCell(3);  
//            cell.setCellValue(statisticsInfo.getCurrentdate());  
//            cell.setCellStyle(style);  
//            rowNum++;  
//        }  
//          
//        //拼装blobName  
//        String fileName = "测试数据统计表.xlsx";  
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");  
//        String dateTime = dateFormat.format(new Date());  
//        String blobName =  dateTime + "/" + UUID.randomUUID().toString().replaceAll("-", "") + "/" + fileName;  
//          
//        //获取或创建container  
//        CloudBlobContainer blobContainer = BlobHelper.getBlobContainer("temp", storageConfig);  
//        //设置文件类型，并且上传到azure blob  
//        try {  
//            CloudBlockBlob blob = blobContainer.getBlockBlobReference(blobName);  
//            ByteArrayOutputStream out = new ByteArrayOutputStream();  
//            workbook.write(out);  
//            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());  
//              
//            blob.upload(in, out.toByteArray().length);  
//            Map map = new HashMap();  
//            map.put("fileName", fileName);  
//            map.put("excelUrl", blob.getUri().toString());  
//              
//            ResultMsg resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(),  
//                    ResultStatusCode.OK.getErrmsg(), map);  
//            return resultMsg;  
//              
//        } catch (Exception e)  
//        {  
//            ResultMsg resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrcode(),  
//                    ResultStatusCode.SYSTEM_ERR.getErrmsg(), null);  
//            return resultMsg;  
//        }  
//    }  
//}  