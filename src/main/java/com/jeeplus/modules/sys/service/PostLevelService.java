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
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.modules.sys.dao.PostLevelDao;
import com.jeeplus.modules.sys.entity.PostLevel;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 岗位级别Service
 * @author panjp
 * @version 2017-03-19
 */
@Service
@Transactional(readOnly = true)
public class PostLevelService extends CrudService<PostLevelDao, PostLevel> {

	@Autowired
	PostLevelDao postLevelDao;
	
	public PostLevel get(String id) {
		return super.get(id);
	}
	
	public List<PostLevel> findList(PostLevel postLevel) {
		return super.findList(postLevel);
	}
	
	public Page<PostLevel> findPage(Page<PostLevel> page, PostLevel postLevel) {
		return super.findPage(page, postLevel);
	}
	public  Integer findUserListByPostLevelId( PostLevel postLevel){
		return postLevelDao.findUserListByPostLevelId(postLevel);
	};
	@Transactional(readOnly = false)
	public void save(String postinfoId,String[] name) {
		if(null != name  && !"".equals(name)){
			for(int i=0;i<name.length;i++){
				PostLevel pl = new PostLevel();
				if(null != name[i] && !"".equals(name[i])){
					pl.setId(IdGen.uuid());
					pl.setName(name[i]);
					pl.setDelFlag("0");
					pl.setIsNewRecord(true);
					pl.setCreateBy(UserUtils.getUser());
					pl.setUpdateBy(UserUtils.getUser());
					pl.setPostinfoId(postinfoId);
					super.save(pl);
				}
			}
		}
	}
	@Transactional(readOnly = false)
	public void saveObject(PostLevel postLevel) {
		super.save(postLevel);
	}
	
	@Transactional(readOnly = false)
	public void delete(PostLevel postLevel) {
		super.delete(postLevel);
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
	
	public 	List<PostLevel> selectedPostLevelList(Map map){
		return dao.selectedPostLevelList(map);
	}
	/***
	 * 根据岗位id，删除岗位级别的信息
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月19日 下午4:34:06
	  * @param postinfoId
	  * @return
	 */
	@Transactional(readOnly = false)
	public int deletePostLevelByPostInfoId(String postInfoId){
		return dao.deletePostLevelByPostInfoId(postInfoId);
	}
	
}