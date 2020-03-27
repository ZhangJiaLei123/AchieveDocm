/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.web;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.resource.entity.MesanInfo;
import com.jeeplus.modules.resource.entity.ResourceDir;
import com.jeeplus.modules.resource.entity.ResourceInfo;
import com.jeeplus.modules.resource.service.ResourceDirService;
import com.jeeplus.modules.resource.service.ResourceInfoService;
import com.jeeplus.modules.sys.service.SystemService;

/**
 * 资源目录Controller
 * @author panjp
 * @version 2017-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/resource/resourceDir")
public class ResourceDirController extends BaseController {

	@Autowired
	private ResourceDirService resourceDirService;
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private ResourceInfoService resourceInfoService;
	@ModelAttribute
	public ResourceDir get(@RequestParam(required=false) String id) {
		ResourceDir entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = resourceDirService.get(id);
		}
		if (entity == null){
			entity = new ResourceDir();
		}
		return entity;
	}
	
	/**
	 * 资源目录列表页面
	 */
	@RequiresPermissions("resource:resourceDir:list")
	@RequestMapping(value = {"list", ""})
	public String list(ResourceDir resourceDir, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ResourceDir> page = resourceDirService.findPage(new Page<ResourceDir>(request, response), resourceDir); 
		model.addAttribute("page", page);
		return "modules/resource/resourceDirList";
	}

	/**
	 * 资源目录列表页面
	 */
	@RequiresPermissions("resource:resourceDir:list")
	@RequestMapping(value = {"treeResourceDir"})
	public String treeResourceDir(ResourceDir resourceDir, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ResourceDir> page = resourceDirService.findPage(new Page<ResourceDir>(request, response,-1), resourceDir); 
		model.addAttribute("list", page.getList());
		return "modules/resource/treeResourceDirList";
	}
	
	/**
	 * 查看，增加，编辑资源目录表单页面
	 */
	@RequiresPermissions(value={"resource:resourceDir:view","resource:resourceDir:add","resource:resourceDir:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ResourceDir resourceDir, Model model) {
		model.addAttribute("resourceDir", resourceDir);
		return "modules/resource/resourceDirForm";
	}

	/**
	 * 保存资源目录
	 */
	@RequiresPermissions(value={"resource:resourceDir:add","resource:resourceDir:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ResourceDir resourceDir, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resourceDir)){
			return form(resourceDir, model);
		}
		if(!resourceDir.getIsNewRecord()){//编辑表单保存
			ResourceDir t = resourceDirService.get(resourceDir.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(resourceDir, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			systemService.saveResourceDir(t);//保存
		}else{//新增表单保存
			systemService.saveResourceDir(resourceDir);//保存
		}
		addMessage(redirectAttributes, "保存资源目录成功");
		return "redirect:"+Global.getAdminPath()+"/resource/resourceDir/treeResourceDir";
	}
	
	/**
	 * 删除资源目录
	 */
	@RequiresPermissions("resource:resourceDir:del")
	@RequestMapping(value = "delete")
	public String delete(ResourceDir resourceDir, RedirectAttributes redirectAttributes) {
		resourceDir.setParentIds(resourceDir.getId());
		resourceDir.setName("");
		List<ResourceDir> resDirLs = resourceDirService.findList(resourceDir);
		if(null != resDirLs && !resDirLs.isEmpty()){
			addMessage(redirectAttributes, "删除资源目录失败，当前目录还有子目录。");
			return "redirect:"+Global.getAdminPath()+"/resource/resourceDir/treeResourceDir";
		}
		ResourceInfo resourceInfo = new ResourceInfo();
		resourceInfo.setResourceDir(resourceDir.getId());
		List<ResourceInfo> ls = resourceInfoService.findList(resourceInfo);
		if(null != ls && !ls.isEmpty()){
			addMessage(redirectAttributes, "删除资源目录失败，当前目录还有资源引用。");
		}else{
			resourceDirService.delete(resourceDir);
			addMessage(redirectAttributes, "删除资源目录成功");
		}
		return "redirect:"+Global.getAdminPath()+"/resource/resourceDir/treeResourceDir";
	}
	
	/**
	 * 批量删除资源目录
	 */
	@RequiresPermissions("resource:resourceDir:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			resourceDirService.delete(resourceDirService.get(id));
		}
		addMessage(redirectAttributes, "删除资源目录成功");
		return "redirect:"+Global.getAdminPath()+"/resource/resourceDir/treeResourceDir";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("resource:resourceDir:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ResourceDir resourceDir, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资源目录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ResourceDir> page = resourceDirService.findPage(new Page<ResourceDir>(request, response, -1), resourceDir);
    		new ExportExcel("资源目录", ResourceDir.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出资源目录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/resourceDir/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("resource:resourceDir:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ResourceDir> list = ei.getDataList(ResourceDir.class);
			for (ResourceDir resourceDir : list){
				try{
					resourceDirService.save(resourceDir);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资源目录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资源目录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资源目录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/resourceDir/?repage";
    }
	
	/**
	 * 下载导入资源目录数据模板
	 */
	@RequiresPermissions("resource:resourceDir:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资源目录数据导入模板.xlsx";
    		List<ResourceDir> list = Lists.newArrayList(); 
    		new ExportExcel("资源目录数据", ResourceDir.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/resourceDir/?repage";
    }
	
	/**
	 * isShowHide是否显示隐藏菜单
	 * @param extId
	 * @param isShowHidden
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId,String dataid,@RequestParam(required=false) String isShowHide, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		 ResourceDir dir = new ResourceDir();
		 dir.setUpateDateId(dataid);
		List<ResourceDir> list = resourceDirService.findList(dir);
		for (int i=0; i<list.size(); i++){
			ResourceDir e = list.get(i);
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		return mapList;
	}
	/**
	 * 验证排序是否存在
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "checkResourceDirSort")
	public String checkResourceDirSort(String sort,String oldResourceDirSort) {
		if (sort !=null && sort.equals(oldResourceDirSort)) {
			return "true";
		}else if (sort !=null && !"".equals(sort.trim())) {
			 ResourceDir dir = new ResourceDir();
			 dir.setSort(sort);
			 List<ResourceDir> resourceDirLs = resourceDirService.findList(dir);
			if(null==resourceDirLs || resourceDirLs.isEmpty()){
				return "true";
			}
		} 
		return "false";
	}

}