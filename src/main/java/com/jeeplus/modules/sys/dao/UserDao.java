/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.entity.SelectUser;
import com.jeeplus.modules.sys.entity.User;

/**
 * 用户DAO接口
 * @author jeeplus
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	
	/**
	 * 插入好友
	 */
	public int insertFriend(@Param("id")String id, @Param("userId")String userId, @Param("friendId")String friendId);
	
	/**
	 * 查找好友
	 */
	public User findFriend(@Param("userId")String userId, @Param("friendId")String friendId);
	/**
	 * 删除好友
	 */
	public void deleteFriend(@Param("userId")String userId, @Param("friendId")String friendId);
	
	/**
	 * 
	 * 获取我的好友列表
	 * 
	 */
	public List<User> findFriends(User currentUser);
	
	/**
	 * 
	 * 查询用户-->用来添加到常用联系人
	 * 
	 */
	public List<User> searchUsers(User user);
	
	/**
	 * 
	 */
	
	public List<User>  findListByOffice(User user);
	
	public List<User>  findAddStudyUserList(User user);
	
	public List<User>  findBmStudyUserListForJgSystem(User user);
	
	public List<User>  findAddCourseUserList(User user);
	public List<User>  findAddCourseUserListForJgSystem(User user);
	public List<User> findSelUser(User user);
	
	public List<User> findJsUser(User user);
	/***
	 * 根据用户id，查找用户角色
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月22日 下午10:05:51
	  * @param map
	  * @return
	 */
	public Role selectRoleByUserId(Map map);

	public List<SelectUser> findSelectUser(SelectUser user) ;
	/***
	 * 超过1000条的数据的查询方法---必学
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月30日 上午10:33:15
	  * @param user
	  * @return
	 */
	public List<SelectUser> findListForOverOneThousandBx(SelectUser user);
	/***
	 * 超过1000条的数据的查询方法---选学
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月30日 上午10:33:15
	  * @param user
	  * @return
	 */
	public List<SelectUser> findListForOverOneThousandXx(SelectUser user);
	
	void insertRole(JSONObject object);
	/***
	 * 根据部门id和岗位id，查询对应的用户信息
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年5月6日 上午11:19:20
	  * @param map
	  * @return
	 */
	List<SelectUser> findListForOfficeAndPost(SelectUser selectUser);
	/***
	 * 课程管理
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年5月11日 下午11:30:12
	  * @param selectUser
	  * @return
	 */
	List<SelectUser> findListForOfficeAndPostCourse(SelectUser selectUser);
	
	public List<User> findListByUser(User user);
	
	public void updateUserVerifyUrlById(User user);
	
	/***
	 * 查询消息提醒
	 * @return List<Map>
	 */
	public List<Map> findMenuDaiSh();
	
	public void addUserScore(User user);
	
	public void updateUserScore(User user);
	
	public User getUserScore(User user);
	public User getUserGradeName(User user);
}
