package com.jeeplus.modules.sys.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.DateUtils;

@Controller
@RequestMapping(value = "${adminPath}/sys/uploadFile")
public class WebUploadFileController {
	/**
	 * 用户岗位关系列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"testUpload"})
	public String testUpload(HttpServletRequest request, HttpServletResponse response, Model model) {
	
		return "modules/sys/testUpload";
	}
	@ResponseBody
	@RequestMapping("upload")
    public String upload(HttpServletRequest request,HttpServletResponse response){
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
        Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象
    	String basePath = Global.getConfig("userfiles.basedir")+"/userfiles/";
    	String path = DateUtils.getDate();
        File pathFile = new File(basePath+path);
        if(!pathFile.exists() && !pathFile.isDirectory()){
        	pathFile.mkdirs();
        }
        String uploadUrl = "";
        String fileName = "";
        for (String  key : files.keySet()) {  
        	MultipartFile file = files.get(key);
        	fileName = file.getOriginalFilename();
        	
        	//重命名文件
        	fileName = formatFileName(fileName);
            File newFile=new File(basePath+path+File.separatorChar+fileName);
            try {
				file.transferTo(newFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }  
       System.out.println(uploadUrl);
        try {
        	//这个地方为什么是docm,修改为nxpt就好了
//        	uploadUrl =  "/docm/userfiles/" +path+"/"+java.net.URLEncoder.encode(fileName, "UTF-8");
        	uploadUrl =  "/nxpt/userfiles/" +path+"/"+java.net.URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return uploadUrl;
     }
	@ResponseBody
	@RequestMapping("download")
    public void download(HttpServletRequest request,HttpServletResponse response){
       String fileUrl = request.getParameter("url");
       try {
    	   String basePath = Global.getConfig("userfiles.basedir");
			String contextPath = basePath+fileUrl.substring(6,fileUrl.length());
			// path是指欲下载的文件的路径。
			File file = new File(contextPath);
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。
			String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(contextPath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
   }
	
	/**
	 * 格式化文件名称
	 */
	private static String formatFileName(String fileName) {
    	String qz = fileName.substring(0, fileName.lastIndexOf("."));
    	String hz = fileName.substring(fileName.lastIndexOf("."));
		String dateFomart = new SimpleDateFormat("yyMMddhhMMss").format(new Date());
		fileName = qz+"-"+dateFomart+hz;
		return fileName;
	}
	
	public static void main(String[] args) {
		System.out.println(formatFileName("我的资料.doc"));
	}
}
