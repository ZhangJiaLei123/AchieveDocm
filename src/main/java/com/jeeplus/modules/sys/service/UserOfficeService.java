/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.modules.sys.dao.OfficeDao;
import com.jeeplus.modules.sys.dao.RoleDao;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.dao.UserOfficeDao;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.UserOffice;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 用户组织关系Service
 * @author panjp
 * @version 2017-03-28
 */
@Service
@Transactional(readOnly = true)
public class UserOfficeService extends CrudService<UserOfficeDao, UserOffice> {

	@Autowired
	private UserOfficeDao userOfficeDao;

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private OfficeDao officeDao;
	
	@Autowired
	private UserDao userDao;
	
	public UserOffice get(String id) {
		return super.get(id);
	}
	
	public List<UserOffice> findList(UserOffice userOffice) {
		return super.findList(userOffice);
	}
	
	public Page<UserOffice> findPage(Page<UserOffice> page, UserOffice userOffice) {
		return super.findPage(page, userOffice);
	}
	
	@Transactional(readOnly = false)
	public void save(UserOffice userOffice) {
		String userId = userOffice.getUserId();
		userOfficeDao.deleteUserRole(userId);
		userOfficeDao.saveUserRole(userId, userOffice.getRoleId());
		userOfficeDao.deleteUserOffice(userId);
		String userOfficeStr = userOffice.getOfficeStr();
		if(null != userOfficeStr && !"".equals(userOfficeStr)){
			String str[] = userOfficeStr.split(",");
			for (int i = 0; i < str.length; i++) {
				UserOffice us = new UserOffice();
				us.setId(IdGen.uuid());
				us.setUserId(userId);
				us.setOfficeId(str[i]);
				us.setIsNewRecord(true);
				super.save(us);
			}
		}
	}
	@Transactional(readOnly = false)
	public void saveUserRoleUserOffice(UserOffice userOffice,HttpServletRequest request) {
		boolean isNx_teacher = false;
		String userId = userOffice.getUserId();
		userOfficeDao.deleteUserRole(userId);
		userOfficeDao.saveUserRole(userId, userOffice.getRoleId());
		userOfficeDao.deleteUserOffice(userId);
		String userOfficeStr = userOffice.getOfficeStr();
		String checkAll = request.getParameter("checkAll");
		Role role = roleDao.get(userOffice.getRoleId());
		if(UserUtils.USER_TEACHER_ENNAME.equals(role.getEnname()) ||
				UserUtils.USER_NX_TEACHER_ENNAME.equals(role.getEnname()) ||
				UserUtils.USER_PX_SYSTEM_ENNAME.equals(role.getEnname())){//如果是教師，内訓師，機構管理與才給組織權限。
			if(UserUtils.USER_NX_TEACHER_ENNAME.equals(role.getEnname())) {
				isNx_teacher = true;
				User user = new User();
				user.setId(userId);
				user.setVerifyUrl(userOffice.getVerifyUrl());
				userDao.updateUserVerifyUrlById(user);
			}
			if(null != checkAll && !"".equals(checkAll)){
				String officeType = request.getParameter("officeType");
				String officeLevel = request.getParameter("officeLevel");
				String areaId = request.getParameter("areaId");
				Office office = new Office();
				office.setOfficeTypeId(officeType);
				office.setGrade(officeLevel);
				office.setAreaId(areaId);
				List<Office> lsOffice = officeDao.findList(office);
				if(null!=lsOffice && !lsOffice.isEmpty()){
					for (int i = 0; i < lsOffice.size(); i++) {
						UserOffice us = new UserOffice();
						us.setId(IdGen.uuid());
						us.setUserId(userId);
						us.setOfficeId(lsOffice.get(i).getId());
						us.setIsNewRecord(true);
						super.save(us);
					}
				}
			}else{
				if(null != userOfficeStr && !"".equals(userOfficeStr)){
					String str[] = userOfficeStr.split(",");
					for (int i = 0; i < str.length; i++) {
						UserOffice us = new UserOffice();
						us.setId(IdGen.uuid());
						us.setUserId(userId);
						us.setOfficeId(str[i]);
						us.setIsNewRecord(true);
						super.save(us);
					}
				}
			}
		}
		if(!isNx_teacher) {
			User user = new User();
			user.setId(userId);
			user.setVerifyUrl(null);
			userDao.updateUserVerifyUrlById(user);
		}
		//重新给用户分配角色
		UserUtils.get(userId).setRole(role);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserOffice userOffice) {
		super.delete(userOffice);
	}
	
	public List<Map> findAllRole(){
		return userOfficeDao.findAllRole();
	}
	
	public void deleteUserRole(String roleId){
		userOfficeDao.deleteUserRole(roleId);
	}
	
	
}