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
import com.jeeplus.modules.train.entity.LessonDIr;
import com.jeeplus.modules.train.dao.LessonDIrDao;

/**
 * 授课时间与章节表Service
 * @author wsp
 * @version 2017-04-03
 */
@Service
@Transactional(readOnly = true)
public class LessonDIrService extends CrudService<LessonDIrDao, LessonDIr> {

	@Autowired
	private LessonDIrDao lessonDIrDao; 
	
	public LessonDIr get(String id) {
		return super.get(id);
	}
	
	public List<LessonDIr> findList(LessonDIr lessonDIr) {
		return super.findList(lessonDIr);
	}
	
	public Page<LessonDIr> findPage(Page<LessonDIr> page, LessonDIr lessonDIr) {
		return super.findPage(page, lessonDIr);
	}
	
	@Transactional(readOnly = false)
	public void save(LessonDIr lessonDIr) {
		super.save(lessonDIr);
	}
	
	@Transactional(readOnly = false)
	public void delete(LessonDIr lessonDIr) {
		super.delete(lessonDIr);
	}
	@Transactional(readOnly = false)
	public void deleteByLessionId(String lessonTimeId){
		lessonDIrDao.deleteByLessionId(lessonTimeId);
	}
	
}