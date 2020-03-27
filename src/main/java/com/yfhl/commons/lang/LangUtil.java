package com.yfhl.commons.lang;

import java.io.Serializable;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ResourceBundleMessageSource;

/** 
 * @author  jiayangli
 * @date 创建时间：2017年2月16日
 * @Description: 多语言信息处理工具类
 */
public class LangUtil implements ApplicationContextAware,BeanNameAware,Serializable{

	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(LangUtil.class);
	
	/** 多语言信息加载器 */
	private LangResourceMessageLoader langResourceMessageLoader;
	
	/** spring 容器 */
	private static ApplicationContext applicationContext;
	
	/** 当前bean注入spring容器的名称 */
	private static String beanName;
	
	private static LangUtil lu;
	
	/**
	 * @author  jiayangli
	 * @date 创建时间：2017年2月17日
	 * @Description:默认构造函数
	 */
	public LangUtil(){}
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月17日
	 * @Description :静态方法获取多语言处理工具类
	 * @return
	 * LangUtil
	 */
	public static LangUtil getLU() {
		if (lu == null) {
			createLangUtil();
		}
		return lu;
	}
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月17日
	 * @Description :静态方法创建多语言处理工具类
	 * void
	 */
	private static void createLangUtil(){
		lu = (LangUtil) applicationContext.getBean(LangUtil.beanName);
		applicationContext = null;
		beanName = null;
	}
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月17日
	 * @Description : 从多语言文件中，获取信息
	 * @param msgKey 资源key
	 * @return
	 * String
	 */
	public String getMessage(String msgKey) {
		if (msgKey == null || msgKey == "") {
			return "";
		}

		Locale locale = Locale.CHINA;

		return getMessage(msgKey, null, locale);
	}


	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月17日
	 * @Description : 从多语言文件中，获取信息
	 * @param msgKey 资源key
	 * @param params 为多语言文件模版传递的参数，组装获取信息
	 * @return
	 * String
	 */
	public String getMessage(String msgKey, Object[] params) {
		if (msgKey == null || msgKey == "") {
			return "";
		}
		Locale locale = Locale.CHINA;
		return getMessage(msgKey, params, locale);
	}

	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月17日
	 * @Description : 从多语言文件中，获取信息
	 * @param msgKey 资源key
	 * @param params 为多语言文件模版传递的参数，组装获取信息
	 * @param locale 区域
	 * @return
	 * String
	 */
	public String getMessage(String msgKey, Object[] params, Locale locale) {

		if (msgKey == null || msgKey == "") {
			return "";
		}
		if (locale == null) {
			locale = Locale.CHINA;
		}
		
		//获取多语言加载器
		ResourceBundleMessageSource rbm = langResourceMessageLoader.getResourceBundleMessageSource();
		try {
			return rbm.getMessage(msgKey, params, locale);
		} catch (org.springframework.context.NoSuchMessageException e) {
			return "信息不存在：" + msgKey;
		}
	}

	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月17日
	 * @Description :注入多语言加载器
	 * @param langResourceMessageLoader
	 * void
	 */
	public void setLangResourceMessageLoader(LangResourceMessageLoader langResourceMessageLoader) {
		this.langResourceMessageLoader = langResourceMessageLoader;
	}

	@Override
	public void setBeanName(String beanName) {
		LangUtil.beanName = beanName;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		LangUtil.applicationContext = applicationContext; 
	}

}
