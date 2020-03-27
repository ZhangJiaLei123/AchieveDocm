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
import com.jeeplus.modules.course.entity.CourseInfoCondition;
import com.jeeplus.modules.course.service.CourseInfoConditionService;
import com.jeeplus.modules.sys.entity.PostInfo;
import com.jeeplus.modules.sys.entity.PostLevel;
import com.jeeplus.modules.sys.entity.PostType;
import com.jeeplus.modules.sys.service.PostInfoService;
import com.jeeplus.modules.sys.service.PostLevelService;
import com.jeeplus.modules.sys.service.PostTypeService;
import com.jeeplus.modules.train.entity.StudyActivityCondition;
import com.jeeplus.modules.train.service.StudyActivityConditionService;

/**
 * 岗位管理Controller
 * @author panjp
 * @version 2017-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/postInfo")
public class PostInfoController extends BaseController {

	@Autowired
	private PostInfoService postInfoService;

	@Autowired
	private PostTypeService postTypeService;

	
	@Autowired
	private PostLevelService postLevelService;
	@Autowired
	private  StudyActivityConditionService studyActivityConditionService;
	
	@Autowired
	private CourseInfoConditionService courseInfoConditionService;
	@ModelAttribute
	public PostInfo get(@RequestParam(required=false) String id) {
		PostInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = postInfoService.get(id);
		}
		if (entity == null){
			entity = new PostInfo();
		}
		return entity;
	}
	
	/**
	 * 岗位管理列表页面
	 */
	@RequiresPermissions("sys:postInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list(PostInfo postInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PostInfo> page = postInfoService.findPage(new Page<PostInfo>(request, response), postInfo); 
		model.addAttribute("page", page);
		return "modules/sys/postInfoList";
	}

	/**
	 * 岗位管理列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"showPostList"})
	public String showPostList(PostInfo postInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PostInfo> page = postInfoService.findPostListForSearch(new Page<PostInfo>(request, response), postInfo); 
		String activId = request.getParameter("activId");//获取学习活动id
		model.addAttribute("page", page);
		return "modules/sys/showPostList";
	}
	/**
	 * 岗位管理列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"showPostListByCondition"})
	public String showPostListByCondition(PostInfo postInfo,String studyActivityId,String type,String firstType,  HttpServletRequest request, HttpServletResponse response, Model model) {
		if(null==postInfo){
			postInfo = new PostInfo();
		}
		if("1".equals(firstType)){//修改或者第一次进入查询上一次保存的条件
			Map<String,String> mapPa = new HashMap<String,String>();
			mapPa.put("studyActivityId",studyActivityId);
			mapPa.put("type", type);
			StudyActivityCondition condition = studyActivityConditionService.findActivityConditionByActivityId(mapPa);
			//查询条件为空的时候，说明是在页面上直接重新查询数据
			if(null != condition){
				postInfo.setPostType(condition.getPostType());
				postInfo.setName(condition.getPostName());
			}
		}
		Page<PostInfo> page = postInfoService.findPostListForSearch(new Page<PostInfo>(request, response), postInfo); 
		//查询岗位分类
		List<PostType> postTypes = postTypeService.findAllList();
		model.addAttribute("page", page);
		model.addAttribute("postInfo", postInfo);
		model.addAttribute("type", type);
		model.addAttribute("postTypes", postTypes);
		return "modules/sys/showPostList";
	}
	/**
	 * 岗位管理列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"showTeacherPostListByCondition"})
	public String showTeacherPostListByCondition(PostInfo postInfo,String studyActivityId,String type,String firstType,  HttpServletRequest request, HttpServletResponse response, Model model) {
		if(null==postInfo){
			postInfo = new PostInfo();
		}
		if("1".equals(firstType)){//修改或者第一次进入查询上一次保存的条件
			Map<String,String> mapPa = new HashMap<String,String>();
			mapPa.put("studyActivityId",studyActivityId);
			mapPa.put("type", type);
			StudyActivityCondition condition = studyActivityConditionService.findActivityConditionByActivityId(mapPa);
			//查询条件为空的时候，说明是在页面上直接重新查询数据
			if(null != condition){
				postInfo.setPostType(condition.getPostType());
				postInfo.setName(condition.getPostName());
			}
		}
		Page<PostInfo> page = postInfoService.findPostListForSearch(new Page<PostInfo>(request, response), postInfo); 
		//查询岗位分类
		List<PostType> postTypes = postTypeService.findAllList();
		model.addAttribute("page", page);
		model.addAttribute("postInfo", postInfo);
		model.addAttribute("type", type);
		model.addAttribute("postTypes", postTypes);
		return "modules/sys/showTeacherPostList";
	}
	/**
	 * 岗位管理列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"showPostListByConditionCourse"})
	public String showPostListByConditionCourse(PostInfo postInfo,String courseId,String type,String firstType, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(null==postInfo){
			postInfo = new PostInfo();
		}
		if("1".equals(firstType)){//如果是第一次进入或者是点击修改根据已保存的条件查询
			Map<String,String> mapPa = new HashMap<String,String>();
			mapPa.put("courseId",courseId);
			mapPa.put("type", type);
			CourseInfoCondition condition = courseInfoConditionService.findActivityConditionByActivityId(mapPa);
			if(null!=condition){
				postInfo.setPostType(condition.getPostType());
				postInfo.setName(condition.getPostName());
			}
		}
		Page<PostInfo> page = postInfoService.findPostListForSearch(new Page<PostInfo>(request, response), postInfo); 
		//查询岗位分类
		List<PostType> postTypes = postTypeService.findAllList();
		model.addAttribute("page", page);
		model.addAttribute("postInfo", postInfo);
		model.addAttribute("type", type);
		model.addAttribute("postTypes", postTypes);
		return "modules/sys/showPostListCourse";
	}
	/**
	 * 岗位管理列表页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"showTeacherPostListByConditionCourse"})
	public String showTeacherPostListByConditionCourse(PostInfo postInfo,String courseId,String type,String firstType, HttpServletRequest request, HttpServletResponse response, Model model) {
	
		if("1".equals(firstType)){//如果是第一次进入或者是点击修改根据已保存的条件查询
			Map<String,String> mapPa = new HashMap<String,String>();
			mapPa.put("courseId",courseId);
			mapPa.put("type", type);
			CourseInfoCondition condition = courseInfoConditionService.findActivityConditionByActivityId(mapPa);
			if(null!=condition){
				postInfo.setPostType(condition.getPostType());
				postInfo.setName(condition.getPostName());
			}
		}
		Page<PostInfo> page = postInfoService.findPostListForSearch(new Page<PostInfo>(request, response), postInfo); 
	
		//查询岗位分类
		List<PostType> postTypes = postTypeService.findAllList();
		model.addAttribute("page", page);
		model.addAttribute("postInfo", postInfo);
		model.addAttribute("type", type);
		model.addAttribute("postTypes", postTypes);
		return "modules/sys/showTeacherPostListCourse";
	}
	/**
	 * 查看，增加，编辑岗位管理表单页面
	 */
	@RequiresPermissions(value={"sys:postInfo:view","sys:postInfo:add","sys:postInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PostInfo postInfo, Model model) {
		model.addAttribute("postInfo", postInfo);
		//查询岗位类别
		List<PostType> postTypes = postInfoService.findAllListTypes();
		model.addAttribute("postTypes", postTypes);
		return "modules/sys/postInfoForm";
	}

	/**
	 * 保存岗位管理
	 */
	@RequiresPermissions(value={"sys:postInfo:add","sys:postInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(PostInfo postInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, postInfo)){
			return form(postInfo, model);
		}
		if(!postInfo.getIsNewRecord()){//编辑表单保存
			PostInfo t = postInfoService.get(postInfo.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(postInfo, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			postInfoService.save(t);//保存
		}else{//新增表单保存
			postInfoService.save(postInfo);//保存
		}
		addMessage(redirectAttributes, "保存岗位管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/postInfo/?repage";
	}
	
	/**
	 * 删除岗位管理
	 */
	@RequiresPermissions("sys:postInfo:del")
	@RequestMapping(value = "delete")
	public String delete(PostInfo postInfo, RedirectAttributes redirectAttributes) {
		postInfoService.delete(postInfo);
		addMessage(redirectAttributes, "删除岗位管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/postInfo/?repage";
	}
	
	/**
	 * 批量删除岗位管理
	 */
	@RequiresPermissions("sys:postInfo:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		String myMsg = "";
		for(String id : idArray){
			Map map = new HashMap();
			map.put("postInfoId", id);
			List<PostLevel> postLevels = postLevelService.selectedPostLevelList(map);
			if(null!=postLevels && !postLevels.isEmpty()){
				myMsg+="["+postInfoService.get(id).getName()+"]";
			}else{
				postInfoService.delete(postInfoService.get(id));
			}
		}
		if(!"".equals(myMsg)){
			myMsg+="岗位下有岗位等级不能删除。";
		}else{
			myMsg+="删除岗位成功。";
		}
		addMessage(redirectAttributes, myMsg);
		return "redirect:"+Global.getAdminPath()+"/sys/postInfo/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:postInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(PostInfo postInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "岗位管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PostInfo> page = postInfoService.findPage(new Page<PostInfo>(request, response, -1), postInfo);
    		new ExportExcel("岗位管理", PostInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出岗位管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/postInfo/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:postInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PostInfo> list = ei.getDataList(PostInfo.class);
			for (PostInfo postInfo : list){
				try{
					postInfoService.save(postInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条岗位管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条岗位管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入岗位管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/postInfo/?repage";
    }
	
	/**
	 * 下载导入岗位管理数据模板
	 */
	@RequiresPermissions("sys:postInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "岗位管理数据导入模板.xlsx";
    		List<PostInfo> list = Lists.newArrayList(); 
    		new ExportExcel("岗位管理数据", PostInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/postInfo/?repage";
    }
	
	
	/**
	 * 根据岗位类别查询岗位
	 */
	@RequiresPermissions("user")
    @RequestMapping(value = "findPostListByType")
    public void findPostListByType(HttpServletResponse response, RedirectAttributes redirectAttributes,String postType) {
		response.setCharacterEncoding("UTF-8");  
		List<Map> lsMap = postInfoService.findPostListByType(postType);
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
	/**
	 * 根据岗位查询岗位级别
	 */
	@RequiresPermissions("user")
    @RequestMapping(value = "findPostLevelByPost")
    public void findPostLevelByPost(HttpServletResponse response, RedirectAttributes redirectAttributes,String postId) {
		response.setCharacterEncoding("UTF-8");  
		List<Map> lsMap = postInfoService.findPostLevelByPost(postId);
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