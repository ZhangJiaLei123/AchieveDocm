package com.yfhl.commons.lang;

import org.springframework.context.support.ResourceBundleMessageSource;

/** 
 * @author  jiayangli
 * @date 创建时间：2017年2月17日
 * @Description: 获取多语言资源信息持有对象
 */
public interface LangResourceMessageLoader {
	public ResourceBundleMessageSource getResourceBundleMessageSource();
}
