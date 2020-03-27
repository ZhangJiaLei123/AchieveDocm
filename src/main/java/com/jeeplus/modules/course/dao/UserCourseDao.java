/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.course.entity.UserCourse;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.train.entity.UserActivity;

/**
 * 学员课程关系表DAO接口
 * @author panjp
 * @version 2017-03-24
 */
@MyBatisDao
public interface UserCourseDao extends CrudDao<UserCourse> {

	public List<UserCourse> findUserCourse(UserCourse userCourse);
	public List<UserCourse> findUserCourseInfoList(UserCourse userCourse);
	
	public int findUserOfficeTypeCount(@Param("courseId")  String courseId,@Param("type") String type);
	
	public int findUserCount(@Param("courseId")  String courseId,@Param("type") String type);
	
	public List<UserCourse> findOfficeListMap( UserCourse userCourse);
	
	public List<Map> findUserByOfficeId(@Param("officeId") String officeId);
	
	public void deleteUserCourseByCourID(Map map);
	
	public List<User> findUserList(User user);
	public List<User> findUserListForJgSystem(User user);
	
	public List<User> findBmUserList(User user);
	
	public void deleteById(@Param("id")String id);
	
	/***
	 * 批量添加数据
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年5月6日 下午5:06:58
	  * @param list
	 */
	public void saveBatch(List<UserCourse> list);
	
	//根据课程id，得到课程人数
	public int getTotalCountByCourseId(UserCourse userCourse);
	
	public void deleteByUserIdAndCourseId(UserCourse userCourse);
	
	public List<UserCourse> findListByEndTime(UserCourse userCourse);
}