package com.cnpc.pms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GenericRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.personal.entity.OssRefFile;
import com.cnpc.pms.personal.manager.OssRefFileManager;

public class OSSUploadUtil {
	private static OSSConfig config = null;  
	  
    public static String uploadFile(File file,String fileType,String remotedir){  
        config = config==null?new OSSConfig():config;  
        config.setPicLocation(remotedir);
        String fileName = config.getPicLocation()+UUID.randomUUID().toString().toUpperCase().replace("-", "")+"."+fileType;   
        return putObject(file,fileType,fileName);  
    }

    public static String uploadFileNew(File file,String fileType,String remotedir,String fileNames){
        config = config==null?new OSSConfig():config;
        config.setPicLocation(remotedir);
        String fileName = config.getPicLocation()+fileNames+"."+fileType;
        return putObject(file,fileType,fileName);
    }
    
    
    public static String uploadFileResName(File file,String fileType,String remotedir){  
        config = config==null?new OSSConfig():config;  
        config.setPicLocation(remotedir);
        String fileName = config.getPicLocation()+file.getName();   
        return putObject(file,fileType,fileName);  
    } 
      
    public static String updateFile(File file,String fileType,String oldUrl){  
        String fileName = getFileName(oldUrl);  
        if(fileName==null) return null;  
        return putObject(file,fileType,fileName);  
    }  
      
   /* public static String replaceFile(File file,String fileType,String oldUrl){  
        boolean flag = deleteFile(oldUrl);      
        if(!flag){  
        }  
        return uploadFile(file, fileType);  
    }  */
      
    public static boolean deleteFile(String fileUrl){  
        config = config==null?new OSSConfig():config;  
          
        String bucketName = OSSUploadUtil.getBucketName(fileUrl);       
        String fileName = OSSUploadUtil.getFileName(fileUrl);             
        if(bucketName==null||fileName==null) return false;  
        OSSClient ossClient = null;   
        try {  
            ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());   
            GenericRequest request = new DeleteObjectsRequest(bucketName).withKey(fileName);  
            ossClient.deleteObject(request);  
        } catch (Exception oe) {  
            oe.printStackTrace();  
            return false;  
        } finally {  
            ossClient.shutdown();  
        }  
        return true;  
    }  
    public static int deleteFile(List<String> fileUrls){  
        int deleteCount = 0;   
        String bucketName = OSSUploadUtil.getBucketName(fileUrls.get(0));     
        List<String> fileNames = OSSUploadUtil.getFileName(fileUrls);           
        if(bucketName==null||fileNames.size()<=0) return 0;  
        OSSClient ossClient = null;   
        try {  
            ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());   
            DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName).withKeys(fileNames);  
            DeleteObjectsResult result = ossClient.deleteObjects(request);  
            deleteCount = result.getDeletedObjects().size();  
        } catch (OSSException oe) {  
            oe.printStackTrace();  
        } catch (ClientException ce) {  
            ce.printStackTrace();  
        } finally {  
            ossClient.shutdown();  
        }  
        return deleteCount;  
          
    }  
    public static int deleteFiles(List<String> fileUrls){  
        int count = 0;  
        for (String url : fileUrls) {  
            if(deleteFile(url)){  
                count++;  
            }  
        }  
        return count;  
    }  
      
    private static String putObject(File file,String fileType,String fileName){
        String proxydomain = PropertiesUtil.getValue("proxy.domain");
        int proxyport = Integer.parseInt(PropertiesUtil.getValue("proxy.port"));
        config = config==null?new OSSConfig():config;  
        String url = null;      
        OSSClient ossClient = null;
        // 创建ClientConfiguration实例
        ClientConfiguration conf = new ClientConfiguration();

        // 配置代理为本地8080端口
        conf.setProxyHost(proxydomain);
        conf.setProxyPort(proxyport);
        try {  

            ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret(),conf);


            InputStream input = new FileInputStream(file);    
            ObjectMetadata meta = new ObjectMetadata();             
            meta.setContentType(OSSUploadUtil.contentType(fileType));  
            meta.setCacheControl("no-cache");                       
            PutObjectRequest request = new PutObjectRequest(config.getBucketName(), fileName,input,meta);            
            ossClient.putObject(request);   
            url = config.getEndpoint().replaceFirst("http://","http://"+config.getBucketName()+".")+"/"+fileName;    
        } catch (OSSException oe) {  
            oe.printStackTrace();  
            return null;  
        } catch (ClientException ce) {  
            ce.printStackTrace();  
            return null;  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            return null;  
        } finally {  
            ossClient.shutdown();  
        }  
        return url;  
    }  
      
    private static String contentType(String fileType){  
        fileType = fileType.toLowerCase();  
        String contentType = "";  

	    if(fileType== "bmp"){ 
	    	contentType = "image/bmp";
	    }
	    else if(fileType== "gif"){
	    	contentType = "image/gif";  
	    }else if(fileType== "png"){
	    	contentType = "image/jpeg"; 
	    }else if(fileType== "jpeg"){
	    	contentType = "image/jpeg"; 
	    }else if(fileType== "jpg"){
	    	contentType = "image/jpeg"; 
	    }else if(fileType== "html"){
	    	contentType = "text/html";
	    }else if(fileType== "txt"){
	    	contentType = "text/plain"; 
	    }else if(fileType== "vsd"){
	    	contentType = "application/vnd.visio"; 
	    }else if(fileType== "ppt"){
	    	contentType = "application/vnd.ms-powerpoint";  
	    }else if(fileType== "pptx"){
	    	contentType = "application/vnd.ms-powerpoint";  
	    }else if(fileType== "doc"){
	    	contentType = "application/msword";  
	    }else if(fileType== "docx"){
	    	contentType = "application/msword";  
	    }else if(fileType== "xml"){
	    	contentType = "text/xml"; 
	    }else if(fileType== "mp4"){
	    	contentType = "video/mp4";  
	    }else{
	    	contentType = "application/octet-stream";  
	    }
        return contentType;  
     }    
      
    private static String getBucketName(String fileUrl){  
        String http = "http://";  
        String https = "https://";  
        int httpIndex = fileUrl.indexOf(http);  
        int httpsIndex = fileUrl.indexOf(https);  
        int startIndex  = 0;  
        if(httpIndex==-1){  
            if(httpsIndex==-1){  
                return null;  
            }else{  
                startIndex = httpsIndex+https.length();  
            }  
        }else{  
            startIndex = httpIndex+http.length();  
        }  
        int endIndex = fileUrl.indexOf(".oss-");   
        return fileUrl.substring(startIndex, endIndex);  
    }  
      
    private static String getFileName(String fileUrl){  
        String str = "aliyuncs.com/";  
        int beginIndex = fileUrl.indexOf(str);  
        if(beginIndex==-1) return null;  
        return fileUrl.substring(beginIndex+str.length());  
    }  
      
    private static List<String> getFileName(List<String> fileUrls){  
        List<String> names = new ArrayList<String>();  
        for (String url : fileUrls) {  
            names.add(getFileName(url));  
        }  
        return names;  
    }
    
    public static String buildClientUrl(String rt){
    	String fileUrl = rt.replace("oss-cn-beijing.aliyuncs.com", "https://cdn.guoanshuju.com");
    	return fileUrl;
    }
    
    //方法会重新生成文件名 随机码 
    public static String uploadOssFile(File f,String suffix,String urlLocation) {
    	String rt = uploadFile(f, suffix , urlLocation); 
    	return buildClientUrl(rt);
    }

    //方法会重新生成文件名 随机码
    public static String uploadOssFileNew(File f,String suffix,String urlLocation,String fileNames) {
        String rt = uploadFileNew(f, suffix , urlLocation,fileNames);
        return buildClientUrl(rt);
    }
    
    //方法不会重新生成文件名
    public static String uploadOssFileResName(File f,String suffix,String urlLocation) {
    	String rt = uploadFileResName(f, suffix , urlLocation); 
    	return buildClientUrl(rt);
    }
    
    
    
    public static List<String> queryOssListByPath(String rootPath) {
    	List<String> result = new ArrayList<String>();
    	if(rootPath.indexOf("house")>-1&&rootPath.indexOf("house_type")==-1) {
        	OSSConfig ossConfig = new OSSConfig();
            OSSClient ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());   
        	// 构造ListObjectsRequest请求
        	ListObjectsRequest listObjectsRequest = new ListObjectsRequest("guoanshuju");
        	// "/" 为文件夹的分隔符
        	listObjectsRequest.setDelimiter("/");
        	// 列出fun目录下的所有文件和文件夹
        	listObjectsRequest.setPrefix(rootPath);
        	ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        	// 遍历所有Object
        	//System.out.println("Objects:");
        	//OSSobjectSummary下包含目录下所有的文件，不包含子目录
        	for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
        		if(objectSummary.getKey()!=null&&!objectSummary.getKey().equals(rootPath)) {
            		result.add(objectSummary.getKey());
        		}
        	    //System.out.println(objectSummary.getKey());
        	}
        	// 遍历所有CommonPrefix
        	/*System.out.println("\nCommonPrefixs:");
        	for (String commonPrefix : listing.getCommonPrefixes()) {
        	    System.out.println(commonPrefix);
        	}*/
        	return result;
    	}
    	return result;
    }
    
    
    
    /**
     * 根据文件路径 删除的方法  
     * @param url  daqWeb/house/xxxxxxx.xls
     */
    public static void deleteObjectByUrl(String url) {
    	if(url.indexOf("house")>-1&&url.indexOf("house_type")==-1) {
    		// 初始化OSSClient
        	OSSConfig ossConfig = new OSSConfig();
            OSSClient ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());   
            // 删除Object
            if(url.indexOf(".")>-1) {
            	ossClient.deleteObject("guoanshuju", url);
            }
    	}
    }
    
    
   /* public static void main(String[] args) {
		File f = new File("d:\\pic\\zizhuyuanxingdian2017-11.xls");
    	String rt = uploadFile(f, "xls","workrecord/");
    	System.out.println(buildClientUrl(rt));
	}*/

    
}
