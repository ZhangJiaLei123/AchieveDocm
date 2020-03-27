/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.cst.HeadCst;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.course.entity.CourseInfoCondition;
import com.jeeplus.modules.course.service.CourseInfoConditionService;
import com.jeeplus.modules.course.service.OfficeCourseService;
import com.jeeplus.modules.sys.entity.District;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.UserOffice;
import com.jeeplus.modules.sys.service.DistrictService;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.UserOfficeService;
import com.jeeplus.modules.sys.utils.DictUtils;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.train.entity.OfficeActivity;
import com.jeeplus.modules.train.entity.StudyActivityCondition;
import com.jeeplus.modules.train.service.OfficeActivityService;
import com.jeeplus.modules.train.service.SearchTempService;
import com.jeeplus.modules.train.service.StudyActivityConditionService;

/**
 * 机构Controller
 * @author jeeplus
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private SearchTempService searchTempService;
	@Autowired
	private  StudyActivityConditionService studyActivityConditionService;
	@Autowired
	private OfficeActivityService officeActivityService;
	//课程管理相关的代码
	@Autowired
	private CourseInfoConditionService courseInfoConditionService;
	@Autowired
	private OfficeCourseService officeCourseService;
	@Autowired
	private UserOfficeService userOfficeService;
	@Autowired
	private DistrictService districtService;
	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return officeService.get(id);
		}else{
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:index")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
//        model.addAttribute("list", officeService.findAll());
		return "modules/sys/officeIndex";
	}

	@RequiresPermissions("sys:office:index")
	@RequestMapping(value = {"list"})
	public String list(Office office, Model model) {
		if(office==null || office.getParentIds() == null){
			 model.addAttribute("list", officeService.findList(false));
		}else{
			List list = officeService.findList(office);
			 model.addAttribute("list", list);
		}
		return "modules/sys/officeList";
	}
	/**
	 * 
	 * @param office机构列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:office:index")
	@RequestMapping(value = {"showOfficeList"})
	public String showOfficeList(Office office, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Office> page = officeService.findPage(new Page<Office>(request, response), office); 
		model.addAttribute("page", page);
		model.addAttribute("office", office);
		return "modules/sys/showOfficeList";
	}
	
	
	
	@RequiresPermissions("user")
	@RequestMapping(value = {"selOfficeList"})
	public String selOfficeList(Office office, HttpServletRequest request, HttpServletResponse response, Model model) {

		String qx = request.getParameter("type");
		if(null != qx && !"".equals(qx)){
			Page<Office>  page = new Page<Office>(request, response);
			page.setPageSize(4);
			page = officeService.findPage(page, office); 
			model.addAttribute("page", page);
			return "modules/sys/selOfficeListForQuanXian";
		}else{
			
			String isCheckAllBx = request.getParameter("isCheckAllBx");
			
			Page<Office>  page = new Page<Office>(request, response);//如果前面选择了全选，则后面不选择了。如果前面没选择全选，则后面正常的加判断条件
			
			if(null == isCheckAllBx || "".equals(isCheckAllBx)){//没有选择全选
				String officeIdsBx = request.getParameter("officeIdsBx");
				if(null != officeIdsBx && !"".equals(officeIdsBx) && officeIdsBx.length()>0){
					officeIdsBx = officeIdsBx.substring(0, officeIdsBx.length()-1);
					String [] officeIdsBxs = officeIdsBx.split(",");//组织的ids
					//使用exists，改用创建一个临时的中间表，先将数据添加到中间表里，然后再进行查询
					List<Map> lists = new ArrayList<Map>();
					for(int i=0;i<officeIdsBxs.length;i++){
						if( officeIdsBxs[i] != null && !"".equals(officeIdsBxs[i])){
							Map map = new HashMap();
							map.put("searchCond", officeIdsBxs[i]);
							map.put("type","1");
							lists.add(map);
						}
					}
					searchTempService.save(lists);//保存查询条件
					//联查中间表的数据
					page.setPageSize(4);
					office.setPage(page);
					page.setList(officeService.findListForOverOneThousand(office));
					//删除临时表的数据
					searchTempService.delete();
				}else{//首次进来，默认情况(?)
					page.setPageSize(4);
					if(office == null){
						office = new Office();
						office.setCurrentUser(UserUtils.getUser());
					}
					page = officeService.findPage(page, office); 
				}
				
			}
			model.addAttribute("page", page);
			return "modules/sys/selOfficeList";
		}
	}
	

	@RequiresPermissions("user")
	@RequestMapping("addUserRoleOfficeList")
	public String addUserRoleOfficeList(Office office,String roleCode,String userId, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Office>  page = new Page<Office>(request, response);
		page = officeService.findPage(page, office); 
		List<UserOffice> lsUserOffice = new ArrayList<UserOffice>();
		if(null != userId && !"".equals(userId.trim())){
			UserOffice userO = new UserOffice();
			userO.setUserId(userId);
			lsUserOffice = userOfficeService.findList(userO);
		}
		model.addAttribute("page", page);
		model.addAttribute("roleCode", roleCode);
		model.addAttribute("lsUserOffice", lsUserOffice);
		return "modules/sys/addUserRoleOfficeList";
	}
	/***
	 * 修改数据用
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年5月6日 下午4:57:33
	  * @param office
	  * @param request
	  * @param response
	  * @param model
	  * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"selOfficeListForUpdate"})
	public String selOfficeListForUpdate(Office office,String type, String firstType,String studyActivityId, HttpServletRequest request, HttpServletResponse response, Model model) {
		if("1".equals(firstType)){//修改或者第一次进入查询上一次保存的条件
			Map<String,String> mapPa = new HashMap<String,String>();
			mapPa.put("studyActivityId",studyActivityId);
			mapPa.put("type", type);
			StudyActivityCondition condition = studyActivityConditionService.findActivityConditionByActivityId(mapPa);
			//查询条件为空的时候，说明是在页面上直接重新查询数据
			if(null != condition){
				//设置查询条件
				office.setOfficeType(condition.getOrgType());
				office.setGrade(condition.getOrgLevel());
				office.setName(condition.getOfficeName());
				office.setOfficeTypeName(condition.getOrgTypeName());
				office.setDistrctId(condition.getDistrctId());
				office.setCityId(condition.getCityId());
				office.setProvinceId(condition.getProvinceId());
				office.setDelFlag("0");
			}
		}
		//根据活动相关的查询条件,过滤要查询的信息
		Page<Office>  page = new Page<Office>(request, response);//如果前面选择了全选，则后面不选择了。如果前面没选择全选，则后面正常的加判断条件
		page = officeService.findPage(page, office); 
		//组织与学习活动
		OfficeActivity officeActivity = new OfficeActivity();
		officeActivity.setActivityId(studyActivityId);
		officeActivity.setType(type);
		List<OfficeActivity> list = officeActivityService.findOfficeListMap(officeActivity);
		List<Office> listOffice = page.getList();
		if(null != listOffice && !listOffice.isEmpty()){
			for(int i=0;i<listOffice.size();i++){
				Office offTemp = listOffice.get(i);
				for(int j=0;j<list.size();j++){
					OfficeActivity oa = list.get(j);
					if(offTemp.getId().equals(oa.getId())){//根据查询条件看到的
						offTemp.setChecked("1");
						break;
					}
				}
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("office", office);
		model.addAttribute("type", type);
		return "modules/sys/selOfficeListForUpdate";
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = {"selOfficeListForUpdateCourse"})
	public String selOfficeListForUpdateCourse(Office office,String type, String firstType,String courseId,HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if("1".equals(firstType)){//修改或者第一次进入查询上一次保存的条件
			Map<String,String> mapPa = new HashMap<String,String>();
			mapPa.put("courseId",courseId);
			mapPa.put("type", type);
			CourseInfoCondition condition = courseInfoConditionService.findActivityConditionByActivityId(mapPa);
			//查询条件为空的时候，说明是在页面上直接重新查询数据
			if(null != condition){
				office = new Office();
				//设置查询条件
				office.setOfficeType(condition.getOrgType());
				office.setOfficeTypeName(condition.getOrgTypeName());
				office.setGrade(condition.getOrgLevel());
				office.setName(condition.getOfficeName());
				office.setDistrctId(condition.getDistrctId());
				office.setCityId(condition.getCityId());
				office.setProvinceId(condition.getProvinceId());
				office.setDelFlag("0");
			}
		}
		//根据活动相关的查询条件,过滤要查询的信息
		Page<Office>  page = new Page<Office>(request, response);//如果前面选择了全选，则后面不选择了。如果前面没选择全选，则后面正常的加判断条件
		page = officeService.findPage(page, office); 
		model.addAttribute("office", office);
		model.addAttribute("page", page);
		model.addAttribute("type", type);
		return "modules/sys/selOfficeListForUpdateCourse";
	}
	
	/***
	 * 修改数据用
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年5月6日 下午4:57:33
	  * @param office
	  * @param request
	  * @param response
	  * @param model
	  * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"selOfficeListForUpdateXx"})
	public String selOfficeListForUpdateXx(Office office, HttpServletRequest request, HttpServletResponse response, Model model) {
		String studyActivityId = request.getParameter("studyActivityId");//根据活动id，获取相应的查询条件
		Map mapPa = new HashMap();
		mapPa.put("studyActivityId",studyActivityId);
		mapPa.put("type", 2);
		StudyActivityCondition condition = studyActivityConditionService.findActivityConditionByActivityId(mapPa);
		
		//根据活动相关的查询条件,过滤要查询的信息
		office.setActivityId(studyActivityId);
		Page<Office>  page = new Page<Office>(request, response);//如果前面选择了全选，则后面不选择了。如果前面没选择全选，则后面正常的加判断条件
		page = officeService.findOfficeListForXx(page, office); 
		//组织与学习活动
		OfficeActivity officeActivity = new OfficeActivity();
		officeActivity.setActivityId(studyActivityId);
		officeActivity.setType("2");
		List<OfficeActivity> list = officeActivityService.findOfficeListMap(officeActivity);
		List<Office> listOffice = page.getList();
		if(null != listOffice && !listOffice.isEmpty()){
			for(int i=0;i<listOffice.size();i++){
				Office offTemp = listOffice.get(i);
				for(int j=0;j<list.size();j++){
					OfficeActivity oa = list.get(j);
					if(offTemp.getId().equals(oa.getId())){//根据查询条件看到的
						offTemp.setChecked("1");
						break;
					}
				}
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("condition", condition);
		return "modules/sys/selOfficeListForUpdateXx";
	}
	@RequiresPermissions("sys:office:index")
	@RequestMapping(value = {"selUserOfficeList"})
	public String selUserOfficeList(Office office, HttpServletRequest request, HttpServletResponse response, Model model) {
		String officeId = request.getParameter("officeId");
		System.out.println(officeId);
		Page<Office>  page = new Page<Office>(request, response);
		page = officeService.findPage(page, office); 
		model.addAttribute("page", page);
		return "modules/sys/selUserOfficeList";
	}
	
	
	
	
	@RequiresPermissions(value={"sys:office:view","sys:office:add","sys:office:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}
		model.addAttribute("office", office);
		return "modules/sys/officeForm";
	}
	/**
	 * 教师端查看组织信息
	 * @param office
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherViewOfficeForm")
	public String teacherViewOfficeForm(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}
		model.addAttribute("office", office);
		return "modules/sys/teacherViewOfficeForm";
	}
	
	@RequiresPermissions(value={"sys:office:add","sys:office:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Office office, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if (!beanValidator(model, office)){
			return form(office, model);
		}
		officeService.save(office);
		
		if(office.getChildDeptList()!=null){
			Office childOffice = null;
			for(String id : office.getChildDeptList()){
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+1));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}
		
		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
		return "redirect:" + adminPath + "/sys/office/list?id="+id+"&parentIds="+office.getParentIds();
	}
	
	@RequiresPermissions(value={"sys:office:add","sys:office:edit"},logical=Logical.OR)
	@RequestMapping(value = "saveNew")
	public String saveNew(Office office, Model model,RedirectAttributes redirectAttributes) {
		officeService.save(office);
		if(office.getChildDeptList()!=null){
			Office childOffice = null;
			for(String id : office.getChildDeptList()){
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+1));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}
		
		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		return "redirect:"+adminPath+"/sys/office/showOfficeList/";
	}
	
	@RequiresPermissions("sys:office:del")
	@RequestMapping(value = "delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list";
		}
		int count = officeService.findUserCountByOfficeId(office.getId());
		if(count>0){
			addMessage(redirectAttributes, "删除机构失败，该机构下还有人员。");
			return "redirect:"+adminPath+"/sys/office/showOfficeList/?repage";
		}
		officeService.delete(office);
		addMessage(redirectAttributes, "删除机构成功");
		return "redirect:"+adminPath+"/sys/office/showOfficeList/?repage";
	}

	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	/**
	 * 查询JSON格式字典信息
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "selDictList")
	public void selDictList(HttpServletResponse response, Model model,String dicType) {
		response.setCharacterEncoding("UTF-8");  
		List<Map> lsMap = officeService.selDictList(dicType);
		String printStr = new String();
		if(null!=lsMap && !lsMap.isEmpty()){
			printStr = JSONArray.toJSONString(lsMap);
		}
		try {
			response.getWriter().print(printStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:office:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Office office, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "组织管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Office> page = officeService.findPage(new Page<Office>(request, response, -1), office);
    		new ExportExcel("组织管理", Office.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出组织管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/showOfficeList/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:office:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			if (!HeadCst.headValid(ei,Office.class)) {
				addMessage(redirectAttributes, "您的导入模板不正确，请使用导出数据模板。");
			} else {
				List<Office> list = ei.getDataList(Office.class);
				if (null != list && !list.isEmpty()) {
					// 初始化所有大区、省份、城市。
					Map<String, String> mapDate = districtService.initMapDate();
					Map<String, String> mapOfficeType = officeService.initOfficeType();
					// 开始验证数据
					String messageStr = "";
					for (int i = 0; i < list.size(); i++) {
						Office office = list.get(i);
						String officeName = office.getName();
						String code = office.getCode();
						String offictType = office.getOfficeTypeName();
						String distrctName = office.getDistrctName();
						String provinceName = office.getProvinceName();
						String cityName = office.getCityName();
						
						if (null == officeName || "".equals(officeName.trim())) {
							messageStr += "第" + (i + 3) + "行机构名称不能为空。";
						} 

						if (null == code
								|| "".equals(code.trim())) {
							messageStr += "第" + (i + 3) + "行机构编码不能为空。";
						} else {
							if (null != mapOfficeType.get(code.trim())) {
								messageStr += "第" + (i + 3) + "行机构编码已存在错误。";
							}
						}

						if (null == offictType || "".equals(offictType.trim())) {
							messageStr += "第" + (i + 3) + "行机构类别不能为空。";
						} else {
							if (null == mapOfficeType.get(offictType.trim())) {
								messageStr += "第" + (i + 3) + "行机构类别不存在。";
							}
						}
						if (null == distrctName || "".equals(distrctName.trim())) {
							messageStr += "第" + (i + 3) + "行大区不能为空。";
						} else {
							if (null == mapDate.get(distrctName.trim())) {
								messageStr += "第" + (i + 3) + "行大区不存在。";
							}
						}
						if (null == provinceName || "".equals(provinceName.trim())) {
							messageStr += "第" + (i + 3) + "行省不能为空。";
						} else {
							if (null == mapDate.get(provinceName.trim())) {
								messageStr += "第" + (i + 3) + "行省不存在。";
							}
						}
						if (null == cityName || "".equals(cityName.trim())) {
							messageStr += "第" + (i + 3) + "行市不能为空。";
						} else {
							if (null == mapDate.get(cityName.trim())) {
								messageStr += "第" + (i + 3) + "行市不存在。";
							}
						}
					}
					if (null != messageStr && !"".equals(messageStr.trim())) {
						addMessage(redirectAttributes, messageStr);
					} else {
						// 验证成功后开始插入数据
						for (Office office : list) {
							try {
								Office officeObj = new Office();
								officeObj.setIsNewRecord(true);
								officeObj.setId(IdGen.uuid());
								officeObj.setName(office.getName());
								officeObj.setCode(office.getCode());
								officeObj.setOfficeType(mapOfficeType.get(office.getOfficeTypeName().trim()));
								officeObj.setDistrctId(mapDate.get(office.getDistrctName()));
								officeObj.setProvinceId(mapDate.get(office.getProvinceName()));
								officeObj.setCityId(mapDate.get(office.getCityName()));
								officeObj.setGrade("1");
								officeObj.setUseable("1");
								officeService.save(officeObj);
								successNum++;
							} catch (ConstraintViolationException ex) {
								failureNum++;
							} catch (Exception ex) {
								failureNum++;
							}
						}
						if (failureNum > 0) {
							failureMsg.insert(0, "，失败 " + failureNum
									+ " 条机构管理记录。");
						}
						addMessage(redirectAttributes, "已成功导入 " + successNum
								+ " 条机构管理记录" + failureMsg);
					}
				}
			}
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入机构管理失败！失败信息：" + e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/office/showOfficeList";
    }
	
	/**
	 * 下载导入区域管理数据模板
	 */
	@RequiresPermissions("sys:district:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "区域管理数据导入模板.xlsx";
    		List<District> list = Lists.newArrayList(); 
    		new ExportExcel("区域管理数据", District.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/showOfficeList/?repage";
    }
}
