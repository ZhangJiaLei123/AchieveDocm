package com.yfhl.commons.domain;
/** 
 * @author  jiayangli
 * @date 创建时间：2017年2月16日 下午1:17:29 
 * @Description: ajax 响应数据传输对象
 */
public class ActionResult<T> extends Entity{

	/** @Author  jiayangli
	 * @Date 创建时间：2017年2月16日 下午1:18:02
	 */
	private static final long serialVersionUID = -2186528966662254463L;
	
	/** 是否成功 */
	private boolean success;
	
	/** 返回响应代码 */
	private String resultCode;
	
	/** 返回响应信息 */
	private String resultMessage;
	
	/** 返回业务数据 */
	private T data;

	/**
	 * @author  jiayangli
	 * @date 创建时间：2017年2月16日 下午1:25:30
	 * @Description :默认构造函数
	 */
	public ActionResult(){
		super();
		this.success = true;
	}

	/**
	 * @author  jiayangli
	 * @date 创建时间：2017年2月16日 下午1:34:15 
	 * @param success
	 * @param resultMessage
	 * @param resultCode
	 * @param data
	 * @Description :失败或成功处理业务响应
	 */
	public ActionResult(boolean success,String resultMessage,String resultCode,T data){
		super();
		this.success = success;
		this.resultMessage = resultMessage;
		this.resultCode = resultCode;
		this.data = data;
	}
	
	/**
	 * @author  jiayangli
	 * @date 创建时间：2017年2月16日 下午1:33:41 
	 * @param success
	 * @param data
	 * @Description :成功处理业务响应
	 */
	public ActionResult(boolean success,T data){
		super();
		this.success = success;
		this.data = data;
	}
	
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	@Override
	public String toString(){
		StringBuffer s = new StringBuffer(super.toString());
		s.append("success=").append(success);
		s.append(",resultCode=").append(resultCode);
		s.append(",resultMessage=").append(resultMessage);
		s.append(",data=").append(data);
		return s.toString();
	}
}
