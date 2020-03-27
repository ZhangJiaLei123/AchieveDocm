/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
import com.jeeplus.modules.train.entity.ActivityDir;
import com.jeeplus.modules.train.service.ActivityDirService;
import com.mysql.fabric.Response;

/**
 * 活动章节目录Controller
 * @author wsp
 * @version 2017-04-02
 */
@Controller
@RequestMapping(value = "${adminPath}/train/activityDir")
public class ActivityDirController extends BaseController {

	@Autowired
	private ActivityDirService activityDirService;
	
	@ModelAttribute
	public ActivityDir get(@RequestParam(required=false) String id) {
		ActivityDir entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = activityDirService.get(id);
		}
		if (entity == null){
			entity = new ActivityDir();
		}
		return entity;
	}
	
	/**
	 * 活动章节列表页面
	 */
	@RequiresPermissions("train:activityDir:list")
	@RequestMapping(value = {"list", ""})
	public String list(ActivityDir activityDir, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ActivityDir> page = activityDirService.findPage(new Page<ActivityDir>(request, response), activityDir); 
		model.addAttribute("page", page);
		return "modules/train/activityDirList";
	}

	/**
	 * 查看，增加，编辑活动章节表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "form")
	public String form(ActivityDir activityDir, Model model) {
		model.addAttribute("activityDir", activityDir);
		return "modules/train/activityDirForm";
	}

	/**
	 * 保存活动章节
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "save")
	public void save(ActivityDir activityDir,String dirName, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception{
		String str = "0";
		if(!activityDir.getIsNewRecord()){//编辑表单保存
			ActivityDir t = activityDirService.get(activityDir.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(activityDir, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			activityDirService.save(t);//保存
			str = "修改成功！";
		}else{//新增表单保存
			activityDirService.save(activityDir);//保存
			str = "添加成功！";
		}
		try {
			response.getWriter().print(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 删除活动章节
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "delete")
	public String delete(ActivityDir activityDir, RedirectAttributes redirectAttributes) {
		activityDirService.delete(activityDir);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/train/activityDir/listview?activityId="+activityDir.getActivityId();
	}
	/**
	 * 删除活动章节
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "techerDelete")
	public String techerDelete(ActivityDir activityDir, RedirectAttributes redirectAttributes) {
		activityDirService.delete(activityDir);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/teacherActivityDirListview?activityId="+activityDir.getActivityId();
	}
	/**
	 * 批量删除活动章节
	 */
	@RequiresPermissions("train:activityDir:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			activityDirService.delete(activityDirService.get(id));
		}
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/train/activityDir/listview?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("train:activityDir:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ActivityDir activityDir, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "活动章节"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ActivityDir> page = activityDirService.findPage(new Page<ActivityDir>(request, response, -1), activityDir);
    		new ExportExcel("活动章节", ActivityDir.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出活动章节记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/activityDir/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("train:activityDir:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ActivityDir> list = ei.getDataList(ActivityDir.class);
			for (ActivityDir activityDir : list){
				try{
					activityDirService.save(activityDir);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条活动章节记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条活动章节记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入活动章节失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/activityDir/?repage";
    }
	
	/**
	 * 下载导入活动章节数据模板
	 */
	@RequiresPermissions("train:activityDir:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "活动章节数据导入模板.xlsx";
    		List<ActivityDir> list = Lists.newArrayList(); 
    		new ExportExcel("活动章节数据", ActivityDir.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/activityDir/?repage";
    }
	
	/**
	 * 活动章节列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"listview"})
	public String listView(ActivityDir activityDir, HttpServletRequest request, HttpServletResponse response, Model model) {	
		activityDir.setParentId("0");
		Page<ActivityDir> page = activityDirService.findPage(new Page<ActivityDir>(request, response), activityDir); 
		List<ActivityDir> tempActivityDirList = page.getList();
		
		List<java.util.HashMap<String, List<ActivityDir>>> resturnList =  new ArrayList<java.util.HashMap<String,List<ActivityDir>>>();
		Iterator<ActivityDir> itParent = tempActivityDirList.iterator();
		while(itParent.hasNext()){
			ActivityDir tmp = itParent.next();
			List<ActivityDir> listParentActivityDir = new ArrayList<ActivityDir>();
			listParentActivityDir.add(tmp);
			
			ActivityDir temp = new ActivityDir();
			temp.setActivityId(tmp.getActivityId());
			temp.setParentId(tmp.getId());
			List<ActivityDir> listSubActivityDir = activityDirService.findList(temp) ;
			
			java.util.HashMap<String, List<ActivityDir>> hashMap = new java.util.HashMap<String, List<ActivityDir>>();
			hashMap.put("parent", listParentActivityDir);
			hashMap.put("son", listSubActivityDir);
			resturnList.add(hashMap);
		}
		
		model.addAttribute("resturnList", resturnList);
		model.addAttribute("activityId", activityDir.getActivityId());
		model.addAttribute("page", page);
		return "modules/train/activityDirList";
	}
	
	/**
	 * 活动章节列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"listviewscore"})
	public String listViewScore(ActivityDir activityDir, HttpServletRequest request, HttpServletResponse response, Model model) {	
		activityDir.setParentId("0");
		Page<ActivityDir> page = activityDirService.findPage(new Page<ActivityDir>(request, response), activityDir); 
		List<ActivityDir> tempActivityDirList = page.getList();
		
		List<java.util.HashMap<String, List<ActivityDir>>> resturnList =  new ArrayList<java.util.HashMap<String,List<ActivityDir>>>();
		Iterator<ActivityDir> itParent = tempActivityDirList.iterator();
		while(itParent.hasNext()){
			ActivityDir tmp = itParent.next();
			List<ActivityDir> listParentActivityDir = new ArrayList<ActivityDir>();
			listParentActivityDir.add(tmp);
			
			ActivityDir temp = new ActivityDir();
			temp.setActivityId(tmp.getActivityId());
			temp.setParentId(tmp.getId());
			List<ActivityDir> listSubActivityDir = activityDirService.findList(temp) ;
			
			java.util.HashMap<String, List<ActivityDir>> hashMap = new java.util.HashMap<String, List<ActivityDir>>();
			hashMap.put("parent", listParentActivityDir);
			hashMap.put("son", listSubActivityDir);
			resturnList.add(hashMap);
		}
		
		model.addAttribute("resturnList", resturnList);
		model.addAttribute("activityId", activityDir.getActivityId());
		model.addAttribute("page", page);
		return "modules/train/activityDirListScore";
	}
	

}