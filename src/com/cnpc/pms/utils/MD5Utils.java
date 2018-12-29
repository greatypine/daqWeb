package com.cnpc.pms.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5 Utils Copyright(c) 2014 Yadea Technology Group  ,
 * http://www.yadea.com.cn
 * 
 * @author IBM
 * @since 2011-7-8
 */
public final class MD5Utils {

	/**
	 * Logger
	 */
	protected static final Logger LOG = LoggerFactory.getLogger(MD5Utils.class);

	/**
	 * the constant NO;
	 */
	private static final int NO = 0xFF;

	/**
	 * MD5 加密
	 * 
	 * @param str
	 *            the str
	 * @return md5StrBuff the md5StrBuff
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e.getMessage());
			return "";
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage());
			return "";
		}

		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(NO & byteArray[i]).length() == 1) {
				md5StrBuff.append("0").append(
						Integer.toHexString(NO & byteArray[i]));
			} else {
				md5StrBuff.append(Integer.toHexString(NO & byteArray[i]));
			}
		}
		return md5StrBuff.toString();
	}

	/**
	 * the default constructor
	 */
	private MD5Utils() {
	}

	// public static void main(String[] args) {
	// System.out.println(MD5Utils.getMD5Str("234123"));
	// String entypeStr = "bc4e71768c878be0e0636839756a8af0";
	// System.out.println(entypeStr.length());
	// }
	
	
	 // 密钥  
    public static String KEY = "X92A5ClEm5H3zh9OxL";  
    private static String CHARSET = "utf-8";  
    private static String ALGORITHM = "AES";  
    /** 
     * 加密 
     *  
     * @param content 
     * @return 
     */  
    public static String encrypt(String content) {  
        return encrypt(content, KEY);  
    }  
  
    /** 
     * 解密 
     *  
     * @param content 
     * @return 
     */  
    public static String decrypt(String content) {  
        return decrypt(content, KEY);  
    }  
    
    
    
    /** 
     * 加密 
     *  
     * @param content 
     *            需要加密的内容 
     * @param key 
     *            加密密码 
     * @return 
     */  
    public static String encrypt(String content, String password) {  
        try {  
        	KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
        	
        	SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
       	 	random.setSeed(password.getBytes());
        	
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);// 创建密码器
            byte[] byteContent = content.getBytes(CHARSET);
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return parseByte2HexStr(result); // 加密
        } catch (Exception e) {  
            e.printStackTrace();
        }  
        return null;  
    }  
    
    
    
    /** 
     * AES（256）解密 
     *  
     * @param content 
     *            待解密内容 
     * @param key 
     *            解密密钥 
     * @return 解密之后 
     * @throws Exception 
     */  
    public static String decrypt(String content, String password) {  
        try {  
        	 byte[] btcontent = parseHexStr2Byte(content);
        	 KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
        	 
        	 SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        	 random.setSeed(password.getBytes());
        	 
             kgen.init(128, random);
             SecretKey secretKey = kgen.generateKey();
             byte[] enCodeFormat = secretKey.getEncoded();
             SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM);
             Cipher cipher = Cipher.getInstance(ALGORITHM);// 创建密码器
             cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
             byte[] result = cipher.doFinal(btcontent);
             return new String(result, CHARSET); // 加密
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
    
    
    /**
     * 将二进制转换成16进制
     * 
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    
    
    /**
     * 将16进制转换为二进制
     * 
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    
    
}
