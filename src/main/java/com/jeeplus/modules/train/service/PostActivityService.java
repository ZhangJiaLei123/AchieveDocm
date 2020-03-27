/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.train.entity.PostActivity;
import com.jeeplus.modules.train.dao.PostActivityDao;

/**
 * 岗位活动Service
 * @author 岗位活动
 * @version 2017-03-26
 */
@Service
@Transactional(readOnly = true)
public class PostActivityService extends CrudService<PostActivityDao, PostActivity> {

	@Autowired
	private PostActivityDao postActivityDao;
	public PostActivity get(String id) {
		return super.get(id);
	}
	
	public List<PostActivity> findList(PostActivity postActivity) {
		return super.findList(postActivity);
	}
	
	public Page<PostActivity> findPage(Page<PostActivity> page, PostActivity postActivity) {
		return super.findPage(page, postActivity);
	}
	
	@Transactional(readOnly = false)
	public void save(PostActivity postActivity) {
		super.save(postActivity);
	}
	
	@Transactional(readOnly = false)
	public void delete(PostActivity postActivity) {
		super.delete(postActivity);
	}
	
	@Transactional(readOnly = false)
	public void deletePostActivityByActivId(String activId,String type){
		postActivityDao.deletePostActivityByActivId(activId,type);
	}
	
	public List<PostActivity> showPostActiveList(PostActivity postActivity){
		return postActivityDao.showPostActiveList(postActivity);
	}
}