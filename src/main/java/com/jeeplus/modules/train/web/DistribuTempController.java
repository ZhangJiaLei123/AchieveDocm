/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.train.web;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.question.entity.QuestionInfo;
import com.jeeplus.modules.question.service.QuestionInfoService;
import com.jeeplus.modules.resource.entity.MesanInfo;
import com.jeeplus.modules.resource.entity.ResourceInfo;
import com.jeeplus.modules.resource.service.MesanInfoService;
import com.jeeplus.modules.resource.service.ResourceInfoService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.train.entity.DistribuTemp;
import com.jeeplus.modules.train.service.DistribuTempService;

/**
 * 活动目录资源分配中间表Controller
 * @author wsp
 * @version 2017-04-03
 */
@Controller
@RequestMapping(value = "${adminPath}/train/distribuTemp")
public class DistribuTempController extends BaseController {

	@Autowired
	private DistribuTempService distribuTempService;
	@Autowired
	private MesanInfoService mesanInfoService;
	@Autowired
	private ResourceInfoService resourceInfoService;
	@Autowired
	private QuestionInfoService questionInfoService;
	
	@ModelAttribute
	public DistribuTemp get(@RequestParam(required=false) String id) {
		DistribuTemp entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = distribuTempService.get(id);
		}
		if (entity == null){
			entity = new DistribuTemp();
		}
		return entity;
	}
	
	/**
	 * 活动目录资源分配列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"list"})
	public String list(DistribuTemp distribuTemp, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<DistribuTemp> distribuTempList = distribuTempService.findListForDir(distribuTemp);
		model.addAttribute("distribuTempList",distribuTempList);
		model.addAttribute("dirTemObj",distribuTemp);
		return "modules/train/distribuTempList";
	}
	
	/**
	 * 活动目录资源分配列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping("showResourList")
	public String showResourList(DistribuTemp distribuTemp, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<DistribuTemp> distribuTempList = distribuTempService.findListForDir(distribuTemp);
		model.addAttribute("distribuTempList",distribuTempList);
		model.addAttribute("dirTemObj",distribuTemp);
		return "modules/train/showResourList";
	}

	/**
	 * 活动目录资源分配列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping("viewResourList")
	public String viewResourList(DistribuTemp distribuTemp, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<DistribuTemp> distribuTempList = distribuTempService.findListForDir(distribuTemp);
		model.addAttribute("distribuTempList",distribuTempList);
		model.addAttribute("dirTemObj",distribuTemp);
		return "modules/train/viewResourList";
	}
	
	/**
	 * 活动目录资源分配列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping("viewCanYuQk")
	public String viewCanYuQk(User user,String activityId, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = new Page<User>(request, response);
		user.setUserActivityId(activityId);
		page  = mesanInfoService.findChanYuQingKuangUser(page, user); 
		
		model.addAttribute("page", page);
		model.addAttribute("user",user);
		return "modules/train/viewCanYuQk";
	}
	
	
	/**
	 * 查看，增加，编辑活动目录资源分配表单页面
	 */
	@RequiresPermissions(value={"train:distribuTemp:view","train:distribuTemp:add","train:distribuTemp:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DistribuTemp distribuTemp, Model model) {
		model.addAttribute("distribuTemp", distribuTemp);
		return "modules/train/distribuTempForm";
	}

	/**
	 * 保存活动目录资源分配
	 */
	@RequestMapping(value = "save")
	public void save(String strIds,String type,String activityId,String activityDirId,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String str = "0";
		if(null!=strIds && !"".equals(strIds)){
			String strid[] = strIds.split(",");
			for (int i = 0; i < strid.length; i++) {
				String refId = strid[i];
				DistribuTemp temp = new DistribuTemp();
				temp.setId(IdGen.uuid());
				temp.setIsNewRecord(true);
				temp.setActivityDirId(activityDirId);
				temp.setActivityId(activityId);
				temp.setRefId(refId);
				temp.setTypeId(type);
				distribuTempService.save(temp);//保存
			}
			str = "1";
		}
		try {
			response.getWriter().print(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改资源分数
	 */
	@RequestMapping(value = "updaStorce")
	public void updaStorce(String strJson,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String str = "0";
		if(null!=strJson && !"".equals(strJson)){
			JSONArray jsonArray = JSONArray.parseArray(strJson);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				String id = obj.getString("id");
				float score = obj.getFloatValue("value");
				distribuTempService.updaStorce(id, score);//修改分数
			}
			str = "1";
		}
		try {
			response.getWriter().print(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除活动目录资源分配
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "delete")
	public String delete(DistribuTemp distribuTemp, RedirectAttributes redirectAttributes, Model model) {
		distribuTempService.delete(distribuTemp);
		addMessage(redirectAttributes, "删除活动目录资源分配成功");
		model.addAttribute("dirTemObj",distribuTemp);
		return "redirect:"+Global.getAdminPath()+"/train/distribuTemp/list?activityId="+distribuTemp.getActivityId()+"&activityDirId="+distribuTemp.getActivityDirId()+"&repage";
	}
	/**
	 * 删除活动目录资源分配
	 */
	@RequestMapping("deleteShow")
	public String deleteShow(DistribuTemp distribuTemp, RedirectAttributes redirectAttributes) {
		distribuTempService.delete(distribuTemp);
		addMessage(redirectAttributes, "删除活动目录资源分配成功");
		return "redirect:"+Global.getAdminPath()+"/train/distribuTemp/showResourList?activityDirId="+distribuTemp.getActivityDirId()+"&repage";
	}
	
	/**
	 * 批量删除活动目录资源分配
	 */
	@RequiresPermissions("train:distribuTemp:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			distribuTempService.delete(distribuTempService.get(id));
		}
		addMessage(redirectAttributes, "删除活动目录资源分配成功");
		return "redirect:"+Global.getAdminPath()+"/train/distribuTemp/list?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("train:distribuTemp:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DistribuTemp distribuTemp, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "活动目录资源分配"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DistribuTemp> page = distribuTempService.findPage(new Page<DistribuTemp>(request, response, -1), distribuTemp);
    		new ExportExcel("活动目录资源分配", DistribuTemp.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出活动目录资源分配记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/distribuTemp/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("train:distribuTemp:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DistribuTemp> list = ei.getDataList(DistribuTemp.class);
			for (DistribuTemp distribuTemp : list){
				try{
					distribuTempService.save(distribuTemp);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条活动目录资源分配记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条活动目录资源分配记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入活动目录资源分配失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/distribuTemp/?repage";
    }
	
	/**
	 * 下载导入活动目录资源分配数据模板
	 */
	@RequiresPermissions("train:distribuTemp:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "活动目录资源分配数据导入模板.xlsx";
    		List<DistribuTemp> list = Lists.newArrayList(); 
    		new ExportExcel("活动目录资源分配数据", DistribuTemp.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/distribuTemp/?repage";
    }
	///////////////
	//资料部分
	//////////////
	
	/**
	 * 资料信息列表页面
	 */
	@RequestMapping(value = {"mesanInfoList"})
	public String mesanInfoList(MesanInfo mesanInfo ,HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MesanInfo> page = new Page<MesanInfo>(request, response);
		page  = mesanInfoService.findSelMesanInfoListPage(page, mesanInfo); 
		model.addAttribute("page", page);
		return "modules/train/distribuTempMesanInfoshList";
	}
	
	/**
	 * 批量添加资料信息
	 */
	@RequestMapping(value = "mesanInfoAddAll")
	public String mesanInfoAddAll(String ids,String activityId,String activityDirId,String typeId, RedirectAttributes redirectAttributes) {
		//得到最大的
		String idArray[] =ids.split(",");
		for(String id : idArray){
			DistribuTemp distribuTemp = new DistribuTemp();
			distribuTemp.setActivityId(activityId);
			distribuTemp.setActivityDirId(activityDirId);
			distribuTemp.setTypeId(typeId);
			distribuTemp.setRefId(id);
			distribuTempService.save(distribuTemp);
		}
		addMessage(redirectAttributes, "添加资料成功");
		return "redirect:"+Global.getAdminPath()+"/train/distribuTemp/?repage";
	}
	
    ///////////////
    //资源部分
    //////////////
	/**
	 * 资源管理列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"resourceinfolist"})
	public String resourceInfoList(ResourceInfo resourceInfo, DistribuTemp distribuTemp, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ResourceInfo> page =new Page<ResourceInfo>(request, response);
		page = resourceInfoService.findSelResourceInfoPage(page, resourceInfo); 
		model.addAttribute("page", page);
		model.addAttribute("distribuInfo",distribuTemp);
		return "modules/train/distribuTempResourceInfoList";
	}
	
	/**
	 * 批量添加资料信息
	 
	@RequestMapping(value = "resourceInfoAddAll")
	public String resourceInfoAddAll(String ids,String activityId,String activityDirId,String typeId, RedirectAttributes redirectAttributes) {
		//得到最大的
		String idArray[] =ids.split(",");
		for(String id : idArray){
			DistribuTemp distribuTemp = new DistribuTemp();
			distribuTemp.setActivityId(activityId);
			distribuTemp.setActivityDirId(activityDirId);
			distribuTemp.setTypeId(typeId);
			distribuTemp.setRefId(id);
			distribuTempService.save(distribuTemp);
		}
		addMessage(redirectAttributes, "添加资料成功");
		return "redirect:"+Global.getAdminPath()+"/train/distribuTemp/?repage";
	}
	*/
    ///////////////
    //问卷部分
    //////////////
	/**
	 * 问卷信息列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"questioninfolist"})
	public String questionInfoList(QuestionInfo questionInfo, DistribuTemp distribuTemp, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QuestionInfo> page = new Page<QuestionInfo>(request, response);
		page = questionInfoService.findSelQuesInfoPage(new Page<QuestionInfo>(request, response), questionInfo); 
		model.addAttribute("page", page);
		model.addAttribute("distribuInfo",distribuTemp);
		return "modules/train/distribuTempQuestionInfoList";
	}
	
	/**
	@RequestMapping(value = "questionInfoAddAll")
	public String questionInfoAddAll(String ids,String activityId,String activityDirId,String typeId, RedirectAttributes redirectAttributes) {
		//得到最大的
		String idArray[] =ids.split(",");
		for(String id : idArray){
			DistribuTemp distribuTemp = new DistribuTemp();
			distribuTemp.setActivityId(activityId);
			distribuTemp.setActivityDirId(activityDirId);
			distribuTemp.setTypeId(typeId);
			distribuTemp.setRefId(id);
			distribuTempService.save(distribuTemp);
		}
		addMessage(redirectAttributes, "添加资料成功");
		return "redirect:"+Global.getAdminPath()+"/train/distribuTemp/?repage";
	}
	*/
	////
	//得分设定
	////
	/**
	 * 设定分数列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"listscore"})
	public String listScore(DistribuTemp distribuTemp, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<DistribuTemp> distribuTempList = distribuTempService.findListForDir(distribuTemp);
		model.addAttribute("distribuInfo",distribuTemp);
		model.addAttribute("list", distribuTempList);
		return "modules/train/distribuTempListScore";
	}
	/**
	 * 批量设定分数页面
	 */
	@RequestMapping(value = "savescoreall")
	public String saveScoreAll(String ids,String scores,String activityDirId, RedirectAttributes redirectAttributes) {
		//得到最大的
		String idArray[] =ids.split(",");
		String scoreArray[] =scores.split(",");
		int flagi = 0;
		for(String id : idArray){
			DistribuTemp distribuTemp = distribuTempService.get(id);
			//distribuTemp.setResScore(scoreArray[flagi]);
			distribuTempService.save(distribuTemp);
			flagi++;
		}
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/train/distribuTemp/listscore?activityDirId="+activityDirId;
	}
}