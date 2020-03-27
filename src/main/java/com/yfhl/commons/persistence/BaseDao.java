package com.yfhl.commons.persistence;

import java.util.List;

/** 
 * @author  jiayangli
 * @date 创建时间：2017年2月21日
 * @Description: 
 */
public interface BaseDao<T,Q> {
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月21日
	 * @Description :获取单条数据
	 * @param id
	 * @return
	 * T
	 */
	public T getById(String id);
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月21日
	 * @Description :获取单条数据
	 * @param entity
	 * @return
	 * T
	 */
	public T getByEntity(T entity);
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月21日
	 * @Description :查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param entity
	 * @return
	 * List<T>
	 */
	public List<T> findList(Q entity);
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月21日
	 * @Description :查询所有数据列表
	 * @param entity
	 * @return
	 * List<T>
	 */
	public List<T> findAllList(T entity);
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月21日
	 * @Description :插入数据
	 * @param entity
	 * @return
	 * int
	 */
	public int insert(T entity);
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月21日
	 * @Description :更新数据
	 * @param entity
	 * @return
	 * int
	 */
	public int update(T entity);
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月21日
	 * @Description :删除数据，物理删除，不推荐使用
	 * @param id
	 * @return
	 * int
	 */
	@Deprecated
	public int delete(String id);
	
	/**
	 * @Author  jiayangli
	 * @Date 创建时间：2017年2月21日
	 * @Description :删除数据，一般为逻辑删除，更新状态
	 * @param entity
	 * @return
	 * int
	 */
	public int delete(T entity);
	 

}
