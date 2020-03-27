/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.record.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.record.entity.UserStudyRecordMaterial;
import com.jeeplus.modules.course.entity.StudyRecord;
import com.jeeplus.modules.record.dao.UserStudyRecordMaterialDao;

/**
 * 记录Service
 * @author w
 * @version 2017-06-29
 */
@Service
@Transactional(readOnly = true)
public class UserStudyRecordMaterialService extends CrudService<UserStudyRecordMaterialDao, UserStudyRecordMaterial> {

	@Autowired
	private UserStudyRecordMaterialDao userStudyRecordMaterialDao;
	
	public UserStudyRecordMaterial get(String id) {
		return super.get(id);
	}
	
	public List<UserStudyRecordMaterial> findList(UserStudyRecordMaterial userStudyRecordMaterial) {
		return super.findList(userStudyRecordMaterial);
	}
	
	public Page<UserStudyRecordMaterial> findPage(Page<UserStudyRecordMaterial> page, UserStudyRecordMaterial userStudyRecordMaterial) {
		return super.findPage(page, userStudyRecordMaterial);
	}
	
	@Transactional(readOnly = false)
	public void save(UserStudyRecordMaterial userStudyRecordMaterial) {
		super.save(userStudyRecordMaterial);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserStudyRecordMaterial userStudyRecordMaterial) {
		super.delete(userStudyRecordMaterial);
	}
	
	public UserStudyRecordMaterial getCurrentDate(UserStudyRecordMaterial userStudyRecordMaterial) {
		return userStudyRecordMaterialDao.getCurrentDate(userStudyRecordMaterial);
	}
	
	
}