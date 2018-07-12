package com.cnpc.pms.upload.action;

import com.cnpc.pms.base.common.action.DispatcherAction;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.personal.manager.OssRefFileManager;
import com.gexin.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.upload.action
 * @Description: 接收文件并上传文件到oss
 * @Author: gbl
 * @CreateDate: 2018/7/12 9:34
 */
public class UploadFileToOssAction extends DispatcherAction {

    private static final long serialVersionUID = 1L;

    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "template";


    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 20;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    private String getOssSavePath (String flag){
        return null;
    }

    /**
     * 上传数据及保存文件
     */
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        OssRefFileManager ossRefFileManager  = (OssRefFileManager)SpringHelper.getBean("ossRefFileManager");
        String storeDir = request.getParameter("storeDir");
        response.setContentType("json");
        PrintWriter writer = response.getWriter();
        JSONObject obj = new JSONObject();
        // 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        //upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        //upload.setSizeMax(MAX_REQUEST_SIZE);

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        //String uploadPath = getServletContext().getRealPath("/") + File.separator + UPLOAD_DIRECTORY;
        String uploadPath = this.getClass().getClassLoader().getResource("").getPath()+ File.separator + UPLOAD_DIRECTORY;

        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        String url = ossRefFileManager.uploadOssFile(storeFile,suffix,storeDir);
                        //返回结果
                        obj.put("downLoadUrl",url);
                        obj.put("fileName",fileName);
                        obj.put("status","success");
                        writer.print(obj.toJSONString());
                    }
                }
            }
        } catch (Exception ex) {
            obj.put("status","fail");
            obj.put("message",ex.getMessage());
            writer.print(obj.toJSONString());
        }

    }

}
