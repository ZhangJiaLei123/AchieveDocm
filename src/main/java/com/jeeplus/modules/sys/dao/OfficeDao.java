/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.jeeplus.common.persistence.TreeDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author jeeplus
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	public Office getByCode(String code);
	public List<Map> selDictList(String dicType);
	public int  findUserCountByOfficeId(String officeId);
	
	public List<Office>findList(Office office);
	/**
	 * 查询大于1000的数据
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年4月28日 下午4:42:17
	  * @param office
	  * @return
	 */
	public List<Office>findListForOverOneThousand(Office office);
	/***
	 * 查询选学的部门信息
	  * Company:     jcby
	  * @version:    1.0   
	  * @since:  JDK 1.7.0_76
	  * user  ygq
	  * Create at:  2017年5月7日 上午6:20:15
	  * @param office
	  * @return
	 */
	public List<Office> findOfficeListForXx(Office office);
	
	public List<Office> showshCourseOfficeCourseList(Office office);
	
}
