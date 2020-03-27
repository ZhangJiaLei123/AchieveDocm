package com.jeeplus.modules.tcourse.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.tcourse.domain.CustomerMember;
import com.yfhl.commons.domain.QueryScope;
import com.yfhl.commons.persistence.BaseDao;


/** 
 * @author  jiayangli
 * @date 创建时间：2017年3月13日
 * @Description: 用户账户dao
 */
@MyBatisDao
public interface ICustomerMemberDao extends BaseDao<CustomerMember, QueryScope<CustomerMember>>{

}
