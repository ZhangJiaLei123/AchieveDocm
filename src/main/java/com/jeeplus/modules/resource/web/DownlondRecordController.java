/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.web;

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
import com.jeeplus.modules.resource.entity.DownlondRecord;
import com.jeeplus.modules.resource.service.DownlondRecordService;

/**
 * 资料下载记录表Controller
 * @author wsp
 * @version 2017-04-10
 */
@Controller
@RequestMapping(value = "${adminPath}/resource/downlondRecord")
public class DownlondRecordController extends BaseController {

	@Autowired
	private DownlondRecordService downlondRecordService;
	
	@ModelAttribute
	public DownlondRecord get(@RequestParam(required=false) String id) {
		DownlondRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = downlondRecordService.get(id);
		}
		if (entity == null){
			entity = new DownlondRecord();
		}
		return entity;
	}
	
	/**
	 * 资料下载记录列表页面
	 */
	@RequiresPermissions("resource:downlondRecord:list")
	@RequestMapping(value = {"list", ""})
	public String list(DownlondRecord downlondRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DownlondRecord> page = downlondRecordService.findPage(new Page<DownlondRecord>(request, response), downlondRecord); 
		model.addAttribute("page", page);
		return "modules/resource/downlondRecordList";
	}

	/**
	 * 查看，增加，编辑资料下载记录表单页面
	 */
	@RequiresPermissions(value={"resource:downlondRecord:view","resource:downlondRecord:add","resource:downlondRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DownlondRecord downlondRecord, Model model) {
		model.addAttribute("downlondRecord", downlondRecord);
		return "modules/resource/downlondRecordForm";
	}

	/**
	 * 保存资料下载记录
	 */
	@RequiresPermissions(value={"resource:downlondRecord:add","resource:downlondRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DownlondRecord downlondRecord, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, downlondRecord)){
			return form(downlondRecord, model);
		}
		if(!downlondRecord.getIsNewRecord()){//编辑表单保存
			DownlondRecord t = downlondRecordService.get(downlondRecord.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(downlondRecord, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			downlondRecordService.save(t);//保存
		}else{//新增表单保存
			downlondRecordService.save(downlondRecord);//保存
		}
		addMessage(redirectAttributes, "保存资料下载记录成功");
		return "redirect:"+Global.getAdminPath()+"/resource/downlondRecord/?repage";
	}
	
	/**
	 * 删除资料下载记录
	 */
	@RequiresPermissions("resource:downlondRecord:del")
	@RequestMapping(value = "delete")
	public String delete(DownlondRecord downlondRecord, RedirectAttributes redirectAttributes) {
		downlondRecordService.delete(downlondRecord);
		addMessage(redirectAttributes, "删除资料下载记录成功");
		return "redirect:"+Global.getAdminPath()+"/resource/downlondRecord/?repage";
	}
	
	/**
	 * 批量删除资料下载记录
	 */
	@RequiresPermissions("resource:downlondRecord:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			downlondRecordService.delete(downlondRecordService.get(id));
		}
		addMessage(redirectAttributes, "删除资料下载记录成功");
		return "redirect:"+Global.getAdminPath()+"/resource/downlondRecord/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("resource:downlondRecord:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DownlondRecord downlondRecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资料下载记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DownlondRecord> page = downlondRecordService.findPage(new Page<DownlondRecord>(request, response, -1), downlondRecord);
    		new ExportExcel("资料下载记录", DownlondRecord.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出资料下载记录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/downlondRecord/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("resource:downlondRecord:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DownlondRecord> list = ei.getDataList(DownlondRecord.class);
			for (DownlondRecord downlondRecord : list){
				try{
					downlondRecordService.save(downlondRecord);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资料下载记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资料下载记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资料下载记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/downlondRecord/?repage";
    }
	
	/**
	 * 下载导入资料下载记录数据模板
	 */
	@RequiresPermissions("resource:downlondRecord:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资料下载记录数据导入模板.xlsx";
    		List<DownlondRecord> list = Lists.newArrayList(); 
    		new ExportExcel("资料下载记录数据", DownlondRecord.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/downlondRecord/?repage";
    }
	
	
	

}