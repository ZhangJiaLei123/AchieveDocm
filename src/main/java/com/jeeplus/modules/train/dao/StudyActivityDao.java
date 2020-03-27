/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.train.entity.StudyActivity;

/**
 * 学习活动DAO接口
 * @author panjp
 * @version 2017-03-25
 */
@MyBatisDao
public interface StudyActivityDao extends CrudDao<StudyActivity> {

	public List<StudyActivity> findTeacherStudyActivityList(StudyActivity studyActivity);
	
	public List<StudyActivity> myTeacherStudyActivity(StudyActivity studyActivity);
	
	public List<StudyActivity> activeStudyTime(StudyActivity studyActivity);
	
}