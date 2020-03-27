package com.jeeplus.modules.tcourse.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.modules.resource.entity.MesanInfo;
import com.jeeplus.modules.tcourse.dao.TeacherDao;
import com.jeeplus.modules.tcourse.domain.ResourceInfo;
import com.yfhl.commons.domain.QueryScope;
@Service
@Transactional(readOnly = true)
public class TeacherService {
	@Autowired
	private TeacherDao iTeacherDao;
	

	
	public Page<Map> findNewCourseInfo(QueryScope<Map> qs) {
		List<Map> list = iTeacherDao.findNewCourseInfo(qs);
		Page<Map> page = qs.getPage();
		page.setList(list);
		return page;
	}

	public Page<Map> findCourseInfoIng(QueryScope<Map>  qs){
		List<Map> list = iTeacherDao.findCourseInfoIng(qs);
		Page<Map> page = qs.getPage();
		page.setList(list);
		return page;
	}
	
	
	public Page<Map> findCourseImg(QueryScope<Map> qs) {
		List<Map> list = iTeacherDao.findCourseImg(qs);
		Page<Map> page = qs.getPage();
		page.setList(list);
		return page;
	}

	
	public Page<Map> findOfficeList(QueryScope<Map> qs) {
		List<Map> list = iTeacherDao.findOfficeList(qs);
		Page<Map> page = qs.getPage();
		page.setList(list);
		return page;
	}

	
	public Page<Map> findUserList(QueryScope<Map> qs) {
		List<Map> list = iTeacherDao.findUserList(qs);
		Page<Map> page = qs.getPage();
		page.setList(list);
		return page;
	}

	
	public Map findUserById(String id) {
		// TODO Auto-generated method stub
		return iTeacherDao.findUserById(id);
	}

	
	public Map findOfficeById(String id) {
		// TODO Auto-generated method stub
		return iTeacherDao.findOfficeById(id);
	}

	
	public List<Map> findPostILTList(String userId) {
		// TODO Auto-generated method stub
		return iTeacherDao.findPostILTList(userId);
	}

	
	public List<Map> findTreeOffice() {
		// TODO Auto-generated method stub
		return iTeacherDao.findTreeOffice();
	}

	
	public Page<Map> findStudyResource(QueryScope<Map> qs) {
		List<Map> list = iTeacherDao.findStudyResource(qs);
		Page<Map> page = qs.getPage();
		page.setList(list);
		return page;
	}

	
	public Page<Map> findMesanInfo(QueryScope<Map> qs) {
		// TODO Auto-generated method stub
		List<Map> list = iTeacherDao.findMesanInfo(qs);
		Page<Map> page = qs.getPage();
		page.setList(list);
		return page;
		
	}
	
	public Page<Map> findMyMesanInfo(QueryScope<Map> qs) {
		// TODO Auto-generated method stub
		List<Map> list = iTeacherDao.findMyMesanInfo(qs);
		Page<Map> page = qs.getPage();
		page.setList(list);
		return page;
		
	}

	
	public Map findUserByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return iTeacherDao.findUserByLoginName(loginName);
	}

	
	public Map findResourseById(String id) {
		// TODO Auto-generated method stub
		return iTeacherDao.findResourseById(id);
	}

	
	public Map findAppRecByResId(String id) {
		// TODO Auto-generated method stub
		return iTeacherDao.findAppRecByResId(id);
	}

	@Transactional(readOnly = false)
	public boolean deleteResourceById(String id) {
		// TODO Auto-generated method stub
		try {
			iTeacherDao.deleteResourceById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Transactional(readOnly = false)
	public boolean fbResourceById(String id,String isPublic) {
		// TODO Auto-generated method stub
		try {
			iTeacherDao.fbResourceById(id,isPublic);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional(readOnly = false)
	public boolean saveResourceSwfById(String id,String swfUrl) {
		// TODO Auto-generated method stub
		try {
			iTeacherDao.saveResourceSwfById(id,swfUrl);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional(readOnly = false)
	public boolean saveResourceInfo(ResourceInfo resourceInfo) {
		try {
			iTeacherDao.saveResourceInfo(resourceInfo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Transactional(readOnly = false)
	public boolean updateResourceInfo(ResourceInfo resourceInfo) {
		try {
			iTeacherDao.updateResourceInfo(resourceInfo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	public List<Map> findResourceDirTree() {
		return iTeacherDao.findResourceDirTree();
	}

	@Transactional(readOnly = false)
	public boolean insertMesanInfo(MesanInfo mesanInfo) {
		try {
			//iTeacherDao.insertMesanInfo(mesanInfo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = false)
	public boolean updateMesanInfo(MesanInfo mesanInfo) {
		try {
			//iTeacherDao.updateMesanInfo(mesanInfo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = false)
	public boolean deleteMesanInfoById(String id) {
		try {
			iTeacherDao.deleteMesanInfoById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	
	public Map findMesanInfoById(String id) {
		// TODO Auto-generated method stub
		return iTeacherDao.findMesanInfoById(id);
	}

}
