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
import com.jeeplus.modules.train.entity.ActivityDir;
import com.jeeplus.modules.train.dao.ActivityDirDao;

/**
 * 活动章节目录Service
 * @author wsp
 * @version 2017-04-02
 */
@Service
@Transactional(readOnly = true)
public class ActivityDirService extends CrudService<ActivityDirDao, ActivityDir> {
	
	@Autowired
	private ActivityDirDao activityDirDao;
	
	public ActivityDir get(String id) {
		return super.get(id);
	}
	
	public List<ActivityDir> findList(ActivityDir activityDir) {
		return super.findList(activityDir);
	}
	public List<ActivityDir> findListUserScore(ActivityDir activityDir) {
		return activityDirDao.findListUserScore(activityDir);
	}
	
	public Page<ActivityDir> findPage(Page<ActivityDir> page, ActivityDir activityDir) {
		return super.findPage(page, activityDir);
	}
	
	public Page<ActivityDir> findListUserScorePage(Page<ActivityDir> page, ActivityDir activityDir) {
		activityDir.setPage(page);
		page.setList( activityDirDao.findListUserScore(activityDir));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(ActivityDir activityDir) {
		super.save(activityDir);
	}
	
	@Transactional(readOnly = false)
	public void delete(ActivityDir activityDir) {
		super.delete(activityDir);
	}
	
	public List<ActivityDir>getListForLessionId(ActivityDir activityDir){
		return activityDirDao.getListForLessionId(activityDir);
	}
	public List<ActivityDir>getListAllDirForLessionId(ActivityDir activityDir){
		return activityDirDao.getListAllDirForLessionId(activityDir);
	}
	
}