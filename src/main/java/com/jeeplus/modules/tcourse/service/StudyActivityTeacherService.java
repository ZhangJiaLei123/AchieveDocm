package com.jeeplus.modules.tcourse.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.modules.tcourse.dao.IStudyActivityDao;
import com.yfhl.commons.domain.QueryScope;


@Service
@Transactional(readOnly=true)
public class StudyActivityTeacherService{
	@Autowired
	IStudyActivityDao iStudyActivityDao;
	
	public Page<Map> findStudyActivit(QueryScope<Map> qs) {
		List<Map> list = iStudyActivityDao.findStudyActivit(qs);
		Page<Map> page = qs.getPage();
		page.setList(list);
		return page;
	}

	
	public Map findTrainProgramById(String id) {
		// TODO Auto-generated method stub
		return iStudyActivityDao.findTrainProgramById(id);
	}

	
	public List<Map> findProgramActivityByProgramId(String trainProgramId) {
		// TODO Auto-generated method stub
		return iStudyActivityDao.findProgramActivityByProgramId(trainProgramId);
	}

}
