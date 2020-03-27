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
import com.jeeplus.modules.train.entity.ProgramFocus;
import com.jeeplus.modules.train.service.ProgramFocusService;

/**
 * 用户关注活动计划表Controller
 * @author wsp
 * @version 2017-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/train/programFocus")
public class ProgramFocusController extends BaseController {

	@Autowired
	private ProgramFocusService programFocusService;
	
	@ModelAttribute
	public ProgramFocus get(@RequestParam(required=false) String id) {
		ProgramFocus entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = programFocusService.get(id);
		}
		if (entity == null){
			entity = new ProgramFocus();
		}
		return entity;
	}
	
	/**
	 * 用户关注活动列表页面
	 */
	@RequiresPermissions("train:programFocus:list")
	@RequestMapping(value = {"list", ""})
	public String list(ProgramFocus programFocus, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProgramFocus> page = programFocusService.findPage(new Page<ProgramFocus>(request, response), programFocus); 
		model.addAttribute("page", page);
		return "modules/train/programFocusList";
	}

	/**
	 * 查看，增加，编辑用户关注活动表单页面
	 */
	@RequiresPermissions(value={"train:programFocus:view","train:programFocus:add","train:programFocus:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ProgramFocus programFocus, Model model) {
		model.addAttribute("programFocus", programFocus);
		return "modules/train/programFocusForm";
	}

	/**
	 * 保存用户关注活动
	 */
	@RequiresPermissions(value={"train:programFocus:add","train:programFocus:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ProgramFocus programFocus, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, programFocus)){
			return form(programFocus, model);
		}
		if(!programFocus.getIsNewRecord()){//编辑表单保存
			ProgramFocus t = programFocusService.get(programFocus.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(programFocus, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			programFocusService.save(t);//保存
		}else{//新增表单保存
			programFocusService.save(programFocus);//保存
		}
		addMessage(redirectAttributes, "保存用户关注活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/programFocus/?repage";
	}
	
	/**
	 * 删除用户关注活动
	 */
	@RequiresPermissions("train:programFocus:del")
	@RequestMapping(value = "delete")
	public String delete(ProgramFocus programFocus, RedirectAttributes redirectAttributes) {
		programFocusService.delete(programFocus);
		addMessage(redirectAttributes, "删除用户关注活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/programFocus/?repage";
	}
	
	/**
	 * 批量删除用户关注活动
	 */
	@RequiresPermissions("train:programFocus:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			programFocusService.delete(programFocusService.get(id));
		}
		addMessage(redirectAttributes, "删除用户关注活动成功");
		return "redirect:"+Global.getAdminPath()+"/train/programFocus/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("train:programFocus:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ProgramFocus programFocus, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户关注活动"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ProgramFocus> page = programFocusService.findPage(new Page<ProgramFocus>(request, response, -1), programFocus);
    		new ExportExcel("用户关注活动", ProgramFocus.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户关注活动记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/programFocus/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("train:programFocus:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ProgramFocus> list = ei.getDataList(ProgramFocus.class);
			for (ProgramFocus programFocus : list){
				try{
					programFocusService.save(programFocus);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户关注活动记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户关注活动记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户关注活动失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/programFocus/?repage";
    }
	
	/**
	 * 下载导入用户关注活动数据模板
	 */
	@RequiresPermissions("train:programFocus:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户关注活动数据导入模板.xlsx";
    		List<ProgramFocus> list = Lists.newArrayList(); 
    		new ExportExcel("用户关注活动数据", ProgramFocus.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/train/programFocus/?repage";
    }
	
	
	

}