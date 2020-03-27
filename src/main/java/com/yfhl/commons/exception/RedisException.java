package com.yfhl.commons.exception;

public class RedisException extends UncheckedException {
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 * 
	 * @param errorCode
	 *            异常编码
	 */
	public RedisException(String errorCode) {
		super(errorCode);
	}

	/**
	 * 构造函数
	 * 
	 * @param errorCode
	 *            异常编码
	 * @param errorParam
	 *            Object[]异常信息用到的参数
	 */
	public RedisException(String errorCode, Object[] errorParam) {
		super(errorCode, errorParam);
	}

	/**
	 * 重载构造函数
	 * 
	 * @param errorCode
	 * @param errorParam
	 * @param t
	 */
	public RedisException(String errorCode, Object[] errorParam, Throwable t) {
		super(errorCode, errorParam, t);
	}

	/**
	 * 重载构造函数
	 * 
	 * @param message
	 * @param t
	 */
	public RedisException(String message, Throwable t) {
		super(message, t);
	}

}
