package com.yfhl.commons.lang;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.yfhl.commons.utils.StringUtils;

/** 
 * @author  jiayangli
 * @date 创建时间：2017年2月17日
 * @Description: 多语言资源信息加载程序
 */
public class ResourceBundleMessageSourceExt implements LangResourceMessageLoader{
	
	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(ResourceBundleMessageSourceExt.class);
	
	/** 模版转换器列表，加载资源文件对象，xml配置，spring注入 */
	private List<ResourcePatternResolver> resourcePatternResolver;
	/** 多语言信息持有对象， */
	private ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
	
	/**
	 * @author  jiayangli
	 * @date 创建时间：2017年2月17日
	 * @Description:默认构造函数
	 */
	public ResourceBundleMessageSourceExt(){}
	
	/**
	 * @author  jiayangli
	 * @date 创建时间：2017年2月17日
	 * @Description: 实现接口，获取多语言信息持有对象
	 */
	@Override
	public ResourceBundleMessageSource getResourceBundleMessageSource() {
		return this.resourceBundleMessageSource;
	}

	public List<ResourcePatternResolver> getResourcePatternResolver() {
		return resourcePatternResolver;
	}

	public void setResourcePatternResolver(
			List<ResourcePatternResolver> resourcePatternResolver) {
		this.resourcePatternResolver = resourcePatternResolver;
	}
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月17日
	 * @Description : spring 初始化方法，加载属性文件目录，封装多语言信息持有对象
	 * <p>可匹配查找文件目录 <br>
	 * <p>可按照文件名称以","分割方式查找<br>
	 */
	public void init() {

		String fileName = "";
		//资源对象文件名(相对路径、不含国际化的)列表
		List<String> basenameList = new ArrayList<String>();
		
		// 循环this.resourcePatternResolver，逐个获取资源模版转换器，进而从模版中获取全部资源对象
		for (int i = 0; i < this.resourcePatternResolver.size(); i++) {
			PathMatchingResourcePatternResolverExt pathResolver = (PathMatchingResourcePatternResolverExt) this.resourcePatternResolver.get(i);
			try {
				
				//从模版中获取全部资源对象，循环Resource[]获取资源文件名
				Resource[] resource = pathResolver.getResources();
				for (int j = 0; j < resource.length; j++) {
					
					// 处理resource对象，通过文件加载类型(0为匹配查找 1为文件名查找)从全路径名中截取获得转成文件名(相对路径、不含国际化的)
					fileName = convert(resource[j], pathResolver.getType(),pathResolver.getLangPathPrifix());
					if (fileName != null && !basenameList.contains(fileName)) {
						basenameList.add(fileName);
					}
				}
			} catch (java.io.IOException ioe) {
				logger.error("加载资源文件异常", ioe);
			}
		}
		if (basenameList.size() == 0) {
			return;
		}

		String[] basenames = new String[basenameList.size()];
		basenameList.toArray(basenames);
		
		//多语言信息对象，封装(相对路径、不含国际化的)资源文件名列表，持有多语言信息
		resourceBundleMessageSource.setBasenames(basenames);
	}

	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月17日
	 * @Description :通过文件路径提取文件名称
	 * @param resource 资源对象，持有资源文件全路径
	 * @param type	转换器模版类型(匹配查找或按文件名查找)
	 * @return filename 例如：lang_I18N/app_global
	 * String
	 */
	protected String convert(Resource resource, int type,String langPathPrifix) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("I18N Resource from " + resource);
		}
		if (resource == null) {
			return null;
		}
		
		//资源文件全路径
		String filenamePath = resource.toString();
		//前后截取索引
		int index_prefix, index_suffix;
		//截取之后的文件名
		String filename = "";
		
		//0为匹配查找
		if (type == 0) {
			//截取的前缀
			
			if(StringUtils.isNullorEmpty(langPathPrifix)){
				if (logger.isDebugEnabled()) {
					logger.debug("I18N Resource langPathPrifix is null or empty");
				}
			}else{
				
				langPathPrifix = langPathPrifix.replace("\\", "/");
				if(langPathPrifix.startsWith("/")){
					langPathPrifix = langPathPrifix.substring(1);
				}
				
				//前截取位置
				index_prefix = 0;
				index_prefix = filenamePath.lastIndexOf(langPathPrifix);
				if (index_prefix == -1) {
					index_prefix = filenamePath.lastIndexOf(langPathPrifix.replace("/", "\\"));
				}
				//后截取位置
				index_suffix = filenamePath.lastIndexOf(".");
				
				if (filenamePath.indexOf("en_US") > 0 || filenamePath.indexOf("zh_CN") > 0) {
					filename = filenamePath.substring(index_prefix,index_suffix - 6);
				} else {
					filename = filenamePath.substring(index_prefix, index_suffix);
				}
			}
			
		//1为按文件名查找
		}else if (type == 1) {
			
			index_prefix = filenamePath.lastIndexOf("[");
			index_suffix = filenamePath.lastIndexOf("]");
			filename = filenamePath.substring(index_prefix + 1, index_suffix);
		} 
		
		if (logger.isDebugEnabled()) {
			logger.debug("I18N Resource BaseFileName:" + filename);
		}
		
		filename = filename.replace('\\', '/');
		return filename;
	}
}
