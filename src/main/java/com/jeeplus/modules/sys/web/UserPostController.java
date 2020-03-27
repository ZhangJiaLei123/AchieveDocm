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
import com.jeeplus.modules.sys.entity.UserPost;
import com.jeeplus.modules.sys.service.UserPostService;

/**
 * 用户岗位关系Controller
 * @author panjp
 * @version 2017-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/userPost")
public class UserPostController extends BaseController {

	@Autowired
	private UserPostService userPostService;
	
	@ModelAttribute
	public UserPost get(@RequestParam(required=false) String id) {
		UserPost entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userPostService.get(id);
		}
		if (entity == null){
			entity = new UserPost();
		}
		return entity;
	}
	
	/**
	 * 用户岗位关系列表页面
	 */
	@RequiresPermissions("sys:userPost:list")
	@RequestMapping(value = {"list", ""})
	public String list(UserPost userPost, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserPost> page = userPostService.findPage(new Page<UserPost>(request, response), userPost); 
		model.addAttribute("page", page);
		return "modules/sys/userPostList";
	}

	/**
	 * 查看，增加，编辑用户岗位关系表单页面
	 */
	@RequiresPermissions(value={"sys:userPost:view","sys:userPost:add","sys:userPost:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(UserPost userPost, Model model) {
		model.addAttribute("userPost", userPost);
		return "modules/sys/userPostForm";
	}

	/**
	 * 保存用户岗位关系
	 */
	@RequiresPermissions(value={"sys:userPost:add","sys:userPost:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(UserPost userPost, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, userPost)){
			return form(userPost, model);
		}
		if(!userPost.getIsNewRecord()){//编辑表单保存
			UserPost t = userPostService.get(userPost.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(userPost, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			userPostService.save(t);//保存
		}else{//新增表单保存
			userPostService.save(userPost);//保存
		}
		addMessage(redirectAttributes, "保存用户岗位关系成功");
		return "redirect:"+Global.getAdminPath()+"/sys/userPost/?repage";
	}
	
	/**
	 * 删除用户岗位关系
	 */
	@RequiresPermissions("sys:userPost:del")
	@RequestMapping(value = "delete")
	public String delete(UserPost userPost, RedirectAttributes redirectAttributes) {
		userPostService.delete(userPost);
		addMessage(redirectAttributes, "删除用户岗位关系成功");
		return "redirect:"+Global.getAdminPath()+"/sys/userPost/?repage";
	}
	
	/**
	 * 批量删除用户岗位关系
	 */
	@RequiresPermissions("sys:userPost:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			userPostService.delete(userPostService.get(id));
		}
		addMessage(redirectAttributes, "删除用户岗位关系成功");
		return "redirect:"+Global.getAdminPath()+"/sys/userPost/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:userPost:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(UserPost userPost, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户岗位关系"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<UserPost> page = userPostService.findPage(new Page<UserPost>(request, response, -1), userPost);
    		new ExportExcel("用户岗位关系", UserPost.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户岗位关系记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/userPost/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:userPost:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<UserPost> list = ei.getDataList(UserPost.class);
			for (UserPost userPost : list){
				try{
					userPostService.save(userPost);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户岗位关系记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户岗位关系记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户岗位关系失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/userPost/?repage";
    }
	
	/**
	 * 下载导入用户岗位关系数据模板
	 */
	@RequiresPermissions("sys:userPost:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户岗位关系数据导入模板.xlsx";
    		List<UserPost> list = Lists.newArrayList(); 
    		new ExportExcel("用户岗位关系数据", UserPost.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/userPost/?repage";
    }
	
	
	

}