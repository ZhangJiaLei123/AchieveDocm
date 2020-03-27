/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

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
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.entity.TUserGrade;
import com.jeeplus.modules.sys.service.TUserGradeService;

/**
 * 用户等级管理Controller
 * @author 潘兴武
 * @version 2018-01-04
 */
@Controller
@RequestMapping(value = "${adminPath}/usergrade/tUserGrade")
public class TUserGradeController extends BaseController {

	@Autowired
	private TUserGradeService tUserGradeService;
	
	@ModelAttribute
	public TUserGrade get(@RequestParam(required=false) String id) {
		TUserGrade entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tUserGradeService.get(id);
		}
		if (entity == null){
			entity = new TUserGrade();
		}
		return entity;
	}
	
	/**
	 * 等级管理列表页面
	 */
	@RequiresPermissions("usergrade:tUserGrade:list")
	@RequestMapping(value = {"list", ""})
	public String list(TUserGrade tUserGrade, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TUserGrade> page = tUserGradeService.findPage(new Page<TUserGrade>(request, response), tUserGrade); 
		model.addAttribute("page", page);
		model.addAttribute("tUserGrade", tUserGrade);
		return "modules/sys/tUserGradeList";
	}

	/**
	 * 查看，增加，编辑等级管理表单页面
	 */
	@RequiresPermissions(value={"usergrade:tUserGrade:view","usergrade:tUserGrade:add","usergrade:tUserGrade:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TUserGrade tUserGrade, Model model) {
		model.addAttribute("tUserGrade", tUserGrade);
		return "modules/sys/tUserGradeForm";
	}

	/**
	 * 保存等级管理
	 */
	@RequiresPermissions(value={"usergrade:tUserGrade:add","usergrade:tUserGrade:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TUserGrade tUserGrade, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, tUserGrade)){
			return form(tUserGrade, model);
		}
		if(!tUserGrade.getIsNewRecord()){//编辑表单保存
			TUserGrade t = tUserGradeService.get(tUserGrade.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(tUserGrade, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			tUserGradeService.save(t);//保存
		}else{//新增表单保存
			tUserGradeService.save(tUserGrade);//保存
		}
		addMessage(redirectAttributes, "保存等级管理成功");
		return "redirect:"+Global.getAdminPath()+"/usergrade/tUserGrade/?repage";
	}
	
	/**
	 * 删除等级管理
	 */
	@RequiresPermissions("usergrade:tUserGrade:del")
	@RequestMapping(value = "delete")
	public String delete(TUserGrade tUserGrade, RedirectAttributes redirectAttributes) {
		tUserGradeService.delete(tUserGrade);
		addMessage(redirectAttributes, "删除等级管理成功");
		return "redirect:"+Global.getAdminPath()+"/usergrade/tUserGrade/?repage";
	}
	
	/**
	 * 批量删除等级管理
	 */
	@RequiresPermissions("usergrade:tUserGrade:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			tUserGradeService.delete(tUserGradeService.get(id));
		}
		addMessage(redirectAttributes, "删除等级管理成功");
		return "redirect:"+Global.getAdminPath()+"/usergrade/tUserGrade/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("usergrade:tUserGrade:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(TUserGrade tUserGrade, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "等级管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TUserGrade> page = tUserGradeService.findPage(new Page<TUserGrade>(request, response, -1), tUserGrade);
    		new ExportExcel("等级管理", TUserGrade.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出等级管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/usergrade/tUserGrade/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("usergrade:tUserGrade:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TUserGrade> list = ei.getDataList(TUserGrade.class);
			for (TUserGrade tUserGrade : list){
				try{
					tUserGradeService.save(tUserGrade);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条等级管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条等级管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入等级管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/usergrade/tUserGrade/?repage";
    }
	
	/**
	 * 下载导入等级管理数据模板
	 */
	@RequiresPermissions("usergrade:tUserGrade:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "等级管理数据导入模板.xlsx";
    		List<TUserGrade> list = Lists.newArrayList(); 
    		new ExportExcel("等级管理数据", TUserGrade.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/usergrade/tUserGrade/?repage";
    }
	
	
	

}