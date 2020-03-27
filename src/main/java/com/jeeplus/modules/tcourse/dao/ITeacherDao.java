package com.jeeplus.modules.tcourse.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.resource.entity.MesanInfo;
import com.jeeplus.modules.resource.entity.ResourceInfo;
import com.jeeplus.modules.tcourse.domain.CustomerMember;
import com.yfhl.commons.domain.QueryScope;
import com.yfhl.commons.persistence.BaseDao;

@MyBatisDao
public interface ITeacherDao  extends BaseDao<CustomerMember, QueryScope<CustomerMember>>{
	
	public List<Map> findNewCourseInfo(QueryScope<Map>  map);
	
	public List<Map> findCourseImg(QueryScope<Map>  qs);
	
	public List<Map> findOfficeList(QueryScope<Map>  qs);
	
	public List<Map> findUserList(QueryScope<Map>  qs);
	
	public Map findUserById(String id);
	
	public Map findOfficeById(String id);
	
	public List<Map> findPostILTList(String userId);
	
	public List<Map> findTreeOffice(); 
	
	public List<Map> findStudyResource(QueryScope<Map> qs);
	
	public List<Map> findMesanInfo(QueryScope<Map> qs);
	
	public Map findUserByLoginName(String loginName);
	
	public Map findResourseById(String id);
	
	public Map findAppRecByResId(String id);
	
	public void deleteResourceById(String id);
	
	public void fbResourceById(@Param("id") String id,@Param("isPublic") String isPublic);
	
	public boolean saveResourceInfo(ResourceInfo resourceInfo);
	
	public boolean updateResourceInfo(ResourceInfo resourceInfo);
	
	public List<Map> findResourceDirTree();
	
	public boolean insertMesanInfo(MesanInfo mesanInfo);
	
	public boolean updateMesanInfo(MesanInfo mesanInfo);
	
	public void deleteMesanInfoById(String id);
	
	public Map findMesanInfoById(String id);
	
}
