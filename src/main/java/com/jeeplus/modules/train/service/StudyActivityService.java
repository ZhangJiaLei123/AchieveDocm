/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.service;

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
import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.PostInfo;
import com.jeeplus.modules.sys.entity.SelectUser;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.PostInfoService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.train.dao.StudyActivityDao;
import com.jeeplus.modules.train.entity.OfficeActivity;
import com.jeeplus.modules.train.entity.PostActivity;
import com.jeeplus.modules.train.entity.StudyActivity;
import com.jeeplus.modules.train.entity.StudyActivityCondition;
import com.jeeplus.modules.train.entity.UserActivity;

/**
 * 学习活动Service
 * @author panjp
 * @version 2017-03-25
 */
@Service
@Transactional(readOnly = true)
public class StudyActivityService extends CrudService<StudyActivityDao, StudyActivity> {
	@Autowired
	private UserActivityService userActivityService;
	
	@Autowired
	private PostActivityService postActivityService;
	
	@Autowired
	private OfficeActivityService officeActivityService;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private PostInfoService postInfoService;
	
	@Autowired
	private StudyActivityDao studyActivityDao;
	
	@Autowired
	private SystemService systemService;
	
	public StudyActivity get(String id) {
		return super.get(id);
	}
	
	public List<StudyActivity> findList(StudyActivity studyActivity) {
		return super.findList(studyActivity);
	}
	
	public Page<StudyActivity> findPage(Page<StudyActivity> page, StudyActivity studyActivity) {
		BaseService.dataScopeFileForJgSystemForStudyActive(studyActivity);
		return super.findPage(page, studyActivity);
	}

	@Transactional(readOnly = false)
	public void saveBase(StudyActivity studyActivity) {
		super.save(studyActivity);
	}
	@Transactional(readOnly = false)
	public void saveBx(StudyActivity studyActivity,String type,HttpServletRequest request,StudyActivityConditionService studyActivityConditionService) {
		save(studyActivity,request,studyActivityConditionService,type);
	}
	@Transactional(readOnly = false)
	public void saveXx(StudyActivity studyActivity,HttpServletRequest request,StudyActivityConditionService studyActivityConditionService) {
		save(studyActivity,request,studyActivityConditionService,"0");//保存选学
	}
	@Transactional(readOnly = false)
	private void save(StudyActivity studyActivity,HttpServletRequest request,StudyActivityConditionService studyActivityConditionService,String type){
		//机构
		String isCheckAll = request.getParameter("isCheckAll");
		String grade = request.getParameter("grade");//机构等级
		String officeType = request.getParameter("officeType");//
		String officeTypeName = request.getParameter("officeTypeName");//
		String officeName = request.getParameter("officeName");
		String officIds = request.getParameter("officIds");
		
		//岗位
		String isCheckPost = request.getParameter("isCheckPost");
		String postIds = request.getParameter("postIds");
		String postType = request.getParameter("postType");
		String postName = request.getParameter("postName");
		
		 String distrctId  = request.getParameter("distrctId");//所属区域ID
		 String provinceId  = request.getParameter("provinceId");//所属省ID
		 String cityId  = request.getParameter("cityId");//所属城市ID
		
		String activId = studyActivity.getId();
		//删除活动组织机构关系
		officeActivityService.deleteOfficeActivityByActivId(activId,type);
		//删除岗位活动
		postActivityService.deletePostActivityByActivId(activId,type);
		Map<String,String> map = new HashMap<String,String>();
		map.put("activId", activId);
		map.put("type",type);
		//删除学员活动
		userActivityService.deleteUserActivityByActivId(map);
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
					OfficeActivity of = new OfficeActivity();
					of.setId(UUID.randomUUID().toString());
					of.setOfficeId(officeId);
					of.setActivityId(activId);
					of.setType(type);
					of.setIsNewRecord(true);
					of.setCreateBy(user);
					of.setUpdateBy(user);
					of.setCreateDate(new Date());
					of.setUpdateDate(new Date());
					officeActivityService.save(of);
				}
			}
		}else{
			if(null != officIds && !"".equals(officIds)){
				String ofidsBx[] = officIds.split(",");
				for (int i = 0; i < ofidsBx.length; i++) {
					OfficeActivity of = new OfficeActivity();
					of.setId(UUID.randomUUID().toString());
					of.setOfficeId(ofidsBx[i]);
					of.setActivityId(activId);
					of.setType(type);
					of.setIsNewRecord(true);
					of.setCreateBy(user);
					of.setUpdateBy(user);
					of.setCreateDate(new Date());
					of.setUpdateDate(new Date());
					officeActivityService.save(of);
				}
			}
		}
		
		//添加岗位活动关系
		if("1".equals(isCheckPost)||("".equals(isCheckPost) && (null == postIds || "".equals(postIds)))){
			PostInfo postInfo = new PostInfo();
			postInfo.setPostType(postType);
			postInfo.setName(postName);
			List<PostInfo> lsPostInfo = postInfoService.findPostListForSearch(postInfo);
			if(null!=lsPostInfo && !lsPostInfo.isEmpty()){
				for (int i = 0; i < lsPostInfo.size(); i++) {
					String positionId = lsPostInfo.get(i).getPositLevelId();//注意，这块保存的是postLevel的id
					PostActivity po = new PostActivity();
					po.setId(UUID.randomUUID().toString());
					po.setPostId(positionId);
					po.setActivityId(activId);
					po.setType(type);
					po.setIsNewRecord(true);
					po.setCreateBy(user);
					po.setUpdateBy(user);
					po.setCreateDate(new Date());
					po.setUpdateDate(new Date());
					postActivityService.save(po);
				}
			}
		}else{
			if(null != postIds && !"".equals(postIds)){
				String poidsBx[] = postIds.split(",");
				for (int i = 0; i < poidsBx.length; i++) {
					PostActivity po = new PostActivity();
					po.setId(UUID.randomUUID().toString());
					po.setPostId(poidsBx[i]);
					po.setActivityId(activId);
					po.setType(type);
					po.setIsNewRecord(true);
					po.setCreateBy(user);
					po.setUpdateBy(user);
					po.setCreateDate(new Date());
					po.setUpdateDate(new Date());
					postActivityService.save(po);
				}
			}
		}
		//根据组织信息和岗位信息,及相关的查询条件，查询出必选的人员信息
		SelectUser su = new SelectUser();
		su.setActivityId(activId);
		su.setSearchType(type);
		if("1".equals(type)){//必学,这块不再考虑去重的情况了
			List<SelectUser> listUsers = systemService.findListForOfficeAndPost(su);
			List<UserActivity> listAct = new ArrayList<UserActivity>();
			if(null != listUsers && listUsers.size()>0){
				for (int i = 0; i < listUsers.size(); i++) {
					UserActivity uc = new UserActivity();
					uc.setId(UUID.randomUUID().toString());
					uc.setUserId(listUsers.get(i).getId());
					uc.setActivityId(activId);
					uc.setType(type);
					uc.setIsNewRecord(true);
					uc.setCreateBy(user);
					uc.setUpdateBy(user);
					uc.setCreateDate(new Date());
					uc.setUpdateDate(new Date());
					listAct.add(uc);
					//如果已经通过导入途径导入选学了，则删除掉
					Map<String,String> mapParam = new HashMap<String,String>();
					mapParam.put("userId", listUsers.get(i).getId());
					mapParam.put("activId", activId);
					mapParam.put("type", "0");
					userActivityService.deleteUserActivityByActivId(mapParam);
				}
				userActivityService.saveBatch(listAct);
				//要考虑通过导入途径添加的数据的排除
			}
		}else if("0".equals(type)){//保存可选学员
			List<SelectUser> listUsers = systemService.findListForOfficeAndPost(su);
			if(null != listUsers && listUsers.size()>0){
				for (int i = 0; i < listUsers.size(); i++) {
					UserActivity uc = new UserActivity();
					uc.setUserId(listUsers.get(i).getId());
					uc.setActivityId(activId);
					UserActivity ucTest = userActivityService.get(uc);
					if(null == ucTest){//如果已经在必选学员中了，则不再保存
						uc.setId(UUID.randomUUID().toString());
						uc.setType(type);
						uc.setIsNewRecord(true);
						uc.setCreateBy(user);
						uc.setUpdateBy(user);
						uc.setCreateDate(new Date());
						uc.setUpdateDate(new Date());
						userActivityService.save(uc);
					}
				}
			}
		}
		//先删除之前的查询条件
		Map<String,String> mapSA = new HashMap<String,String>();
		mapSA.put("studyActivityId", activId);
		mapSA.put("type", type);
		studyActivityConditionService.deleteByActivityIdAndType(mapSA);
		//保存查询条件
		StudyActivityCondition sac = new StudyActivityCondition();
		sac.setStudyActivityId(activId);
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
		studyActivityConditionService.save(sac);
	}
	//保存必选学员和可选学员
	@Transactional(readOnly = false)
	public int saveForImport(String type,String userId,String activId,String edit){
		int result = 1;
		User user = UserUtils.getUser();//获取当前用户
		if("1".equals(type)){//必学
			//如果可学学员在必学中已经有了，则给予排除
			UserActivity uc = new UserActivity();
			uc.setUserId(userId);
			uc.setActivityId(activId);
			uc.setType("1");
			//重复导入的数据，则不进行导入
			UserActivity ucTest = userActivityService.getIsBtExists(uc);
            if(ucTest == null){		
				uc.setId(UUID.randomUUID().toString());
				uc.setUserId(userId);
				uc.setActivityId(activId);
				uc.setType(type);
				uc.setIsNewRecord(true);
				uc.setCreateBy(user);
				uc.setUpdateBy(user);
				uc.setCreateDate(new Date());
				uc.setUpdateDate(new Date());
				userActivityService.save(uc);
				//必学的优先级高，重新导入必学的时候，如果该学员已经在选学中有了，则将该学员从选学中删除
				if(null != edit && !"".equals(edit)){
					Map<String,String> map = new HashMap<String,String>();
					map.put("userId", userId);
					map.put("activId", activId);
					map.put("type", "0");
					userActivityService.deleteUserActivityByActivId(map);
				}
            }else{
            	result = 0;
            }
		
		}else if("0".equals(type)){//保存可选学员
			//如果可学学员在必学中已经有了，则给予排除。 如果可学学员中有了，则不再保存
			UserActivity uc = new UserActivity();
			uc.setUserId(userId);
			uc.setActivityId(activId);
			UserActivity ucTest = userActivityService.getIsBtExists(uc);
			if(null == ucTest){//如果已经在必选学员中了，则不再保存
				uc.setId(UUID.randomUUID().toString());
				uc.setType(type);
				uc.setIsNewRecord(true);
				uc.setCreateBy(user);
				uc.setUpdateBy(user);
				uc.setCreateDate(new Date());
				uc.setUpdateDate(new Date());
				userActivityService.save(uc);
			}else{
				result = 0;
			}
		}
		return result;
	}
	@Transactional(readOnly = false)
	public void delete(StudyActivity studyActivity) {
		super.delete(studyActivity);
	}
	
	public  Page<StudyActivity> findTeacherStudyActivityList(Page<StudyActivity> page, StudyActivity studyActivity) {
		studyActivity.setPage(page);
		//BaseService.dataScopeFileForOffice(studyActivity);
		page.setList(studyActivityDao.findTeacherStudyActivityList(studyActivity));
		return page;
	}
	public  Page<StudyActivity> activeStudyTime(Page<StudyActivity> page, StudyActivity studyActivity) {
		studyActivity.setPage(page);
		page.setList(studyActivityDao.activeStudyTime(studyActivity));
		return page;
	}
	
	
	public  Page<StudyActivity> myTeacherStudyActivity(Page<StudyActivity> page, StudyActivity studyActivity) {
		studyActivity.setPage(page);
		page.setList(studyActivityDao.myTeacherStudyActivity(studyActivity));
		return page;
	}
	
	
}