package com.example.demo.util.secure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

import org.junit.Test;

/**
 * RSA 加密
 * 生成公钥 私钥对
 * 公钥加密私钥解密，私钥加密公钥解密
 * @author Administrator
 *
 */
public class RSA {

	public static void getKey(String pubKeyfile,String priKeyfile) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		//生成公钥私钥对
		KeyPairGenerator gen= KeyPairGenerator.getInstance("RSA");
		//初始化密钥对生成器，密钥大小为1024位 
		gen.initialize(1024);
		//生成秘钥对 保存在keyPair中
		KeyPair keyPair = gen.generateKeyPair();
		//获取公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		//获取私钥
		PrivateKey privateKey = keyPair.getPrivate();
		
		//生成Key文件
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pubKeyfile));
		oos.writeObject(publicKey);
		oos.flush();
		oos.close();
		
		oos = new ObjectOutputStream(new FileOutputStream(priKeyfile));
		oos.writeObject(privateKey);
		oos.flush();
		oos.close();
		
	}
	
	/**
	 * 
	 * @param k 秘钥
	 * @param data 加密或解密字节组
	 * @param type 类型 1 加密，0解密
	 * @return resultBytes
	 * @throws Exception 
	 */
	public static byte[] deCodeAndEnCode(Key k,byte[] data,int type) throws Exception {
		if (k!=null) {
			Cipher cipher = Cipher.getInstance("RSA");
			
			if (type==1) {
				cipher.init(Cipher.ENCRYPT_MODE, k);
				byte[] resultBytes=cipher.doFinal(data);
				return resultBytes;
			}else if (type==0) {
				cipher.init(Cipher.DECRYPT_MODE, k);
				byte[] resultBytes=cipher.doFinal(data);
				return resultBytes;
			}else {
				throw new RuntimeException(new String("参数必须为: 1 加密 0解密".getBytes(),"UTF-8"));
			}
		}
		return null;
		
	}
	
//	@Test
    public static void main(String[] args) throws Exception {  
    	  
        String pubfile = "D:\\demo\\tmp\\pub.key";  
        String prifile = "D:\\demo\\tmp\\pri.key";  
  
        //getKey(pubfile, prifile);  
  
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pubfile));  
        RSAPublicKey pubkey = (RSAPublicKey) ois.readObject();  
        ois.close();  
  
        ois = new ObjectInputStream(new FileInputStream(prifile));  
        RSAPrivateKey prikey = (RSAPrivateKey) ois.readObject();  
        ois.close();  
  
        // 使用公钥加密  
        String msg = "Hello";  
        String enc = "UTF-8";  
  
        // 使用公钥加密私钥解密  
        System.out.println("///////////////////使用公钥加密私钥解密//////////////////////");
        System.out.println("原文: " + msg);  
        byte[] result = deCodeAndEnCode(pubkey, msg.getBytes(enc), 1);  
        System.out.println("加密: " + new String(result, enc));  
        byte[] deresult = deCodeAndEnCode(prikey, result, 0);  
        System.out.println("解密: " + new String(deresult, enc));  
  
        msg = "World";  
        // 使用私钥加密公钥解密  
        System.out.println("///////////////////使用私钥加密公钥解密 //////////////////////");
        System.out.println("原文: " + msg);  
        byte[] result2 = deCodeAndEnCode(prikey, msg.getBytes(enc), 1);  
        System.out.println("加密: " + new String(result2, enc));  
        byte[] deresult2 = deCodeAndEnCode(pubkey, result2, 0);  
        System.out.println("解密: " + new String(deresult2, enc));  
  
    }  
}
