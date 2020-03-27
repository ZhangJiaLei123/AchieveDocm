/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.record.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.course.entity.StudyRecord;
import com.jeeplus.modules.record.entity.UserStudyRecordMaterial;

/**
 * 记录DAO接口
 * @author w
 * @version 2017-06-29
 */
@MyBatisDao
public interface UserStudyRecordMaterialDao extends CrudDao<UserStudyRecordMaterial> {

	public UserStudyRecordMaterial getCurrentDate(UserStudyRecordMaterial userStudyRecordMaterial);
	
}