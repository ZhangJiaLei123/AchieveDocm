/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.course.entity.StudyRecord;
import com.jeeplus.modules.course.dao.StudyRecordDao;

/**
 * 学习记录信息Service
 * @author wsp
 * @version 2017-04-09
 */
@Service
@Transactional(readOnly = true)
public class StudyRecordService extends CrudService<StudyRecordDao, StudyRecord> {

	@Autowired
	private StudyRecordDao studyRecordDao;
	
	public StudyRecord get(String id) {
		return super.get(id);
	}
	
	public List<StudyRecord> findList(StudyRecord studyRecord) {
		return super.findList(studyRecord);
	}
	
	public Page<StudyRecord> findPage(Page<StudyRecord> page, StudyRecord studyRecord) {
		return super.findPage(page, studyRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(StudyRecord studyRecord) {
		super.save(studyRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(StudyRecord studyRecord) {
		super.delete(studyRecord);
	}
	
	public StudyRecord getCurrentDate(StudyRecord studyRecord) {
		return studyRecordDao.getCurrentDate(studyRecord);
	}
	
	
	
}