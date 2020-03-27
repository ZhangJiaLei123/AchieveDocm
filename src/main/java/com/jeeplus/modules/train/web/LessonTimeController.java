/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.web;

import java.io.IOException;
import java.util.ArrayList;
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
import com.jeeplus.modules.train.entity.LessonDIr;
import com.jeeplus.modules.train.entity.LessonTime;
import com.jeeplus.modules.train.service.ActivityDirService;
import com.jeeplus.modules.train.service.LessonDIrService;
import com.jeeplus.modules.train.service.LessonTimeService;

/**
 * 授课时间功能Controller
 * @author wsp
 * @version 2017-04-03
 */
@Controller
@RequestMapping(value = "${adminPath}/train/lessonTime")
public class LessonTimeController extends BaseController {

	@Autowired
	private LessonTimeService lessonTimeService;
	@Autowired
	private ActivityDirService activityDirService;
	@Autowired
	private LessonDIrService lessonDIrService;
	
	@ModelAttribute
	public LessonTime get(@RequestParam(required=false) String id) {
		LessonTime entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = lessonTimeService.get(id);
		}
		if (entity == null){
			entity = new LessonTime();
		}
		return entity;
	}
	
	/**
	 * 授课时间列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"list", ""})
	public String list(LessonTime lessonTime, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LessonTime> page = lessonTimeService.findPage(new Page<LessonTime>(request, response,-1), lessonTime); 
		//增加章节信息
		List<LessonTime> tempList = page.getList();
		Iterator<LessonTime> itsLessonTime = tempList.iterator();
		while(itsLessonTime.hasNext()){
			LessonTime tempLessonTime = itsLessonTime.next();
			ActivityDir dir = new ActivityDir();
			dir.setLessionTimeId(tempLessonTime.getId());
			dir.setParentId("0");
			List<ActivityDir> activityDirList =activityDirService.getListForLessionId(dir);
			if(null!=activityDirList && !activityDirList.isEmpty()){
				for (int i = 0; i < activityDirList.size(); i++) {
					ActivityDir indexDir = activityDirList.get(i);
					ActivityDir dirT = new ActivityDir();
					dirT.setLessionTimeId(tempLessonTime.getId());
					dirT.setParentId(indexDir.getId());
					List<ActivityDir> activityDirIndex =activityDirService.getListForLessionId(dirT);
					indexDir.setListActivityDir(activityDirIndex);
				}
			}else{
				dir.setParentId("");
				activityDirList = activityDirService.getListForLessionId(dir);
			}
			tempLessonTime.setListActivityDir(activityDirList);
		}
		
		model.addAttribute("page", page);
		model.addAttribute("lessonTime", lessonTime);
		return "modules/train/lessonTimeList";
	}

	/**
	 * 查看，增加，编辑授课时间表单页面
	 */
	@RequiresPermissions(value={"train:lessonTime:view","train:lessonTime:add","train:lessonTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LessonTime lessonTime, Model model) {
		model.addAttribute("lessonTime", lessonTime);
		return "modules/train/lessonTimeForm";
	}

	/**
	 * 保存授课时间
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "saveTime")
	public String save(LessonTime lessonTime, Model model, RedirectAttributes redirectAttributes) throws Exception{
		System.out.println("----------------------");
		if(!lessonTime.getIsNewRecord()){//编辑表单保存
			LessonTime t = lessonTimeService.get(lessonTime.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(lessonTime, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			lessonTimeService.save(t);//保存
		}else{//新增表单保存
			lessonTimeService.save(lessonTime);//保存
		}
		//保存对应的章节（先全部删除，然后保存新的）
		lessonDIrService.deleteByLessionId(lessonTime.getId());
		
		String idArray[] = lessonTime.getActivityDirIds().split(",");
		for(String idm : idArray){
			LessonDIr temp = new LessonDIr();
			temp.setActivityDirId(idm);
			temp.setLessonTimeId(lessonTime.getId());
			lessonDIrService.save(temp);
		}
		
		addMessage(redirectAttributes, "保存授课时间成功");
		return "redirect:"+Global.getAdminPath()+"/train/lessonTime/?repage";
	}
	
	/**
	 * 保存授课时间
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "saveLessonTimeAjax")
	public void saveLessonTimeAjax(LessonTime lessonTime,HttpServletResponse response) throws Exception{
		System.out.println("----------------------");
		String str = "0";
		if(!lessonTime.getIsNewRecord()){//编辑表单保存
			LessonTime t = lessonTimeService.get(lessonTime.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(lessonTime, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			lessonTimeService.save(t);//保存
			str = "2";
		}else{//新增表单保存
			lessonTimeService.save(lessonTime);//保存
			str ="1";
		}
		//保存对应的章节（先全部删除，然后保存新的）
		lessonDIrService.deleteByLessionId(lessonTime.getId());
		
		String idArray[] = lessonTime.getActivityDirIds().split(",");
		for(String idm : idArray){
			LessonDIr temp = new LessonDIr();
			temp.setActivityDirId(idm);
			temp.setLessonTimeId(lessonTime.getId());
			lessonDIrService.save(temp);
		}
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除授课时间
	 */
	@RequiresPermissions("train:lessonTime:del")
	@RequestMapping(value = "delete")
	public String delete(LessonTime lessonTime, RedirectAttributes redirectAttributes) {
		lessonTimeService.delete(lessonTime);
		addMessage(redirectAttributes, "删除授课时间成功");
		return "redirect:"+Global.getAdminPath()+"/train/lessonTime/?repage";
	}
	
	/**
	 * 批量删除授课时间
	 */
	@RequiresPermissions("train:lessonTime:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			lessonTimeService.delete(lessonTimeService.get(id));
		}
		addMessage(redirectAttributes, "删除授课时间成功");
		return "redirect:"+Global.getAdminPath()+"/train/lessonTime/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("train:lessonTime:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(LessonTime lessonTime, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "授课时间"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LessonTime> page = lessonTimeService.findPage(new Page<LessonTime>(request, response, -1), lessonTime);
    		new ExportExcel("授课时间", LessonTime.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出授课时间记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/lessonTime/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("train:lessonTime:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LessonTime> list = ei.getDataList(LessonTime.class);
			for (LessonTime lessonTime : list){
				try{
					lessonTimeService.save(lessonTime);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条授课时间记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条授课时间记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入授课时间失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/lessonTime/?repage";
    }
	
	/**
	 * 下载导入授课时间数据模板
	 */
	@RequiresPermissions("train:lessonTime:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "授课时间数据导入模板.xlsx";
    		List<LessonTime> list = Lists.newArrayList(); 
    		new ExportExcel("授课时间数据", LessonTime.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/lessonTime/?repage";
    }
	
	
	/**
	 * from 表单
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "formdir")
	public String formDir(String studyId,String id, Model model) {
		LessonTime les = new LessonTime();
		les.setId(id);
		LessonTime lessonTime = lessonTimeService.get(les);
		if(null==lessonTime){
			lessonTime = new LessonTime();
			lessonTime.setStudyId(studyId);
		}
		ActivityDir activityDir = new ActivityDir();
		//得到目录列表
		activityDir.setParentId("0");
		activityDir.setActivityId(studyId);
		List<ActivityDir> tempActivityDirList = activityDirService.findList(activityDir);
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
		//修改是查询已经选中的章节
		ActivityDir dir = new ActivityDir();
		dir.setLessionTimeId(lessonTime.getId());
		List<ActivityDir> activityDirList =activityDirService.getListAllDirForLessionId(dir);
		
		model.addAttribute("resturnList", resturnList);
		model.addAttribute("activityId", activityDir.getActivityId());
		model.addAttribute("lessonTime", lessonTime);
		model.addAttribute("activityDirList",activityDirList);
		return "modules/train/lessonTimeForm";
	}
	
	

}