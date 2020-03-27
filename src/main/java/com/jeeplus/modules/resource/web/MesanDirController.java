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
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.resource.entity.MesanDir;
import com.jeeplus.modules.resource.entity.MesanInfo;
import com.jeeplus.modules.resource.service.MesanDirService;
import com.jeeplus.modules.resource.service.MesanInfoService;
import com.jeeplus.modules.sys.service.SystemService;

/**
 * 资料目录Controller
 * @author panjp
 * @version 2017-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/resource/mesanDir")
public class MesanDirController extends BaseController {

	@Autowired
	private MesanDirService mesanDirService;
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private MesanInfoService mesanInfoService;
	@ModelAttribute
	public MesanDir get(@RequestParam(required=false) String id) {
		MesanDir entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mesanDirService.get(id);
		}
		if (entity == null){
			entity = new MesanDir();
		}
		return entity;
	}
	
	/**
	 * 资料目录列表页面
	 */
	@RequiresPermissions("resource:mesanDir:list")
	@RequestMapping(value = {"list", ""})
	public String list(MesanDir mesanDir, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MesanDir> page = mesanDirService.findPage(new Page<MesanDir>(request, response), mesanDir); 
		model.addAttribute("page", page);
		return "modules/resource/mesanInfoDirList";
	}

	/**
	 * 资料目录列表页面
	 */
	@RequiresPermissions("resource:mesanDir:list")
	@RequestMapping(value = {"treemesanDir"})
	public String treemesanDir(MesanDir mesanDir, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MesanDir> page = mesanDirService.findPage(new Page<MesanDir>(request, response,-1), mesanDir); 
		model.addAttribute("list", page.getList());
		return "modules/resource/treeMesanDirList";
	}
	
	/**
	 * 查看，增加，编辑资料目录表单页面
	 */
	@RequiresPermissions(value={"resource:mesanDir:view","resource:mesanDir:add","resource:mesanDir:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MesanDir mesanDir, Model model) {
		model.addAttribute("mesanDir", mesanDir);
		return "modules/resource/mesanInfoDirForm";
	}

	/**
	 * 保存资料目录
	 */
	@RequiresPermissions(value={"resource:mesanDir:add","resource:mesanDir:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MesanDir mesanDir, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, mesanDir)){
			return form(mesanDir, model);
		}
		if(!mesanDir.getIsNewRecord()){//编辑表单保存
			MesanDir t = mesanDirService.get(mesanDir.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(mesanDir, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			systemService.saveResourceDir(t);//保存;//保存
		}else{//新增表单保存
			systemService.saveResourceDir(mesanDir);//保存
		}
		addMessage(redirectAttributes, "保存资料目录成功");
		return "redirect:"+Global.getAdminPath()+"/resource/mesanDir/treemesanDir";
	}
	
	/**
	 * 删除资料目录
	 */
	@RequiresPermissions("resource:mesanDir:del")
	@RequestMapping(value = "delete")
	public String delete(MesanDir mesanDir, RedirectAttributes redirectAttributes) {
		mesanDir.setParentIds(mesanDir.getId());
		mesanDir.setName("");
		List<MesanDir> resDirLs = mesanDirService.findList(mesanDir);
		if(null != resDirLs && !resDirLs.isEmpty()){
			addMessage(redirectAttributes, "删除资料目录失败，当前目录还有子目录。");
			return "redirect:"+Global.getAdminPath()+"/resource/mesanDir/treemesanDir";
		}
		MesanInfo resourceInfo = new MesanInfo();
		resourceInfo.setMesanType(mesanDir.getId());
		List<MesanInfo> ls = mesanInfoService.findList(resourceInfo);
		if(null != ls && !ls.isEmpty()){
			addMessage(redirectAttributes, "删除资料目录失败，当前目录还有资料引用。");
		}else{
			mesanDirService.delete(mesanDir);
			addMessage(redirectAttributes, "删除资料目录成功");
		}
		return "redirect:"+Global.getAdminPath()+"/resource/mesanDir/treemesanDir";
	}
	
	/**
	 * 批量删除资料目录
	 */
	@RequiresPermissions("resource:mesanDir:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			mesanDirService.delete(mesanDirService.get(id));
		}
		addMessage(redirectAttributes, "删除资料目录成功");
		return "redirect:"+Global.getAdminPath()+"/resource/mesanDir/treemesanDir";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("resource:mesanDir:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(MesanDir mesanDir, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资料目录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MesanDir> page = mesanDirService.findPage(new Page<MesanDir>(request, response, -1), mesanDir);
    		new ExportExcel("资料目录", MesanDir.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出资料目录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/mesanDir/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("resource:mesanDir:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MesanDir> list = ei.getDataList(MesanDir.class);
			for (MesanDir mesanDir : list){
				try{
					mesanDirService.save(mesanDir);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资料目录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资料目录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资料目录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/mesanDir/?repage";
    }
	
	/**
	 * 下载导入资料目录数据模板
	 */
	@RequiresPermissions("resource:mesanDir:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资料目录数据导入模板.xlsx";
    		List<MesanDir> list = Lists.newArrayList(); 
    		new ExportExcel("资料目录数据", MesanDir.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/mesanDir/?repage";
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
		MesanDir dir = new MesanDir();
		 dir.setUpateDateId(dataid);
		List<MesanDir> list = mesanDirService.findList(dir);
		for (int i=0; i<list.size(); i++){
			MesanDir e = list.get(i);
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		return mapList;
	}
	/**
	 * 验证排序是否已经存在
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "checkMesanDirSort")
	public String checkMesanCode(String sort,String oldMesanDirSort) {
		if (sort !=null && sort.equals(oldMesanDirSort)) {
			return "true";
		}else if (sort !=null && !"".equals(sort)) {
			MesanDir m = new MesanDir();
			m.setSort(sort);
			List<MesanDir> listMesain = mesanDirService.findList(m);
			if(null==listMesain || listMesain.isEmpty()){
				return "true";
			}
		} 
		return "false";
	}

}