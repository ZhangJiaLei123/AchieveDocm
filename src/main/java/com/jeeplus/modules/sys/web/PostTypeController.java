/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.entity.PostInfo;
import com.jeeplus.modules.sys.entity.PostType;
import com.jeeplus.modules.sys.service.PostInfoService;
import com.jeeplus.modules.sys.service.PostTypeService;

/**
 * 岗位类别Controller
 * @author ygq
 * @version 2017-04-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/postType")
public class PostTypeController extends BaseController {

	@Autowired
	private PostTypeService postTypeService;

	@Autowired
	private PostInfoService postInfoService;
	
	@ModelAttribute
	public PostType get(@RequestParam(required=false) String id) {
		PostType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = postTypeService.get(id);
		}
		if (entity == null){
			entity = new PostType();
		}
		return entity;
	}
	
	/**
	 * 岗位类别列表页面
	 */
	@RequiresPermissions("sys:postType:list")
	@RequestMapping(value = {"list", ""})
	public String list(PostType postType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PostType> page = postTypeService.findPage(new Page<PostType>(request, response), postType); 
		model.addAttribute("page", page);
		return "modules/sys/postTypeList";
	}

	/**
	 * 查看，增加，编辑岗位类别表单页面
	 */
	@RequiresPermissions(value={"sys:postType:view","sys:postType:add","sys:postType:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PostType postType, Model model) {
		model.addAttribute("postType", postType);
		return "modules/sys/postTypeForm";
	}

	/**
	 * 保存岗位类别
	 */
	@RequiresPermissions(value={"sys:postType:add","sys:postType:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(PostType postType, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, postType)){
			return form(postType, model);
		}
		if(!postType.getIsNewRecord()){//编辑表单保存
			PostType t = postTypeService.get(postType.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(postType, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			postTypeService.save(t);//保存
		}else{//新增表单保存
			postTypeService.save(postType);//保存
		}
		addMessage(redirectAttributes, "保存岗位类别成功");
		return "redirect:"+Global.getAdminPath()+"/sys/postType/?repage";
	}
	
	/**
	 * 删除岗位类别
	 */
	@RequiresPermissions("sys:postType:del")
	@RequestMapping(value = "delete")
	public String delete(PostType postType, RedirectAttributes redirectAttributes) {
		postTypeService.delete(postType);
		addMessage(redirectAttributes, "删除岗位类别成功");
		return "redirect:"+Global.getAdminPath()+"/sys/postType/?repage";
	}
	
	/**
	 * 批量删除岗位类别
	 */
	@RequiresPermissions("sys:postType:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		String myMsg = "";
		for(String id : idArray){
			PostInfo postInfo = new PostInfo();
			postInfo.setPostType(id);
			PostType postType = postTypeService.get(id);
			List ls = postInfoService.findList(postInfo);
			if(null!=ls && !ls.isEmpty()){
				myMsg+="["+postType.getName()+"]";
			}else{
				postTypeService.delete(postType);
			}
		}
		if(!"".equals(myMsg)){
			myMsg+="岗位类别下有岗位信息不能删除。";
		}else{
			myMsg+="删除岗位类别成功。";
		}
		addMessage(redirectAttributes, myMsg);
		return "redirect:"+Global.getAdminPath()+"/sys/postType/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:postType:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(PostType postType, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "岗位类别"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PostType> page = postTypeService.findPage(new Page<PostType>(request, response, -1), postType);
    		new ExportExcel("岗位类别", PostType.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出岗位类别记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/postType/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:postType:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PostType> list = ei.getDataList(PostType.class);
			for (PostType postType : list){
				try{
					postTypeService.save(postType);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条岗位类别记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条岗位类别记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入岗位类别失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/postType/?repage";
    }
	
	/**
	 * 下载导入岗位类别数据模板
	 */
	@RequiresPermissions("sys:postType:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "岗位类别数据导入模板.xlsx";
    		List<PostType> list = Lists.newArrayList(); 
    		new ExportExcel("岗位类别数据", PostType.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/postType/?repage";
    }
	
	/***
	 * 查询名字是否存在
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月17日 下午6:06:18
	  * @param ids
	  * @param redirectAttributes
	  * @return
	 */
	@RequestMapping(value = "findNameExists")
	public @ResponseBody String findNameExists(RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String name = request.getParameter("name");
		logger.info("findNameExists:::::::::::" + name);
		Integer result = postTypeService.findNameExists(name);
		if( null== result || result == 0){
			return "1";
		}else{
			return "0";
		}
	}
	/**
	 * 查询JSON格式字典信息
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "findAllList")
	public void findAllList(HttpServletResponse response, Model model,String dicType) {
		response.setCharacterEncoding("UTF-8");  
		List<PostType> lsMap = postTypeService.findAllList();
		String printStr = new String();
		if(null!=lsMap && !lsMap.isEmpty()){
			printStr = JSONArray.toJSONString(lsMap);
		}
		try {
			response.getWriter().print(printStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}