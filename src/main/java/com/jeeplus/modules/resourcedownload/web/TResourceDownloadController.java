/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resourcedownload.web;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.resourcedownload.entity.TResourceDownload;
import com.jeeplus.modules.resourcedownload.service.TResourceDownloadService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
/**
 * 资源下载Controller
 * @author pxw
 * @version 2017-12-31
 */
@Controller
@RequestMapping(value = "${adminPath}/resourcedownload/tResourceDownload")
public class TResourceDownloadController extends BaseController {
	@Autowired
	private SystemService systemService;
	@Autowired
	private TResourceDownloadService tResourceDownloadService;
	
	@ModelAttribute
	public TResourceDownload get(@RequestParam(required=false) String id) {
		TResourceDownload entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tResourceDownloadService.get(id);
		}
		if (entity == null){
			entity = new TResourceDownload();
		}
		return entity;
	}
	
	/**
	 * 资源下载列表页面
	 */
	@RequiresPermissions("resourcedownload:tResourceDownload:list")
	@RequestMapping(value = {"list", ""})
	public String list(TResourceDownload tResourceDownload, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = tResourceDownload.getUser();
		if(user!=null && (user.getId()==null || "".equals(user.getId())))tResourceDownload.setUser(null);
		Page<TResourceDownload> page = tResourceDownloadService.findPage(new Page<TResourceDownload>(request, response), tResourceDownload); 
		model.addAttribute("page", page);
		model.addAttribute("tResourceDownload", tResourceDownload);
		return "modules/resourcedownload/tResourceDownloadList";
	}

	/**
	 * 查看，增加，编辑资源下载表单页面
	 */
	@RequiresPermissions(value={"resourcedownload:tResourceDownload:view","resourcedownload:tResourceDownload:add","resourcedownload:tResourceDownload:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TResourceDownload tResourceDownload, Model model) {
		model.addAttribute("tResourceDownload", tResourceDownload);
		return "modules/resourcedownload/tResourceDownloadForm";
	}
	/**
	 * @param request
	 * @param response
	 * @param model
	 * @param id
	 * @description: 点击申请下载按钮，生成一条申请数据
	 */
	@RequestMapping(value = "add")
	public void add(HttpServletRequest request, HttpServletResponse response, Model model,String id) throws Exception{
		String mesanInfoId = request.getParameter("mesanInfoId");
		TResourceDownload tResourceDownload = new TResourceDownload();//从数据库取出记录的值
		tResourceDownload.setResourceId(mesanInfoId);
		tResourceDownload.setStatus(1);
		tResourceDownload.setUser(UserUtils.getUser());
		tResourceDownload.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		tResourceDownload.setIsNewRecord(true);
		tResourceDownload.setCreateDate(new Date());
		try {
			tResourceDownloadService.save(tResourceDownload);//保存
			Map<String,String> map = new HashMap<String,String>();
			map.put("code", "00000");
			String str = "";
			if(null !=map){
				str = JSONObject.toJSONString(map);
			}
			response.getWriter().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保存资源下载
	 */
	@RequiresPermissions(value={"resourcedownload:tResourceDownload:add","resourcedownload:tResourceDownload:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TResourceDownload tResourceDownload, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, tResourceDownload)){
			return form(tResourceDownload, model);
		}
		if(!tResourceDownload.getIsNewRecord()){//编辑表单保存
			TResourceDownload t = tResourceDownloadService.get(tResourceDownload.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(tResourceDownload, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			tResourceDownloadService.save(t);//保存
		}else{//新增表单保存
			tResourceDownloadService.save(tResourceDownload);//保存
		}
		addMessage(redirectAttributes, "保存资源下载成功");
		return "redirect:"+Global.getAdminPath()+"/resourcedownload/tResourceDownload/?repage";
	}
	
	/**
	 * 删除资源下载
	 */
	@RequiresPermissions("resourcedownload:tResourceDownload:del")
	@RequestMapping(value = "delete")
	public String delete(TResourceDownload tResourceDownload, RedirectAttributes redirectAttributes) {
		tResourceDownloadService.delete(tResourceDownload);
		addMessage(redirectAttributes, "删除资源下载成功");
		return "redirect:"+Global.getAdminPath()+"/resourcedownload/tResourceDownload/?repage";
	}
	
	/**
	 * 批量删除资源下载
	 */
	@RequiresPermissions("resourcedownload:tResourceDownload:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			tResourceDownloadService.delete(tResourceDownloadService.get(id));
		}
		addMessage(redirectAttributes, "删除资源下载成功");
		return "redirect:"+Global.getAdminPath()+"/resourcedownload/tResourceDownload/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("resourcedownload:tResourceDownload:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(TResourceDownload tResourceDownload, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资源下载"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TResourceDownload> page = tResourceDownloadService.findPage(new Page<TResourceDownload>(request, response, -1), tResourceDownload);
    		new ExportExcel("资源下载", TResourceDownload.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出资源下载记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resourcedownload/tResourceDownload/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("resourcedownload:tResourceDownload:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TResourceDownload> list = ei.getDataList(TResourceDownload.class);
			for (TResourceDownload tResourceDownload : list){
				try{
					tResourceDownloadService.save(tResourceDownload);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资源下载记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资源下载记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资源下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resourcedownload/tResourceDownload/?repage";
    }
	
	/**
	 * 下载导入资源下载数据模板
	 */
	@RequiresPermissions("resourcedownload:tResourceDownload:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资源下载数据导入模板.xlsx";
    		List<TResourceDownload> list = Lists.newArrayList(); 
    		new ExportExcel("资源下载数据", TResourceDownload.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resourcedownload/tResourceDownload/?repage";
    }
}