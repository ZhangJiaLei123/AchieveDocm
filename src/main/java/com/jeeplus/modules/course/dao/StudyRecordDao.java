/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.dao;


import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.course.entity.StudyRecord;

/**
 * 学习记录信息DAO接口
 * @author wsp
 * @version 2017-04-09
 */
@MyBatisDao
public interface StudyRecordDao extends CrudDao<StudyRecord> {
	public StudyRecord getCurrentDate(StudyRecord studyRecord);
}