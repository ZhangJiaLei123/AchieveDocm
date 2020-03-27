package com.yfhl.commons.domain;

import java.util.HashMap;

import com.jeeplus.common.persistence.Page;

/** 
 * @author  jiayangli
 * @date 创建时间：2017年2月21日
 * @Description: 查询条件集合
 */
public class QueryScope<T> extends AbsEntity{

	private static final long serialVersionUID = 1L;
	
	/** 当前实体对象 */
	private T bizEntity;
	
	/** 当前实体分页对象 */
	private Page<T> page;
	
	/** 实体之外的参数列表 */
	private HashMap<String,Object> paramMap;

	public T getBizEntity() {
		return bizEntity;
	}

	public void setBizEntity(T bizEntity) {
		this.bizEntity = bizEntity;
	}

	public Page<T> getPage() {
		if(page == null){
			page = new Page<T>();
		}
		return page;
	}

	public Page<T> setPage(Page<T> page) {
		this.page = page;
		return page;
	}

	public HashMap<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(HashMap<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	
	
}
