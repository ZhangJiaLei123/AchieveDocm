/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.entity.PostLevel;
import com.jeeplus.modules.sys.service.PostLevelService;

/**
 * 岗位级别Controller
 * @author panjp
 * @version 2017-03-19
 * --------------------------------------
 * 2017-04-21 ygq 将该Controller类的功能更改成岗位级别
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/postLevel")
public class PostLevelController extends BaseController {

	@Autowired
	private PostLevelService postLevelService;
	
	@ModelAttribute
	public PostLevel get(@RequestParam(required=false) String id) {
		PostLevel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = postLevelService.get(id);
		}
		if (entity == null){
			entity = new PostLevel();
		}
		return entity;
	}
	
	/**
	 * 岗位级别列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(PostLevel postLevel, HttpServletRequest request, HttpServletResponse response, Model model) {
		String postInfoId = request.getParameter("postInfoId");
		Map map = new HashMap();
		map.put("postInfoId", postInfoId);
		List<PostLevel> postLevels = postLevelService.selectedPostLevelList(map);
		request.setAttribute("postLevels", postLevels);
		return "modules/sys/postLevelList";
	}

	/**
	 * 查看，增加，编辑岗位级别表单页面
	 */
	@RequestMapping(value = "form")
	public String form(PostLevel postLevel, Model model,HttpServletRequest request) {
		model.addAttribute("postLevel", postLevel);
		String postInfoId = request.getParameter("postInfoId");
		//查询所有的postInfo下的postLevel
		Map map = new HashMap();
		map.put("postInfoId", postInfoId);
		List<PostLevel> postLevels = postLevelService.selectedPostLevelList(map);
		request.setAttribute("postLevels", postLevels);
		return "modules/sys/postLevelForm";
	}

	/**
	 * 保存岗位级别
	 */
	@RequestMapping(value = "save")
	public String save(PostLevel postLevel,String jsonStr, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws Exception{
		if(null!=jsonStr && !"".equals(jsonStr.trim())){
			JSONArray jsonArr = JSONArray.parseArray(jsonStr);
			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject obj = jsonArr.getJSONObject(i);
				String levelName = obj.getString("name");
				String levelNameId = obj.getString("nameId");
				PostLevel psl = new PostLevel();
				psl.setName(levelName);
				psl.setPostinfoId(postLevel.getPostinfoId());
				if(null!=levelNameId && !"".equals(levelNameId.trim())){//修改岗位级别
					psl.setId(levelNameId);
				}else{
					psl.setIsNewRecord(true);
					psl.setId(IdGen.uuid());
				}
				psl.setSort(i+1);
				psl.setDelFlag("0");
				postLevelService.saveObject(psl);
			}
			
		}
		addMessage(redirectAttributes, "保存岗位级别成功");
		return "redirect:"+Global.getAdminPath()+"/sys/postInfo/?repage";
	}
	
	/**
	 * 删除岗位级别
	 */
	@RequestMapping(value = "delete")
	public String delete(PostLevel postLevel, RedirectAttributes redirectAttributes) {
		postLevelService.delete(postLevel);
		addMessage(redirectAttributes, "删除岗位级别成功");
		return "redirect:"+Global.getAdminPath()+"/sys/postLevel/?repage";
	}
	

	/**
	 * 删除岗位级别
	 */
	@RequestMapping(value = "ajaxDelete")
	public void ajaxDelete(PostLevel postLevel, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		String str = "1";
		try {
			int count = postLevelService.findUserListByPostLevelId(postLevel);
			if(count>0){
				str = "2";
			}
			postLevelService.delete(postLevel);
		} catch (Exception e) {
			e.printStackTrace();
			str = "0";
		}
		try {
			response.getWriter().write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 批量删除岗位级别
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			postLevelService.delete(postLevelService.get(id));
		}
		addMessage(redirectAttributes, "删除岗位级别成功");
		return "redirect:"+Global.getAdminPath()+"/sys/postLevel/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:postLevel:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(PostLevel postLevel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "岗位级别"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PostLevel> page = postLevelService.findPage(new Page<PostLevel>(request, response, -1), postLevel);
    		new ExportExcel("岗位级别", PostLevel.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出岗位级别记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/postLevel/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:postLevel:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PostLevel> list = ei.getDataList(PostLevel.class);
			for (PostLevel postLevel : list){
				try{
					postLevelService.save(postLevel);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条岗位级别记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条岗位级别记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入岗位级别失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/postLevel/?repage";
    }
	
	/**
	 * 下载导入岗位级别数据模板
	 */
	@RequiresPermissions("sys:postLevel:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "岗位级别数据导入模板.xlsx";
    		List<PostLevel> list = Lists.newArrayList(); 
    		new ExportExcel("岗位级别数据", PostLevel.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/postLevel/?repage";
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
		Integer result = postLevelService.findNameExists(name);
		if(result ==null || result == 0){
			return "true";
		}else{
			return "false";
		}
	}
	

}