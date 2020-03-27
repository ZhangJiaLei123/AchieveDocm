package com.jeeplus.modules.sys.utils;

import java.io.UnsupportedEncodingException;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.DocConverter;
import com.jeeplus.common.utils.IdGen;

public class FileUtils {
	public static String fileToSwf(String urlFile) throws UnsupportedEncodingException{
		String path = Global.getConfig("userfiles.basedir");
		String url =java.net.URLDecoder.decode(urlFile, "UTF-8");
		String converTerUrl="";
		if(url.indexOf("nxpt/")>-1){
			url = url.substring(url.indexOf("nxpt/")+5, url.length());
		}
		String fileSwfName = IdGen.uuid();
		converTerUrl =  url.substring(0,url.lastIndexOf("/")+1)+fileSwfName+".swf";
		DocConverter d = new DocConverter(path+url,fileSwfName);  
	       //调用conver方法开始转换，先执行doc2pdf()将office文件转换为pdf;再执行pdf2swf()将pdf转换为swf;  
	    d.conver(); 
	    return converTerUrl;
	}

}
