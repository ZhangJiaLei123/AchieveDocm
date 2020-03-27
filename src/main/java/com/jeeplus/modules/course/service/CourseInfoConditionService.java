/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.course.dao.CourseInfoConditionDao;
import com.jeeplus.modules.course.entity.CourseInfoCondition;
/**
 * 查询条件Service
 * @author ygq
 * @version 2017-04-26
 */
@Service
@Transactional(readOnly = true)
public class CourseInfoConditionService extends CrudService<CourseInfoConditionDao, CourseInfoCondition> {

	public CourseInfoCondition get(String id) {
		return super.get(id);
	}
	
	public List<CourseInfoCondition> findList(CourseInfoCondition CourseInfoCondition) {
		return super.findList(CourseInfoCondition);
	}
	
	public Page<CourseInfoCondition> findPage(Page<CourseInfoCondition> page, CourseInfoCondition CourseInfoCondition) {
		return super.findPage(page, CourseInfoCondition);
	}
	
	@Transactional(readOnly = false)
	public void save(CourseInfoCondition CourseInfoCondition) {
		super.save(CourseInfoCondition);
	}
	
	@Transactional(readOnly = false)
	public void delete(CourseInfoCondition CourseInfoCondition) {
		super.delete(CourseInfoCondition);
	}
	
	public CourseInfoCondition findActivityConditionByActivityId(Map map){
		return dao.findActivityConditionByActivityId(map);
	}
	
	public int deleteByActivityIdAndType(Map map){
		return dao.deleteByActivityIdAndType(map);
	}
	
}