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
import com.jeeplus.modules.sys.dao.PostInfoDao;
import com.jeeplus.modules.sys.dao.PostTypeDao;
import com.jeeplus.modules.sys.entity.PostInfo;
import com.jeeplus.modules.sys.entity.PostType;

/**
 * 岗位管理Service
 * @author panjp
 * @version 2017-03-19
 */
@Service
@Transactional(readOnly = true)
public class PostInfoService extends CrudService<PostInfoDao, PostInfo> {

	@Autowired
	private PostInfoDao postInfoDao;
	
	@Autowired
	private PostTypeDao postTypeDao;
	
	
	public PostInfo get(String id) {
		return super.get(id);
	}
	
	public List<PostInfo> findList(PostInfo postInfo) {
		return super.findList(postInfo);
	}
	
	public Page<PostInfo> findPage(Page<PostInfo> page, PostInfo postInfo) {
		postInfo.setPage(page);
		page.setList(postInfoDao.findPostList(postInfo));
		return page;
	}
	
	public List<PostInfo> findPostList(PostInfo postInfo){
		return postInfoDao.findPostList(postInfo);
	}
	public Page<PostInfo> findPostListPage(Page<PostInfo> page, PostInfo postInfo) {
		return super.findPage(page, postInfo);
	}
	
	
	
	@Transactional(readOnly = false)
	public void save(PostInfo postInfo) {
		super.save(postInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(PostInfo postInfo) {
		super.delete(postInfo);
	}
	public List<Map> findPostListByType(String postType){
		return postInfoDao.findPostListByType(postType);
	}
	
	public List<Map> findPostLevelByPost(String postId){
		return postInfoDao.findPostLevelByPost(postId);
	}
	
	public List<PostInfo> findPostListAll(PostInfo postInfo){
		return  postInfoDao.findPostList(postInfo);
	}
	public List<PostType> findAllListTypes(){
		return postTypeDao.findAllListTypes();
	}
	
	public Page<PostInfo> findPostListForSearch(Page<PostInfo> page, PostInfo postInfo) {
		postInfo.setPage(page);
		page.setList(postInfoDao.findPostListForSearch(postInfo));
		return page;
	}
	public List<PostInfo> findPostListForSearch(PostInfo postInfo){
		return  postInfoDao.findPostListForSearch(postInfo);
	}
	
}