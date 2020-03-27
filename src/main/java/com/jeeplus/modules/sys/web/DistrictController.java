/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

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

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.cst.HeadCst;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.entity.District;
import com.jeeplus.modules.sys.entity.DistrictCity;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.ProvinceCity;
import com.jeeplus.modules.sys.service.DistrictCityService;
import com.jeeplus.modules.sys.service.DistrictService;
import com.jeeplus.modules.sys.service.OfficeService;

/**
 * 区域管理Controller
 * @author panjp
 * @version 2017-05-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/district")
public class DistrictController extends BaseController {

	@Autowired
	private DistrictService districtService;
	@Autowired
	private DistrictCityService districtCityService;
	
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public District get(@RequestParam(required=false) String id) {
		District entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = districtService.get(id);
		}
		if (entity == null){
			entity = new District();
		}
		return entity;
	}
	
	/**
	 * 区域管理列表页面
	 */
	@RequiresPermissions("sys:district:list")
	@RequestMapping(value = {"list", ""})
	public String list(District district, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<District> page = districtService.findPageList(new Page<District>(request, response), district); 
		model.addAttribute("page", page);
		return "modules/sys/districtList";
	}
	/**
	 * 区域管理列表页面
	 */
	@ResponseBody
	@RequiresPermissions(value={"user"},logical=Logical.OR)
	@RequestMapping(value = {"checkCode"})
	public String checkCode(String code,String oldCode) {
		if (code !=null && code.equals(oldCode)) {
			return "true";
		} else if (code !=null && districtService.getDistrictByCode(code) == null) {
			return "true";
		}
		return "false";
	}
	
	/**
	 * 获取所有区域
	 */
	@ResponseBody
	@RequiresPermissions(value={"user"},logical=Logical.OR)
	@RequestMapping(value = {"getAllDistrict"})
	public String getAllDistrict() {
		List ls = districtService.findList(new District());
		if(null!=ls && !ls.isEmpty()){
			return JSONArray.toJSONString(ls);
		}else{
			return "";
		}
	}
	
	
	/**
	 * 根据区域ID获取省份
	 */
	@ResponseBody
	@RequiresPermissions(value={"user"},logical=Logical.OR)
	@RequestMapping(value = {"getProinceByDistrictId"})
	public String getProinceByDistrictId(String districtId) {
		List<Map> ls = districtService.getProinceByDistrictId(districtId);
		if(null!=ls && !ls.isEmpty()){
			return JSONArray.toJSONString(ls);
		}else{
			return "";
		}
	}
	/**
	 * 根据区域ID，省份ID获取城市
	 */
	@ResponseBody
	@RequiresPermissions(value={"user"},logical=Logical.OR)
	@RequestMapping(value = {"getCityByDistrictId"})
	public String getCityByDistrictId(String districtId,String provinceId) {
		List<Map> ls = districtService.getCityByDistrictId(districtId,provinceId);
		if(null!=ls && !ls.isEmpty()){
			return JSONArray.toJSONString(ls);
		}else{
			return "";
		}
	}
	
	
	/**
	 * 获取所有省份
	 */
	@ResponseBody
	@RequiresPermissions(value={"user"})
	@RequestMapping(value = {"getAllProvice"})
	public String getAllProvice() {
		String str = "";
		List<Map> mapList = districtService.findAllProvinceList();
		if(null!=mapList && !mapList.isEmpty()){
			str = JSONArray.toJSONString(mapList);
		}
		return str;
	}
	/**
	 * 获取所有城市
	 */
	@ResponseBody
	@RequiresPermissions(value={"user"})
	@RequestMapping(value = {"getCityByProvinceId"})
	public String getCityByProvinceId(String ids) {
		String str = "";
		List<Map> mapList = districtService.getCityByProvinceId(ids);
		if(null!=mapList && !mapList.isEmpty()){
			str = JSONArray.toJSONString(mapList);
		}
		return str;
	}
	/**
	 * 查看，增加，编辑区域管理表单页面
	 */
	@RequiresPermissions(value={"sys:district:view","sys:district:add","sys:district:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(District district, Model model) {
		List<Map> lsMap = districtService.findAllCheckedProvinceAndCity(district.getId());
		model.addAttribute("district", district);
		if(null!=lsMap && !lsMap.isEmpty()){
			model.addAttribute("lsMapStr", JSONArray.toJSONString(lsMap));
		}else{
			model.addAttribute("lsMapStr", "[]");
		}
		return "modules/sys/districtForm";
	}

	/**
	 * 保存区域管理
	 */
	@RequiresPermissions(value={"sys:district:add","sys:district:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(District district, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, district)){
			return form(district, model);
		}
		if(!district.getIsNewRecord()){//编辑表单保存
			District t = districtService.get(district.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(district, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			districtService.save(t);//保存
		}else{//新增表单保存
			districtService.save(district);//保存
		}
		addMessage(redirectAttributes, "保存区域管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/district/?repage";
	}
	
	/**
	 * 删除区域管理
	 */
	@RequiresPermissions("sys:district:del")
	@RequestMapping(value = "delete")
	public String delete(District district, RedirectAttributes redirectAttributes) {
		DistrictCity d = new DistrictCity();
		d.setDistrictId(district.getId());
		List disCity = districtCityService.findList(d);
		if(null!=disCity && !disCity.isEmpty()){
			Office of =new Office();
			of.setDistrctId(district.getId());
			List ls = officeService.findAllList(of);
			if(null!=ls && !ls.isEmpty()){
				addMessage(redirectAttributes, "删除区域城市失败，当前区域下有部门，不能删除。");
			}else{
				districtService.delete(district);
				addMessage(redirectAttributes, "删除区域成功");
			}
		}else{
			districtService.delete(district);
			addMessage(redirectAttributes, "删除区域成功");
		}
		return "redirect:"+Global.getAdminPath()+"/sys/district/?repage";
	}
	
	/**
	 * 批量删除区域管理
	 */
	@RequiresPermissions("sys:district:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			DistrictCity d = new DistrictCity();
			d.setDistrictId(id);
			List disCity = districtCityService.findList(d);
			if(null!=disCity && !disCity.isEmpty()){
				Office of =new Office();
				of.setActivityId(id);
				List ls = officeService.findAllList(of);
				if(null!=ls && !ls.isEmpty()){
					addMessage(redirectAttributes, "删除区域城市失败，当前区域下有部门，不能删除。");
					return "redirect:"+Global.getAdminPath()+"/sys/district/?repage";
				}else{
					districtService.delete(districtService.get(id));
					addMessage(redirectAttributes, "删除区域城市成功");
				}
			}else{
				districtService.delete(districtService.get(id));
				addMessage(redirectAttributes, "删除区域成功");
			}
		}
		return "redirect:"+Global.getAdminPath()+"/sys/district/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:district:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(District district, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "区域管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<District> page = districtService.findPageList(new Page<District>(request, response, -1), district);
    		new ExportExcel("区域管理", District.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出区域管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/district/?repage";
    }
	/**
	 * 导出省市excel文件
	 */
	@RequiresPermissions("sys:district:export")
    @RequestMapping(value = "exportProvinceCity", method=RequestMethod.GET)
    public String exportProvinceCity(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "省市"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ProvinceCity> page = districtService.findProvCityPageList(new Page<ProvinceCity>(request, response, -1), new ProvinceCity());
    		new ExportExcel("省市", ProvinceCity.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出区域管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/district/?repage";
    }
	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:district:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			if (!HeadCst.headValid(ei,District.class)) {
				addMessage(redirectAttributes, "您的导入模板不正确，请使用导出数据模板。");
			} else {
				List<District> list = ei.getDataList(District.class);
				if (null != list && !list.isEmpty()) {
					// 初始化所有大区、省份、城市。
					Map<String, String> mapDate = districtService.initMapDate();
					// 开始验证数据
					String messageStr = "";
					for (int i = 0; i < list.size(); i++) {
						District district = list.get(i);
						String code = district.getCode();
						String provinceName = district.getProvinceName();
						String cityName = district.getCityName();
						if (null == code || "".equals(code.trim())) {
							messageStr += "第" + (i + 3) + "行大区编码不能为空。";
						} else {
							if (null == mapDate.get(code.trim())) {
								messageStr += "第" + (i + 3) + "行大区编码不存在。";
							}
						}

						if (null == provinceName
								|| "".equals(provinceName.trim())) {
							messageStr += "第" + (i + 3) + "行省不能为空。";
						} else {
							if (null == mapDate.get(provinceName.trim())) {
								messageStr += "第" + (i + 3) + "行省错误。";
							}
						}

						if (null == cityName || "".equals(cityName.trim())) {
							messageStr += "第" + (i + 3) + "行市不能为空。";
						} else {
							if (null == mapDate.get(cityName.trim())) {
								messageStr += "第" + (i + 3) + "行市错误。";
							}
						}
					}
					if (null != messageStr && !"".equals(messageStr.trim())) {
						addMessage(redirectAttributes, messageStr);
					} else {
						// 验证成功后开始插入数据
						for (District district : list) {
							String code = district.getCode();
							String cityName = district.getCityName();
							String provinceName = district.getProvinceName();
							try {
								DistrictCity disCity = new DistrictCity();
								disCity.setCityId(mapDate.get(cityName.trim())
										.toString());
								disCity.setId(IdGen.uuid());
								disCity.setDistrictId(mapDate.get(code.trim())
										.toString());
								disCity.setProvinceId(mapDate.get(provinceName.trim())
										.toString());
								disCity.setIsNewRecord(true);
								districtService.saveDistrictCity(disCity);
								successNum++;
							} catch (ConstraintViolationException ex) {
								failureNum++;
							} catch (Exception ex) {
								failureNum++;
							}
						}
						if (failureNum > 0) {
							failureMsg.insert(0, "，失败 " + failureNum
									+ " 条区域管理记录。");
						}
						addMessage(redirectAttributes, "已成功导入 " + successNum
								+ " 条区域管理记录" + failureMsg);
					}
				}
			}
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入区域管理失败！失败信息：" + e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/district/?repage";
    }
	
	/**
	 * 下载导入区域管理数据模板
	 */
	@RequiresPermissions("sys:district:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "区域管理数据导入模板.xlsx";
    		List<District> list = Lists.newArrayList(); 
    		new ExportExcel("区域管理数据", District.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/district/?repage";
    }
	
	
	

}