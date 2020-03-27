/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.web;

import java.io.UnsupportedEncodingException;
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

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.course.entity.CourseInfo;
import com.jeeplus.modules.course.service.CourseInfoService;
import com.jeeplus.modules.resource.entity.MesanInfo;
import com.jeeplus.modules.resource.entity.ResourceInfo;
import com.jeeplus.modules.resource.service.ResourceInfoService;
import com.jeeplus.modules.sys.entity.ApprovalRecord;
import com.jeeplus.modules.sys.service.ApprovalRecordService;
import com.jeeplus.modules.sys.utils.FileUtils;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.train.entity.DistribuTemp;
import com.jeeplus.modules.train.service.DistribuTempService;

/**
 * 资源管理Controller
 * @author panjp
 * @version 2017-03-19
 */
@Controller
@RequestMapping(value = "${adminPath}/resource/resourceInfo")
public class ResourceInfoController extends BaseController {

	@Autowired
	private ResourceInfoService resourceInfoService;
	@Autowired
	private ApprovalRecordService approvalRecordService;
	@Autowired
	private CourseInfoService courseInfoService;
	@Autowired
	private DistribuTempService distribuTempService;
	
	@ModelAttribute
	public ResourceInfo get(@RequestParam(required=false) String id) {
		ResourceInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = resourceInfoService.get(id);
		}
		if (entity == null){
			entity = new ResourceInfo();
		}
		return entity;
	}
	
	/**
	 * 资源管理列表页面
	 */
	@RequiresPermissions("resource:resourceInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list(ResourceInfo resourceInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ResourceInfo> page = resourceInfoService.findPage(new Page<ResourceInfo>(request, response), resourceInfo); 
		model.addAttribute("page", page);
		return "modules/resource/resourceInfoList";
	}
	
	/**
	 * 资源管理列表页面
	 */
	@RequiresPermissions("resource:resourceInfo:list")
	@RequestMapping(value = "shList")
	public String shList(ResourceInfo resourceInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ResourceInfo> page = resourceInfoService.findPage(new Page<ResourceInfo>(request, response), resourceInfo); 
		model.addAttribute("page", page);
		return "modules/resource/resourceInfoshList";
	}
	/**
	 * 审核资料查看信息
	 */
	@RequiresPermissions("resource:resourceInfo:list")
	@RequestMapping(value = "showShResourceInfoForm")
	public String showShResourceInfoForm(ResourceInfo resourceInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		String converTerUrl =  resourceInfo.getResourceSwfUrl();
		if(null == converTerUrl || "".equals(converTerUrl)){
			 try {
				converTerUrl = FileUtils.fileToSwf(resourceInfo.getResourceUrl());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      resourceInfo.setResourceSwfUrl(converTerUrl);
		      resourceInfoService.save(resourceInfo);
		      
		}
	    resourceInfo.setResourceSwfUrl(converTerUrl);
		model.addAttribute("resourceInfo", resourceInfo);
		return "modules/resource/showShResourceInfoForm";
	}
	/**
	 * 教师端资料查看
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherShowResourceInfo")
	public String teacherShowResourceInfo(ResourceInfo resourceInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		String converTerUrl =  resourceInfo.getResourceSwfUrl();
		if(null == converTerUrl || "".equals(converTerUrl)){
			 try {
				converTerUrl = FileUtils.fileToSwf(resourceInfo.getResourceUrl());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      resourceInfo.setResourceSwfUrl(converTerUrl);
		      resourceInfoService.save(resourceInfo);
		      
		}
	    resourceInfo.setResourceSwfUrl(converTerUrl);
		model.addAttribute("resourceInfo", resourceInfo);
		return "modules/resource/showShResourceInfoForm";
	}
	/**
	 * 添加课程预览资源
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "viewResourceInfoForCourse")
	public String viewResourceInfoForCourse(ResourceInfo resourceInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		String converTerUrl =  resourceInfo.getResourceSwfUrl();
		if(null == converTerUrl || "".equals(converTerUrl)){
			 try {
				converTerUrl = FileUtils.fileToSwf(resourceInfo.getResourceUrl());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      resourceInfo.setResourceSwfUrl(converTerUrl);
		      resourceInfoService.save(resourceInfo);
		}
	    resourceInfo.setResourceSwfUrl(converTerUrl);
		model.addAttribute("resourceInfo", resourceInfo);
		return "modules/resource/viewResourceInfoForCourse";
	}
	
	
	/**
	 * 打开选择资源列表
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "seleResInfolist")
	public String seleResInfolist(ResourceInfo resourceInfo, HttpServletRequest request, HttpServletResponse response, Model model) {;
		if(!UserUtils.USER_SYSTEM_ROLE_ENNAME.equals(UserUtils.getUser().getRole().getEnname())){//如果不是管理员则添加数据权限
			resourceInfo.setIsPublic("1");
		}
		Page<ResourceInfo> page = resourceInfoService.findResourceInfoIsShSuccess(new Page<ResourceInfo>(request, response), resourceInfo); 
		model.addAttribute("page", page);
		return "modules/resource/seleResInfolist";
	}
	/**
	 * 查看，增加，编辑资源管理表单页面
	 */
	@RequiresPermissions(value={"resource:resourceInfo:view","resource:resourceInfo:add","resource:resourceInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ResourceInfo resourceInfo, Model model) {
		model.addAttribute("resourceInfo", resourceInfo);
		return "modules/resource/resourceInfoForm";
	}
	@RequiresPermissions("user")
	@RequestMapping("teacherResourceForm")
	public String teacherResourceForm(ResourceInfo resourceInfo, Model model) {
		model.addAttribute("resourceInfo", resourceInfo);
		return "modules/resource/teacherResourceForm";
	}
	
	/**
	 * 保存资源管理
	 */
	@RequiresPermissions(value={"resource:resourceInfo:add","resource:resourceInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ResourceInfo resourceInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resourceInfo)){
			return form(resourceInfo, model);
		}
		if(!resourceInfo.getIsNewRecord()){//编辑表单保存
			ResourceInfo t = resourceInfoService.get(resourceInfo.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(resourceInfo, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			t.setResourceSwfUrl("");
			resourceInfoService.save(t);//保存
		}else{//新增表单保存
			//如果
			if(UserUtils.getUser().getRole().getEnname().equals("student")){
				resourceInfo.setIsSubmit("0");
			}else if(UserUtils.getUser().getRole().getEnname().equals("system")){
				resourceInfo.setIsSubmit("1");//直接审核
				resourceInfo.setIsCreateAdmin("1");//是管理员添加
				resourceInfoService.save(resourceInfo);//保存
				ApprovalRecord approvalRecord = new ApprovalRecord();
				approvalRecord.setStatus("3");//直接审核通过
				approvalRecord.setOpinion("admin创建，不进行审核。");
				approvalRecord.setId(resourceInfo.getId());
				approvalRecord.setResourceId(resourceInfo.getId());
				approvalRecordService.save(approvalRecord,resourceInfo.getId());
			}else if(UserUtils.getUser().getRole().getEnname().equals("px_system")
					||UserUtils.getUser().getRole().getEnname().equals("jg_system")){
				resourceInfo.setIsSubmit("1");//增加审核
			}
			resourceInfoService.save(resourceInfo);//保存
		}
		addMessage(redirectAttributes, "保存资源管理成功");
		return "redirect:"+Global.getAdminPath()+"/resource/resourceInfo/?repage";
	}
	/***
	 * 
	 * @param resourceInfo发布
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(value={"resource:resourceInfo:add","resource:resourceInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "faResourceInfo")
	public String faResourceInfo(String resourceId, Model model, RedirectAttributes redirectAttributes) throws Exception{
		ApprovalRecord appr = new ApprovalRecord();
		appr.setResourceId(resourceId);
		 appr = approvalRecordService.findByResourceId(appr);
		if(appr.getId() !=null ){
			appr.setStatus("0");
			approvalRecordService.save(appr);
			addMessage(redirectAttributes, "发布资源管理成功");
		}else{
			addMessage(redirectAttributes, "发布资源管理失败 ");
		}
		return "redirect:"+Global.getAdminPath()+"/resource/resourceInfo/shList?repage";
	}
	
	/**
	 * 保存资源管理
	 */
	@RequiresPermissions("user")
	@RequestMapping("teacherSaveResource")
	public String teacherSaveResource(ResourceInfo resourceInfo, RedirectAttributes redirectAttributes) throws Exception{
	
		if(!resourceInfo.getIsNewRecord()){//编辑表单保存
			ResourceInfo t = resourceInfoService.get(resourceInfo.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(resourceInfo, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			t.setResourceSwfUrl("");
			resourceInfoService.save(t);//保存
		}else{//新增表单保存
			//如果
			resourceInfoService.save(resourceInfo);//保存
		}
		addMessage(redirectAttributes, "保存资源管理成功");
		return "redirect:"+Global.getAdminPath()+"/findMyResourse?repage";
	}
	/**
	 * 删除资源管理
	 */
	@RequiresPermissions("resource:resourceInfo:del")
	@RequestMapping(value = "delete")
	public String delete(ResourceInfo resourceInfo, RedirectAttributes redirectAttributes) {
		
		//判断是否被课程选择
		CourseInfo courseInfo = new CourseInfo();
		courseInfo.setResResource(resourceInfo.getId());
		List<CourseInfo> courseList =  courseInfoService.findList(courseInfo);
		
		//判断是否被活动选择
		DistribuTemp distribuTemp = new DistribuTemp();
		distribuTemp.setTypeId("2");
		distribuTemp.setRefId(resourceInfo.getId());
		List<DistribuTemp> listDistribuTemp = distribuTempService.findList(distribuTemp);
		if(courseList.size()!=0 || listDistribuTemp.size()!=0){
			addMessage(redirectAttributes, "资源已被课程或活动关联，不能删除");
		}
		else{
			resourceInfoService.delete(resourceInfo);
			addMessage(redirectAttributes, "删除资源管理成功");
		}
		return "redirect:"+Global.getAdminPath()+"/resource/resourceInfo/?repage";
	}
	/**
	 * 删除资源管理
	 */
	@RequiresPermissions("user")
	@RequestMapping("teacherDeleteResource")
	public String teacherDeleteResource(ResourceInfo resourceInfo, RedirectAttributes redirectAttributes) {
		//判断是否被课程选择
		CourseInfo courseInfo = new CourseInfo();
		courseInfo.setResResource(resourceInfo.getId());
		List<CourseInfo> courseList =  courseInfoService.findList(courseInfo);
		
		//判断是否被活动选择
		DistribuTemp distribuTemp = new DistribuTemp();
		distribuTemp.setTypeId("2");
		distribuTemp.setRefId(resourceInfo.getId());
		List<DistribuTemp> listDistribuTemp = distribuTempService.findList(distribuTemp);
		if(courseList.size()!=0 || listDistribuTemp.size()!=0){
			addMessage(redirectAttributes, "资源已被课程或活动关联，不能删除");
		}
		else{
			resourceInfoService.delete(resourceInfo);
			addMessage(redirectAttributes, "删除资源管理成功");
		}
		return "redirect:"+Global.getAdminPath()+"/findMyResourse?repage";
	}
	/**
	 * 批量删除资源管理
	 */
	@RequiresPermissions("resource:resourceInfo:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		int success = 0;
		int fail = 0;
		for(String id : idArray){
			ResourceInfo resourceInfo = new ResourceInfo();
			resourceInfo.setId(id);
			//判断是否被课程选择
			CourseInfo courseInfo = new CourseInfo();
			courseInfo.setResResource(resourceInfo.getId());
			List<CourseInfo> courseList =  courseInfoService.findList(courseInfo);
			//判断是否被活动选择
			DistribuTemp distribuTemp = new DistribuTemp();
			distribuTemp.setTypeId("2");
			distribuTemp.setRefId(resourceInfo.getId());
			List<DistribuTemp> listDistribuTemp = distribuTempService.findList(distribuTemp);
			if(courseList.size()!=0 || listDistribuTemp.size()!=0){
				fail++;
			}else{
				success++;
				resourceInfoService.delete(resourceInfoService.get(id));
			}
		}
		addMessage(redirectAttributes, "删除资源管理成功"+success+"条，失败"+fail+"条，失败原因是资源已被课程或活动关联，不能删除");
		return "redirect:"+Global.getAdminPath()+"/resource/resourceInfo/?repage";
	}
	
	/**
	 * 公开或取消公开
	 */
	@RequiresPermissions("resource:resourceInfo:edit")
	@RequestMapping(value = "isPublic")
	public String isPublic(String id,String isPublic, RedirectAttributes redirectAttributes) {
		ResourceInfo resourceInfo = resourceInfoService.get(id);
		resourceInfo.setIsPublic(isPublic);
		resourceInfoService.save(resourceInfo);
		if(!resourceInfo.getIsNewRecord()){//编辑表单保存
			resourceInfoService.save(resourceInfo);//保存
		}
		if("0".equals(isPublic)){
			addMessage(redirectAttributes, "取消公开资源信息成功。");
		}else if("1".equals(isPublic)){
			addMessage(redirectAttributes, "公开资源信息成功。");
		}
		return "redirect:"+Global.getAdminPath()+"/resource/resourceInfo/?repage";
	}
	
	/**
	 * 发布资源
	 */
	@RequiresPermissions("resource:resourceInfo:edit")
	@RequestMapping(value = "fbResourceInfo")
	public String fbResourceInfo(String id,String isPublic, RedirectAttributes redirectAttributes) {
		ResourceInfo resourceInfo = resourceInfoService.get(id);
		ApprovalRecord approvalRecord = new ApprovalRecord();
		approvalRecord.setResourceId(resourceInfo.getId());
		
		approvalRecord = approvalRecordService.get(approvalRecord);
		
		approvalRecordService.save(approvalRecord,resourceInfo.getId());
		
		addMessage(redirectAttributes, "发布资源信息成功。");
		return "redirect:"+Global.getAdminPath()+"/resource/resourceInfo/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("resource:resourceInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ResourceInfo resourceInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资源管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ResourceInfo> page = resourceInfoService.findPage(new Page<ResourceInfo>(request, response, -1), resourceInfo);
    		new ExportExcel("资源管理", ResourceInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出资源管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/resourceInfo/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("resource:resourceInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ResourceInfo> list = ei.getDataList(ResourceInfo.class);
			for (ResourceInfo resourceInfo : list){
				try{
					resourceInfoService.save(resourceInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资源管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资源管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资源管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/resourceInfo/?repage";
    }
	
	/**
	 * 下载导入资源管理数据模板
	 */
	@RequiresPermissions("resource:resourceInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资源管理数据导入模板.xlsx";
    		List<ResourceInfo> list = Lists.newArrayList(); 
    		new ExportExcel("资源管理数据", ResourceInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/resourceInfo/?repage";
    }
	public static void main(String[] args) throws UnsupportedEncodingException {
		String url =  java.net.URLDecoder.decode("|/docm/userfiles/1/files/resource/resourceInfo/2017/03/%E6%8A%A5%E5%90%8D%E8%A1%A8.doc", "UTF-8");
		url = url.substring(url.indexOf("docm/")+5, url.length());
		String converTerUrl = url.substring(0,url.lastIndexOf("."))+".swf";
		System.out.println(converTerUrl);
	}
	
	/**
	 * 验证编码是否已经存在
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "checkResourceInfoCode")
	public String checkResourceInfoCode(String resourceCode,String oldResourceCode) {
		if (resourceCode !=null && resourceCode.equals(oldResourceCode)) {
			return "true";
		}else if (resourceCode !=null && !"".equals(resourceCode.trim())) {
			ResourceInfo m = new ResourceInfo();
			m.setResourceCode(resourceCode);
			List<ResourceInfo> listMesain = resourceInfoService.findList(m);
			if(null==listMesain || listMesain.isEmpty()){
				return "true";
			}
		} 
		return "false";
	}
}