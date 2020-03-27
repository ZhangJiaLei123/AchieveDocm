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

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.train.entity.PostActivity;
import com.jeeplus.modules.train.service.PostActivityService;

/**
 * 岗位活动Controller
 * @author 岗位活动
 * @version 2017-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/train/postActivity")
public class PostActivityController extends BaseController {

	@Autowired
	private PostActivityService postActivityService;
	
	@ModelAttribute
	public PostActivity get(@RequestParam(required=false) String id) {
		PostActivity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = postActivityService.get(id);
		}
		if (entity == null){
			entity = new PostActivity();
		}
		return entity;
	}
	
	/**
	 * 岗位活动列表页面
	 */
	@RequiresPermissions("train:postActivity:list")
	@RequestMapping(value = {"list", ""})
	public String list(PostActivity postActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PostActivity> page = postActivityService.findPage(new Page<PostActivity>(request, response), postActivity); 
		model.addAttribute("page", page);
		return "modules/train/postActivityList";
	}

	/**
	 * 查看，增加，编辑岗位活动表单页面
	 */
	@RequiresPermissions(value={"train:postActivity:view","train:postActivity:add","train:postActivity:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PostActivity postActivity, Model model) {
		model.addAttribute("postActivity", postActivity);
		return "modules/train/postActivityForm";
	}

	/**
	 * 保存岗位活动
	 */
	@RequiresPermissions(value={"train:postActivity:add","train:postActivity:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(PostActivity postActivity, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, postActivity)){
			return form(postActivity, model);
		}
		if(!postActivity.getIsNewRecord()){//编辑表单保存
			PostActivity t = postActivityService.get(postActivity.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(postActivity, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			postActivityService.save(t);//保存
		}else{//新增表单保存
			postActivityService.save(postActivity);//保存
		}
		addMessage(redirectAttributes, "保存岗位活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/postActivity/?repage";
	}
	
	/**
	 * 删除岗位活动
	 */
	@RequiresPermissions("train:postActivity:del")
	@RequestMapping(value = "delete")
	public String delete(PostActivity postActivity, RedirectAttributes redirectAttributes) {
		postActivityService.delete(postActivity);
		addMessage(redirectAttributes, "删除岗位活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/postActivity/?repage";
	}
	
	/**
	 * 批量删除岗位活动
	 */
	@RequiresPermissions("train:postActivity:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			postActivityService.delete(postActivityService.get(id));
		}
		addMessage(redirectAttributes, "删除岗位活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/postActivity/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("train:postActivity:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(PostActivity postActivity, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "岗位活动"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PostActivity> page = postActivityService.findPage(new Page<PostActivity>(request, response, -1), postActivity);
    		new ExportExcel("岗位活动", PostActivity.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出岗位活动记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/postActivity/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("train:postActivity:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PostActivity> list = ei.getDataList(PostActivity.class);
			for (PostActivity postActivity : list){
				try{
					postActivityService.save(postActivity);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条岗位活动记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条岗位活动记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入岗位活动失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/postActivity/?repage";
    }
	
	/**
	 * 下载导入岗位活动数据模板
	 */
	@RequiresPermissions("train:postActivity:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "岗位活动数据导入模板.xlsx";
    		List<PostActivity> list = Lists.newArrayList(); 
    		new ExportExcel("岗位活动数据", PostActivity.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/postActivity/?repage";
    }
	
	
	

}