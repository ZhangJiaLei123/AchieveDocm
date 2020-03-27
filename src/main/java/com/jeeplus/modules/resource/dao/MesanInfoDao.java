/**
 * Copyright &copy; 2015-2020 <a href="http://www.nxpt.com/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.resource.dao;

import java.util.List;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.resource.entity.MesanInfo;
import com.jeeplus.modules.sys.entity.User;

/**
 * 资料信息DAO接口
 * @author panjp
 * @version 2017-03-20
 */
@MyBatisDao
public interface MesanInfoDao extends CrudDao<MesanInfo> {
	
	public List<MesanInfo> findMyMesanInfoPage(MesanInfo mesanInfo);
	
	public List<MesanInfo> findSelMesanInfoListPage(MesanInfo mesanInfo);
	
	public List<User> findChanYuQingKuangUser(User user);
	
	public void setViewTop(MesanInfo mesanInfo);
	
	public List<MesanInfo> statisMesanList(MesanInfo mesanInfo);
	
	public List<MesanInfo> mesanStudyTime(MesanInfo mesanInfo);

	public MesanInfo getByFileName(MesanInfo mesanInfo);
	
}