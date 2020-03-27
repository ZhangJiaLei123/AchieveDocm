/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.record.web;

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
import com.jeeplus.modules.record.entity.UserStudyRecordMaterial;
import com.jeeplus.modules.record.service.UserStudyRecordMaterialService;

/**
 * 记录Controller
 * @author w
 * @version 2017-06-29
 */
@Controller
@RequestMapping(value = "${adminPath}/record/userStudyRecordMaterial")
public class UserStudyRecordMaterialController extends BaseController {

	@Autowired
	private UserStudyRecordMaterialService userStudyRecordMaterialService;
	
	@ModelAttribute
	public UserStudyRecordMaterial get(@RequestParam(required=false) String id) {
		UserStudyRecordMaterial entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userStudyRecordMaterialService.get(id);
		}
		if (entity == null){
			entity = new UserStudyRecordMaterial();
		}
		return entity;
	}
	
	/**
	 * 记录列表页面
	 */
	@RequiresPermissions("record:userStudyRecordMaterial:list")
	@RequestMapping(value = {"list", ""})
	public String list(UserStudyRecordMaterial userStudyRecordMaterial, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserStudyRecordMaterial> page = userStudyRecordMaterialService.findPage(new Page<UserStudyRecordMaterial>(request, response), userStudyRecordMaterial); 
		model.addAttribute("page", page);
		return "modules/record/userStudyRecordMaterialList";
	}

	/**
	 * 查看，增加，编辑记录表单页面
	 */
	@RequiresPermissions(value={"record:userStudyRecordMaterial:view","record:userStudyRecordMaterial:add","record:userStudyRecordMaterial:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(UserStudyRecordMaterial userStudyRecordMaterial, Model model) {
		model.addAttribute("userStudyRecordMaterial", userStudyRecordMaterial);
		return "modules/record/userStudyRecordMaterialForm";
	}

	/**
	 * 保存记录
	 */
	@RequiresPermissions(value={"record:userStudyRecordMaterial:add","record:userStudyRecordMaterial:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(UserStudyRecordMaterial userStudyRecordMaterial, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, userStudyRecordMaterial)){
			return form(userStudyRecordMaterial, model);
		}
		if(!userStudyRecordMaterial.getIsNewRecord()){//编辑表单保存
			UserStudyRecordMaterial t = userStudyRecordMaterialService.get(userStudyRecordMaterial.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(userStudyRecordMaterial, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			userStudyRecordMaterialService.save(t);//保存
		}else{//新增表单保存
			userStudyRecordMaterialService.save(userStudyRecordMaterial);//保存
		}
		addMessage(redirectAttributes, "保存记录成功");
		return "redirect:"+Global.getAdminPath()+"/record/userStudyRecordMaterial/?repage";
	}
	
	/**
	 * 删除记录
	 */
	@RequiresPermissions("record:userStudyRecordMaterial:del")
	@RequestMapping(value = "delete")
	public String delete(UserStudyRecordMaterial userStudyRecordMaterial, RedirectAttributes redirectAttributes) {
		userStudyRecordMaterialService.delete(userStudyRecordMaterial);
		addMessage(redirectAttributes, "删除记录成功");
		return "redirect:"+Global.getAdminPath()+"/record/userStudyRecordMaterial/?repage";
	}
	
	/**
	 * 批量删除记录
	 */
	@RequiresPermissions("record:userStudyRecordMaterial:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			userStudyRecordMaterialService.delete(userStudyRecordMaterialService.get(id));
		}
		addMessage(redirectAttributes, "删除记录成功");
		return "redirect:"+Global.getAdminPath()+"/record/userStudyRecordMaterial/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("record:userStudyRecordMaterial:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(UserStudyRecordMaterial userStudyRecordMaterial, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<UserStudyRecordMaterial> page = userStudyRecordMaterialService.findPage(new Page<UserStudyRecordMaterial>(request, response, -1), userStudyRecordMaterial);
    		new ExportExcel("记录", UserStudyRecordMaterial.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出记录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/record/userStudyRecordMaterial/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("record:userStudyRecordMaterial:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<UserStudyRecordMaterial> list = ei.getDataList(UserStudyRecordMaterial.class);
			for (UserStudyRecordMaterial userStudyRecordMaterial : list){
				try{
					userStudyRecordMaterialService.save(userStudyRecordMaterial);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/record/userStudyRecordMaterial/?repage";
    }
	
	/**
	 * 下载导入记录数据模板
	 */
	@RequiresPermissions("record:userStudyRecordMaterial:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "记录数据导入模板.xlsx";
    		List<UserStudyRecordMaterial> list = Lists.newArrayList(); 
    		new ExportExcel("记录数据", UserStudyRecordMaterial.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/record/userStudyRecordMaterial/?repage";
    }
	
	
	

}