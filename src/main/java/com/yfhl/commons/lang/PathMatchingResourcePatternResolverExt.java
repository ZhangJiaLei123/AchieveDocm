package com.yfhl.commons.lang;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/** 
 * @author  jiayangli
 * @date 创建时间：2017年2月17日
 * @Description: 资源路径转换扩展程序，获取资源对象
 */
public class PathMatchingResourcePatternResolverExt extends PathMatchingResourcePatternResolver{
	
	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(PathMatchingResourcePatternResolverExt.class);
	
	/** spring 注入路径类型 */
	private int type;
	
	/** spring 注入路径 */
	private String path;
	
	/** 匹配查找使用的路径前缀，i18n/template、i18n\\template */
	private String langPathPrifix;
	
	/**
	 * @author  jiayangli
	 * @date 创建时间：2017年2月17日
	 * @Description:默认构造函数
	 */
	public PathMatchingResourcePatternResolverExt(){}

	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月17日
	 * @Description :加载属性文件,获取资源对象.
	 * <p>可匹配查找文件目录 如：classpath*:lang_I18N/  /*.properties<br>
	 * <p>可按照文件名称以","分割方式查找 如：intelliweb,intelliplatform<br>
	 * Resource[] 源文件对象数组
	 */
	public Resource[] getResources() throws java.io.IOException {
		
		if(logger.isDebugEnabled()){
			logger.debug("注入资源路径：path="+path+",路径类型：type="+type);
		}
		if (this.path == null) {
			return new Resource[0];
		}
		if (type == 0) {
			// 在类路径下寻找，支持子文件夹搜索和通配符。
			return super.getResources(this.path);
		} else if (type == 1) {
			// 查找指定的资源文件，多个文件使用","分隔。
			return this.getDefResources();
		}
		return new Resource[0];

	}

	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月17日
	 * @Description :查找指定的资源文件，多个文件使用","分隔
	 * Resource[] 源文件对象数组
	 */
	protected Resource[] getDefResources() {
		
		if (this.path == null) {
			return new Resource[0];
		}
		List<Resource> resourceList = new ArrayList<Resource>();

		String[] fileNameArray = this.path.split(",");
		for (int i = 0; i < fileNameArray.length; i++) {
			// 保存每个资源文件名称
			resourceList.add(super.getResource(fileNameArray[i].trim()));
		}
		
		Resource[] resources = new Resource[resourceList.size()];
		resourceList.toArray(resources);
		return resources;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void setLangPathPrifix(String langPathPrifix) {
		this.langPathPrifix = langPathPrifix;
	}
	
	public String getLangPathPrifix() {
		return langPathPrifix;
	}

}
