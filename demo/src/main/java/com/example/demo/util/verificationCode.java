package com.example.demo.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vCode/")
public class verificationCode extends HttpServlet {
	private static final Logger LOGGER = LoggerFactory.getLogger(verificationCode.class);
    public static final String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";//放到session中的key
    private Random random = new Random();
    private String randString = "0123456789abcdefghijklmnopqrstuvwxyz";//随机产生的字符串ABCDEFGHIJKLMNOPQRSTUVWXYZ
    
    private int width = 140;//图片宽
    private int height = 26;//图片高
    private int lineSize = 800;//干扰线数量
    private int stringNum = 8;//随机产生字符数量
    /*
     * 获得字体
     */
    private Font getFont(){
        return new Font("Fixedsys",Font.CENTER_BASELINE,18);
    }
    /*
     * 获得颜色
     */
    private Color getRandColor(int fc,int bc){
        if(fc > 255)
            fc = 255;
        if(bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc-fc-16);
        int g = fc + random.nextInt(bc-fc-14);
        int b = fc + random.nextInt(bc-fc-18);
        return new Color(r,g,b);
    }
	
	private static final long serialVersionUID = -4119514123150930928L;

	public verificationCode() {
		super();
	}

	@RequestMapping(value = "checkcode/getCheckCode")
	public void check(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Object code = session.getAttribute(RANDOMCODEKEY);
		PrintWriter out = response.getWriter();
		out.print(code);
		out.close();
	}
	
	@RequestMapping(value = "checkcode/generatorcheckcode.do")
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	     HttpSession session = request.getSession();
	        //BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
	        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
	        Graphics g = image.getGraphics();//产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
	        g.fillRect(0, 0, width, height);
	        g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,18));
	        g.setColor(getRandColor(1, 255));//110, 133
	        //绘制干扰线
	        for(int i=0;i<=lineSize;i++){
	            drowLine(g);
	        }
	        //绘制随机字符
	        String randomString = "";
	        for(int i=1;i<=stringNum;i++){
	            randomString=drowString(g,randomString,i);
	        }
	        session.removeAttribute(RANDOMCODEKEY);
	        session.setAttribute(RANDOMCODEKEY, randomString);
	        LOGGER.debug("生成验证码："+randomString);
	        g.dispose();
	        try {
	            ImageIO.write(image, "JPEG", response.getOutputStream());//将内存中的图片通过流动形式输出到客户端
	        } catch (Exception e) {
	        	LOGGER.error("exception occured : ", e);
	        }
	}

	public void destroy() {
		super.destroy();
	}

	public void init() throws ServletException {
		super.init();
	}
	
    /*
     * 绘制字符串
     */
    private String drowString(Graphics g,String randomString,int i){
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101),random.nextInt(111),random.nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
        randomString +=rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 13*i, 16);
        return randomString;
    }
    /*
     * 绘制干扰线
     */
    private void drowLine(Graphics g){
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x+xl, y+yl);
    }
    /*
     * 获取随机的字符
     */
    public String getRandomString(int num){
        return String.valueOf(randString.charAt(num));
    }
}

