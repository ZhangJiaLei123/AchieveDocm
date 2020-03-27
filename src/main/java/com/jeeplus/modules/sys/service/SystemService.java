/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.security.Digests;
import com.jeeplus.common.security.shiro.session.SessionDAO;
import com.jeeplus.common.service.BaseService;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.Encodes;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.resource.dao.MesanDirDao;
import com.jeeplus.modules.resource.dao.ResourceDirDao;
import com.jeeplus.modules.resource.entity.MesanDir;
import com.jeeplus.modules.resource.entity.ResourceDir;
import com.jeeplus.modules.sys.dao.MenuDao;
import com.jeeplus.modules.sys.dao.RoleDao;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.entity.SelectUser;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.entity.UserPost;
import com.jeeplus.modules.sys.security.SystemAuthorizingRealm;
import com.jeeplus.modules.sys.utils.LogUtils;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author jeeplus
 * @version 2013-12-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService implements InitializingBean {
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private ResourceDirDao resourceDirDao;
	
	@Autowired
	private MesanDirDao mesanDirDao;
	
	@Autowired
	private SessionDAO sessionDao;
	
	@Autowired
	private UserPostService userPostService;
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	
	public SessionDAO getSessionDao() {
		return sessionDao;
	}


	//-- User Service --//
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		return UserUtils.get(id);
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}
	
	public Page<User> findUser(Page<User> page, User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		BaseService.dataScopeFileForUserList(user);
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findList(user));
		return page;
	}
	
	public Page<SelectUser> findSelectUser(Page<SelectUser> page, SelectUser user){
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findSelectUser(user));
		return page;
	}
	/**
	 * 必学人员信息查询
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月30日 下午4:07:15
	  * @param page
	  * @param user
	  * @return
	 */
	public Page<SelectUser> findListForOverOneThousandBx(Page<SelectUser> page, SelectUser user){
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findListForOverOneThousandBx(user));
		return page;
	}
	/****
	 * 选学
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月30日 下午4:07:07
	  * @param page
	  * @param user
	  * @return
	 */
	public Page<SelectUser> findListForOverOneThousandXx(Page<SelectUser> page, SelectUser user){
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findListForOverOneThousandXx(user));
		return page;
	}
	
	public Page<User> findAddStudyUserList(Page<User> page, User user){
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findAddStudyUserList(user));
		return page;
	}
	public Page<User> findBmStudyUserListForJgSystem(Page<User> page, User user){
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findBmStudyUserListForJgSystem(user));
		return page;
	}
	
	public Page<User> findAddCourseUserList(Page<User> page, User user){
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findAddCourseUserList(user));
		return page;
	}
	
	public Page<User> findAddCourseUserListForJgSystem(Page<User> page, User user){
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findAddCourseUserListForJgSystem(user));
		return page;
	}
	/**
	 * 无分页查询人员列表
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user){
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		List<User> list = userDao.findList(user);
		return list;
	}

	/**
	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUserByOfficeId(String officeId) {
		List<User> list = (List<User>)CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
		if (list == null){
			User user = new User();
			user.setOffice(new Office(officeId));
			list = userDao.findUserByOfficeId(user);
			CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
		}
		return list;
	}
	
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		String userId = user.getId();
		if(null != userId && !"".equals(userId)){
			userPostService.deleteByUserId(userId);
		}
		if (StringUtils.isBlank(user.getId())){
			user.preInsert();
			userDao.insert(user);
		}else{
			/*// 清除原用户机构用户缓存
			
			if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null){
				CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + oldUser.getOffice().getId());
			}*/
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
		}
		String postInfoStr = user.getPostInfoStr();
		if(null!=postInfoStr && !"".equals(postInfoStr)){
			JSONArray jsonArr = JSONArray.parseArray(postInfoStr);
			for (int i = 0; i < jsonArr.size(); i++) {
				UserPost userPost = JSONObject.parseObject(jsonArr.getString(i),UserPost.class);
				if(i==0){
					userPost.setIsDefault("1");
				}else{
					userPost.setIsDefault("0");
				}
				userPost.setUserId(user.getId());
				userPost.setId(IdGen.uuid());
				userPost.setIsNewRecord(true);
				userPost.setOrderNum(i+1);
				userPostService.save(userPost);
			}
		}
		
		/*if (StringUtils.isNotBlank(user.getId())){
			// 更新用户与角色关联
			userDao.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0){
				userDao.insertUserRole(user);
			}else{
				throw new ServiceException(user.getLoginName() + "没有设置角色！");
			}
			// 清除用户缓存
			UserUtils.clearCache(user);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
		}*/
	}

	@Transactional(readOnly = false)
	public void save(User user) {
		userDao.insert(user);
	}
	@Transactional(readOnly = false)
	public void updateUserInfo(User user) {
		user.preUpdate();
		userDao.updateUserInfo(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		userDao.deleteByLogic(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPassword(entryptPassword(newPassword));
		userDao.updatePasswordById(user);
		// 清除用户缓存
		user.setLoginName(loginName);
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void updateUserLoginInfo(User user) {
		// 保存上次登录信息
		user.setOldLoginIp(user.getLoginIp());
		user.setOldLoginDate(user.getLoginDate());
		// 更新本次登录信息
		user.setLoginIp(UserUtils.getSession().getHost());
		user.setLoginDate(new Date());
		userDao.updateLoginInfo(user);
	}
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 获得活动会话
	 * @return
	 */
	public Collection<Session> getActiveSessions(){
		return sessionDao.getActiveSessions(false);
	}
	
	//-- Role Service --//
	
	public Role getRole(String id) {
		return roleDao.get(id);
	}

	public Role getRoleByName(String name) {
		Role r = new Role();
		r.setName(name);
		return roleDao.getByName(r);
	}
	
	public Role getRoleByEnname(String enname) {
		Role r = new Role();
		r.setEnname(enname);
		return roleDao.getByEnname(r);
	}
	
	public List<Role> findRole(Role role){
		return roleDao.findList(role);
	}
	
	public List<Role> findAllRole(){
		return UserUtils.getRoleList();
	}
	
	@Transactional(readOnly = false)
	public void saveRole(Role role) {
		if (StringUtils.isBlank(role.getId())){
			role.preInsert();
			roleDao.insert(role);
		}else{
			role.preUpdate();
			roleDao.update(role);
		}
		// 更新角色与菜单关联
		roleDao.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0){
			roleDao.insertRoleMenu(role);
		}
		// 更新角色与部门关联
		roleDao.deleteRoleOffice(role);
		if (role.getOfficeList().size() > 0){
			roleDao.insertRoleOffice(role);
		}
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void deleteRole(Role role) {
		roleDao.delete(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, User user) {
		List<Role> roles = user.getRoleList();
		for (Role e : roles){
			if (e.getId().equals(role.getId())){
				roles.remove(e);
				saveUser(user);
				return true;
			}
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, User user) {
		if (user == null){
			return null;
		}
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}

	//-- Menu Service --//
	
	public Menu getMenu(String id) {
		return menuDao.get(id);
	}
	
	public ResourceDir getResourceDir(String id) {
		return resourceDirDao.get(id);
	}

	public List<Menu> findAllMenu(){
		return UserUtils.getMenuList();
	}
	
	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) {
		
		// 获取父节点实体
		menu.setParent(this.getMenu(menu.getParent().getId()));
		
		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds(); 
		
		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds()+menu.getParent().getId()+",");

		// 保存或更新实体
		if (StringUtils.isBlank(menu.getId())){
			menu.preInsert();
			menuDao.insert(menu);
		}else{
			menu.preUpdate();
			menuDao.update(menu);
		}
		
		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%,"+menu.getId()+",%");
		List<Menu> list = menuDao.findByParentIdsLike(m);
		for (Menu e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			menuDao.updateParentIds(e);
		}
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}
	@Transactional(readOnly = false)
	public void saveResourceDir(ResourceDir resourceDir) {
		
		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = resourceDir.getParentIds(); 
		if(null != resourceDir.getParent().getParentIds() && null != resourceDir.getParent().getId()){		
			// 获取父节点实体
			resourceDir.setParent(this.getResourceDir(resourceDir.getParent().getId()));
			// 设置新的父节点串
			resourceDir.setParentIds(resourceDir.getParent().getParentIds()+resourceDir.getParent().getId()+",");
		}
		// 保存或更新实体
		if (StringUtils.isBlank(resourceDir.getId())){
			resourceDir.preInsert();
			resourceDirDao.insert(resourceDir);
		}else{
			resourceDir.preUpdate();
			resourceDirDao.update(resourceDir);
		}
		
		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%,"+resourceDir.getId()+",%");
		List<Menu> list = menuDao.findByParentIdsLike(m);
		for (Menu e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, resourceDir.getParentIds()));
			resourceDirDao.updateParentIds(resourceDir);
		}
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void updateMenuSort(Menu menu) {
		menuDao.updateSort(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void deleteMenu(Menu menu) {
		menuDao.delete(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}
	
	/**
	 * 获取Key加载信息
	 */
	public static boolean printKeyLoadMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用 "+Global.getConfig("productName")+"  - Powered By http://www.nxpt.com\r\n");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
		return true;
	}

	public Page<User> findSelUser(Page<User> page, User user) {
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findSelUser(user));
		return page;
	}
	
	public Page<User> findJsUser(Page<User> page, User user) {
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findJsUser(user));
		return page;
	}
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Transactional(readOnly = false)
	public void insertRole(String userId){
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userId", userId);
			jsonObject.put("roleId", "1208ec943d434a85a44f7977fd9381cb");
			userDao.insertRole(jsonObject);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/***
	 * 根据组织和岗位信息，查询对应的必学人员信息
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年5月6日 上午11:31:08
	  * @param map
	  * @return
	 */
	public List<SelectUser> findListForOfficeAndPost(SelectUser selectUser){
		// 执行分页查询
		return userDao.findListForOfficeAndPost(selectUser);
	}
	
	public Page<SelectUser> findListForOfficeAndPostBxForUpdate(Page<SelectUser> page, SelectUser user) {
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findListForOfficeAndPost(user));
		return page;
	}
	/***
	 * 根据登录名获取用户
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年5月10日 上午3:04:50
	  * @param user
	  * @return
	 */
	public User getUserByLoginName(User user){
		return userDao.getByLoginName(user);
	}
	/***
	 * 根据组织和岗位信息，查询对应的必学人员信息
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年5月6日 上午11:31:08
	  * @param map
	  * @return
	 */
	public List<SelectUser> findListForOfficeAndPostCourse(SelectUser selectUser){
		// 执行分页查询
		return userDao.findListForOfficeAndPostCourse(selectUser);
	}
	
	
	@Transactional(readOnly = false)
	public void saveResourceDir(MesanDir mesanDir) {
		
		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = mesanDir.getParentIds(); 
		if(null != mesanDir.getParent().getParentIds() && null != mesanDir.getParent().getId()){		
			// 获取父节点实体
			mesanDir.setParent(this.getMesanDir(mesanDir.getParent().getId()));
			// 设置新的父节点串
			mesanDir.setParentIds(mesanDir.getParent().getParentIds()+mesanDir.getParent().getId()+",");
		}
		// 保存或更新实体
		if (StringUtils.isBlank(mesanDir.getId())){
			mesanDir.preInsert();
			mesanDirDao.insert(mesanDir);
		}else{
			mesanDir.preUpdate();
			mesanDirDao.update(mesanDir);
		}
		
		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%,"+mesanDir.getId()+",%");
		List<Menu> list = menuDao.findByParentIdsLike(m);
		for (Menu e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, mesanDir.getParentIds()));
			mesanDirDao.updateParentIds(mesanDir);
		}
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}
	public MesanDir getMesanDir(String id) {
		return mesanDirDao.get(id);
	}
}
