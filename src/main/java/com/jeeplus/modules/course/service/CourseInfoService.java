/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.course.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.BaseService;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.course.dao.CourseInfoDao;
import com.jeeplus.modules.course.entity.CourseInfo;
import com.jeeplus.modules.course.entity.CourseInfoCondition;
import com.jeeplus.modules.course.entity.OfficeCourse;
import com.jeeplus.modules.course.entity.PostCourse;
import com.jeeplus.modules.course.entity.UserCourse;
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.PostInfo;
import com.jeeplus.modules.sys.entity.SelectUser;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.PostInfoService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 课程管理Service
 * @author panjp
 * @version 2017-03-21
 */
@Service
@Transactional(readOnly = true)
public class CourseInfoService extends CrudService<CourseInfoDao, CourseInfo> {
	@Autowired
	private UserCourseService userCourseService;
	
	@Autowired
	private PostCourseService postCourseService;
	
	@Autowired
	private OfficeCourseService officeCourseService;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private PostInfoService postInfoService;
	
	@Autowired
	private CourseInfoDao courseInfoDao;
	
	@Autowired
	private SystemService systemService;
	
	public CourseInfo get(String id) {
		return super.get(id);
	}
	
	public List<CourseInfo> findList(CourseInfo courseInfo) {
		return super.findList(courseInfo);
	}
	
	public Page<CourseInfo> findPage(Page<CourseInfo> page, CourseInfo courseInfo) {
		BaseService.dataScopeFileForJgSystemForCourse(courseInfo);
		return super.findPage(page, courseInfo);
	}
	public Page<CourseInfo> findMyCourseInfoPage(Page<CourseInfo> page, CourseInfo courseInfo) {
		courseInfo.setPage(page);
		page.setList(courseInfoDao.findMyCourseInfoPage(courseInfo));
		return page;
	}
	public Page<CourseInfo> courseStudyTime(Page<CourseInfo> page, CourseInfo courseInfo) {
		courseInfo.setPage(page);
		page.setList(courseInfoDao.courseStudyTime(courseInfo));
		return page;
	}
	
	/**
	 * 
	 * @param page复制课程查询审核通过的课程
	 * @param courseInfo
	 * @return
	 */
	public Page<CourseInfo> courseInfoIsShSuccess(Page<CourseInfo> page, CourseInfo courseInfo) {
		/*String userRoleEnName = UserUtils.getUser().getRoleEnName();
		if(!UserUtils.USER_SYSTEM_ROLE_ENNAME.equals(userRoleEnName)){//如果是管理员角色则不设置权限
			courseInfo.setLoginUserId(UserUtils.getUser().getId());
		}*/
		//添加数据权限
		//BaseService.dataScopeFileForOffice(courseInfo);
		
		courseInfo.setPage(page);
		page.setList(courseInfoDao.courseInfoIsShSuccess(courseInfo));
		return page;
	}
	
	public Page<CourseInfo> findTeacherPage(Page<CourseInfo> page, CourseInfo courseInfo) {
		courseInfo.setPage(page);
		page.setList(courseInfoDao.findTeacherPage(courseInfo));
		return page;
	}
	public Page<CourseInfo> findMyTeacherPage(Page<CourseInfo> page, CourseInfo courseInfo) {
		courseInfo.setPage(page);
		page.setList(courseInfoDao.findMyTeacherPage(courseInfo));
		return page;
	}
	
	
	@Transactional(readOnly = false)
	public void delete(CourseInfo courseInfo) {
		super.delete(courseInfo);
	}
	
	@Transactional(readOnly = false)
	public void fbCourseInfoById(String id) {
		courseInfoDao.fbCourseInfoById(id);
	}
	/****************************************20170510-保存信息******************************************************************************/
	//保存基本的课程信息
	@Transactional(readOnly = false)
	public void saveBase(CourseInfo courseInfo) {
		super.save(courseInfo);
	}
	@Transactional(readOnly = false)
	public void saveBx(CourseInfo courseInfo,String type,HttpServletRequest request,CourseInfoConditionService courseInfoConditionService) {
		save(courseInfo,request,courseInfoConditionService,type);
	}
	@Transactional(readOnly = false)
	public void saveXx(CourseInfo courseInfo,HttpServletRequest request,CourseInfoConditionService courseInfoConditionService) {
		save(courseInfo,request,courseInfoConditionService,"0");//保存选学
	}
	@Transactional(readOnly = false)
	private void save(CourseInfo courseInfo,HttpServletRequest request,CourseInfoConditionService courseInfoConditionService,String type){
		//机构
		String isCheckAll = request.getParameter("isCheckAll");
		String grade = request.getParameter("grade");//机构等级
		String officeType = request.getParameter("officeType");//
		String officeTypeName = request.getParameter("officeTypeName");//
		
		String officeName = request.getParameter("officeName");
		String officIds = request.getParameter("officIds");
		//大区
		
		 String distrctId  = request.getParameter("distrctId");//所属区域ID
		 String provinceId  = request.getParameter("provinceId");//所属省ID
		 String cityId  = request.getParameter("cityId");//所属城市ID
		
		//岗位
		String isCheckPost = request.getParameter("isCheckPost");
		String postIds = request.getParameter("postIds");
		String postType = request.getParameter("postType");
		String postName = request.getParameter("postName");
				
		String courseId = courseInfo.getId();
		//删除课程组织机构关系
		officeCourseService.deleteOfficeCourseByCourseId(courseId,type);
		//删除岗位课程
		postCourseService.deletePostCourseByCourseId(courseId,type);
		//删除学员课程
		Map<String,String>  map = new HashMap<String,String>();
		map.put("courseId", courseId);
		map.put("type",type);
		//删除学员活动
		userCourseService.deleteUserCourseByCourID(map);
		
		User user = UserUtils.getUser();//获取当前用户
		if("1".equals(isCheckAll)){//全选必学组织
			Office office = new Office();
			office.setOfficeType(officeType);
			office.setGrade(grade);
			office.setDistrctId(distrctId);
			office.setProvinceId(provinceId);
			office.setCityId(cityId);
			office.setName(officeName);
			office.setDelFlag("0");
			List<Office> lsOffice = officeService.findAllList(office);
			if(null!=lsOffice && !lsOffice.isEmpty()){
				for (int i = 0; i < lsOffice.size(); i++) {
					String officeId = lsOffice.get(i).getId();
					OfficeCourse of = new OfficeCourse();
					of.setId(UUID.randomUUID().toString());
					of.setOfficeId(officeId);
					of.setCourseId(courseId);
					of.setType(type);
					of.setIsNewRecord(true);
					of.setCreateBy(user);
					of.setUpdateBy(user);
					of.setCreateDate(new Date());
					of.setUpdateDate(new Date());
					officeCourseService.save(of);
				}
			}
		}else{
			if(null != officIds && !"".equals(officIds)){
				String ofidsBx[] = officIds.split(",");
				for (int i = 0; i < ofidsBx.length; i++) {
					OfficeCourse of = new OfficeCourse();
					of.setId(UUID.randomUUID().toString());
					of.setOfficeId(ofidsBx[i]);
					of.setCourseId(courseId);
					of.setType(type);
					of.setIsNewRecord(true);
					of.setCreateBy(user);
					of.setUpdateBy(user);
					of.setCreateDate(new Date());
					of.setUpdateDate(new Date());
					officeCourseService.save(of);
				}
			}
		}
		//添加岗位活动关系
		if("1".equals(isCheckPost)||("".equals(isCheckPost) && (null == postIds || "".equals(postIds)))){
			isCheckPost = "1";//如果为空了默认为全选
			PostInfo postInfo = new PostInfo();
			postInfo.setPostType(postType);
			postInfo.setName(postName);
			List<PostInfo> lsPostInfo = postInfoService.findPostListForSearch(postInfo);
			if(null!=lsPostInfo && !lsPostInfo.isEmpty()){
				for (int i = 0; i < lsPostInfo.size(); i++) {
					String positionId = lsPostInfo.get(i).getPositLevelId();//注意，这块保存的是postLevel的id
					PostCourse po = new PostCourse();
					po.setId(UUID.randomUUID().toString());
					po.setPostId(positionId);
					po.setCourseId(courseId);
					po.setType(type);
					po.setIsNewRecord(true);
					po.setCreateBy(user);
					po.setUpdateBy(user);
					po.setCreateDate(new Date());
					po.setUpdateDate(new Date());
					postCourseService.save(po);
				}
			}
		}else{
			if(null != postIds && !"".equals(postIds)){
				String poidsBx[] = postIds.split(",");
				for (int i = 0; i < poidsBx.length; i++) {
					PostCourse po = new PostCourse();
					po.setId(UUID.randomUUID().toString());
					po.setPostId(poidsBx[i]);
					po.setCourseId(courseId);
					po.setType(type);
					po.setIsNewRecord(true);
					po.setCreateBy(user);
					po.setUpdateBy(user);
					po.setCreateDate(new Date());
					po.setUpdateDate(new Date());
					postCourseService.save(po);
				}
			}
		}

		//根据组织信息和岗位信息,及相关的查询条件，查询出必选的人员信息
		SelectUser su = new SelectUser();
		su.setCourseId(courseId);
		su.setSearchType(type);
		if("1".equals(type)){//必学
			List<SelectUser> listUsers = systemService.findListForOfficeAndPostCourse(su);
			List<UserCourse> listAct = new ArrayList<UserCourse>();
			if(null != listUsers && listUsers.size()>0){
				for (int i = 0; i < listUsers.size(); i++) {
					UserCourse uc = new UserCourse();
					uc.setId(UUID.randomUUID().toString());
					uc.setUserId(listUsers.get(i).getId());
					uc.setCourseId(courseId);
					uc.setType(type);
					uc.setIsNewRecord(true);
					uc.setCreateBy(user);
					uc.setUpdateBy(user);
					uc.setCreateDate(new Date());
					uc.setUpdateDate(new Date());
					listAct.add(uc);
					//如果已经通过导入途径导入选学了，则删除掉
					Map<String,String>  mapParam = new HashMap<String,String> ();
					mapParam.put("userId", listUsers.get(i).getId());
					mapParam.put("courseId", courseId);
					mapParam.put("type", "0");
					userCourseService.deleteUserCourseByCourID(mapParam);
				}
				if(null != listAct && listAct.size()>0){
					userCourseService.saveBatch(listAct);
				}
			}
		}else if("0".equals(type)){//保存可选学员
			List<SelectUser> listUsers = systemService.findListForOfficeAndPostCourse(su);
			if(null != listUsers && listUsers.size()>0){
				for (int i = 0; i < listUsers.size(); i++) {
					UserCourse uc = new UserCourse();
					uc.setUserId(listUsers.get(i).getId());
					uc.setCourseId(courseId);
					UserCourse ucTest = userCourseService.get(uc);
					if(null == ucTest){//如果已经在必选学员中了，则不再保存
						uc.setId(UUID.randomUUID().toString());
						uc.setType(type);
						uc.setIsNewRecord(true);
						uc.setCreateBy(user);
						uc.setUpdateBy(user);
						uc.setCreateDate(new Date());
						uc.setUpdateDate(new Date());
						userCourseService.save(uc);
					}
				}
			}
		}
		//先删除之前的查询条件
		Map<String,String> mapSA = new HashMap<String,String>();
		mapSA.put("courseId", courseId);
		mapSA.put("type", type);
		courseInfoConditionService.deleteByActivityIdAndType(mapSA);
		//保存查询条件
		CourseInfoCondition sac = new CourseInfoCondition();
		sac.setCourseId(courseId);
		sac.setOrgCheckAll(isCheckAll);//全选组织
		sac.setOrgLevel(grade);//机构等级
		sac.setOrgType(officeType);//机构类别
		sac.setOrgTypeName(officeTypeName);//机构类别名称
	
		sac.setOfficeName(officeName);//
		sac.setPostCheckAll(isCheckPost);
		sac.setPostType(postType);
		sac.setPostName(postName);
		sac.setType(type);
		sac.setDistrctId(distrctId);
		sac.setProvinceId(provinceId);
		sac.setCityId(cityId);
		courseInfoConditionService.save(sac);
	}
	
	//保存必选学员和可选学员
	@Transactional(readOnly = false)
	public int saveForImport(String type,String userId,String courseId,String edit){
		int result = 1;
		User user = UserUtils.getUser();//获取当前用户
		if("1".equals(type)){//必学
			//如果可学学员在必学中已经有了，则给予排除
			UserCourse uc = new UserCourse();
			uc.setUserId(userId);
			uc.setCourseId(courseId);
			uc.setType("1");
			//重复导入的数据，则不进行导入
			UserCourse ucTest = userCourseService.get(uc);
            if(ucTest == null){		
				uc.setId(UUID.randomUUID().toString());
				uc.setUserId(userId);
				uc.setCourseId(courseId);
				uc.setType(type);
				uc.setIsNewRecord(true);
				uc.setCreateBy(user);
				uc.setUpdateBy(user);
				uc.setCreateDate(new Date());
				uc.setUpdateDate(new Date());
				userCourseService.save(uc);
				//必学的优先级高，重新导入必学的时候，如果该学员已经在选学中有了，则将该学员从选学中删除
				if(null != edit && !"".equals(edit)){
					Map map = new HashMap();
					map.put("userId", userId);
					map.put("courseId", courseId);
					map.put("type", 0);
					userCourseService.deleteUserCourseByCourID(map);
				}
           }else{
            	result = 0;
            }
		}else if("0".equals(type)){//保存可选学员
			UserCourse uc = new UserCourse();
			uc.setUserId(userId);
			uc.setCourseId(courseId);
			UserCourse ucTest = userCourseService.get(uc);
			if(null == ucTest){//如果已经在必选学员中了，则不再保存
				uc.setId(UUID.randomUUID().toString());
				uc.setType(type);
				uc.setIsNewRecord(true);
				uc.setCreateBy(user);
				uc.setUpdateBy(user);
				uc.setCreateDate(new Date());
				uc.setUpdateDate(new Date());
				userCourseService.save(uc);
			}else{
				result = 0;
			}
		}
		return result;
	}
	
}