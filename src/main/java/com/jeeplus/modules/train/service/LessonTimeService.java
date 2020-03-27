/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.train.entity.LessonTime;
import com.jeeplus.modules.train.dao.LessonTimeDao;

/**
 * 授课时间功能Service
 * @author wsp
 * @version 2017-04-03
 */
@Service
@Transactional(readOnly = true)
public class LessonTimeService extends CrudService<LessonTimeDao, LessonTime> {

	public LessonTime get(String id) {
		return super.get(id);
	}
	
	public List<LessonTime> findList(LessonTime lessonTime) {
		return super.findList(lessonTime);
	}
	
	public Page<LessonTime> findPage(Page<LessonTime> page, LessonTime lessonTime) {
		return super.findPage(page, lessonTime);
	}
	
	@Transactional(readOnly = false)
	public void save(LessonTime lessonTime) {
		super.save(lessonTime);
	}
	
	@Transactional(readOnly = false)
	public void delete(LessonTime lessonTime) {
		super.delete(lessonTime);
	}
	
	
	
	
}