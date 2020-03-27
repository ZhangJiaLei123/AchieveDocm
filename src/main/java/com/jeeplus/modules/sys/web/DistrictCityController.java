/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
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
import com.jeeplus.modules.sys.entity.DistrictCity;
import com.jeeplus.modules.sys.service.DistrictCityService;

/**
 * 大区城市Controller
 * @author panjp
 * @version 2017-05-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/districtCity")
public class DistrictCityController extends BaseController {

	@Autowired
	private DistrictCityService districtCityService;
	
	@ModelAttribute
	public DistrictCity get(@RequestParam(required=false) String id) {
		DistrictCity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = districtCityService.get(id);
		}
		if (entity == null){
			entity = new DistrictCity();
		}
		return entity;
	}
	
	/**
	 * 大区城市列表页面
	 */
	@RequiresPermissions("sys:districtCity:list")
	@RequestMapping(value = {"list", ""})
	public String list(DistrictCity districtCity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DistrictCity> page = districtCityService.findPage(new Page<DistrictCity>(request, response), districtCity); 
		model.addAttribute("page", page);
		return "modules/sys/districtCityList";
	}

	/**
	 * 查看，增加，编辑大区城市表单页面
	 */
	@RequiresPermissions(value={"sys:districtCity:view","sys:districtCity:add","sys:districtCity:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DistrictCity districtCity, Model model) {
		model.addAttribute("districtCity", districtCity);
		return "modules/sys/districtCityForm";
	}

	/**
	 * 保存大区城市
	 */
	@RequiresPermissions(value={"sys:districtCity:add","sys:districtCity:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DistrictCity districtCity, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, districtCity)){
			return form(districtCity, model);
		}
		if(!districtCity.getIsNewRecord()){//编辑表单保存
			DistrictCity t = districtCityService.get(districtCity.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(districtCity, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			districtCityService.save(t);//保存
		}else{//新增表单保存
			districtCityService.save(districtCity);//保存
		}
		addMessage(redirectAttributes, "保存大区城市成功");
		return "redirect:"+Global.getAdminPath()+"/sys/districtCity/?repage";
	}
	
	/**
	 * 删除大区城市
	 */
	@RequiresPermissions("sys:districtCity:del")
	@RequestMapping(value = "delete")
	public String delete(DistrictCity districtCity, RedirectAttributes redirectAttributes) {
		districtCityService.delete(districtCity);
		addMessage(redirectAttributes, "删除大区城市成功");
		return "redirect:"+Global.getAdminPath()+"/sys/districtCity/?repage";
	}
	
	/**
	 * 批量删除大区城市
	 */
	@RequiresPermissions("sys:districtCity:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			districtCityService.delete(districtCityService.get(id));
		}
		addMessage(redirectAttributes, "删除大区城市成功");
		return "redirect:"+Global.getAdminPath()+"/sys/districtCity/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:districtCity:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DistrictCity districtCity, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "大区城市"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DistrictCity> page = districtCityService.findPage(new Page<DistrictCity>(request, response, -1), districtCity);
    		new ExportExcel("大区城市", DistrictCity.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出大区城市记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/districtCity/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:districtCity:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DistrictCity> list = ei.getDataList(DistrictCity.class);
			for (DistrictCity districtCity : list){
				try{
					districtCityService.save(districtCity);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条大区城市记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条大区城市记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入大区城市失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/districtCity/?repage";
    }
	
	/**
	 * 下载导入大区城市数据模板
	 */
	@RequiresPermissions("sys:districtCity:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "大区城市数据导入模板.xlsx";
    		List<DistrictCity> list = Lists.newArrayList(); 
    		new ExportExcel("大区城市数据", DistrictCity.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/districtCity/?repage";
    }
	
	
	

}