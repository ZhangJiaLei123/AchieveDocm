/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.cst.HeadCst;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.LessionImportUser;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.SelectUser;
import com.jeeplus.modules.sys.entity.SystemConfig;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.UserPost;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.SystemConfigService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.service.UserPostService;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.train.entity.UserActivity;
import com.jeeplus.modules.train.service.SearchTempService;
import com.jeeplus.modules.train.service.UserActivityService;

/**
 * 用户Controller
 * @author jeeplus
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private SystemConfigService systemConfigService;
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserPostService userPostService;
	
	@Autowired
	private SearchTempService searchTempService;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private UserActivityService userActivityService;

	@RequestMapping(value = {"modPassword"})
	public void modPassword(String oldPassword, String newPassword, Model model, HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		String code = "00000";
		String message = "修改密码成功";
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
			}else{
				code = "10000";
				message = "修改密码失败，旧密码错误";
			}
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("code", code);
		map.put("message", message);
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
	
	@RequestMapping(value = {"usercenter"})
	public String usercenter(HttpServletRequest request, HttpServletResponse response, Model model) {
		User userInfo = UserUtils.getUser();
		 model.addAttribute("user", userInfo);
		return "usercenter";
	}
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	@RequiresPermissions("sys:user:index")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/userIndex";
	}

	@RequiresPermissions("sys:user:index")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/userList";
	}

	@RequiresPermissions("sys:user:index")
	@RequestMapping(value = {"showListUser"})
	public String showListUser(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
        model.addAttribute("loginUser",UserUtils.getUser());
		return "modules/sys/showListUser";
	}
	@RequiresPermissions("sys:user:index")
	@RequestMapping(value = {"nxsShListUser"})
	public String nxsShListUser(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		user.setRoleEnName("nx_teacher");
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/nxsShListUser";
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = {"selShowUser"})
	public String selShowUser(SelectUser user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SelectUser> page = new Page<SelectUser>(request, response); 
		//查询必学组织相关的信息
		String officIdsBx = request.getParameter("officeIds");//必学学员机构组织ID
		String isCheckPostBx = request.getParameter("isCheckPostBx");//岗位是否全选了
		String postIdsBx = request.getParameter("postIdsBx");//必学人员岗位ID
		String userIdsBx = request.getParameter("userIdsBx");//必学学员ID
		//查询岗位信息
		//查询学员信息
		String type = request.getParameter("type");
		if(null != type && "bx".equals(type)){
			//如果组织全选了，则默认不关联组织进行数据查询了，反之则关联相关的查询条件
			if(null==user.getIsCheckAll() || "".equals(user.getIsCheckAll())){
				//设置部门ids
				if(null != officIdsBx && officIdsBx.length()>0){
					String [] officeIdsbxs = officIdsBx.substring(0,officIdsBx.length()-1).split(",");
					user.setOfficeIdBx(officeIdsbxs);//设置可选学员的ids
					//经过分析，组织，岗位，人员都有可能超过1000条数据，统一使用exits进行查询条件的过滤
					List<Map> lists = new ArrayList<Map>();
					for(int i=0;i<officeIdsbxs.length;i++){
						if( officeIdsbxs[i] != null && !"".equals(officeIdsbxs[i])){
							Map map = new HashMap();
							map.put("searchCond", officeIdsbxs[i]);
							map.put("type", "1");//组织
							lists.add(map);
						}
					}
					searchTempService.save(lists);//保存查询条件
				}
			}
			
			//设置岗位ids
			user.setIsBxPostIdsCheckAll(isCheckPostBx);
			if(null == isCheckPostBx || "".equals(isCheckPostBx)){//岗位不用考虑把前面的信息排除
				if(null != postIdsBx && postIdsBx.length()>0){
					String [] postIdsBxs = postIdsBx.substring(0,postIdsBx.length()-1).split(",");
					user.setOfficeIdBx(postIdsBxs);//设置可选学员的ids
					
					List<Map> lists = new ArrayList<Map>();
					for(int i=0;i<postIdsBxs.length;i++){
						if( postIdsBxs[i] != null && !"".equals(postIdsBxs[i])){
							Map map = new HashMap();
							map.put("searchCond", postIdsBxs[i]);
							map.put("type", "2");//职位
							lists.add(map);
						}
					}
					searchTempService.save(lists);//保存查询条件
				}
			}
			//根据人员的名字查询
			String name = request.getParameter("name");
			user.setName(name);//根据学员的名称查询
			page = systemService.findListForOverOneThousandBx(page, user);
			//设置哪些用户应该被选中
			String userIdsBxChecked = request.getParameter("userIdsBxChecked");
			if(null != userIdsBxChecked){
				String [] userIDs = userIdsBxChecked.split(",");
				List<SelectUser> list = page.getList();
				if(null != userIDs && list!= null && list.size()>0){
					for(int i=0;i<list.size();i++){
						SelectUser su = list.get(i);
						for(int j=0;j<userIDs.length;j++){
							if(su.getId().equals(userIDs[j])){
								su.setIsChecked("1");
								break;
							}
						}
					}
				}
			}
			//删除临时表的数据(暂时先不删除，先进行校验）
			searchTempService.delete();
		}else if(null != type && "xx".equals(type)){
			//如果组织全选了，则默认不关联组织进行数据查询了，反之则关联相关的查询条件
			if(null!=user.getIsCheckAll() && !"".equals(user.getIsCheckAll())){//如果必学前面的组织机构全选了，后面的岗位和人员都不能选了
				page = new Page();
			}else{
					String  isCheckAllxx  = request.getParameter("isCheckAllxx");//全选
					user.setIsCheckAllxx(isCheckAllxx);
					if(null==isCheckAllxx || "".equals(isCheckAllxx)){//如果没有全选组织机构
						String officeIdsxx = request.getParameter("officeIdsxx");//机构id
						//设置部门ids
						if(null != officeIdsxx && officeIdsxx.length()>0){
							String [] officeIdsxxs = officeIdsxx.substring(0,officeIdsxx.length()-1).split(",");
							user.setOfficeIdsxx(officeIdsxxs);//设置可选学员的ids
							//经过分析，组织，岗位，人员都有可能超过1000条数据，统一使用exits进行查询条件的过滤
							List<Map> lists = new ArrayList<Map>();
							for(int i=0;i<officeIdsxxs.length;i++){
								if( officeIdsxxs[i] != null && !"".equals(officeIdsxxs[i])){
									Map map = new HashMap();
									map.put("searchCond", officeIdsxxs[i]);
									map.put("type", "1");//组织
									lists.add(map);
								}
							}
							searchTempService.save(lists);//保存查询条件
						}
					}
				//设置岗位ids
				String  isCheckPostXx = request.getParameter("isCheckPostXx");//选学
				String postIdsXx = request.getParameter("postIdsXx");
				
				user.setIsXxPostIdsCheckAll(isCheckPostXx);
				if(null == isCheckPostXx || "".equals(isCheckPostXx)){//判断岗位是否设置了全选按钮,如果设置了，则不过滤查询条件
					if(null != postIdsXx && postIdsXx.length()>0){
						String [] postIdsXxs = postIdsXx.substring(0,postIdsXx.length()-1).split(",");
						user.setPostIdsXx(postIdsXxs);;//设置可选学员的ids
						List<Map> lists = new ArrayList<Map>();
						for(int i=0;i<postIdsXxs.length;i++){
							if(postIdsXxs[i] != null && !"".equals(postIdsXxs[i])){
								Map map = new HashMap();
								map.put("searchCond", postIdsXxs[i]);
								map.put("type", "2");//职位
								lists.add(map);
							}
						}
						searchTempService.save(lists);//保存查询条件
					}
				}
				//根据人员的名字查询
				String name = request.getParameter("name");
				user.setName(name);//根据学员的名称查询
				//排除掉必选人员
				if(null != userIdsBx && !"".equals(userIdsBx) && userIdsBx.length()>0){
					
					userIdsBx = userIdsBx.substring(0, userIdsBx.length()-1);
					String[] userIdsBxs = userIdsBx.split(",");
					user.setUserIdsBx(userIdsBxs);

					List<Map> lists = new ArrayList<Map>();
					for(int i=0;i<userIdsBxs.length;i++){
						if( userIdsBxs[i] != null && !"".equals(userIdsBxs[i])){
							Map map = new HashMap();
							map.put("searchCond", userIdsBxs[i]);
							map.put("type", "3");//必选人员
							lists.add(map);
						}
					}
					searchTempService.save(lists);//保存查询条件
				}
				page = systemService.findListForOverOneThousandXx(page, user);
				//删除临时表的数据(暂时先不删除，先进行校验）
				searchTempService.delete();
			}
		}
        model.addAttribute("page", page);
		return "modules/sys/selShowUser";
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = {"selShowUserForUpdate"})
	public String selShowUserForUpdate(SelectUser user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SelectUser> page = new Page<SelectUser>(request, response); 
		//查询必学组织相关的信息
		String studyActivityId = request.getParameter("studyActivityId");
		SelectUser  suP = new SelectUser();
		suP.setActivityId(studyActivityId);
		//查询岗位信息
		String type = request.getParameter("type");
		if(null != type && "bx".equals(type)){//查询必学的学员信息
			page = systemService.findListForOfficeAndPostBxForUpdate(page,suP);
			//设置哪些用户应该被选中
			String userIdsBxChecked = request.getParameter("userIdsBxChecked");
			if(null != userIdsBxChecked){
				String [] userIDs = userIdsBxChecked.split(",");
				List<SelectUser> list = page.getList();
				if(null != userIDs && list!= null && list.size()>0){
					for(int i=0;i<list.size();i++){
						SelectUser su = list.get(i);
						for(int j=0;j<userIDs.length;j++){
							if(su.getId().equals(userIDs[j])){
								su.setIsChecked("1");
								break;
							}
						}
					}
				}
			}
		}else if(null != type && "xx".equals(type)){
		}
        model.addAttribute("page", page);
		return "modules/sys/selShowUser";
	}
	
	/**
	 * 查询选择用户列表
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"findSelUser"})
	public String findSelUser(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*if(!"admin".equals(UserUtils.getUser().getLoginName())){
			user.setCreateBy(UserUtils.getUser());
		}*/
		Page<User> page = systemService.findJsUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/sys/findSelUser";         
	}
	
	@RequiresPermissions(value={"sys:user:view","sys:user:add","sys:user:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		/*if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}*/
		//chaxu
		UserPost userPost = new UserPost();
		userPost.setUserId(user.getId());
		List<Map> lsUserPost = userPostService.findListMap(userPost);
		model.addAttribute("user", user);
		if(null!=lsUserPost && !lsUserPost.isEmpty()){
			String retuStr = "[";
			for (int i = 0; i < lsUserPost.size(); i++) {
				Map map = lsUserPost.get(i);
				retuStr +="{'postId':'"+map.get("postId")+"','postType':'"+map.get("postType")+"','postLevel':'"+map.get("postLevel")+"'},";
			}
			retuStr = retuStr.substring(0, retuStr.length()-1)+"]";
			model.addAttribute("lsUserPostStr", retuStr);
		}else{
			model.addAttribute("lsUserPostStr", "0");
		}
		//model.addAttribute("allRoles", systemService.findAllRole());
		
		return "modules/sys/userForm";
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = "teacherViewUserForm")
	public String teacherViewUserForm(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		//chaxu
		UserPost userPost = new UserPost();
		userPost.setUserId(user.getId());
		List<Map> lsUserPost = userPostService.findListMap(userPost);
		model.addAttribute("user", user);
		if(null!=lsUserPost && !lsUserPost.isEmpty()){
			String retuStr = "[";
			for (int i = 0; i < lsUserPost.size(); i++) {
				Map map = lsUserPost.get(i);
				retuStr +="{'postname':'"+map.get("postName")+"','posttypename':'"+map.get("postTypeName")+"','postlevelname':'"+map.get("postLevelName")+"'},";
			}
			retuStr = retuStr.substring(0, retuStr.length()-1)+"]";
			model.addAttribute("lsUserPostStr", retuStr);
		}else{
			model.addAttribute("lsUserPostStr", "0");
		}
		//model.addAttribute("allRoles", systemService.findAllRole());
		
		return "modules/sys/teacherViewUserForm";
	}

	@RequiresPermissions(value={"sys:user:add","sys:user:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		String postInfoStr = request.getParameter("postInfoStr");
		user.setPostInfoStr(postInfoStr);
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));//对应的公司id应该是空值
		user.setOffice(new Office(request.getParameter("officeId")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		// 保存用户信息
		String userId = user.getId();
		systemService.saveUser(user);
		UserUtils.clearCache(user);
		if(null==userId || "".equals(userId)){//如果是添加則默認添加角色
			//添加默认角色
			systemService.insertRole(user.getId());
		}
		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/sys/user/showListUser?repage";
	}
	
	@RequiresPermissions("sys:user:del")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (User.isAdmin(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:" + adminPath + "/sys/user/showListUser?repage";
	}
	
	/**
	 * 批量删除用户
	 */
	@RequiresPermissions("sys:user:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			User user = systemService.getUser(id);
			if(Global.isDemoMode()){
				addMessage(redirectAttributes, "演示模式，不允许操作！");
				return "redirect:" + adminPath + "/sys/user/list?repage";
			}
			if (UserUtils.getUser().getId().equals(user.getId())){
				addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
			}else if (User.isAdmin(user.getId())){
				addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
			}else{
				systemService.deleteUser(user);
				addMessage(redirectAttributes, "删除用户成功");
			}
		}
		return "redirect:" + adminPath + "/sys/user/showListUser?repage";
	}
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
    		new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response,request, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user")
    @RequestMapping(value = "teacherExport", method=RequestMethod.POST)
    public String teacherExport(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
    		new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response,request, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			if (!HeadCst.headValid(ei,User.class)) {
				addMessage(redirectAttributes, "您的导入模板不正确，请使用导出数据模板。");
			} else {
				List<User> list = ei.getDataList(User.class);
				// 开始验证数据
				String messageStr = "";
				Map<String, String> mapOfficeType = officeService.initOfficeType();
				for (int i = 0; i < list.size(); i++){
					User user = list.get(i);
					String userName = user.getName();
					String userLoginName = user.getLoginName();
					String officeCode = user.getOfficeCode();
					String officName = user.getOfficeName();
		
					if (null == userName || "".equals(userName.trim())) {
						messageStr += "第" + (i + 3) + "用户名不能为空。";
					} 

					if (null == userLoginName
							|| "".equals(userLoginName.trim())) {
						messageStr += "第" + (i + 3) + "行登录名不能为空。";
					} else {
						if ("false".equals(checkLoginName("", user.getLoginName()))) {
							messageStr += "第" + (i + 3) + "行登录名已存在。";
						}
					}
					
					if (null == officName || "".equals(officName.trim())) {
						messageStr += "第" + (i + 3) + "行机构名称不能为空。";
					} 
					if (null == officeCode
							|| "".equals(officeCode.trim())) {
						messageStr += "第" + (i + 3) + "行机构编码不能为空。";
					} else {
						if (null == mapOfficeType.get(officeCode)) {
							messageStr += "第" + (i + 3) + "行机构编码不存在。";
						}
					}
				}
				if(null==messageStr || "".equals(messageStr)){//验证通过开始导入数据
					for (int j = 0; j < list.size(); j++) {
						String userId = IdGen.uuid();
						User insertU = list.get(j);
						insertU.setId(userId);
						insertU.setIsNewRecord(true);
						insertU.setOfficeId( mapOfficeType.get(insertU.getOfficeCode()));
						insertU.setPassword(SystemService.entryptPassword("123456"));
						systemService.save(insertU);
						//默认添加学员角色
						systemService.insertRole(insertU.getId());
						successNum++;
						
					}
					addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
				}else{
					addMessage(redirectAttributes,messageStr);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/showListUser?repage";
    }
	/**
	 * 导入活动参与人员
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user")
    @RequestMapping(value = "importCanYuUser", method=RequestMethod.POST)
	@ResponseBody
    public String importCanYuUser(MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String lessionTimeId = request.getParameter("lessionTimeId");
		String activeId = request.getParameter("activityId");
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		String messageStr = "";
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			if (!HeadCst.headValid(ei,User.class)) {
				addMessage(redirectAttributes, "您的导入模板不正确，请使用用户管理导出数据模板。");
			} else {
				List<User> list = ei.getDataList(User.class);
				// 开始验证数据
				
				Map<String, String> mapOfficeType = officeService.initOfficeType();
				for (int i = 0; i < list.size(); i++){
					User user = list.get(i);
					String userName = user.getName();
					String userLoginName = user.getLoginName();
					String officeCode = user.getOfficeCode();
					String officName = user.getOfficeName();
		
					if (null == userName || "".equals(userName.trim())) {
						messageStr += "第" + (i + 3) + "用户名不能为空。";
					} 

					if (null == userLoginName
							|| "".equals(userLoginName.trim())) {
						messageStr += "第" + (i + 3) + "行登录名不能为空。";
					} else {
						if ("true".equals(checkLoginName("", user.getLoginName()))) {
							messageStr += "第" + (i + 3) + "行登录名不存在。";
						}
					}
					
					if (null == officName || "".equals(officName.trim())) {
						messageStr += "第" + (i + 3) + "行机构名称不能为空。";
					} 
					if (null == officeCode
							|| "".equals(officeCode.trim())) {
						messageStr += "第" + (i + 3) + "行机构编码不能为空。";
					} else {
						if (null == mapOfficeType.get(officeCode)) {
							messageStr += "第" + (i + 3) + "行机构编码不存在。";
						}
					}
				}
				if(null==messageStr || "".equals(messageStr)){//验证通过开始导入数据
					for (int j = 0; j < list.size(); j++) {
						User user = list.get(j);
						
						String userId = systemService.getUserByLoginName( user.getLoginName()).getId();
						User userExits = systemService.getUser(userId);
						
						if(null!=userExits && !UserUtils.USER_TEACHER_ENNAME.equals(userExits.getRole().getEnname())){
							UserActivity userAc = new UserActivity();
							userAc.setIsNewRecord(true);
							
							userAc.setUserId(userId);
							userAc.setActivityId(activeId);
							//userAc.setType("1");
							UserActivity uc = userActivityService.getIsBtExists(userAc);
							if(null==uc){//不存在的时候导入
								userAc.setId(IdGen.uuid());
								userActivityService.save(userAc);
								//添加成绩表
								LessionImportUser lesionUser = new LessionImportUser();
								lesionUser.setId(IdGen.uuid());
								lesionUser.setIsNewRecord(true);
								lesionUser.setActiveId(activeId);
								lesionUser.setUserId(userId);
								userActivityService.deleteActiveUser(lesionUser);
								userActivityService.saveActiveUser(lesionUser);
								successNum++;
							}else{
								failureNum++;
							}
						}else{
							failureNum++;
						}
					
					}
					if (failureNum>0){
						failureMsg.insert(0, "，失败 "+failureNum+" 条学员记录。");
					}
					messageStr = "已成功导入 "+successNum+" 条用户"+failureMsg;
				}
			}
		} catch (Exception e) {
			messageStr = "导入用户失败！失败信息："+e.getMessage();
		}
		return messageStr;
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<User> list = Lists.newArrayList(); list.add(UserUtils.getUser());
    		new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:user:add","sys:user:edit"},logical=Logical.OR)
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}
	/**
	 * 验证身份证号码是否已经存在
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:user:add","sys:user:edit"},logical=Logical.OR)
	@RequestMapping(value = "checkUserIdNum")
	public String checkUserIdNum(String idNum,String oldIdNum) {
		if (idNum !=null && idNum.equals(oldIdNum)) {
			return "true";
		}else if (idNum !=null && !"".equals(idNum.trim())) {
			User u = new User();
			u.setIdNum(idNum);
			List<User> listUser = userDao.findListByUser(u);
			if(null==listUser || listUser.isEmpty()){
				return "true";
			}
		} 
		return "false";
	}
	/**
	 * 验证邮箱是否已经存在
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:user:add","sys:user:edit"},logical=Logical.OR)
	@RequestMapping(value = "checkUserEmail")
	public String checkUserEmail(String email,String oldEmail) {
		if (email !=null && email.equals(oldEmail)) {
			return "true";
		}else if (email !=null && !"".equals(email.trim())) {
			User u = new User();
			u.setEmail(email);
			List<User> listUser = userDao.findListByUser(u);
			if(null==listUser || listUser.isEmpty()){
				return "true";
			}
		} 
		return "false";
	}
	/**
	 * 验证手机号码是否已经存在
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:user:add","sys:user:edit"},logical=Logical.OR)
	@RequestMapping(value = "checkUserPhone")
	public String checkUserPhone(String phone,String oldPhone){
		if (phone !=null && phone.equals(oldPhone)) {
			return "true";
		}else if (phone !=null && !"".equals(phone.trim())) {
			User u = new User();
			u.setPhone(phone);
			List<User> listUser = userDao.findListByUser(u);
			if(null==listUser || listUser.isEmpty()){
				return "true";
			}
		} 
		return "false";
	}
	
	/**
	 * 用户信息显示
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfo";
	}
	
	/**
	 * 用户信息显示编辑保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "infoEdit")
	public String infoEdit(User user, boolean __ajax, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			if(user.getName() !=null )
				currentUser.setName(user.getName());
			if(user.getEmail() !=null )
				currentUser.setEmail(user.getEmail());
			if(user.getPhone() !=null )
				currentUser.setPhone(user.getPhone());
			if(user.getMobile() !=null )
				currentUser.setMobile(user.getMobile());
			if(user.getRemarks() !=null )
				currentUser.setRemarks(user.getRemarks());
//			if(user.getPhoto() !=null )
//				currentUser.setPhoto(user.getPhoto());
			systemService.updateUserInfo(currentUser);
			if(__ajax){//手机访问
				AjaxJson j = new AjaxJson();
				j.setSuccess(true);
				j.setMsg("修改个人资料成功!");
				return renderString(response, j);
			}
			model.addAttribute("user", currentUser);
			model.addAttribute("Global", new Global());
			model.addAttribute("message", "保存用户信息成功");
			return "modules/sys/userInfo";
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfoEdit";
	}

	
	/**
	 * 用户头像显示编辑保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "imageEdit")
	public String imageEdit(User user, boolean __ajax, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			if(user.getPhoto() !=null )
				currentUser.setPhoto(user.getPhoto());
			systemService.updateUserInfo(currentUser);
			if(__ajax){//手机访问
				AjaxJson j = new AjaxJson();
				j.setSuccess(true);
				j.setMsg("修改个人头像成功!");
				return renderString(response, j);
			}
			model.addAttribute("message", "保存用户信息成功");
			return "modules/sys/userInfo";
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userImageEdit";
	}
	/**
	 * 用户头像显示编辑保存
	 * @param user
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "imageUpload")
	public String imageUpload( HttpServletRequest request, HttpServletResponse response,MultipartFile file) throws IllegalStateException, IOException {
		User currentUser = UserUtils.getUser();
		
		// 判断文件是否为空  
        if (!file.isEmpty()) {  
                // 文件保存路径  
            	String realPath = Global.USERFILES_BASE_URL
        		+ UserUtils.getPrincipal() + "/images/" ;
                // 转存文件  
            	FileUtils.createDirectory(Global.getUserfilesBaseDir()+realPath);
            	file.transferTo(new File( Global.getUserfilesBaseDir() +realPath +  file.getOriginalFilename()));  
            	currentUser.setPhoto(request.getContextPath()+realPath + file.getOriginalFilename());
    			systemService.updateUserInfo(currentUser);
        }  

		return "modules/sys/userImageEdit";
	}

	/**
	 * 返回用户信息
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public AjaxJson infoData() {
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
		j.setErrorCode("-1");
		j.setMsg("获取个人信息成功!");
		j.put("data", UserUtils.getUser());
		return j;
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
    
	/**
	 * web端ajax验证用户名是否可用
	 * @param loginName
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "validateLoginName")
	public boolean validateLoginName(String loginName, HttpServletResponse response) {
		
	    User user =  userDao.findUniqueByProperty("login_name", loginName);
	    if(user == null){
	    	return true;
	    }else{
		    return false;
	    }
	    
	}
	
	/**
	 * web端ajax验证手机号是否可以注册（数据库中不存在）
	 */
	@ResponseBody
	@RequestMapping(value = "validateMobile")
	public boolean validateMobile(String mobile, HttpServletResponse response, Model model) {
		  User user =  userDao.findUniqueByProperty("mobile", mobile);
		    if(user == null){
		    	return true;
		    }else{
			    return false;
		    }
	}
	
	/**
	 * web端ajax验证手机号是否已经注册（数据库中已存在）
	 */
	@ResponseBody
	@RequestMapping(value = "validateMobileExist")
	public boolean validateMobileExist(String mobile, HttpServletResponse response, Model model) {
		  User user =  userDao.findUniqueByProperty("mobile", mobile);
		    if(user != null){
		    	return true;
		    }else{
			    return false;
		    }
	}
	
	@ResponseBody
	@RequestMapping(value = "resetPassword")
	public AjaxJson resetPassword(String mobile, HttpServletResponse response, Model model) {
		SystemConfig config = systemConfigService.get("1");//获取短信配置的用户名和密码
		AjaxJson j = new AjaxJson();
		if(userDao.findUniqueByProperty("mobile", mobile) == null){
			j.setSuccess(false);
			j.setMsg("手机号不存在!");
			j.setErrorCode("1");
			return j;
		}
		User user =  userDao.findUniqueByProperty("mobile", mobile);
		String newPassword = String.valueOf((int) (Math.random() * 900000 + 100000));
		try {
			String result = UserUtils.sendPass(config.getSmsName(), config.getSmsPassword(), mobile, newPassword);
			if (!result.equals("100")) {
				j.setSuccess(false);
				j.setErrorCode("2");
				j.setMsg("短信发送失败，密码重置失败，错误代码："+result+"，请联系管理员。");
			}else{
				j.setSuccess(true);
				j.setErrorCode("-1");
				j.setMsg("短信发送成功，密码重置成功!");
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
			}
		} catch (IOException e) {
			j.setSuccess(false);
			j.setErrorCode("3");
			j.setMsg("因未知原因导致短信发送失败，请联系管理员。");
		}
		return j;
	}
	@ResponseBody
	@RequestMapping(value = "findMenuDaiSh")
	public String findMenuDaiSh() {
		List<Map> msgLs =  userDao.findMenuDaiSh();
		if(null != msgLs && !msgLs.isEmpty()){
			return JSONArray.toJSONString(msgLs);
		}else{
			return "[]";
		}
	}
 
	
//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}
}
