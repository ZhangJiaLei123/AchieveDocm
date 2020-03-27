/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import java.io.IOException;
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

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.entity.OfficeType;
import com.jeeplus.modules.sys.service.OfficeTypeService;

/**
 * 机构分类Controller
 * @author panjp
 * @version 2017-03-25
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/officeType")
public class OfficeTypeController extends BaseController {

	@Autowired
	private OfficeTypeService officeTypeService;
	
	@ModelAttribute
	public OfficeType get(@RequestParam(required=false) String id) {
		OfficeType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = officeTypeService.get(id);
		}
		if (entity == null){
			entity = new OfficeType();
		}
		return entity;
	}
	
	/**
	 * 机构分类列表页面
	 */
	@RequiresPermissions("sys:officeType:list")
	@RequestMapping(value = {"list", ""})
	public String list(OfficeType officeType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OfficeType> page = officeTypeService.findPage(new Page<OfficeType>(request, response), officeType); 
		model.addAttribute("page", page);
		return "modules/sys/officeTypeList";
	}
	/**
	 * 机构分类列表页面
	 */
	@RequiresPermissions("sys:officeType:list")
	@RequestMapping(value = {"treeOfficeTypeList"})
	public String treeOfficeTypeList(OfficeType officeType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OfficeType> page = officeTypeService.findPage(new Page<OfficeType>(request, response,-1), officeType); 
		model.addAttribute("list", page.getList());
		return "modules/sys/treeOfficeTypeList";
	}
	/**
	 * 查看，增加，编辑机构分类表单页面
	 */
	@RequiresPermissions(value={"sys:officeType:view","sys:officeType:add","sys:officeType:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OfficeType officeType, Model model) {
		model.addAttribute("officeType", officeType);
		return "modules/sys/officeTypeForm";
	}

	/**
	 * 保存机构分类
	 */
	@RequiresPermissions(value={"sys:officeType:add","sys:officeType:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OfficeType officeType, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, officeType)){
			return form(officeType, model);
		}
		if(!officeType.getIsNewRecord()){//编辑表单保存
			OfficeType t = officeTypeService.get(officeType.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(officeType, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			officeTypeService.save(t);//保存
		}else{//新增表单保存
			officeTypeService.save(officeType);//保存
		}
		addMessage(redirectAttributes, "保存机构分类成功");
		return "redirect:"+Global.getAdminPath()+"/sys/officeType/treeOfficeTypeList";
	}
	
	/**
	 * 删除机构分类
	 */
	@RequiresPermissions("sys:officeType:del")
	@RequestMapping(value = "delete")
	public String delete(OfficeType officeType, RedirectAttributes redirectAttributes) {
		List<OfficeType> lsOffType =officeTypeService.findChildrenOfficeType(officeType);
		if(null!=lsOffType && !lsOffType.isEmpty()){
			addMessage(redirectAttributes, "删除机构分类失败，请先删除该分类下的子级！");
			return "redirect:"+Global.getAdminPath()+"/sys/officeType/treeOfficeTypeList";
		}
		
		List<Map> lsMap =officeTypeService.findOfficeByTypeId(officeType.getId());
		if(null!=lsMap && !lsMap.isEmpty()){
			addMessage(redirectAttributes, "删除机构分类失败，当前分类下还有组织无法删除！");
			return "redirect:"+Global.getAdminPath()+"/sys/officeType/treeOfficeTypeList";
		}
			officeTypeService.delete(officeType);
		addMessage(redirectAttributes, "删除机构分类成功");
		return "redirect:"+Global.getAdminPath()+"/sys/officeType/treeOfficeTypeList";
	}
	
	/**
	 * 批量删除机构分类
	 */
	@RequiresPermissions("sys:officeType:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			officeTypeService.delete(officeTypeService.get(id));
		}
		addMessage(redirectAttributes, "删除机构分类成功");
		return "redirect:"+Global.getAdminPath()+"/sys/officeType/treeOfficeTypeList";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:officeType:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(OfficeType officeType, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "机构分类"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OfficeType> page = officeTypeService.findPage(new Page<OfficeType>(request, response, -1), officeType);
    		new ExportExcel("机构分类", OfficeType.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出机构分类记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/officeType/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:officeType:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OfficeType> list = ei.getDataList(OfficeType.class);
			for (OfficeType officeType : list){
				try{
					officeTypeService.save(officeType);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条机构分类记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条机构分类记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入机构分类失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/officeType/?repage";
    }
	
	/**
	 * 下载导入机构分类数据模板
	 */
	@RequiresPermissions("sys:officeType:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "机构分类数据导入模板.xlsx";
    		List<OfficeType> list = Lists.newArrayList(); 
    		new ExportExcel("机构分类数据", OfficeType.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/officeType/?repage";
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
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId,String dataid,@RequestParam(required=false) String isShowHide, HttpServletResponse response){
		List<Map<String, Object>> mapList = Lists.newArrayList();
		OfficeType oftype = new OfficeType();
		oftype.setUpdateDataId(dataid);
		List<OfficeType> list = officeTypeService.findList(oftype);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", list.get(i).getId());
			map.put("pId", list.get(i).getParentId());
			map.put("name", list.get(i).getName());
			mapList.add(map);
		}
		return mapList;
	}
	/**
	 * 组织类别
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("toSelectOfficeType")
	public String toSelectOfficeType(HttpServletRequest request, HttpServletResponse response, Model model,String officeName,String pageNo,String officeTypeId,String officeTypeName) {
		return "modules/sys/officetype/zTreeOffice";
	}
	/**
	 * 组织人员--查询机构类别树
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("findTreeOffice")
	public void findTreeOffice(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map>  map = officeTypeService.findTreeOffice();
		try {
			String str=JSONObject.toJSONString(map);
			response.getWriter().print( str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}