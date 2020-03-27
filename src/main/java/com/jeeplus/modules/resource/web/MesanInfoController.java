/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
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
import com.jeeplus.common.utils.Encodes;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.utils.lucene.LuceneCreateIndexByFile;
import com.jeeplus.modules.resource.entity.DownlondRecord;
import com.jeeplus.modules.resource.entity.MesanInfo;
import com.jeeplus.modules.resource.service.MesanInfoService;
import com.jeeplus.modules.sys.entity.ApprovalRecord;
import com.jeeplus.modules.sys.service.ApprovalRecordService;
import com.jeeplus.modules.sys.utils.FileUtils;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.train.entity.DistribuTemp;
import com.jeeplus.modules.train.service.DistribuTempService;

/**
 * 资料信息Controller
 * @author panjp
 * @version 2017-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/resource/mesanInfo")
public class MesanInfoController extends BaseController {

	@Autowired
	private MesanInfoService mesanInfoService;
	@Autowired
	private ApprovalRecordService approvalRecordService;
	@Autowired
	private DistribuTempService distribuTempService;
	@ModelAttribute
	public MesanInfo get(@RequestParam(required=false) String id) {
		MesanInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mesanInfoService.get(id);
		}
		if (entity == null){
			entity = new MesanInfo();
		}
		return entity;
	}
	
	/**
	 * 资料信息列表页面
	 */
	@RequiresPermissions("resource:mesanInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list(MesanInfo mesanInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		mesanInfo.setUserId(UserUtils.getUser().getId());
		Page<MesanInfo> page = mesanInfoService.findPage(new Page<MesanInfo>(request, response), mesanInfo); 
		model.addAttribute("page", page);
		return "modules/resource/mesanInfoList";
	}

	/**
	 * 资料信息列表页面
	 */
	@RequiresPermissions("resource:mesanInfo:shList")
	@RequestMapping(value = {"shList"})
	public String shList(MesanInfo mesanInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		mesanInfo.setUserId(UserUtils.getUser().getId());
		Page<MesanInfo> page = mesanInfoService.findPage(new Page<MesanInfo>(request, response), mesanInfo); 
		model.addAttribute("page", page);
		return "modules/resource/mesanInfoshList";
	}
 
	/**
	 * 查看，增加，编辑资料信息表单页面
	 */
	@RequiresPermissions(value={"resource:mesanInfo:add","resource:mesanInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MesanInfo mesanInfo, Model model) {
		model.addAttribute("mesanInfo", mesanInfo);
		return "modules/resource/mesanInfoForm";
	}
	/**
	 * 教师端 查看，增加，编辑资料信息表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherMesanInfoForm")
	public String teacherMesanInfoForm(MesanInfo mesanInfo, Model model) {
		model.addAttribute("mesanInfo", mesanInfo);
		return "modules/resource/teacherMesanInfoForm";
	}
	
	/**
	 * 查看，增加，编辑资料信息表单页面
	 */
	@RequiresPermissions(value={"resource:mesanInfo:view"},logical=Logical.OR)
	@RequestMapping(value = "viewForm")
	public String viewForm(MesanInfo mesanInfo, Model model) {
		model.addAttribute("mesanInfo", mesanInfo);
		String url =  mesanInfo.getMesanUrl();
		if(null!=url && !"".equals(url.trim())){
			String fileName = url.substring(url.lastIndexOf(".")+1,url.length()).toUpperCase();
			//if("PDF".equals(fileName) || "PPT".equals(fileName) || "DOC".equals(fileName) ||"DOCX".equals(fileName) ||"PPTX".equals(fileName)){//如果是可以在线预览文件则生成预览文件
			if( "PDF".equals(fileName) || "PPT".equals(fileName) || "DOC".equals(fileName) ||"DOCX".equals(fileName) ||"PPTX".equals(fileName)||
					"TXT".equals(fileName)||"XLS".equals(fileName)||"XLSX".equals(fileName)){//如果是可以在线预览文件则生成预览文件
				String swfStr = mesanInfo.getSwfUrl();
				if(null==swfStr || "".equals(swfStr.trim())){//如果未生成预览文件则生成
					try {
						swfStr = FileUtils.fileToSwf(mesanInfo.getMesanUrl());
						//生成成功后更新文件
						mesanInfo.setSwfUrl(swfStr);
						mesanInfoService.save(mesanInfo);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return "modules/resource/viewMesanInfoForm";
	}
	/**
	 * 根据文件ID预览
	 */
	@RequiresPermissions(value={"user"},logical=Logical.OR)
	@RequestMapping(value = "teacherViewForm")
	public String teacherViewForm(MesanInfo mesanInfo, Model model) {
		String url =  mesanInfo.getMesanUrl();
		if(null!=url && !"".equals(url.trim())){
			String fileName = url.substring(url.lastIndexOf(".")+1,url.length()).toUpperCase();
			if("PDF".equals(fileName) || "PPT".equals(fileName) || "DOC".equals(fileName) ||"DOCX".equals(fileName) ||"PPTX".equals(fileName)){//如果是可以在线预览文件则生成预览文件
				String swfStr = mesanInfo.getSwfUrl();
				if(null==swfStr || "".equals(swfStr.trim())){//如果未生成预览文件则生成
					try {
						swfStr = FileUtils.fileToSwf(mesanInfo.getMesanUrl());
						//生成成功后更新文件
						mesanInfo.setSwfUrl(swfStr);
						mesanInfoService.save(mesanInfo);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		model.addAttribute("mesanInfo", mesanInfo);
		return "modules/resource/viewMesanInfoForm";
	}

	/**
	 * 根据文件name预览
	 * @throws UnsupportedEncodingException 
	 */
	@RequiresPermissions(value={"user"},logical=Logical.OR)
	@RequestMapping(value = "teacherViewFormByName")
	public String teacherViewFormByName(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		MesanInfo queryParam = new MesanInfo();
		String mesanFileName = request.getParameter("mesanName");
		System.out.println("文件名称："+mesanFileName);
		queryParam.setMesanUrl(java.net.URLEncoder.encode(mesanFileName, "UTF-8"));
		MesanInfo mesanInfo = mesanInfoService.getByFileName(queryParam);
		String url =  mesanInfo.getMesanUrl();
		if(null!=url && !"".equals(url.trim())){
			String fileName = url.substring(url.lastIndexOf(".")+1,url.length()).toUpperCase();
			if("PDF".equals(fileName) || "PPT".equals(fileName) || "DOC".equals(fileName) ||"DOCX".equals(fileName) ||"PPTX".equals(fileName)){//如果是可以在线预览文件则生成预览文件
				String swfStr = mesanInfo.getSwfUrl();
				if(null==swfStr || "".equals(swfStr.trim())){//如果未生成预览文件则生成
					try {
						swfStr = FileUtils.fileToSwf(mesanInfo.getMesanUrl());
						//生成成功后更新文件
						mesanInfo.setSwfUrl(swfStr);
						mesanInfoService.save(mesanInfo);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		model.addAttribute("mesanInfo", mesanInfo);
		return "modules/resource/viewMesanInfoForm";
	}	
	
	/**
	 * 保存资料信息
	 */
	@RequiresPermissions(value={"resource:mesanInfo:add","resource:mesanInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MesanInfo mesanInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, mesanInfo)){
			return form(mesanInfo, model);
		}
		if(!mesanInfo.getIsNewRecord()){//编辑表单保存
			MesanInfo t = mesanInfoService.get(mesanInfo.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(mesanInfo, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			t.setSwfUrl("");
			mesanInfoService.save(t);//保存
		}else{//新增表单保存
			String mesaninfoid = IdGen.uuid();
			mesanInfo.setId(mesaninfoid);
			mesanInfo.setIsNewRecord(true);
			mesanInfo.setIsCreateAdmin("1");
			mesanInfoService.save(mesanInfo);//保存
			
			String userRoleEnName = UserUtils.getUser().getRole().getEnname();
			if(UserUtils.USER_SYSTEM_ROLE_ENNAME.equals(userRoleEnName)){//自动提交审核
				ApprovalRecord ap = new ApprovalRecord();
				ap.setIsNewRecord(true);
				ap.setId(IdGen.uuid());
				ap.setResourceId(mesaninfoid);
				ap.setStatus("3");
				ap.setOpinion("管理员提交自动审核通过。");
				approvalRecordService.save(ap);
			}
			
		}
		addMessage(redirectAttributes, "保存资料信息成功");
		return "redirect:"+Global.getAdminPath()+"/resource/mesanInfo/?repage";
	}
	/**
	 * 保存资料信息
	 */
	@RequiresPermissions(value={"resource:mesanInfo:add","resource:mesanInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "setViewTop")
	public String setViewTop(MesanInfo mesanInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
			
		if("1".equals(mesanInfo.getIsViewTop())){
			mesanInfo.setUpdateDate(new Date());
			addMessage(redirectAttributes, "置顶成功");
		}else{
			mesanInfo.setIsViewTop("0");
			addMessage(redirectAttributes, "取消置顶成功");
		}
		mesanInfoService.setViewTop(mesanInfo);
		return "redirect:"+Global.getAdminPath()+"/resource/mesanInfo/?repage";
	}
	/**
	 * 发布资料
	 */
	@RequiresPermissions(value={"resource:mesanInfo:add","resource:mesanInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "fbMesanInfo")
	public String fbMesanInfo(String resourceId, Model model, RedirectAttributes redirectAttributes) throws Exception{
		ApprovalRecord appr = new ApprovalRecord();
		appr.setResourceId(resourceId);
		appr = approvalRecordService.findByResourceId(appr);
		if(appr.getId() !=null ){
			appr.setStatus("0");
			approvalRecordService.save(appr);
			
			//发布的同时创建索引
			MesanInfo mesanInfo = mesanInfoService.get(resourceId);
			String path = Global.getConfig("userfiles.basedir");
			String filePath = path+mesanInfo.getMesanUrl().substring(6, mesanInfo.getMesanUrl().length());
			filePath = URLDecoder.decode(filePath,"UTF-8");//解码
			LuceneCreateIndexByFile.createIndex(filePath, Global.getConfig("lucene.indexdir"));
			
			addMessage(redirectAttributes, "发布资料成功!");
		}else{
			addMessage(redirectAttributes, "发布资料失败!");
		}
		return "redirect:"+Global.getAdminPath()+"/resource/mesanInfo/shList?repage";
	}
	
	/**
	 * 保存资料信息
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teachetSaveMesanInfo")
	public String teachetSaveMesanInfo(MesanInfo mesanInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, mesanInfo)){
			return form(mesanInfo, model);
		}
		if(!mesanInfo.getIsNewRecord()){//编辑表单保存
			MesanInfo t = mesanInfoService.get(mesanInfo.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(mesanInfo, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			mesanInfoService.save(t);//保存
		}else{//新增表单保存
			String mesaninfoid = IdGen.uuid();
			mesanInfo.setId(mesaninfoid);
			mesanInfo.setIsCreateAdmin("1");
			mesanInfo.setIsNewRecord(true);
			mesanInfoService.save(mesanInfo);//保存
			//默认审核通过
			ApprovalRecord ap = new ApprovalRecord();
			ap.setIsNewRecord(true);
			ap.setId(IdGen.uuid());
			ap.setResourceId(mesaninfoid);
			ap.setStatus("3");
			ap.setOpinion("审核通过");
			approvalRecordService.save(ap);
		}
		addMessage(redirectAttributes, "保存资料信息成功");
		return "redirect:"+Global.getAdminPath()+"/findMyMesanInfo/?repage";
	}
	
	/**
	 * 删除资料信息
	 */
	@RequiresPermissions("resource:mesanInfo:del")
	@RequestMapping(value = "delete")
	public String delete(MesanInfo mesanInfo, RedirectAttributes redirectAttributes) {
	
		//判断是否被活动选择
		DistribuTemp distribuTemp = new DistribuTemp();
		distribuTemp.setRefId(mesanInfo.getId());
		List<DistribuTemp> listDistribuTemp = distribuTempService.findList(distribuTemp);
		if(listDistribuTemp.size()!=0){
			addMessage(redirectAttributes, "资料源已被活动关联，不能删除");
		}
		else{
			mesanInfoService.delete(mesanInfo);
			addMessage(redirectAttributes, "删除资料信息成功");
		}
		return "redirect:"+Global.getAdminPath()+"/resource/mesanInfo/?repage";
	}
	/**
	 * 删除资料信息
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherDelMesanInfo")
	public String teacherDelMesanInfo(MesanInfo mesanInfo, RedirectAttributes redirectAttributes) {
		
		//判断是否被活动选择
		DistribuTemp distribuTemp = new DistribuTemp();
		distribuTemp.setRefId(mesanInfo.getId());
		List<DistribuTemp> listDistribuTemp = distribuTempService.findList(distribuTemp);
		if(listDistribuTemp.size()!=0){
			addMessage(redirectAttributes, "资料源已被活动关联，不能删除");
		}
		else{
			mesanInfoService.delete(mesanInfo);
			addMessage(redirectAttributes, "删除资料信息成功");
		}
		return "redirect:"+Global.getAdminPath()+"/findMyMesanInfo/?repage";
	}
	/**
	 * 批量删除资料信息
	 */
	@RequiresPermissions("resource:mesanInfo:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		int success = 0;
		int fail = 0;
		for(String id : idArray){
			//判断是否被活动选择
			DistribuTemp distribuTemp = new DistribuTemp();
			distribuTemp.setRefId(id);
			List<DistribuTemp> listDistribuTemp = distribuTempService.findList(distribuTemp);
			if(listDistribuTemp.size()!=0){
				fail++;
			}
			else{
				success++;
				mesanInfoService.delete(mesanInfoService.get(id));
			}
		}
		addMessage(redirectAttributes, "删除资料成功"+success+"条，失败"+fail+"条，失败原因资料被活动关联，不能删除");
		return "redirect:"+Global.getAdminPath()+"/resource/mesanInfo/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("resource:mesanInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(MesanInfo mesanInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资料信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MesanInfo> page = mesanInfoService.findPage(new Page<MesanInfo>(request, response, -1), mesanInfo);
    		new ExportExcel("资料信息", MesanInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出资料信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/mesanInfo/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("resource:mesanInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MesanInfo> list = ei.getDataList(MesanInfo.class);
			for (MesanInfo mesanInfo : list){
				try{
					mesanInfoService.save(mesanInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条资料信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条资料信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入资料信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/mesanInfo/?repage";
    }
	
	/**
	 * 下载导入资料信息数据模板
	 */
	@RequiresPermissions("resource:mesanInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "资料信息数据导入模板.xlsx";
    		List<MesanInfo> list = Lists.newArrayList(); 
    		new ExportExcel("资料信息数据", MesanInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/resource/mesanInfo/?repage";
    }
	
	/**
	 * 资料下载
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "downloadMaterial" })
	@ResponseBody
	public HttpServletResponse HttpResponseMessage(String mesanInfoId,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		
		//下载文件
		MesanInfo mesanInfo = mesanInfoService.get(mesanInfoId);
		try {
			String path = Global.getConfig("userfiles.basedir");
			String contextPath = path+mesanInfo.getMesanUrl().substring(6, mesanInfo.getMesanUrl().length());
			contextPath = URLDecoder.decode(contextPath,"UTF-8");//解码
			// path是指欲下载的文件的路径。
			File file = new File(contextPath);
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。
			String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(contextPath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			 final String userAgent = request.getHeader("USER-AGENT");  
				if(userAgent.indexOf("Firefox")>-1){//google,火狐浏览器  
					filename = new String(filename.getBytes(), "ISO8859-1");  
			    }else{  
			    	filename =Encodes.urlEncode(filename);//其他浏览器  
			    }  
			response.addHeader("Content-Disposition", "attachment;filename=" +filename);
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			try {
				response.setCharacterEncoding("utf-8");
				response.getWriter().print("file does't exist!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 验证编码是否已经存在
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "checkMesanCode")
	public String checkMesanCode(String mesanCode,String oldMesanCode) {
		if (mesanCode !=null && mesanCode.equals(oldMesanCode)) {
			return "true";
		}else if (mesanCode !=null && !"".equals(mesanCode.trim())) {
			MesanInfo m = new MesanInfo();
			m.setMesanCode(mesanCode);
			List<MesanInfo> listMesain = mesanInfoService.findList(m);
			if(null==listMesain || listMesain.isEmpty()){
				return "true";
			}
		} 
		return "false";
	}
}