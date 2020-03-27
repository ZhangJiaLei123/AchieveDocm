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
import com.jeeplus.modules.course.entity.OfficeCourse;
import com.jeeplus.modules.course.dao.OfficeCourseDao;
import com.jeeplus.modules.sys.dao.OfficeDao;
import com.jeeplus.modules.sys.entity.Office;

/**
 * 组织课程关系Service
 * @author panjp
 * @version 2017-03-24
 */
@Service
@Transactional(readOnly = true)
public class OfficeCourseService extends CrudService<OfficeCourseDao, OfficeCourse> {
	@Autowired
	OfficeCourseDao officeCourseDao;
	
	@Autowired
	OfficeDao officeDao;
	
	public OfficeCourse get(String id) {
		return super.get(id);
	}
	
	public List<OfficeCourse> findList(OfficeCourse officeCourse) {
		return super.findList(officeCourse);
	}
	
	public Page<OfficeCourse> findPage(Page<OfficeCourse> page, OfficeCourse officeCourse) {
		return super.findPage(page, officeCourse);
	}
	
	@Transactional(readOnly = false)
	public void save(OfficeCourse officeCourse) {
		super.save(officeCourse);
	}
	
	@Transactional(readOnly = false)
	public void delete(OfficeCourse officeCourse) {
		super.delete(officeCourse);
	}
	
	public Page<OfficeCourse> showOfficeCourseLlist(Page<OfficeCourse> page, OfficeCourse officeCourse) {
		officeCourse.setPage(page);
		page.setList(officeCourseDao.findOfficeCourse(officeCourse));
		return page;
	}
	
	public Page<Office> showshCourseOfficeCourseList(Page<Office> page, Office office) {
		office.setPage(page);
		page.setList(officeDao.showshCourseOfficeCourseList(office));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void deleteOfficeCourseByCourseId(String courseId,String type) {
		officeCourseDao.deleteOfficeCourseByCourseId(courseId,type);
	}
	
	
	
	
}