package com.yfhl.commons.exception;

import java.util.Locale;

import com.yfhl.commons.lang.LangUtil;

/** 
 * @author  jiayangli
 * @date 创建时间：2017年2月16日
 * @Description: 异常处理类
 */
public class UncheckedException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/** 异常代码 */
	private String errorCode;
	
	/** 异常信息 */
	private String errorMessage;
	
	/** 异常信息所需参数 */
	private Object[] errorParam;

	/**
	 * @author  jiayangli
	 * @date 创建时间：2017年2月16日
	 * @Description: 构造函数
	 * @param errorCode	异常编码
	 */
	public UncheckedException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}

	/**
	 * @author  jiayangli
	 * @date 创建时间：2017年2月16日
	 * @Description: 构造函数
	 * @param errorCode 异常编码
	 * @param param 异常信息所需参数
	 */
	public UncheckedException(String errorCode, Object[] errorParam) {
		super();
		this.errorCode = errorCode;
		this.errorParam = errorParam;
	}

	/**
	 * @author  jiayangli
	 * @date 创建时间：2017年2月16日
	 * @Description: 构造函数
	 * @param errorCode 异常编码
	 * @param param 异常信息所需参数
	 * @param t 源异常
	 */
	public UncheckedException(String errorCode, Object[] errorParam,Throwable t) {
		super(t);
		this.errorCode = errorCode;
		this.errorParam = errorParam;
	}
	
	/**
	 * @author  jiayangli
	 * @date 创建时间：2017年2月16日
	 * @Description: 构造函数
	 * @param errorMessage 异常信息
	 * @param t 源异常
	 */
	public UncheckedException(String errorMessage,Throwable t) {
		super(errorMessage,t);
		this.errorMessage = errorMessage;
	}

	/**
	 * 重写getMessage方法
	 */
	@Override
	public String getMessage() {
		if(errorMessage != null){
			return errorMessage;
		}
		if(errorCode != null && !"".equals(errorCode.trim())){
			this.setErrorMessage(LangUtil.getLU().getMessage(errorCode, errorParam,Locale.SIMPLIFIED_CHINESE));
		}
		return this.getErrorMessage();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object[] getErrorParam() {
		return errorParam;
	}

	public void setErrorParam(Object[] errorParam) {
		this.errorParam = errorParam;
	}

}
