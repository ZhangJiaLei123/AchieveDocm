/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.sys.dao.UserPostDao;
import com.jeeplus.modules.sys.entity.UserPost;

/**
 * 用户岗位关系Service
 * @author panjp
 * @version 2017-03-28
 */
@Service
@Transactional(readOnly = true)
public class UserPostService extends CrudService<UserPostDao, UserPost> {

	@Autowired
	private UserPostDao userPostDao;
	public UserPost get(String id) {
		return super.get(id);
	}
	
	public List<UserPost> findList(UserPost userPost) {
		return super.findList(userPost);
	}
	
	public Page<UserPost> findPage(Page<UserPost> page, UserPost userPost) {
		return super.findPage(page, userPost);
	}
	
	@Transactional(readOnly = false)
	public void save(UserPost userPost) {
		super.save(userPost);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserPost userPost) {
		super.delete(userPost);
	}
	
	@Transactional(readOnly = false)
	public void deleteByUserId(String userId) {
		userPostDao.deleteByUserId(userId);
	}
	public List<Map> findListMap(UserPost userPost){
		return userPostDao.findListMap(userPost);
	}
	
	
}