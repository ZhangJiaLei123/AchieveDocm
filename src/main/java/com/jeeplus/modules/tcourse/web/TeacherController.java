package com.jeeplus.modules.tcourse.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.queryParser.ParseException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.lucene.LuceneSearch;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.resource.entity.MesanInfo;
import com.jeeplus.modules.resource.entity.ResourceInfo;
import com.jeeplus.modules.resource.service.MesanInfoService;
import com.jeeplus.modules.resource.service.ResourceInfoService;
import com.jeeplus.modules.sys.entity.ApprovalRecord;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.ApprovalRecordService;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.FileUtils;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.tcourse.service.TeacherService;
import com.yfhl.commons.domain.QueryScope;

@Controller
@RequestMapping(value = "${adminPath}/")
public class TeacherController extends BaseController{
	@Autowired
	private TeacherService iTeacherService;
	@Autowired
	private MesanInfoService mesanInfoService;
	@Autowired
	private ApprovalRecordService approvalRecordService;
	@Autowired
	private ResourceInfoService resourceInfoService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private OfficeService officeService;
	
	/**
	 * 教师端首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("teacherindex")
	public String teacherindex(HttpServletRequest request, HttpServletResponse response, Model model) {
		//查询最新课程
		Page<Map> pageNew = new Page<Map>();
		pageNew.setPageSize(4);
		pageNew.setPageNo(1);
		QueryScope<Map> qs = new QueryScope<Map>();
		qs.setPage(pageNew);
		Page<Map> pageNewLs = iTeacherService.findNewCourseInfo(qs);
		
		//查询轮播图
		Page<Map> pageImg = new Page<Map>();
		pageImg.setPageSize(5);
		pageImg.setPageNo(1);
		QueryScope<Map> qsImg = new QueryScope<Map>();
		qs.setPage(pageImg);
		Page<Map> pageImgLs = iTeacherService.findCourseImg(qsImg);
		
		//查询正在播放课程
		Page<Map> pageCourIng = new Page<Map>();
		pageCourIng.setPageSize(4);
		pageCourIng.setPageNo(1);
		QueryScope<Map> qsCourIng = new QueryScope<Map>();
		qsCourIng.setPage(pageCourIng);
		Page<Map> courIngLs = iTeacherService.findCourseInfoIng(qsCourIng);
	
		model.addAttribute("pageNewLs", pageNewLs.getList());
		model.addAttribute("courIngLs", courIngLs.getList());
		model.addAttribute("pageImgLs", pageImgLs.getList());
		model.addAttribute("mesanInfo", new MesanInfo());
		return "/teacherindex";
	}
	/**
	 * 组织人员--组织机构查看
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("selectOfficeInfo")
	public String selectOfficeInfo(Office office, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Office> page = officeService.findPage(new Page<Office>(request, response), office); 
		model.addAttribute("page", page);
		model.addAttribute("office", office);
		return "/selectOfficeInfo";
	}
	
	/**
	 * 组织人员--人员信息查看
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("selectUserInfo")
	public String selectUserInfo(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "/selectUserInfo";
	}
	/**
	 * 组织人员--查看单个组织信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("selectOfficeById")
	public void selectOfficeById(HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		Map map = iTeacherService.findOfficeById(id);
		try {
			response.getWriter().print( JSONObject.toJSONString(map));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 组织人员--查看单个人员信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("selectUserById")
	public void selectUserById(HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		Map map = iTeacherService.findUserById(id);
		List<Map> mapT = iTeacherService.findPostILTList(id);
		try {
			String str="{\"userInfo\":"+JSONObject.toJSONString(map)+",\"postTList\":"+JSONObject.toJSONString(mapT)+"}";
			response.getWriter().print( str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 组织人员--查询机构类别树
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("findTreeOffice")
	public void findTreeOffice(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map>  map = iTeacherService.findTreeOffice();
		try {
			String str=JSONObject.toJSONString(map);
			response.getWriter().print( str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 学习资源--资源库
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("findStudyResourse")
	public String findStudyResourse(ResourceInfo resourceInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		resourceInfo.setResourceStatus("0");
		resourceInfo.setIsPublic("1");
		Page<ResourceInfo> page = resourceInfoService.findPage(new Page<ResourceInfo>(request, response), resourceInfo); 
		model.addAttribute("page", page);
		return "/studyResourse";
	}
	
	/**
	 * 学习资源--资源库
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("teacherViewResourse")
	public String teacherViewResourse(ResourceInfo resourceInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		resourceInfo = resourceInfoService.get(resourceInfo.getId());
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
		return "/modules/tcourse/teacherViewResourse";
	}
	/**
	 * 学习资源--我的资源
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("findMyResourse")
	public String findMyResourse(ResourceInfo resourceInfo, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<ResourceInfo> page = resourceInfoService.findMyResourcePage(new Page<ResourceInfo>(request, response), resourceInfo); 
		model.addAttribute("page", page);
		return "/myResourse";
	}
	/**
	 * 学习资源--根据资源ID查看资源
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("findResourseById")
	public void findResourseById(HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		Map map = iTeacherService.findResourseById(id);
		String str = "";
		
		if(null !=map){
			String resourceSwfUrl = map.get("resourceSwfUrl")==null?"":map.get("resourceSwfUrl").toString();
			if(null==resourceSwfUrl || "".equals(resourceSwfUrl)){
				try {
					String resourceUrl = map.get("resourceUrl")==null?"":map.get("resourceUrl").toString();
					if(null!=resourceUrl && !"".equals(resourceUrl)){
						resourceSwfUrl = FileUtils.fileToSwf(resourceUrl);
						map.put("resourceSwfUrl", resourceSwfUrl);
						iTeacherService.saveResourceSwfById(id, resourceSwfUrl);
					}
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			str = JSONObject.toJSONString(map);
		}
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 搜索资料
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("searchMesanInfo")
	public String openSearch(MesanInfo mesanInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
//		mesanInfo.setUserId(UserUtils.getUser().getId());
//		mesanInfo.setApprovalStatus("0");
//		Page<MesanInfo> page = mesanInfoService.findPage(new Page<MesanInfo>(request, response), mesanInfo); 
//		model.addAttribute("page", page);
		
		String keywords = request.getParameter("keywords");
		try {
			List<Map<String,String>> searchResult = LuceneSearch.search(keywords,Global.getConfig("lucene.indexdir"));
			List<MesanInfo> mesanInfos = new ArrayList<MesanInfo>();
			for (Iterator iterator = searchResult.iterator(); iterator.hasNext();) {
				Map<String,String> temp = (Map<String,String>) iterator.next();
				MesanInfo mesan = new MesanInfo();
				String filename = temp.get("filename");
				mesan.setName(filename);
				mesan.setContentDesc(temp.get("contents"));
				mesanInfos.add(mesan);
			}
			if(mesanInfos.size()>0) {
				model.addAttribute("searchResult", mesanInfos);
			}
			model.addAttribute("keywords",keywords);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "/mesan-search";
	}	
	
	/**
	 * 资料--全部资料
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("findMesanInfo")
	public String findMesanInfo(MesanInfo mesanInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		mesanInfo.setUserId(UserUtils.getUser().getId());
		mesanInfo.setApprovalStatus("0");
		Page<MesanInfo> page = mesanInfoService.findPage(new Page<MesanInfo>(request, response), mesanInfo); 
		model.addAttribute("page", page);
		return "/mesanInfo";
	}
	
	/**
	 * 资料--我的资料
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("findMyMesanInfo")
	public String findMyMesanInfo(MesanInfo mesanInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MesanInfo> page = mesanInfoService.findMyMesanInfoPage(new Page<MesanInfo>(request, response), mesanInfo); 
		model.addAttribute("page", page);
		return "/myMesanInfo";
	}
	
	/**
	 * 教师端，资料发布
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("sendMesanInfo")
	public void sendMesanInfo(HttpServletRequest request, HttpServletResponse response, Model model,String pageNo,String mesanInfoId) {
		//删除原来审核状态（如果有审核）
		ApprovalRecord approvalRecord = new ApprovalRecord();
		approvalRecord.setResourceId(mesanInfoId);
		approvalRecord = approvalRecordService.get(approvalRecord);
		approvalRecordService.delete(approvalRecord);
		
		//新建->发布&&不通过->发布
		com.jeeplus.modules.resource.entity.MesanInfo mesanInfo = mesanInfoService.get(mesanInfoId);
		mesanInfo.setDelFlag("0");
		mesanInfoService.save(mesanInfo);
		
		try {
			response.getWriter().print("1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 学习资源--查看失败原因
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("findAppRecByResId")
	public void findAppRecByResId(HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		Map map = iTeacherService.findAppRecByResId(id);
		String str = "";
		if(null !=map){
			str = JSONObject.toJSONString(map);
		}
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 学习资源--删除学习资源
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("deleteResourceById")
	public void deleteResourceById(HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		boolean bl = iTeacherService.deleteResourceById(id);
		try {
			if(bl){
				response.getWriter().print("1");
			}
			response.getWriter().print("0");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 学习资源--发布学习资源[!!!!!!这个发布的是错误的，根据需求，发布是提交审核，这里发布指的是公开]
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("fbResourceById")
	public void fbResourceById(HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		boolean bl = iTeacherService.fbResourceById(id,"0");
		try {
			if(bl){
				response.getWriter().print("1");
			}
			response.getWriter().print("0");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 学习资源--发布（提交审核）
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("sendResourceInfo")
	public void sendResourceInfo(HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		//删除已经存在的审核失败的内容（审核失败后，再次发布（提交审核））
		ApprovalRecord approvalRecord = new ApprovalRecord();
		approvalRecord.setResourceId(id);
		approvalRecord = approvalRecordService.get(approvalRecord);
		if(approvalRecord!=null){
			approvalRecordService.delete(approvalRecord);
		}
		
		//修改状态
		com.jeeplus.modules.resource.entity.ResourceInfo resourceInfo = resourceInfoService.get(id);
		if(resourceInfo!=null && resourceInfo.getIsSubmit().equals("0")){
			resourceInfo.setIsSubmit("1");
			resourceInfoService.save(resourceInfo);
		}
		
		try {
			response.getWriter().print("1");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	/**
	 * 学习资源--修改资源信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("editResource")
	public void editResource(HttpServletRequest request, HttpServletResponse response,ResourceInfo resourceInfo) {
		/*String str = "0";
		try {
			String id = resourceInfo.getId();
			String userId  = UserUtils.getUser().getId();
			if (null != id && !"".equals(id)) {// 修改信息
				resourceInfo.setUpdateBy(userId);
				resourceInfo.setUpdateDate(new Date());
				resourceInfo.setDelFlag("0");
				iTeacherService.updateResourceInfo(resourceInfo);
				str = "2";
			} else {// 添加信息
				
				resourceInfo.setId(UniqueUtil.getKey());
				resourceInfo.setCreateBy(userId);
				resourceInfo.setCreateDate(new Date());
				resourceInfo.setDelFlag("0");
				resourceInfo.setUpdateBy(userId);
				resourceInfo.setUpdateDate(new Date());
				iTeacherService.saveResourceInfo(resourceInfo);
				str = "1";
				//resourceInfo.setName(resourceInfo.substring(0, fileName.lastIndexOf(".")));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * 学习资源--选择资源类别
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("findResourceDirTree")
	public void findResourceDirTree(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Map> map = iTeacherService.findResourceDirTree();
		String str = "";
		if(null !=map){
			str = JSONObject.toJSONString(map);
		}
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 资料管理--资料信息查看
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("findMesanInfoById")
	public void findMesanInfoById(HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		Map map = iTeacherService.findMesanInfoById(id);
		try {
			response.getWriter().print( JSONObject.toJSONString(map));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 资料管理--删除资料
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("deleteMesanInfoById")
	public void deleteMesanInfoById(HttpServletRequest request, HttpServletResponse response, Model model,String id) {
		boolean bl = iTeacherService.deleteMesanInfoById(id);
		try {
			if(bl){
				response.getWriter().print("1");
			}
			response.getWriter().print("0");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *  资料管理--修改资料管理
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping("editMesanInfo")
	public void editMesanInfo(HttpServletRequest request, HttpServletResponse response, MesanInfo mesanInfo) {
		/*String str = "0";
		try {
			String id = mesanInfo.getId();
			String userId  = UserUtils.getUser().getId();
			if (null != id && !"".equals(id)) {// 修改信息
				mesanInfo.setUpdateBy(userId);
				mesanInfo.setUpdateDate(new Date());
				mesanInfo.setDelFlag("2");//2是没有发布的状态，这个时候只能教师自己看到
				iTeacherService.updateMesanInfo(mesanInfo);
			} else {// 添加信息
				mesanInfo.setId(UniqueUtil.getKey());
				mesanInfo.setCreateBy(userId);
				mesanInfo.setCreateDate(new Date());
				mesanInfo.setDelFlag("2");
				mesanInfo.setUpdateBy(userId);
				mesanInfo.setUpdateDate(new Date());
				iTeacherService.insertMesanInfo(mesanInfo);
			}
			str = "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response.getWriter().print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * 下载文件
	 */
	/**
	 * 下载文件
	 */
	@RequestMapping(value = { "downloadMesanInfo" })
	@ResponseBody
	public HttpServletResponse HttpResponseMessage(String mesanInfoId,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		
		//下载文件
		Map map = iTeacherService.findMesanInfoById(mesanInfoId);
		try {
			String contextPath = request.getRealPath(map.get("mesanUrl").toString().substring(6, map.get("mesanUrl").toString().length()));
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
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
