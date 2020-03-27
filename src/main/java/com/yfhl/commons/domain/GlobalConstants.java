package com.yfhl.commons.domain;

/**
 * @Author  jiayangli
 * @Date 创建时间：2017年2月16日 下午1:43:15 
 * @Description:全局常量
 */
public class GlobalConstants {
	public static String FLAG_ENABLED = "1";
	public static String FLAG_DISABLED = "0";
	
	public static String FLAG_EDITABLE = "1";
	public static String FLAG_NOT_EDITABLE = "0";
	
	public static String FLAG_DELETED = "1";
	public static String FLAG_NOT_DELETED = "0";
	
	/** 邮箱激活标识。0：否；1：是 */
	public static String FLAG_DEFAULT_EMAIL = "0";
	public static String FLAG_ACTION_EMAIL = "1";
	
	/** 手机激活标识。0：否；1：是 */
	public static String FLAG_DEFAULT_MOBILE = "0";
	public static String FLAG_ACTION_MOBILE = "1";
	
	public static String FLAG_TEMP = "1";
	public static String FLAG_NOT_TEMP = "0";
	
	public static String FLAG_LOCKED = "1";
	public static String FLAG_NOT_LOCKED = "0";

	public static String FLAG_SUB_ADMIN = "2";
	public static String FLAG_SUPER_ADMIN = "1";
	public static String FLAG_NOT_ADMIN = "0";
	
	public static String FLAG_REJECTED = "1";
	public static String FLAG_NOT_REJECTED = "0";

	public static String FLAG_NONE = "NONE";
	public static String FLAG_ID_DEFAULT = "0";
	
	public static String FLAG_SUCCESS = "SUCCESS";
	public static String FLAG_FAIL = "FAIL";

	/** 流程起始状态为0 */
	public static String FLAG_STATUS_INIT = "0";
	/** 流程完成状态为99 */
	public static String FLAG_STATUS_FINISHED = "99";
	/** 访问来源分类 */
	public static String DEFAULT_ACCESS_FROM = "PC";
	/** 内建序列号（保留） */
	public static int DEFAULT_INNER_SERIAL = 0;
	/** 默认组织级别 */
	public static String DEFAULT_ORG_LEVEL = "0";
	/** 默认字典项父节点ID */
	public static String DEFAULT_DICTITEM_PARENTID = "0";
	/** 默认组织父节点ID */
	public static String DEFAULT_ORG_PARENTORGID = "-1";
	/** 默认显示顺序，默认1。 */
	public static int DEFAULT_DISPLAY_ORDER = 1;
	/** 默认是叶子节点 */
	public static String FLAG_ISLEAF = "1";
	/** 默认不是叶子节点 */
	public static String FLAG_NOT_ISLEAF = "0";
	
	/** 默认普通标识 */
	public static String FLAG_DEFAULT_INNER = "0";
	/** 超级管理员内建角色 */
	public static String FLAG_ROLE_ADMIN_INNER = "2";
	
	/** 默认登录失败次数 */
	public static int DEFAULT_LOGIN_FAILURE_COUNT = 0;
	/** 设置无锁定持续时间 */
	public static int NONE_LOCKED_DURATION_TIME = 0;
	

	/**
	 * 是/否
	 */
	public static final Integer YES = 1;
	public static final Integer NO = 0;
	
	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";
}
