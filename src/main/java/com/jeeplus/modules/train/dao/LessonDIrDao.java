/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.dao;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.train.entity.LessonDIr;

/**
 * 授课时间与章节表DAO接口
 * @author wsp
 * @version 2017-04-03
 */
@MyBatisDao
public interface LessonDIrDao extends CrudDao<LessonDIr> {

	public void deleteByLessionId(@Param("lessonTimeId")String lessonTimeId);
}