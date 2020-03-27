/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.train.dao.StudyActivityConditionDao;
import com.jeeplus.modules.train.entity.StudyActivityCondition;

/**
 * 查询条件Service
 * @author ygq
 * @version 2017-04-26
 */
@Service
@Transactional(readOnly = true)
public class StudyActivityConditionService extends CrudService<StudyActivityConditionDao, StudyActivityCondition> {

	public StudyActivityCondition get(String id) {
		return super.get(id);
	}
	
	public List<StudyActivityCondition> findList(StudyActivityCondition studyActivityCondition) {
		return super.findList(studyActivityCondition);
	}
	
	public Page<StudyActivityCondition> findPage(Page<StudyActivityCondition> page, StudyActivityCondition studyActivityCondition) {
		return super.findPage(page, studyActivityCondition);
	}
	
	@Transactional(readOnly = false)
	public void save(StudyActivityCondition studyActivityCondition) {
		super.save(studyActivityCondition);
	}
	
	@Transactional(readOnly = false)
	public void delete(StudyActivityCondition studyActivityCondition) {
		super.delete(studyActivityCondition);
	}
	
	public StudyActivityCondition findActivityConditionByActivityId(Map map){
		return dao.findActivityConditionByActivityId(map);
	}
	
	public int deleteByActivityIdAndType(Map map){
		return dao.deleteByActivityIdAndType(map);
	}
	
}