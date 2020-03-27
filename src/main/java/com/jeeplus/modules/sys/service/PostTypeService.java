/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.dao.PostTypeDao;
import com.jeeplus.modules.sys.entity.PostType;

/**
 * 岗位类别Service
 * @author ygq
 * @version 2017-04-18
 */
@Service
@Transactional(readOnly = true)
public class PostTypeService extends CrudService<PostTypeDao, PostType> {

	public PostType get(String id) {
		return super.get(id);
	}
	
	public List<PostType> findList(PostType postType) {
		return super.findList(postType);
	}
	
	public Page<PostType> findPage(Page<PostType> page, PostType postType) {
		return super.findPage(page, postType);
	}
	
	@Transactional(readOnly = false)
	public void save(PostType postType) {
		super.save(postType);
	}
	
	@Transactional(readOnly = false)
	public void delete(PostType postType) {
		super.delete(postType);
	}
	/**
	 * 查询名称是否存在
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月17日 下午6:23:14
	  * @param name
	  * @return
	 */
	public Integer  findNameExists(String name){
		return dao.findNameExists(name);
	}
	
	public List<PostType> findAllList() {
		return dao.findAllListTypes();
	}
	
	
}