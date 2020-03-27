/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.web;

import java.util.ArrayList;
import java.util.Iterator;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.train.entity.UserActivity;
import com.jeeplus.modules.train.service.UserActivityService;

/**
 * 学员活动Controller
 * @author panjp
 * @version 2017-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/train/userActivity")
public class UserActivityController extends BaseController {

	@Autowired
	private UserActivityService userActivityService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public UserActivity get(@RequestParam(required=false) String id) {
		UserActivity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userActivityService.get(id);
		}
		if (entity == null){
			entity = new UserActivity();
		}
		return entity;
	}
	
	/**
	 * 学员活动列表页面
	 */
	@RequiresPermissions("train:userActivity:list")
	@RequestMapping(value = {"list"})
	public String list(UserActivity userActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserActivity> page = userActivityService.findPage(new Page<UserActivity>(request, response), userActivity); 
		model.addAttribute("page", page);
		return "modules/train/userActivityList";
	}

	/**
	 * 查看，增加，编辑学员活动表单页面
	 */
	@RequiresPermissions(value={"train:userActivity:view","train:userActivity:add","train:userActivity:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(UserActivity userActivity, Model model) {
		model.addAttribute("userActivity", userActivity);
		return "modules/train/userActivityForm";
	}

	/**
	 * 保存学员活动
	 */
	@RequiresPermissions(value={"train:userActivity:add","train:userActivity:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(UserActivity userActivity, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, userActivity)){
			return form(userActivity, model);
		}
		if(!userActivity.getIsNewRecord()){//编辑表单保存
			UserActivity t = userActivityService.get(userActivity.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(userActivity, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			userActivityService.save(t);//保存
		}else{//新增表单保存
			userActivityService.save(userActivity);//保存
		}
		addMessage(redirectAttributes, "保存学员活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/userActivity/?repage";
	}
	
	/**
	 * 删除学员活动
	 */
	@RequiresPermissions("train:userActivity:del")
	@RequestMapping(value = "delete")
	public String delete(UserActivity userActivity, RedirectAttributes redirectAttributes) {
		userActivityService.delete(userActivity);
		addMessage(redirectAttributes, "删除学员活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/userActivity/?repage";
	}
	
	/**
	 * 批量删除学员活动
	 */
	@RequiresPermissions("train:userActivity:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			userActivityService.delete(userActivityService.get(id));
		}
		addMessage(redirectAttributes, "删除学员活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/userActivity/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("train:userActivity:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(UserActivity userActivity, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "学员活动"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<UserActivity> page = userActivityService.findPage(new Page<UserActivity>(request, response, -1), userActivity);
    		new ExportExcel("学员活动", UserActivity.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出学员活动记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/userActivity/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("train:userActivity:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<UserActivity> list = ei.getDataList(UserActivity.class);
			for (UserActivity userActivity : list){
				try{
					userActivityService.save(userActivity);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条学员活动记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条学员活动记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入学员活动失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/userActivity/?repage";
    }
	
	/**
	 * 下载导入学员活动数据模板
	 */
	@RequiresPermissions("train:userActivity:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "学员活动数据导入模板.xlsx";
    		List<UserActivity> list = Lists.newArrayList(); 
    		new ExportExcel("学员活动数据", UserActivity.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/userActivity/?repage";
    }
	
	
	/**
	 * 查询必学学员和可以报名的活动
	 */
	@RequestMapping(value = {"liststudentinfo"})
	public String listStudentInfo(@RequestParam(required=false) String activityid,@RequestParam(required=false) String type, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		//根据活动id和类型id，查找所有人员
		UserActivity userActivity = new UserActivity();
		userActivity.setActivityId(activityid);
		userActivity.setType(type);
		Page<UserActivity> page = userActivityService.findPage(new Page<UserActivity>(request, response), userActivity);
		List<UserActivity> userActivityList = page.getList();
		
		//根据userid，获取所属机构信息，并利用list进行顺序的排序
		Iterator<UserActivity> itUserActivity = userActivityList.iterator();
		Map<String,List<String>> userMap = new java.util.HashMap<String, List<String>>();
		Map<String,List<String>> officeMap = new java.util.HashMap<String, List<String>>();
		while(itUserActivity.hasNext()){
			UserActivity tempUserActivity = itUserActivity.next();
			User tempUser = systemService.getUser(tempUserActivity.getUserId());
			Office tempOffice = tempUser.getOffice();
			
			List<String> tempUserList =  userMap.get(tempOffice.getId());
			if(tempUserList==null){
				tempUserList = new ArrayList<String>();
			}
			tempUserList.add(tempUser.getName());
			userMap.put(tempOffice.getId(),tempUserList);
			
			List<String> tempActivityList =  officeMap.get(tempOffice.getId());
			
			if(tempActivityList == null){
				List<String> tempOfficeNameDes = new ArrayList<String>();
				Office tempTempOffice = tempOffice;
				while(tempTempOffice!=null){
					tempOfficeNameDes.add(tempTempOffice.getName());
					tempTempOffice = officeService.get(tempTempOffice.getParentId());
				}	
				//反序并存储
				tempActivityList = new ArrayList<String>();
				for(int i=tempOfficeNameDes.size()-1; i>=0; i--){
					tempActivityList.add(tempOfficeNameDes.get(i));
				}
				officeMap.put(tempOffice.getId(), tempActivityList);
			}
		}
		
		//构建返回类型
		List<java.util.HashMap<String, List<String>>> resturnList =  new ArrayList<java.util.HashMap<String,List<String>>>();
		for (String map : userMap.keySet()) {
		       java.util.HashMap tempMap = new java.util.HashMap<String, List<String>>();
			   tempMap.put("user", userMap.get(map));
			   tempMap.put("office", officeMap.get(map));
			   resturnList.add(tempMap);
	    }
		
		model.addAttribute("resturnList",resturnList);
		model.addAttribute("page", page);
		return "modules/train/activityInfoManageViewStudent";
	}

}