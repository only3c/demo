package com.example.demo.util.ls;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

/**
 * 文字转化为Base64码
 */
public class WordsToBase64 {
	
    public static String wordsToBase64(String str,HttpServletResponse response){
        Integer width = 120;
        Integer height = 60;
        Integer fontWidth = 26;
        Font font = new Font("微软雅黑", Font.PLAIN, fontWidth );//Font.PLAIN,BOLD
        // 创建图片
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setClip(0, 0, width, height);
        //设置透明色
        g.setColor(new Color(255, 255, 255,0));
        g.fillRect(0, 0, width, height);// 先用透明色填充整张图片,也就是背景
        g.setColor(Color.black);// 在换成黑色
        g.setFont(font);// 设置画笔字体
        /** 用于获得垂直居中y */
        Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics(font);
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int y = ((clip.height - (ascent + descent)) / 2) + ascent;
        //        for (int i = 0; i < 6; i++) {// 256 340 0 680
        //            g.drawString(str, i * 680, y);// 画出字符串
        //        }
        //水平居中
        int wid = (width-(fontWidth*str.length()))/2;
        g.drawString(str, wid, y);
        g.dispose();

        //图片转化为Base64码  先把得到图片的字节数组
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            //ImageIO.write(image, "png", out);
            ImageIO.write(image, "png", response.getOutputStream());//将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "png", out);//生成字节数字
        } catch (IOException e) {
            throw new RuntimeException("图片输出有误！");
        }
        byte[] b = out.toByteArray();
        //对字节数组Base64编码
        byte[] en = Base64.encodeBase64(b);
        //System.out.println("2");
        System.out.println(new String(en));
        return new String(en);//返回Base64编码过的字节数组字符串

    }
    
//    @SuppressWarnings("restriction")
	public static Boolean deCode(String name,String imgStr,HttpServletResponse response) {
    	
//		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
//    	try {
//			decoder.decodeBuffer(imgStr);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
    	//对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) //图像数据为空  
            return false;  
        try   
        {  
            //Base64解码  
            //byte[] b = Base64.decodeBase64(imgStr.getBytes());
        	byte[] b = Base64.decodeBase64(imgStr);
//            for(int i=0;i<b.length;++i)  
//            {  
//                if(b[i]<0)  
//                {//调整异常数据  
//                    b[i]+=256;  
//                }  
//            }  
            //生成jpeg图片  
            String imgFilePath = "d://"+name+".png";//新生成的图片  
            OutputStream out = new FileOutputStream(imgFilePath);      
            out.write(b);  
            out.flush();  
            out.close();  
            //ImageIO.write(out.write(b), "jpg", response.getOutputStream());
            return true;  
        }   
        catch (Exception e)   
        {  
            return false;  
        }  
	}
}
